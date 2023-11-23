package com.ssafy.moment.domain.dto.response;

import com.ssafy.moment.domain.entity.AttractionReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRes {

    private int id;
    private int score;
    private String content;
    private MemberOverviewRes member;
    private int contentId;
    private LocalDateTime createdAt;

    public static ReviewRes from(AttractionReview review) {
        return ReviewRes.builder()
            .id(review.getId())
            .score(review.getScore())
            .content(review.getContent())
            .member(MemberOverviewRes.from(review.getMember()))
            .contentId(review.getAttractionInfo().getId())
            .createdAt(review.getCreatedAt())
            .build();
    }

}
