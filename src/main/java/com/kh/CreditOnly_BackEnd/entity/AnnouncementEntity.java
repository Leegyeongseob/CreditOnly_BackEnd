package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Announcement_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Announcement_id")
    private Long id;

    private String email;

    private String classTitle;

    private String title;

    @Lob
    private String contents;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
