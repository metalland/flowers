package io.github.flowers.test_client.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.reactive.function.client.WebClient

@Controller
class FlowersController {

  @Autowired
  private lateinit var webClient: WebClient;

  @GetMapping("/")
  fun getHomePage(
    model: Model,
    @RegisteredOAuth2AuthorizedClient("flowers_app_authorization_code") authorizedClient: OAuth2AuthorizedClient
  ): String {
    model.addAttribute(
      "response",
      webClient
        .get()
        .uri("http://flowers.io:9002/api/flowers")
        .attributes(
          ServletOAuth2AuthorizedClientExchangeFilterFunction
            .oauth2AuthorizedClient(authorizedClient)
        )
        .retrieve()
        .bodyToMono(FlowerResponse::class.java)
        .block()
    )
    return "home";
  }

}

data class FlowerResponse(val flowers: List<Flower>)

data class Flower(val name: String)