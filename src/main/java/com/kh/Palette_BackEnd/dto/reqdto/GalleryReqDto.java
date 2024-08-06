package com.kh.Palette_BackEnd.dto.reqdto;

import com.kh.Palette_BackEnd.entity.GalleryListEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GalleryReqDto {
    private String email;
    private String urls;
}