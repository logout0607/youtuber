package com.todaywork.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by 권 오빈 on 2016. 6. 6..
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "USER")
@DynamicUpdate
public class User implements Serializable{

	private static final long serialVersionUID = 8365677483543370455L;

	/**
	 * 유저 유일 식별키
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Type(type="uuid-char")
	@Column(name = "UNIQUE_ID")
	private UUID uniqueId;

	/**
	 * 이메일
	 */
	@Column(name = "EMAIL", length = 150, unique = true, updatable = false)
	@Email
	private String email;

	/**
	 * 비밀번호
	 */
	@Column(name = "PASSWORD", length = 200, nullable = false)
	private String password;

	/**
	 * 로그인 가능 여부
	 */
	@Column(name = "ENABLED", nullable = false)
	private boolean enabled = true;

	/**
	 * 사용자 이름
	 */
	@Column(name = "USER_NAME", nullable = false)
	private String userName;

	/**
	 * 생성 일시 + 타임존
	 */
	@CreatedDate
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeAndZone")
	@Columns(columns={@Column(name = "CREATED_DATE", nullable = false, updatable = false), @Column(name = "CREATED_DATE_TIMEZONE", nullable = false, updatable = false)})
	private DateTime createdDate;

	/**
	 * 최종 수정일시 + 타임존
	 */
	@LastModifiedDate
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeAndZone")
	@Columns(columns={@Column(name = "LAST_MODIFIED_DATE"),@Column(name = "LAST_MODIFIED_DATE_TIMEZONE") })
	private DateTime lastModifiedDate;

	/**
	 * 권한 목록
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	@JsonManagedReference
	private List<UserRole> userRoleList;

}
