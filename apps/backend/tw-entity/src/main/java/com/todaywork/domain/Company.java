package com.todaywork.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@Entity
@Table(name = "COMPANY")
@Data
@ToString(exclude = {"employmentList"})
public class Company {

    @Id
    @Column(name = "IDX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String name;

    private String category;

    @OneToMany(mappedBy = "company")
    private List<Employment> employmentList;

}
