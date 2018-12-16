import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should get product by id"
    request {
        url '/default-product-id-1'
        method GET()
    }
    response {
        status 200
        body(file('product1.json')
        )
        headers {
            header 'Content-Type': applicationJsonUtf8()
        }
    }
}
