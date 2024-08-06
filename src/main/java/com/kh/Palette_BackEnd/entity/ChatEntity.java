package com.kh.Palette_BackEnd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "chat_tb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id")
    private Long id;

    private String chatData;

    private LocalDateTime regDate;

    private String sender;

    private String receiver;

    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }

    // chat와 chatRoom간에 순환참조가 일어나는걸 막아주는 JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private ChatRoomEntity chatRoom;
}