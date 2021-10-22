package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("should find user by id = 2")

    request {
        url("/users/2")
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(id: 2, name: "username")
    }
}

