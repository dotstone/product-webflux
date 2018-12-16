import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should get a list of products"
    request {
        url '/'
        method GET()
    }
    response {
        status 200
        body(file('products.json')
        )
        headers {
            header 'Content-Type': applicationJsonUtf8()
        }
    }
}
