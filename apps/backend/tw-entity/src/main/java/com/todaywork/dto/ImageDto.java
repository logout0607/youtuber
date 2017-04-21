package com.todaywork.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Image 처리 관련 DTO
 * Created by 권 오빈 on 2016. 6. 8..
 */
public class ImageDto {
	@Data
	public static class Create {

	}

	@Component
	@Data
	public static class Response {
		private static String IMAGE_HOST_URL;
		private long imageIdx;
		private String imageName;
		private String storedImageName;

		private String thumbUrl;
		private String originUrl;

		public String getThumbUrl() {
			return IMAGE_HOST_URL + "/thumb/" + this.storedImageName + "?imageIdx=" + this.imageIdx;
		}

		public String getOriginUrl() {
			return IMAGE_HOST_URL + "/" + this.storedImageName + "?imageIdx=" + this.imageIdx;
		}

		@Value("${fsa.image-host-url}")
		public void setImageHostUrl(String imageHostUrl){
			IMAGE_HOST_URL = imageHostUrl;
		}
	}

	@Data
	public static class Update {
		private long imageIdx;
		private boolean enabled;
		private int sortOrder;
	}
}
