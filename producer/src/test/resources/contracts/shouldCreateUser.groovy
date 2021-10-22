package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("should create user")

    request {
        url("/users")
        method POST()
        headers {
            contentType applicationJson()
        }
        body("name": "username")
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
            location()
        }
        body([id: 2, name: "username"])
    }
}