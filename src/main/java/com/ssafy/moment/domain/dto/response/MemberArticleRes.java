package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Article;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberArticleRes {

    private int id;
    private String title;
    private LocalDate createAt;

    public static MemberArticleRes from(Article article) {
        return MemberArticleRes.builder()
                .id(article.getId())
                .title(article.getTitle())
                .createAt(article.getCreatedAt().toLocalDate())
                .build();
    }

}
