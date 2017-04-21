package com.todaywork.image.service.impl;

import com.todaywork.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Image 관련 처리 DAO
 * Created by 권 오빈 on 2016. 6. 8..
 */
@Repository("imageDAO")
public interface ImageDAO extends JpaRepository<Image, Long>{
}
