import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description('should send user created message')
    label('user.created.event')
    input {
        triggeredBy('createUser()')
    }
    outputMessage {
        sentTo 'users-exchange'
        headers {
            header('contentType', 'application/json')
            header('amqp_receivedRoutingKey', 'user.created')
        }
        body ([
                userId: 3,
                username: "user"
        ])
    }
}