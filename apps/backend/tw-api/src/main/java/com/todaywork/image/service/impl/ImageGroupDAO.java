package com.todaywork.image.service.impl;

import com.todaywork.domain.ImageGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 권 오빈 on 2016. 6. 9..
 */
@Repository("imageGroupDAO")
public interface ImageGroupDAO extends JpaRepository<ImageGroup, Long> {
}
