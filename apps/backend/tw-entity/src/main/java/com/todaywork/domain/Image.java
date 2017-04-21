package com.todaywork.domain;

import lombok.Data;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 이미지 관련 Entity
 * Created by 권 오빈 on 2016. 6. 8..
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "IMAGE")
@Data
@DynamicUpdate
public class Image implements Serializable {

	private static final long serialVersionUID = -8915775734871983128L;

	@Id
	@Column(name = "IMAGE_IDX")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long imageIdx;

	@ManyToOne
	private ImageGroup imageGroup;

	/**
	 * 이미지 원본 명
	 */
	@Column(name = "IMAGE_NAME", nullable = false)
	private String imageName;

	/**
	 * 이미지 파일 사이즈
	 */
	@Column(name = "IMAGE_SIZE", nullable = false)
	private long imageSize;

	/**
	 * 저장된 이미지 명
	 */
	@Column(name = "STORED_IMAGE_NAME", nullable = false)
	private String storedImageName;

	/**
	 * 이미지 컨텐츠 타입
	 */
	@Column(name = "CONTENT_TYPE")
	private String contentType;

	/**
	 * 사용 여부
	 */
	@Column(name = "ENABLED", nullable = false)
	private boolean enabled = true;

	/**
	 * 정렬 순서
	 */
	@Column(name = "SORT_ORDER")
	private int sortOrder = 1;

	@OneToOne(fetch = FetchType.LAZY)
	@CreatedBy
	private User createdUser;

	@OneToOne(fetch = FetchType.LAZY)
	@LastModifiedBy
	private User lastModifiedUser;

	@CreatedDate
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeAndZone")
	@Columns(columns={@Column(name = "CREATED_DATE", nullable = false, updatable = false), @Column(name = "CREATED_DATE_TIMEZONE", nullable = false, updatable = false)})
	private DateTime createdDate;

	@LastModifiedDate
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeAndZone")
	@Columns(columns={@Column(name = "LAST_MODIFIED_DATE"), @Column(name = "LAST_MODIFIED_DATE_TIMEZONE")})
	private DateTime lastModifiedDate;
}
