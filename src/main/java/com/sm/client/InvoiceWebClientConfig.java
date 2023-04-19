package com.sm.client;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.util.UUID;

@Configuration
@Log4j2
public class InvoiceWebClientConfig {

    private static final String LINE_SEPARATOR = "=================================================================";

    @Bean
    public WebClient invoiceWebClient() throws IOException {
        String baseUrl = "http://localhost:9090";

        // Disable SSL. Set the value to InsecureTrustManagerFactory.Instance
        SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        return WebClient.builder().baseUrl(baseUrl).filter(logRequest())
                .filter(logResponse())
                .defaultHeader("x-request-id", UUID.randomUUID().toString())
                .clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.debug(LINE_SEPARATOR);
            log.debug("=========== Outgoing Request ==============");
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            log.debug(LINE_SEPARATOR);
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.debug(LINE_SEPARATOR);
            log.debug("=========== Incoming Response ===========");
            log.debug("Response: {} ", clientResponse.statusCode());
            log.debug(LINE_SEPARATOR);
            return Mono.just(clientResponse);
        });
    }

}
