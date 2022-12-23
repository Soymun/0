package com.example.demo.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private Object body;

    private String error;
}
