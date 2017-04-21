package com.todaywork.image;

import com.todaywork.cmm.FileSenderUtil;
import com.todaywork.domain.Image;
import com.todaywork.image.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * 이미지 업로드 / 다운로드 등 관련 Controller
 * Created by 권 오빈 on 2016. 6. 10..
 */
@Controller
@RequestMapping("/image")
public class ImageController {

	@Resource(name = "imageService") private ImageService imageService;

	@GetMapping("/{imageName}")
	public void downImg(
		@RequestParam(name = "imageIdx") long imageIdx,
		@PathVariable("imageName") String imageName,
		HttpServletRequest request, HttpServletResponse response){

		fileDownload(imageIdx, imageName, request, response);
	}

	@GetMapping("/thumb/{imageIdx:\\d+}-{imageName}")
	public void downThumbImg(
		@RequestParam(name = "imageIdx") long imageIdx,
		@PathVariable("imageName") String imageName,
		HttpServletRequest request, HttpServletResponse response){

		fileDownload(imageIdx, imageName, request, response);
	}

	private void fileDownload(long imageIdx, String imageName, HttpServletRequest request, HttpServletResponse response) {
		try {

			Map<String, Object> map = imageService.getImage(imageIdx);
			File file = (File) map.get("file");
			Image image = (Image) map.get("image");

			FileSenderUtil.fromFile(file)
				.with(request)
				.with(response)
				.with(MediaType.valueOf(image.getContentType()))
				.with(imageName)
				.serveResource();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
