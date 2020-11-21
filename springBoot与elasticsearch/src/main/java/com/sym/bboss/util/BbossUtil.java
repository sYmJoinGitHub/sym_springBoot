package com.sym.bboss.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.frameworkset.elasticsearch.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bboss操作工具类
 *
 * Created by shenym on 2019/8/8 8:25.
 */
public class BbossUtil {

    public final static String TOTAL_KEY = "total";
    public final static String DATA_KEY = "data";
    private static ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger logger = LoggerFactory.getLogger(BbossUtil.class);

    /**
     * 读取bboss查询ES的返回对象 RestResponse
     *
     * @param response
     * @return
     */
    public static Map<String, Object> readRestResponse(RestResponse response) {
        if (null == response || response.getSearchHits() == null) {
            return null;
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        SearchHits searchHits = response.getSearchHits();
        List<SearchHit> hits = searchHits.getHits();
        List<Object> dataList = new ArrayList<Object>(hits.size());
        for (SearchHit hit : hits) {
            dataList.add(hit.getSource());
        }
        retMap.put("total", searchHits.getTotal());
        retMap.put("data", dataList);
        return retMap;
    }


    /**
     * 解析Bboss返回值对象{@link MapRestResponse},将其转换成Map
     * @param response ES数据返回值
     * @param getMetadata 是否处理ES的元数据
     * @return
     */
    public static Map<String,Object> parseToMap(MapRestResponse response,boolean getMetadata){
        if( checkNull(response) ) return null;
        List<MapSearchHit> searchHits = response.getSearchHits().getHits();
        if( searchHits.size()>1 ){
            throw new IllegalArgumentException("期待返回值为1个,但实际上有"+searchHits.size()+"个返回值");
        }
        MapSearchHit mapSearchHit = searchHits.get(0);
        Map<String, Object> retMap = mapSearchHit.getSource();
        if( getMetadata ){
            addEsMetadataToMap(mapSearchHit,retMap);
        }
        return retMap;
    }


    /**
     * 解析Bboss返回值对象{@link MapRestResponse},将其转换成指定的Bean类型
     * 当自定义Bean类型时,需要继承{@link BaseEsMetadata}
     *
     * @param response ES数据返回值
     * @param getMetadata 是否处理ES的元数据
     */
    public static <T> T parseMapRestResponseToBean(MapRestResponse response, Class<T> t, boolean getMetadata){
        Map<String,Object> parseMap = parseToMap(response,getMetadata);
        try {
            String s = objectMapper.writeValueAsString(parseMap);
            return objectMapper.readValue(s, t);
        } catch (IOException e) {
            logger.error("解析JSON字符串失败,原因：{}",e.getMessage());
        }
        return null;
    }


    /**
     * 解析Bboss返回值对象{@link MapRestResponse},将其转换成指定的Bean类型，排除ES元数据，只读取source数据(用户设置的实际数据)
     * @param restResponse ES数据返回值
     * @param t 待转的对象类型
     */
    public static <T> T parseSourceDataToBean(MapRestResponse restResponse,Class<T> t){
        return parseMapRestResponseToBean(restResponse,t,false);
    }


    /**
     * 解析Bboss返回值对象{@link MapRestResponse},将其转换成指定的集合类型
     * @param restResponse ES数据返回值
     * @param collectionType 集合类型
     * @param genericType 集合泛型类型
     * @param getMetadata 是否加入ES元数据
     * @return
     */
    public static <T> T parseMapRestResponseToCollection(MapRestResponse restResponse, Class<T> collectionType,Class genericType,boolean getMetadata){
        if( checkNull(restResponse) )return null;
        if( null == collectionType ) return null;
        List<MapSearchHit> list = restResponse.getSearchHits().getHits();
        //获取集合泛型的类类型
        List<Map<String, Object>> collect = list.stream().map(MapSearchHit::getSource).collect(Collectors.toList());
        if( getMetadata ){
            for( int i=0;i<list.size();i++ ){
                addEsMetadataToMap(list.get(i),collect.get(i));
            }
        }
        try {
            String jsonString = objectMapper.writeValueAsString(collect);
            // 若未指定泛型类型，则直接转换集合
            if( null == genericType ) return objectMapper.readValue(jsonString,collectionType);
            // 否则创建集合与其泛型的JavaType
            JavaType type = objectMapper.getTypeFactory().constructParametricType(collectionType,genericType);
            return objectMapper.readValue(jsonString,type);
        } catch (IOException e) {
            logger.error("解析JSON字符串失败,原因：{}",e.getMessage());
        }
        return null;

    }

    /**
     * 解析Bboss返回值对象{@link MapRestResponse},将其转换成指定的集合类型,只获取只读取source数据(用户设置的实际数据)
     */
    public static <T> T parseSourceDataToCollection(MapRestResponse restResponse, Class<T> t,Class objClass){
        return parseMapRestResponseToCollection(restResponse,t,objClass,false);
    }


    /**
     * 解析Bboss返回值对象{@link MapRestResponse}，并获取查询总量，用于分页，加入ES元数据
     * @param restResponse
     * @param getMetadata
     * @return
     */
    public static Map<String,Object> parseMapRestResponseWithPage(MapRestResponse restResponse,boolean getMetadata){
        if (checkNull( restResponse )) return null;
        MapSearchHits searchHits = restResponse.getSearchHits();
        List<MapSearchHit> hits = searchHits.getHits();
        Map<String,Object> retMap = new HashMap<>();
        List<Map<String, Object>> list = hits.stream().map(hit -> {
            Map<String, Object> source = hit.getSource();
            if (getMetadata) addEsMetadataToMap(hit, source);
            return source;
        }).collect(Collectors.toList());
        retMap.put(TOTAL_KEY,searchHits.getTotal());
        retMap.put(DATA_KEY,list);
        return retMap;
    }


    /**
     * 解析Bboss返回值对象{@link MapRestResponse}，并获取查询总量，用于分页，排除ES元数据
     * @param restResponse
     * @return
     */
    public static Map<String,Object> parseMapRestResponseWithPage(MapRestResponse restResponse){
        return parseMapRestResponseWithPage(restResponse,false);
    }

    /**
     * 判断返回结果是否为0
     * @param baseResponse
     * @return true-查询结果为0,false-查询结果不为0
     */
    private static boolean checkNull(BaseRestResponse baseResponse){
        if( baseResponse == null ) return true;
        if( baseResponse instanceof RestResponse ){
            RestResponse restResponse = (RestResponse)baseResponse;
            return null == restResponse.getSearchHits() || restResponse.getSearchHits().getHits().size() == 0;
        }
        if( baseResponse instanceof MapRestResponse ){
            MapRestResponse mapRestResponse = (MapRestResponse)baseResponse;
            return null == mapRestResponse.getSearchHits() || mapRestResponse.getSearchHits().getHits().size() == 0;
        }
        return true;
    }

    /**
     * 添加ES的元数据
     * @param hit
     * @param map
     */
    private static void addEsMetadataToMap(BaseSearchHit hit,Map<String,Object> map){
        if( null == hit || null == map ) return;
        map.put("_id", hit.getId());
        map.put("_index", hit.getIndex());
        map.put("_type", hit.getType());
        map.put("_version", hit.getVersion());
        map.put("_routing", hit.getRouting());
    }
}
