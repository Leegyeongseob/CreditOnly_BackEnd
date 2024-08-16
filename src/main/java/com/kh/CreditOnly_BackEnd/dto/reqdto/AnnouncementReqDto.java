package com.kh.CreditOnly_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementReqDto {
    private String email;
    private String classTitle;
    private String title;
    private String contents;
    private boolean sendNotification;
}
