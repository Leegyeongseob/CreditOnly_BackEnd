package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Help_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "help_id")
    private Long id;

    private String email;

    private String title;

    @Lob
    private String contents;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
