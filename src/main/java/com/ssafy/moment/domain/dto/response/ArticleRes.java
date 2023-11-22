package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Member;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

}
