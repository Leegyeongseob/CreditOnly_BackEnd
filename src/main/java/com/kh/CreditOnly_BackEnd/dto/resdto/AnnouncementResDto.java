package com.kh.CreditOnly_BackEnd.dto.resdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementResDto {
    private Long id;
    private String title;
    private String classTitle;
    private String contents;
    private String createdDate;
}
