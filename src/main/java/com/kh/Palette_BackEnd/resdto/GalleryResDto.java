package com.kh.Palette_BackEnd.resdto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GalleryResDto {

    private String email;
    private List<String> urls;
}
