package net.devtoon.dos.productapiannotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}

