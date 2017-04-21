package com.todaywork.domain;

import lombok.Data;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 이미지 그룹 Entity
 * Created by 권 오빈 on 2016. 6. 9..
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "IMAGE_GROUP")
@Data
public class ImageGroup implements Serializable {

	private static final long serialVersionUID = 7884732430258263180L;

	@Id
	@Column(name = "IMAGE_GROUP_IDX")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long imageGroupIdx;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "imageGroup", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("sortOrder desc, imageIdx asc")
	@Where(clause = " enabled = true ")
	private List<Image> imageList;

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
