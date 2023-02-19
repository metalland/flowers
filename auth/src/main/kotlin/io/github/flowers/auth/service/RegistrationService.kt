package io.github.flowers.auth.service

import io.github.flowers.auth.dto.registration.RegistrationPayload
import io.github.flowers.auth.dto.registration.RegistrationResponse
import org.springframework.stereotype.Service

@Service
interface RegistrationService<Payload> {

  fun getSupportedPayload() : Class<Payload>

  fun register(payload: Payload) : RegistrationResponse

}