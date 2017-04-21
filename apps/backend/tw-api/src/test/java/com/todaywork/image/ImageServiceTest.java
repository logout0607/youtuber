package com.todaywork.image;

import com.todaywork.domain.Image;
import com.todaywork.domain.ImageGroup;
import com.todaywork.image.service.ImageService;
import com.todaywork.image.service.impl.ImageDAO;
import com.todaywork.image.service.impl.ImageGroupDAO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;

/**
 * 이미지 업로드 / 다운로드 관련 서비스 테스트
 * Created by 권 오빈 on 2016. 6. 10..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ImageServiceTest {

	private MockMultipartFile file;
	private Collection<MultipartFile> files;

	private Image image;

	@Value("${fsa.upload.path}")
	private String uploadPath;

	@Autowired private ImageDAO imageDAO;
	@Autowired private ImageGroupDAO imageGroupDAO;
	@Autowired private ImageService imageService;

	@Before
	public void setUp() {
		image = new Image();

		// 단건 용 테스트 이미지 생성
		file = new MockMultipartFile("image", "test.jpg", "image/jpg", new byte[]{1, 2, 3, 4, 5, 66, 7, 7, 8, 9, 77, 8, 9, 0});

		// 여러 건 업로드용 이미지
		files = new ArrayList<>();
		files.add(file);
		files.add(new MockMultipartFile("image2", "test2.jpg", "image/jpg", new byte[]{1, 2, 3, 4, 5, 66, 7, 7, 8, 9, 77, 8, 9, 0}));
		files.add(new MockMultipartFile("image3", "test3.jpg", "image/jpg", new byte[]{1, 2, 3, 4, 5, 66, 7, 7, 8, 9, 77, 8, 9, 0}));
		files.add(new MockMultipartFile("image4", "test4.jpg", "image/jpg", new byte[]{1, 2, 3, 4, 5, 66, 7, 7, 8, 9, 77, 8, 9, 0}));
	}

	@Test
	public void 단건_업로드_테스트() {
		// 향후 S3로 업로드 테스트 해야 함

		log.info("저장 기준 현재 시간 및 날짜 : {}", DateTime.now(DateTimeZone.UTC));

		DateTime now = DateTime.now(DateTimeZone.UTC);
		String storedImageName = now + UUID.randomUUID().toString();
		storedImageName = storedImageName.replace(":", "").replace(".", "");

		log.info("글자 자리수 : {}", storedImageName.length());
		log.info("신규 로그인 파일 저장 명 : {}", storedImageName);

		DecimalFormat df = new DecimalFormat("00");
		String fileUploadPath =
				uploadPath
				+ File.separator
				+ now.getYear()
				+ File.separator
				+ df.format(now.getMonthOfYear())
				+ File.separator
				+ df.format(now.getDayOfMonth())
				+ File.separator;

		log.info("업로드 경로 : {}", fileUploadPath);

		File dir = new File(fileUploadPath);
		if(!dir.exists() && !dir.mkdirs()){
			log.error("폴더 생성 에러");
			fail("폴더 안만들어 짐");
		}

		fileUploadPath = fileUploadPath.concat(storedImageName);

		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fileUploadPath));
		} catch (IOException e) {
			log.error("업로드 에러 : {}", e.getCause());
			fail("업로드 에러");
		}

		image.setStoredImageName(storedImageName);
		image.setContentType(file.getContentType());
		image.setImageSize(file.getSize());
		image.setImageName(file.getOriginalFilename());
		imageDAO.save(image);
	}

	@Test
	public void 여러건_업로드_테스트() {
		ImageGroup imageGroup = new ImageGroup();
		List<Image> imageList = new ArrayList<>();


		files.forEach(file -> {
			DateTime now = DateTime.now(DateTimeZone.UTC);
			String storedImageName = now + UUID.randomUUID().toString();
			storedImageName = storedImageName.replace(":", "").replace(".", "");

			log.info("글자 자리수 : {}", storedImageName.length());
			log.info("신규 로그인 파일 저장 명 : {}", storedImageName);

			DecimalFormat df = new DecimalFormat("00");
			String fileUploadPath =
				uploadPath
					+ File.separator
					+ now.getYear()
					+ File.separator
					+ df.format(now.getMonthOfYear())
					+ File.separator
					+ df.format(now.getDayOfMonth())
					+ File.separator;

			log.info("업로드 경로 : {}", fileUploadPath);

			File dir = new File(fileUploadPath);
			if(!dir.exists() && !dir.mkdirs()){
				log.error("폴더 생성 에러");
				fail("폴더 안만들어 짐");
			}

			fileUploadPath = fileUploadPath.concat(storedImageName);

			try {
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fileUploadPath));
			} catch (IOException e) {
				log.error("업로드 에러 : {}", e.getCause());
				fail("업로드 에러");
			}

			Image image = new Image();
			image.setStoredImageName(storedImageName);
			image.setContentType(file.getContentType());
			image.setImageSize(file.getSize());
			image.setImageName(file.getOriginalFilename());

			image.setImageGroup(imageGroup);
			imageList.add(image);
		});

		imageGroup.setImageList(imageList);
		imageGroupDAO.save(imageGroup);
	}

	@Test
	public void 이미지_여러건_업로드_서비스_테스트() {
		imageService.upload(files, null);
	}

}
