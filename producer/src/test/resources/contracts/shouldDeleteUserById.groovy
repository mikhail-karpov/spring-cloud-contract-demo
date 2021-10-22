package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description("should delete user by id")

    request {
        url("/users/1")
        method DELETE()
    }

    response {
        status NO_CONTENT()
    }
}