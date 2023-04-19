package com.sm.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;
    private String amount;
    private String status;
}
