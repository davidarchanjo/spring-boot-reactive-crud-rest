package io.davidarchanjo.code.router;

import io.davidarchanjo.code.handler.AppHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AppRouter {

    private static final String BASE_PATH = "/api/functional/apps";

    @Bean
    public RouterFunction<ServerResponse> routes(AppHandler handler) {
        return route(GET(BASE_PATH), handler::all)
            .andRoute(POST(BASE_PATH), handler::create)
            .andRoute(GET(BASE_PATH + "/{id}"), handler::get)
            .andRoute(PUT(BASE_PATH + "/{id}"), handler::update)
            .andRoute(DELETE(BASE_PATH + "/{id}"), handler::delete);
    }

}