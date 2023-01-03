package com.example.newhealthcare;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {
    //api 통신시간
    private LocalDateTime transactionTime;

    private String result;

    private String content;

    private T data;

    //ok
    public static<T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .result("OK")
                .content("")
                .build();
    }
    //Data ok
    public static<T> Header<T> OK(T data){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .result("OK")
                .content("")
                .data(data)
                .build();
    }

    //error
    public static<T> Header<T> ERROR(String content){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .result("Fail")
                .content(content)
                .build();
    }

}
