package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "An_check_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnCheckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "An_check_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", referencedColumnName = "Announcement_id")
    private AnnouncementEntity announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member; // MemberEntity 와의 관계 설정

    private boolean isRead; // 읽음 여부를 나타내는 필드

}
