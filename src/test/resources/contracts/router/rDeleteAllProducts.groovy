import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should delete product by id"
    request {
        url '/r/'
        method DELETE()
    }
    response {
        status 200
        body ''
    }
}
