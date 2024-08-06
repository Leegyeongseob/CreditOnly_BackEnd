package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.entity.PlaceEntity;
import com.kh.Palette_BackEnd.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 장소 저장
    public PlaceEntity savePlace(PlaceEntity placeEntity) {
        return placeRepository.save(placeEntity);
    }
}
