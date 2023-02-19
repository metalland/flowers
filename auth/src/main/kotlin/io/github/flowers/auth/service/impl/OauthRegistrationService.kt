package io.github.flowers.auth.service.impl

import io.github.flowers.auth.dto.registration.RegistrationResponse
import io.github.flowers.auth.service.RegistrationService
import org.springframework.stereotype.Service

@Service("OAUTH_REGISTRATION_SERVICE")
class OauthRegistrationService: RegistrationService<OauthRegistrationRequest> {
  override fun register(payload: OauthRegistrationRequest): RegistrationResponse {
    println("Oauth registration")
    return RegistrationResponse()
  }

  override fun getSupportedPayload(): Class<OauthRegistrationRequest> = OauthRegistrationRequest::class.java
}

class OauthRegistrationRequest {

}
