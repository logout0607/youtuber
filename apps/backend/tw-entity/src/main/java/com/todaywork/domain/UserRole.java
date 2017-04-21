package com.todaywork.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 권 오빈 on 2016. 6. 6..
 */
@Entity
@Table(name = "USER_ROLE")
@Data
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class UserRole implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ROLE_IDX")
	private int userRoleIdx;

	/**
	 * 권한 명
	 */
	@Column(name = "ROLE", nullable = false, length = 45)
	private String role;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIQUE_ID", nullable = false)
	@JsonBackReference
	private User user;
}
