package io.github.flowers.auth.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.config.ClientSettings
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class SecurityConfig {

  @Autowired
  private lateinit var datasource: DataSource


  @Bean
  fun users(): UserDetailsService {
    return JdbcUserDetailsManagerConfigurer()
      .dataSource(datasource)
      .userDetailsService
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(12)


  @Bean
  fun registeredClientRepository() = InMemoryRegisteredClientRepository(
    RegisteredClient
      .withId(UUID.randomUUID().toString())
      .clientId("flowers_app")
      .clientName("Flowers App")
      .clientSecret("\$2y\$12\$VI.Cp6T1tH4cUdHH9nckYuxeTgXfeBno.3WHZKeLmUPInTurs10QW")
      .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
      .redirectUri("http://flowers.io:9001/login/oauth2/code/flowers_app_oidc")
      .redirectUri("http://flowers.io:9001/callback")
      .scope(OidcScopes.OPENID)
      .scope("flowers.user")
      .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
      .build()
  )

  @Bean
  @Order(1)
  fun authServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)
    return http.exceptionHandling {
      it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
    }.build()
  }

  @Bean
  @Order(2)
  fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.authorizeRequests {
      it.antMatchers("/register").permitAll()
      it.anyRequest().authenticated()
    }.formLogin(withDefaults())
    return http.build()
  }

  @Bean
  fun providerSettings(): ProviderSettings {
    return ProviderSettings.builder()
      .issuer("http://auth-flowers.io:9000")
      .build()
  }

  @Bean
  fun jwkSource(): JWKSource<SecurityContext> {
    val rsaKey: RSAKey = generateRsa()
    val jwkSet = JWKSet(rsaKey)
    return ImmutableJWKSet(jwkSet)
  }

  private fun generateRsa(): RSAKey {
    val keyPair: KeyPair = generateRsaKey()
    val publicKey: RSAPublicKey = keyPair.getPublic() as RSAPublicKey
    val privateKey: RSAPrivateKey = keyPair.getPrivate() as RSAPrivateKey
    return RSAKey.Builder(publicKey)
      .privateKey(privateKey)
      .keyID(UUID.randomUUID().toString())
      .build()
  }

  private fun generateRsaKey(): KeyPair {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048)
    return keyPairGenerator.generateKeyPair()
  }

}