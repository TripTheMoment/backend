package com.ssafy.moment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Integer id;

    private Integer contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private Integer sidoCode;
    private Integer gugunCode;
    private Double latitude;
    private Double longitude;
    private String mlevel;

//    @OneToMany(mappedBy = "attractionInfo")
//    private List<AttractionBookmark> bookmarks;
//
//    @OneToMany(mappedBy = "attractionInfo")
//    private List<AttractionReview> reviews;

}