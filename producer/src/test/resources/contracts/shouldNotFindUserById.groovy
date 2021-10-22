package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("should not find user by id = 1")

    request {
        url("/users/1")
        method GET()
    }

    response {
        status NOT_FOUND()
    }
}

