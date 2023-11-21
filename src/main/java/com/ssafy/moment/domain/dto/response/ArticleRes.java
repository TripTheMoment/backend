package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
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
public class ArticleRes {

    private int id;
    private String title;
    private String content;
    private String imgUrl;
    private MemberOverviewRes member;
    private List<ReplyRes> replies;

    public static ArticleRes of(Article article, Member member) {
        return ArticleRes.builder()
            .id(article.getId())
            .title(article.getTitle())
            .content(article.getContent())
            .imgUrl(article.getImgUrl())
            .member(MemberOverviewRes.from(member))
            .replies(article.getReplies().stream()
                .map(e -> ReplyRes.of(e, e.getMember()))
                .collect(Collectors.toList()))
            .build();
    }

}
