package net.devtoon.dos.productapiannotation;

import net.devtoon.dos.productapiannotation.handler.ProductHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner init(ReactiveMongoOperations operations, ProductRepository repository) {
//        return args -> {
//            Flux<Product> productFlux = Flux.just(
//                    new Product(null, "Big Latte", 3),
//                    new Product(null, "Big Decaf", 2),
//                    new Product(null, "Green Tea", 1))
//                    .flatMap(repository::save);
//
//            operations.collectionExists(Product.class)
//                    .flatMap(exists -> exists ? operations.dropCollection(Product.class) : Mono.just(exists))
//                    .then(operations.createCollection(Product.class))
//                    .thenMany(productFlux)
//                    .thenMany(repository.findAll())
//                    .subscribe(System.out::println);
//        };
//    }

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler handler) {
//        return route(GET("/r").and(accept(APPLICATION_JSON_UTF8)), handler::getAllProducts)
//                .andRoute(GET("/r/events").and(accept(TEXT_EVENT_STREAM)), handler::getProductEvents)
//                .andRoute(GET("/r/{id}").and(accept(APPLICATION_JSON_UTF8)), handler::getProduct)
//                .andRoute(POST("/r").and(contentType(APPLICATION_JSON_UTF8)), handler::saveProduct)
//                .andRoute(PUT("/r/{id}").and(contentType(APPLICATION_JSON_UTF8)), handler::updateProduct)
//                .andRoute(DELETE("/r/{id}").and(contentType(APPLICATION_JSON_UTF8)), handler::deleteProduct)
//                .andRoute(DELETE("/r").and(contentType(APPLICATION_JSON_UTF8)), handler::deleteAll);

        return nest(path("/r"),
                route(method(GET), handler::getAllProducts)
                        .andRoute(GET("/events"), handler::getProductEvents)
                        .andRoute(method(POST), handler::saveProduct)
                        .andRoute(method(DELETE), handler::deleteAll)
                        .andNest(path("/{id}"),
                                route(method(GET), handler::getProduct)
                                        .andRoute(method(PUT), handler::updateProduct)
                                        .andRoute(method(DELETE), handler::deleteProduct))


        );
    }
}

