package io.github.flowers.auth.service.impl

import io.github.flowers.auth.dto.registration.RegistrationPayload
import io.github.flowers.auth.dto.registration.RegistrationResponse
import io.github.flowers.auth.service.RegistrationService
import org.springframework.stereotype.Service

@Service("EMAIL_PASSWORD_REGISTRATION_SERVICE")
class EmailPasswordRegistrationService: RegistrationService<RegistrationPayload> {
  override fun register(payload: RegistrationPayload): RegistrationResponse {
    println("Email registration")
    return RegistrationResponse()
  }

  override fun getSupportedPayload(): Class<RegistrationPayload> = RegistrationPayload::class.java
}