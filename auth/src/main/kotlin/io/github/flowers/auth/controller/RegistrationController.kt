package io.github.flowers.auth.controller

import io.github.flowers.auth.dto.registration.RegistrationPayload
import io.github.flowers.auth.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RegistrationController {

  @Autowired
  private lateinit var registrationServices: List<RegistrationService<*>>

  @GetMapping("/register")
  fun register(model: Model) : String {
    model.addAttribute("registration", RegistrationPayload())
    return "registration";
  }


  @Suppress("UNCHECKED_CAST")
  private inline fun <reified T> pickService(): RegistrationService<T> {
    return registrationServices
      .find { it.getSupportedPayload() == T::class.java } as RegistrationService<T>
  }


}