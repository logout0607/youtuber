package com.todaywork.image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaywork.domain.Image;
import com.todaywork.domain.ImageGroup;
import com.todaywork.dto.ImageDto;
import com.todaywork.dto.ImageGroupDto;
import com.todaywork.image.service.impl.ImageDAO;
import com.todaywork.image.service.impl.ImageGroupDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 이미지 디비 처리 관련 테스트
 * Created by 권 오빈 on 2016. 6. 8..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class ImageDAOTest {

	@Autowired private ImageDAO imageDAO;
	@Autowired private ImageGroupDAO imageGroupDAO;
	@Autowired private ModelMapper modelMapper;
	@Autowired private ObjectMapper objectMapper;

	// 중도 Commit 용
	@PersistenceContext private EntityManager entityManager;

	@Before
	public void setUp(){

	}

	@Test
	public void 이미지_디비_등록_테스트() {
		Image image = new Image();

		image.setImageName("테스트이미지.jpg");
		image.setStoredImageName("년월일+Timestamp+RandomValue 로 저장 예정");
		image.setContentType("image/jpg");
		image.setImageSize(1000);

		Image returnImage = imageDAO.save(image);

		log.info("저장된 이미지 : {}", returnImage);
	}

	@Test
	public void 이미지_한건_정보_및_DTO_테스트() throws JsonProcessingException {
		이미지_디비_등록_테스트();

		List<Image> imageAllList = imageDAO.findAll();
		assertTrue("이미지 등록 테스트를 돌린 후라 목록은 0건 이상이어야 한다", imageAllList.size() > 0);

		ImageDto.Response returnImage = modelMapper.map(imageAllList.get(0), ImageDto.Response.class);

		assertNotNull(returnImage.getImageIdx());
		assertNotNull(returnImage.getImageName());
		assertNotNull(returnImage.getThumbUrl());
		assertNotNull(returnImage.getOriginUrl());

		log.info("이미지 한건 JSON : {}", objectMapper.writeValueAsString(returnImage));
	}

	@Test
	public void 이미지_그룹_목록_테스트() {
		ImageGroup imageGroup = new ImageGroup();

		List<Image> imageList = new ArrayList<>();
		Image image = new Image();
		image.setImageName("이미지1.jpg");
		image.setStoredImageName("test");
		image.setContentType("image/jpg");
		image.setImageSize(1000);
		image.setSortOrder(1);

		image.setImageGroup(imageGroup);
		imageList.add(image);

		image = new Image();
		image.setImageName("이미지2.jpg");
		image.setStoredImageName("test2");
		image.setContentType("image/jpg");
		image.setImageSize(1000);
		image.setSortOrder(3);

		image.setImageGroup(imageGroup);
		imageList.add(image);

		image = new Image();
		image.setImageName("이미지3.jpg");
		image.setStoredImageName("test3");
		image.setContentType("image/jpg");
		image.setImageSize(1000);
		image.setSortOrder(2);

		image.setImageGroup(imageGroup);
		imageList.add(image);

		imageGroup.setImageList(imageList);

		long imageGroupIdx = imageGroupDAO.save(imageGroup).getImageGroupIdx();

		// Commit 시켜줘서, 확인 시 가능하도록 해준다.
		entityManager.clear();

		ImageGroup returnImageGroup = imageGroupDAO.findOne(imageGroupIdx);
		assertNotNull(returnImageGroup);

		try {
			log.info("전체 저장된 이미지 JSON : {}", objectMapper.writeValueAsString(modelMapper.map(returnImageGroup, ImageGroupDto.Response.class)));
		} catch (JsonProcessingException e) {
			fail("JSON 만들기 Fail");
		}
	}
}
