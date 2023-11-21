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
    private List<Article> articles;
    private List<Follow> followings;
    private List<Follow> followers;
    private List<Bookmark> bookmarks;
    private LocalDateTime createdAt;

    public static MemberRes from(Member member) {
        return MemberRes.builder()
            .email(member.getEmail())
            .name(member.getName())
            .articles(member.getArticles())
            .followings(member.getFollowings())
            .followers(member.getFollowers())
            .bookmarks(member.getBookmarks())
            .createdAt(member.getCreatedAt())
            .build();
    }

}