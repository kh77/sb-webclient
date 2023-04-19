package com.sm.client.service;

import com.sm.api.InvoiceRequest;
import com.sm.api.InvoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceWebClientService {

    private final WebClient invoiceWebClient;

    public InvoiceResponse createInvoice(InvoiceRequest request) {
        return invoiceWebClient.post()
                .uri("/invoice")
                .body(Mono.just(request), InvoiceRequest.class)
                .retrieve()
                .bodyToMono(InvoiceResponse.class).block();
    }

    public Mono<InvoiceResponse> createInvoiceAsync(InvoiceRequest request) {
        return invoiceWebClient.post()
                .uri("/async/invoice")
                .body(Mono.just(request), InvoiceRequest.class)
                .retrieve()
                .bodyToMono(InvoiceResponse.class);
    }

    public InvoiceResponse readInvoice(Long invoiceId) {
        return invoiceWebClient.get()
                .uri("/invoice/{id}", invoiceId)
                .retrieve()
                .bodyToMono(InvoiceResponse.class).block();
    }

    public Mono<InvoiceResponse> readInvoiceAsync(Long invoiceId) {
        return invoiceWebClient.get()
                .uri("/async/invoice/{id}", invoiceId)
                .retrieve()
                .bodyToMono(InvoiceResponse.class);
    }

    public InvoiceResponse updateInvoice(Long invoiceId, InvoiceRequest Invoice) {
        return invoiceWebClient.put()
                .uri("/invoice/{id}", invoiceId)
                .body(Mono.just(Invoice), InvoiceRequest.class)
                .retrieve()
                .bodyToMono(InvoiceResponse.class).block();
    }


    public Mono<InvoiceResponse> updateInvoiceAsync(Long invoiceId, InvoiceRequest Invoice) {
        return invoiceWebClient.put()
                .uri("/async/invoice/{id}", invoiceId)
                .body(Mono.just(Invoice), InvoiceRequest.class)
                .retrieve()
                .bodyToMono(InvoiceResponse.class);
    }

    public void deleteInvoice(Long invoiceId) {
        invoiceWebClient.delete()
                .uri("/invoice/{id}", invoiceId)
                .retrieve()
                .bodyToMono(Void.class).block();
    }

    public Mono<Void> deleteInvoiceAsync(Long invoiceId) {
        return invoiceWebClient.delete()
                .uri("/async/invoice/{id}", invoiceId)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Flux<InvoiceResponse> findAllInvoiceAsync() {
        return invoiceWebClient.get()
                .uri("/async/invoices")
                .retrieve()
                .bodyToFlux(InvoiceResponse.class);
    }

    public List<InvoiceResponse> findAllInvoice() {
        return invoiceWebClient.get()
                .uri("/invoices")
                .retrieve()
                .bodyToFlux(InvoiceResponse.class).collectList()
                .block();
    }
}
