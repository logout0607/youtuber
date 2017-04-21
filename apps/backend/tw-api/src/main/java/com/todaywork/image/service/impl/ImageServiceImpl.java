package com.todaywork.image.service.impl;

import com.todaywork.domain.Image;
import com.todaywork.domain.ImageGroup;
import com.todaywork.dto.ImageDto;
import com.todaywork.dto.ImageGroupDto;
import com.todaywork.image.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 이미지 관련 서비스 구현체
 * Created by 권 오빈 on 2016. 6. 10..
 */
@Profile("!prod")
@Service("imageService")
@Slf4j
public class ImageServiceImpl implements ImageService{

	@Resource(name = "imageDAO") private ImageDAO imageDAO;
	@Resource(name = "imageGroupDAO") private ImageGroupDAO imageGroupDAO;

	@Value("${fsa.upload.path}") private String uploadPath;

	@Transactional
	@Modifying
	@Override
	public ImageGroup upload(Collection<MultipartFile> fileList, ImageGroupDto.Update imageGroupDtoUpdate) {
		ImageGroup imageGroup;
		List<Image> imageList;

		if(ObjectUtils.isEmpty(imageGroupDtoUpdate) || imageGroupDtoUpdate.getImageGroupIdx() == 0){
			imageGroup = new ImageGroup();
			imageList = new ArrayList<>();
		} else {
			imageGroup = imageGroupDAO.getOne(imageGroupDtoUpdate.getImageGroupIdx());
			imageList = imageGroup.getImageList();

			List<ImageDto.Update> imageDtoUpdate = imageGroupDtoUpdate.getImageList();

			imageList.forEach(image -> {
				if(ObjectUtils.isEmpty(imageDtoUpdate) || imageDtoUpdate.size() == 0){
					image.setEnabled(false);
				} else {
					boolean update = true;
					for(ImageDto.Update imageDto : imageDtoUpdate){
						if(image.getImageIdx() == imageDto.getImageIdx()){
							update = false;
						}
					}
					if(update) image.setEnabled(false);
				}
			});
		}

		fileList.forEach(file -> {
			DateTime now = DateTime.now(DateTimeZone.UTC);
			String storedImageName = now + UUID.randomUUID().toString();
			storedImageName = storedImageName.replace(":", "").replace(".", "")  + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

			log.info("글자 자리수 : {}", storedImageName.length());
			log.info("신규 로그인 파일 저장 명 : {}", storedImageName);
			log.info("오리지널 파일 명 : {}", file.getOriginalFilename());
			log.info("파일 사이즈 : {}", file.getSize());

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
			}

			fileUploadPath = fileUploadPath.concat(storedImageName);

			try {
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fileUploadPath));
			} catch (IOException e) {
				log.error("업로드 에러 : {}", e.getCause());
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

		if(log.isDebugEnabled()){
			imageGroup.getImageList().forEach(image -> {
				log.debug("이미지 : {}", image.getImageName());
			});
		}

		return imageGroupDAO.save(imageGroup);
	}

	@Override
	public ImageGroup upload(Collection<MultipartFile> fileList) {
		return upload(fileList, null);
	}

	@Transactional
	@Modifying
	@Override
	public ImageGroup updateEnabledAuto(ImageGroup imageGroup) {
		ImageGroup preImageGroup = imageGroupDAO.getOne(imageGroup.getImageGroupIdx());
		List<Image> preImageList = preImageGroup.getImageList();

		List<Image> imageList = imageGroup.getImageList();

		if(preImageGroup.getImageList().size() == imageList.size()){
			return preImageGroup;
		}

		preImageList.forEach(image -> {
			if(ObjectUtils.isEmpty(imageList) || imageList.size() == 0){
				image.setEnabled(false);
			} else {
				boolean update = true;
				for(Image imageUpdate : imageList){
					if(image.getImageIdx() == imageUpdate.getImageIdx()){
						update = false;
					}
				}
				if(update) image.setEnabled(false);
			}
		});

		preImageGroup.setImageList(preImageList);

		return imageGroupDAO.save(preImageGroup);
	}

	@Override
	public Map<String, Object> getImage(long imageIdx) {
		Image image = imageDAO.findOne(imageIdx);

		if(ObjectUtils.isEmpty(image)){
			return null;
		}

		Map<String, Object> map = new HashMap<>();
		map.put("image", image);

		String storedImageName = image.getStoredImageName();

		String fileFullPath = uploadPath
			+ File.separator
			+ storedImageName.substring(0, 4)
			+ File.separator
			+ storedImageName.substring(5, 7)
			+ File.separator
			+ storedImageName.substring(8, 10)
			+ File.separator
			+ storedImageName;

		map.put("file", new File(fileFullPath));

		return map;
	}
}
