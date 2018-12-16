import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should create product"
    request {
        url '/'
        method POST()
        headers {
            header 'Content-Type': applicationJsonUtf8()
        }
        body(file('product3.json'))
    }
    response {
        status 201
        body(file('product3.json'))
        headers {
            header 'Content-Type': applicationJsonUtf8()
        }
    }
}
