package com.ssafy.moment.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleOverviewRes {

    private int id;
    private String title;
    private String imgUrl;
    private LocalDateTime createdAt;

}
