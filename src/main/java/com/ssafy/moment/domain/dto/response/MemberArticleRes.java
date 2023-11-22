package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Article;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberArticleRes {

    private int id;
    private String title;
    private LocalDateTime createAt;

    public static MemberArticleRes from(Article article) {
        return MemberArticleRes.builder()
                .id(article.getId())
                .title(article.getTitle())
                .createAt(article.getCreatedAt())
                .build();
    }

}
