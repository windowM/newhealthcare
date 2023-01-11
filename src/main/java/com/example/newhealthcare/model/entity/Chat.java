package com.example.newhealthcare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "chat_seq",
        sequenceName = "chat_seq",
        initialValue = 1,
        allocationSize = 1
)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @Column(name="chat_sequence")
    private Long chatSequence;

    private String sender;

    private String receiver;

    private String msg;

    private String sendDate;

    @ManyToOne
    @JoinColumn(name="room_id")
    private ChatRoom roomId;

}
