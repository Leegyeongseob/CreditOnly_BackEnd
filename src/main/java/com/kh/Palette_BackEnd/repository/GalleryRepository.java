package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.entity.GalleryListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GalleryRepository extends JpaRepository<GalleryEntity,Long> {

    List<GalleryEntity> findByCouple(CoupleEntity couple);
}
