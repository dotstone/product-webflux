package net.devtoon.dos.productapiannotation;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"spring.main.web-application-type=reactive",
            "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration",
            "server.port=0"})
@AutoConfigureJsonTesters
public abstract class BaseContractTest {

    private static Product[] defaultProducts;

    @MockBean
    private ProductRepository repositoryMock;

    @LocalServerPort
    private
    String port;

    @Before
    public void setup() {
        defaultProducts = new Product[] {
                new Product("default-product-id-1", "default-product-1", 5),
                new Product("default-product-id-2", "default-product-2", 3)
        };

        when(repositoryMock.findAll()).thenReturn(Flux.just(defaultProducts));
        when(repositoryMock.findById("default-product-id-1")).thenReturn(Mono.just(defaultProducts[0]));
        when(repositoryMock.delete(defaultProducts[0])).thenReturn(Mono.empty());
        when(repositoryMock.save(Mockito.any())).thenAnswer(invocation -> Mono.just(invocation.getArguments()[0]));

        RestAssured.baseURI = "http://localhost:" + port;
    }
}
