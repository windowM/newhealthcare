package com.example.newhealthcare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SensorController {

    @PostMapping("/ecg")
    public ResponseEntity<String> receiveEcgData(@RequestParam("ecg1") int ecg1, @RequestParam("ecg2") int ecg2) {
        // 받은 ECG 데이터 처리 로직
        System.out.println("ECG1: " + ecg1);
        System.out.println("ECG2: " + ecg2);

        // 여기에 스프링 부트 서버에서 처리할 로직을 추가할 수 있습니다.

        return ResponseEntity.ok("ECG data received successfully");
    }

}
