package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("should find all users")

    request {
        url("/users")
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body([id: 2, name: "username"])
    }
}