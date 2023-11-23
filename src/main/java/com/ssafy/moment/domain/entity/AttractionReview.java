package com.ssafy.moment.domain.entity;

import com.ssafy.moment.domain.dto.request.ReviewForm;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer id;

    private int score;

    @Column(name = "review_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private AttractionInfo attractionInfo;

    public static AttractionReview of(Member member, AttractionInfo info, ReviewForm form) {
        return AttractionReview.builder()
            .score(form.getScore())
            .content(form.getContent())
            .member(member)
            .attractionInfo(info)
            .build();
    }

}
