import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should delete product by id"
    request {
        url '/r/default-product-id-1'
        method DELETE()
    }
    response {
        status 200
        body ''
//        headers {
//            header 'Content-Type': applicationJsonUtf8()
//        }
    }
}
