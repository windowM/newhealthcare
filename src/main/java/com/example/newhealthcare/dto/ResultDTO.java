package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResultDTO {
    private String result;
    private String content;
}
