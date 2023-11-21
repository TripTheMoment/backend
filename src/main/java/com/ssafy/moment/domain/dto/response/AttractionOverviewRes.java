package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.AttractionInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionOverviewRes {

    private Integer id;
    private String title;
    private String addr1;
    private String firstImage;

    public static AttractionOverviewRes from(AttractionInfo info) {
        return AttractionOverviewRes.builder()
            .id(info.getId())
            .title(info.getTitle())
            .addr1(info.getAddr1())
            .firstImage(info.getFirstImage())
            .build();
    }

}