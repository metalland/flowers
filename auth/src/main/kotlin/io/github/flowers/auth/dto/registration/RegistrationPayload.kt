package io.github.flowers.auth.dto.registration

data class RegistrationPayload(
  var firstName: String? = "",
  var lastName: String? = "",
  var email: String? = "",
  var password: String? = "",
  var matchingPassword: String? = ""
)

