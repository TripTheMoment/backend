package com.ssafy.moment.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDetail {

    @Id
    @Column(name = "content_id")
    private Integer id;

    private String cat1;
    private String cat2;
    private String cat3;
    private String createdTime;
    private String modifiedTime;
    private String booktour;

}