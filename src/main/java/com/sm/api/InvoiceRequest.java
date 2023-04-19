package com.sm.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceRequest {

    private Long id;
    private String amount;
}
