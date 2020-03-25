package com.sym;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 自定义 LocaleResolver ，以获取区域对象信息
 *
 * Created by 沈燕明 on 2018/10/20.
 */
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 通过request内的参数判断区域信息
        String localeParam = request.getParameter("lang");
        Locale locale = Locale.getDefault();
        if( !StringUtils.isEmpty( localeParam )){
            if( "zh".equals(localeParam) ){
                // 如果是中文，创建中文的区域对象信息
                locale = new Locale("zh","CN");
            }else if( "en".equals( localeParam ) ){
                // 如果是英文，创建英文的区域对象信息
                locale = new Locale("en","US");
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
