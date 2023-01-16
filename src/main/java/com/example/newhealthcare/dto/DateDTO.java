package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DateDTO {

    //1~8 : 9:00, 9:30, 10:00, 10:30, 14:00, 14:30, 15:00, 15:30
    private int resTime;

    //년/월/일
    private String resDate;
}
