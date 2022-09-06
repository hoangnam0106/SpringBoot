package com.tutorial.springboot.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private boolean success;
    private String errorCode;
    private String message;
    private Object result;
}
