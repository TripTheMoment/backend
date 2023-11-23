package com.ssafy.moment.domain.dto.response;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleOverviewRes {

    private int id;
    private String title;
    private String imgUrl;
    private LocalDate createdAt;

}
