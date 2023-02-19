package io.github.flowers.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class GatewayController {

  @GetMapping("/flowers")
  fun getFlowers() = FlowersResponse(listOf(Flower("Rose"), Flower("Canabis")))

}

data class Flower(val name: String)

data class FlowersResponse(val flowers: List<Flower>)