package com.example.newhealthcare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="chatroom")
public class ChatRoom {

    @Id
    @Column(name="room_id")
    private String roomId;

    private String name;//방 이름

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "roomId")
    @OrderBy(value = "roomId asc")
    private List<Chat> chatList;

}
