package com.kh.Palette_BackEnd.dto.reqdto;

import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.repository.DiaryCheckListRepository;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryCheckListReqDto {

    private List<DiaryCheckListEntity.Event> events;

    public DiaryCheckListEntity toEntityEvents(DiaryCheckListRepository diaryCheckListRepository) {
        return DiaryCheckListEntity.builder()
                .events(events)
                .build();
    }
}
