package com.benvcode.banco.intermediario.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuração do WebClient.
 *
 * Esta classe define a configuração necessária para criar e configurar uma instância do
 * WebClient, que será usada para fazer solicitações HTTP.
 */
@Configuration
public class WebClientConfig
{
    @Value("${banco.a.base-url}")
    private String bancoABaseUrl;

    @Value("${banco.b.base-url}")
    private String bancoBBaseUrl;

    @Bean
    public WebClient bancoAWebClient() {
        return WebClient.builder()
                .baseUrl(bancoABaseUrl)
                .build();
    }

    @Bean
    public WebClient bancoBWebClient() {
        return WebClient.builder()
                .baseUrl(bancoBBaseUrl)
                .build();
    }
}