package com.sym.route;

import com.sym.handler.SimpleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author shenym
 * @date 2020/3/25 17:35
 */
@Configuration
public class SimpleRouter {

    @Bean
    public RouterFunction<ServerResponse> simpleRoute(SimpleHandler simpleHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        simpleHandler::helloWorld);
    }

}
