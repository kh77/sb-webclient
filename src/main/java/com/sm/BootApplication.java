package com.sm;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public static void setupWireMockStubs() {
        WireMockServer wireMockServer = new WireMockServer(9090);
        WireMock.configureFor("localhost", 9090);
        wireMockServer.start();


        // Stub for createInvoice
        stubFor(post(urlEqualTo("/invoice"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"amount\": \"30\",\"status\": \"created\"}")));

        // Stub for createInvoiceAsync
        stubFor(post(urlEqualTo("/async/invoice")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"id\": 1,  \"amount\": \"30\", \"status\": \"created\"}")));

        // Stub for readInvoice
        stubFor(get(urlMatching("/invoice/[0-9]+")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"id\": 1, \"amount\": \"30\",\"status\": \"paid\"}")));

        // Stub for readInvoiceAsync
        stubFor(get(urlMatching("/async/invoice/[0-9]+")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"id\": 1, \"amount\": \"30\",\"status\": \"paid\"}")));

        // Stub for updateInvoice
        stubFor(put(urlMatching("/invoice/[0-9]+")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"id\": 1, \"amount\": \"30\",\"status\": \"updated\"}")));

        // Stub for updateInvoiceAsync
        stubFor(put(urlMatching("/async/invoice/[0-9]+")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"id\": 1, \"amount\": \"30\",\"status\": \"updated\"}")));

        // Stub for deleteInvoice
        stubFor(delete(urlMatching("/invoice/[0-9]+")).willReturn(aResponse().withStatus(200)));

        // Stub for deleteInvoiceAsync
        stubFor(delete(urlMatching("/async/invoice/[0-9]+")).willReturn(aResponse().withStatus(200)));

        // Stub for findAllInvoiceAsync
        stubFor(get(urlEqualTo("/async/invoices")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("[{\"id\": 1, \"amount\": \"30\",\"status\": \"paid\"}, {\"id\": 2, \"amount\": \"40\",\"status\": \"pending\"}]")));

        // Stub for findAllInvoice
        stubFor(get(urlEqualTo("/invoices")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("[{\"id\": 1,\"amount\": \"30\", \"status\": \"paid\"}, {\"id\": 2,\"amount\": \"50\", \"status\": \"pending\"}]")));
    }


}