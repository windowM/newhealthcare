package com.example.newhealthcare.dto;

import com.example.newhealthcare.entity.DandP;
import com.example.newhealthcare.entity.Sensor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HomeResponseDTO {
    private String born;

    private String name;

    private String email;

    private String result;

    private String content;

    private DandP dandPList;

    private Sensor sensor;

}
