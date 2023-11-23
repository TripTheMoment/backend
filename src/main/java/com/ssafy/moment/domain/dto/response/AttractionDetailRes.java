package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.AttractionInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDetailRes {

    private Integer id;
    private int contentTypeId;
    private String title;
    private String description;
    private String addr1;
    private String zipcode;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private double latitude;
    private double longitude;
    private int bookmarkCnt;
    private List<ReviewRes> reviewResList;

    public static AttractionDetailRes from(AttractionInfo info) {
        return AttractionDetailRes.builder()
            .id(info.getId())
            .contentTypeId(info.getContentTypeId())
            .title(info.getTitle())
            .addr1(info.getAddr1())
            .zipcode(info.getZipcode())
            .tel(info.getTel())
            .firstImage(info.getFirstImage())
            .firstImage2(info.getFirstImage2())
            .latitude(info.getLatitude())
            .longitude(info.getLongitude())
//            .bookmarkCnt(info.getBookmarks().size())
            .build();
    }

}