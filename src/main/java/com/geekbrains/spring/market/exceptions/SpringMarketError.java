package com.geekbrains.spring.market.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class SpringMarketError {
    private int status;
    private String message;
    private Date timestamp;

    public SpringMarketError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
