package com.sym.bboss.annotation;


import com.frameworkset.util.StringUtil;
import com.sym.bboss.util.BbossClientUtil;
import com.sym.bboss.util.BbossUtil;
import org.apache.commons.lang3.StringUtils;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.MapRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 处理{@link com.sym.bboss.annotation.BbossMapper}的动态代理类
 */
public class BbossMapperHandler implements InvocationHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(BbossMapperHandler.class);

    /**
     * 动态代理逻辑
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        Class aClass = method.getDeclaringClass();
        if (aClass.isAnnotationPresent(BbossMapper.class)) {
            BbossMapper bbossMapper = (BbossMapper)aClass.getAnnotation(BbossMapper.class);
            String mapperPath = bbossMapper.value();//mapper.xml地址
            String serverName = bbossMapper.serverName();//es集群名
            ClientInterface clientInterface = StringUtils.isBlank(serverName)?
                    BbossClientUtil.getConfigClient(mapperPath):
                    BbossClientUtil.getConfigClient(serverName,mapperPath);
            if (clientInterface == null) {
                LOGGER.error("不能获取到Bboss操作ES的客户端,class={},method={}",aClass.getName(),method.getName());
                return null;
            }
            String methodName = method.getName();// 获取方法名，对应xml里面的一个DSL Properties
            try {
                // 方法参数名称
                Parameter[] parameters = method.getParameters();

                // 处理方法参数值(这里获取不到方法参数名)
                Map<String,Object> paramMap = null;
                if( null != args && args.length > 0){
                    paramMap = new HashMap<>(args.length);
                    int index = 0;
                    for( Object o : args ){
                        paramMap.put(parameters[index].getName(),o);
                        index++;
                    }
                }
                // 执行查询
                String queryApi = StringUtil.isEmpty(bbossMapper.index())?"/_search":bbossMapper.index().concat("/_search");
                MapRestResponse mapRestResponse = clientInterface.search(queryApi, methodName, paramMap);

                // 解析数据并且处理方法返回值
                Class returnType = method.getReturnType();
                if(Collection.class.isAssignableFrom(returnType)){
                    // 如果返回值类型是集合,尝试取出它的泛型类类型
                    Type genericReturnType = method.getGenericReturnType();
                    if( genericReturnType instanceof ParameterizedType ){
                        ParameterizedType parameterizedType = (ParameterizedType)genericReturnType;
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        if( null != actualTypeArguments && actualTypeArguments.length >0 ){
                            return BbossUtil.parseMapRestResponseToCollection(mapRestResponse,returnType,(Class) actualTypeArguments[0],bbossMapper.getMetadata());
                        }
                    }
                    return BbossUtil.parseMapRestResponseToCollection(mapRestResponse,returnType,null,bbossMapper.getMetadata());
                }else{
                    return BbossUtil.parseMapRestResponseToBean(mapRestResponse,returnType,bbossMapper.getMetadata());
                }
            } catch (Exception e) {
                LOGGER.error("查询失败，{}", e.getMessage());
            }
        }
        return null;
    }


    /**
     * 获取接口的代理对象
     *
     * @param t
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapper(Class<T> t) {
        if (t.isInterface()) {
            Class[] cs = {t};
            return (T) Proxy.newProxyInstance(t.getClassLoader(), cs, new BbossMapperHandler());
        }
        return null;
    }

    /**
     * 判断一个Class 是java自身对象还是用户定义的对象
     * @param c
     * @return true-java自身对象,false-用户定义对象
     */
    private static boolean isJavaClass(Class c){
        return null!=c && c.getClassLoader()==null;
    }
}
