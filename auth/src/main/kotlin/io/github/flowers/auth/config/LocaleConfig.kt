package io.github.flowers.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class LocaleConfig : WebMvcConfigurer {

  @Bean
  fun localeResolver(): LocaleResolver? {
    val slr = SessionLocaleResolver()
    slr.setDefaultLocale(Locale.US)
    return slr
  }

  @Bean
  fun localeChangeInterceptor(): LocaleChangeInterceptor? {
    val lci = LocaleChangeInterceptor()
    lci.paramName = "lang"
    return lci
  }

  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(localeChangeInterceptor()!!)
  }

/*  @Bean
  fun messageSource(): ReloadableResourceBundleMessageSource? {
    val messageSource = ReloadableResourceBundleMessageSource()
    messageSource.setBasename("classpath:messages")
    messageSource.setCacheSeconds(3600)
    messageSource.setDefaultEncoding("UTF-8") // Add this
    return messageSource
  }*/


}