package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.AttractionBookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRes {

    private int id;
    private AttractionOverviewRes info;

    public static BookmarkRes from(AttractionBookmark bookmark) {
        return BookmarkRes.builder()
            .id(bookmark.getId())
            .info(AttractionOverviewRes.from(bookmark.getAttractionInfo()))
            .build();
    }

}