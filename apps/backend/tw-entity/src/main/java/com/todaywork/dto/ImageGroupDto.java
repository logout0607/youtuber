package com.todaywork.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 이미지 목록 관련 DTO
 * Created by 권 오빈 on 2016. 6. 9..
 */
public class ImageGroupDto {

	public static class Create {

	}

	@Data
	public static class Response {
		private long imageGroupIdx;
		private List<ImageDto.Response> imageList;

		private String mainImageOriginUrl;
		private String mainImageThumbUrl;

		public String getMainImageOriginUrl() {
			if (!ObjectUtils.isEmpty(imageList) && imageList.size() > 0) {
				return imageList.get(0).getOriginUrl();
			}
			return null;
		}

		public String getMainImageThumbUrl() {
			if (!ObjectUtils.isEmpty(imageList) && imageList.size() > 0) {
				return imageList.get(0).getThumbUrl();
			}
			return null;
		}
	}

	@Data
	public static class Update {
		private long imageGroupIdx;

		private List<ImageDto.Update> imageList;
	}
}
