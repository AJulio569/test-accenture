package co.com.bancolombia.api.rest;

import co.com.bancolombia.api.handler.FranchiseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler franchiseHandler) {
        return route(GET("/api/franchise"), franchiseHandler::getAllFranchise)
                .andRoute(POST("/api/franchise"), franchiseHandler::createFranchise)
                .and(route(GET("/api/franchise/name/{name}"), franchiseHandler::getFranchiseByName))
                .and(route(GET("/api/franchise/id/{id}"), franchiseHandler::getFranchiseById))
                .and(route(POST("/api/franchise/{franchiseId}/branches"), franchiseHandler::addBranchToFranchise))
                .and(route(POST("/api/franchise/{franchiseId}/branches/products"), franchiseHandler::addProductToBranch));
    }
}
