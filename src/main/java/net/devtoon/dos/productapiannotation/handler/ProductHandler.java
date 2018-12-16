package net.devtoon.dos.productapiannotation.handler;

import net.devtoon.dos.productapiannotation.Product;
import net.devtoon.dos.productapiannotation.ProductEvent;
import net.devtoon.dos.productapiannotation.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ProductHandler {

    private ProductRepository repository;

    @Autowired
    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        Flux<Product> products = repository.findAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> productMono = this.repository.findById(id);

        return productMono.flatMap(product -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(fromObject(product)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        Mono<Product> productMono = request.bodyToMono(Product.class);
        return productMono.flatMap(p -> repository.save(p))
                .flatMap(p -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(fromObject(p)))
                .switchIfEmpty(ServerResponse.status(500).build());
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> existingProduct = this.repository.findById(id);
        Mono<Product> product = request.bodyToMono(Product.class);

        return existingProduct.zipWith(product,(existing, updated) -> new Product(existing.getId(), updated.getName(), updated.getPrice()))
                .doOnNext(updated -> repository.save(updated))
                .flatMap(updated -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromObject(updated)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Product> product = repository.findById(id);
        return product.flatMap(p -> ServerResponse.ok().build(repository.delete(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return ServerResponse.ok().build(repository.deleteAll());
    }

    public Mono<ServerResponse> getProductEvents(ServerRequest request) {
        Flux<ProductEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(val -> new ProductEvent(val, "Product Event"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventFlux, ProductEvent.class);
    }
}
