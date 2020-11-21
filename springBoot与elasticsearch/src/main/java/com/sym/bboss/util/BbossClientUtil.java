package com.sym.bboss.util;

import com.frameworkset.util.StringUtil;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.boot.ElasticSearchPropertiesFilePlugin;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bboss客户端工具类
 *
 * Created by shenym on 2019/8/7 16:54.
 */
@Component
public class BbossClientUtil implements InitializingBean {

    private final static byte[] LOCK_OBJECT = new byte[0];

    private final static Logger LOGGER = LoggerFactory.getLogger(BbossClientUtil.class);

    private final static String DEFAULT_KEY_PREFIX = "_default_";

    private static Map<String, ClientInterface> clientMap = new ConcurrentHashMap<>();

    private static String location;

    @Autowired
    private Environment env;

    static{
        // 自定义bboss加载的外部配置
        ElasticSearchPropertiesFilePlugin.init("biz.properties");
    }


    /**
     * 获取bboss es 客户端(rest版，不带配置文件)
     * @return
     */
    public static ClientInterface getRestClient(){
        return getRestClient(DEFAULT_KEY_PREFIX);
    }


    /**
     * 获取bboss es 客户端(rest版，不带配置文件)
     * @param esClusterName 集群名
     * @return
     */
    public static ClientInterface getRestClient(String esClusterName){
        ClientInterface clientInterface =  clientMap.get(esClusterName);
        if( null == clientInterface ){
            synchronized (LOCK_OBJECT){
                if( null == clientMap.get(esClusterName) ){
                    clientInterface = DEFAULT_KEY_PREFIX.equals(esClusterName)?
                            ElasticSearchHelper.getRestClientUtil():
                            ElasticSearchHelper.getRestClientUtil(esClusterName);
                    LOGGER.info("初始化 bboss rest 客户端，集群地址为：{}", DEFAULT_KEY_PREFIX.equals(esClusterName)?"默认集群":esClusterName);
                    clientMap.put(esClusterName,clientInterface);
                }
            }
        }
        return clientInterface;
    }



    /**
     * 获取bboss es 客户端(带有配置文件)
     * @param mapperPath dsl配置文件路径
     * @return
     */
    public static ClientInterface getConfigClient(String mapperPath){
        return BbossClientUtil.getConfigClient(DEFAULT_KEY_PREFIX,mapperPath);
    }

    /**
     * 获取bboss es 客户端（带有配置文件）
     * @param esClusterName es集群名称
     * @param mapperPath dsl配置文件路径
     * @return
     */
    public static ClientInterface getConfigClient(String esClusterName, String mapperPath){
        // 配置文件路径
        String configPath = buildConfigPath(mapperPath);
        // 集群名+配置文件路径，作为唯一标识
        String key = esClusterName+":"+configPath;
        // 尝试获取客户端
        ClientInterface clientInterface = clientMap.get(key);
        if( null == clientInterface ){
            synchronized (LOCK_OBJECT){
                if( null == clientMap.get(key) ){
                    clientInterface = DEFAULT_KEY_PREFIX.equals(esClusterName)?
                            ElasticSearchHelper.getConfigRestClientUtil(configPath):
                            ElasticSearchHelper.getConfigRestClientUtil(esClusterName,configPath);
                    LOGGER.info("初始化 bboss config 客户端，集群地址为：{}，配置文件为：{}", DEFAULT_KEY_PREFIX.equals(esClusterName)?"默认集群":esClusterName,configPath);
                    clientMap.put(key,clientInterface);
                }
            }
        }
        return clientInterface;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        String property = env.getProperty("elasticsearch.config.location");
        location = property == null?"":property;
    }


    /**
     * 构建配置文件的类路径
     * @param mapperPath
     * @return
     */
    private static String buildConfigPath(String mapperPath){
        String baseLocation = location;
        if( !"".equals(baseLocation) && !baseLocation.endsWith("/") ){
            baseLocation += "/";
        }
        if(!StringUtil.isEmpty(mapperPath)){
            if( mapperPath.startsWith("/") ){
                mapperPath = mapperPath.substring(1);
            }
            return baseLocation+mapperPath;
        }
        return baseLocation;
    }

}
