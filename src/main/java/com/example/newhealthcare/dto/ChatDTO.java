package com.example.newhealthcare.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChatDTO {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}