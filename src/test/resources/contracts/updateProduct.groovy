import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update product"
    request {
        url '/default-product-id-1'
        method PUT()
        headers {
            header 'Content-Type': applicationJsonUtf8()
        }
        body(file('product1updated.json'))
    }
    response {
        status 200
        body(file('product1updated.json'))
        headers {
            header 'Content-Type': applicationJsonUtf8()
        }
    }
}
