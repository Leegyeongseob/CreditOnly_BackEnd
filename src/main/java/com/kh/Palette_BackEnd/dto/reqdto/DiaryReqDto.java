package com.kh.Palette_BackEnd.dto.reqdto;

import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryReqDto {
    private String email;
    private LocalDate anniversary;
    private String dateContents;
    private String contents;
    private List<DiaryCheckListEntity.Event> events;
}
