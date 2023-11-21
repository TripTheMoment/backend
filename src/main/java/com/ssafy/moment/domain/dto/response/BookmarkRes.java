package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Bookmark;
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
    private int contentId;
    private int memberId;

    public static BookmarkRes from(Bookmark bookmark) {
        return BookmarkRes.builder()
            .id(bookmark.getId())
            .contentId(bookmark.getId())
            .memberId(bookmark.getId())
            .build();
    }

}