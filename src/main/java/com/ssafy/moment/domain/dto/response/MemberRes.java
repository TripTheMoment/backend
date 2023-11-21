package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Bookmark;
import com.ssafy.moment.domain.entity.Follow;
import com.ssafy.moment.domain.entity.Member;
import java.time.LocalDateTime;
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
public class MemberRes {

    private String email;
    private String name;
    private boolean followYn;
    private List<ArticleRes> articles;
    private int followingCnt;
    private int followerCnt;
    private List<BookmarkRes> bookmarks;
    private LocalDateTime createdAt;

}