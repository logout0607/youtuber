package com.todaywork.domain;

import com.todaywork.domain.code.Gender;
import com.todaywork.domain.code.Nationality;
import com.todaywork.domain.code.WorkArea;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EMPLOYMENT")
@Data
@ToString(exclude = {"company", "imageGroup", "createdUser", "lastModifiedUser"})
public class Employment {

    @Id
    @Column(name = "IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private long startDailyPay;

    private long endDailyPay;

    private Gender gender;

    private Nationality nationality;

    private Date startDate;

    private Date endDate;

    private String address;

    private String details;

    private int recruitingPersonnel;

    @ElementCollection
    @CollectionTable(name = "workAreaList",
            joinColumns = @JoinColumn(name = "EMPLOYMENT_IDX"))
    private List<WorkArea> workAreaList;

    @ManyToOne
    private Company company;

    @OneToOne
    @JoinColumn(name = "IMAGE")
    private ImageGroup imageGroup;

    @CreatedDate
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeAndZone")
    @Columns(columns={@Column(name = "REG_DT"), @Column(name = "REG_DT_TIMEZONE")})
    private DateTime regDt;

    @LastModifiedDate
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeAndZone")
    @Columns(columns={@Column(name = "UPD_DT"), @Column(name = "UPD_DT_TIMEZONE")})
    private DateTime updDt;

    @OneToOne
    @CreatedBy
    private User createdUser;

    @OneToOne
    @LastModifiedBy
    private User lastModifiedUser;

}
