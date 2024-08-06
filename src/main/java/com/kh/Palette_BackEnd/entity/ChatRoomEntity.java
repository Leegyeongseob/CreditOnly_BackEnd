package com.kh.Palette_BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "Chat_Room")
public class ChatRoomEntity {
    @Id
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "Created_At")
    private LocalDateTime createdAt;
    @Column(name = "first_email")
    private String firstEmail;
    @Column(name = "second_email")
    private String secondEmail;
}
