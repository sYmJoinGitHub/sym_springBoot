package com.sym.bboss.annotation;

import java.lang.annotation.*;

/**
 * 用来对应Bboss的dsl配置文件查询的注解
 * <p>
 * 类似mybatis一样，直接使用接口代理去执行dsl配置文件里面的dsl语句
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target(ElementType.TYPE)
public @interface BbossMapper {

    /**
     * mapper.xml的路径
     * @return
     */
    String value() default "";

    /**
     * ES内的索引名称
     */
    String index() default "";

    /**
     * ES集群名称
     * @return
     */
    String serverName() default "";

    /**
     * 查询结果是否要加上ES元数据
     * @see com.sym.util.BaseEsMetadata
     * @return
     */
    boolean getMetadata() default false;
}
