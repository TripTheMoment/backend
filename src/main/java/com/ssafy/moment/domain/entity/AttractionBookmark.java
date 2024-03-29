package com.ssafy.moment.domain.entity;

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
public class AttractionBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private AttractionInfo attractionInfo;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public static AttractionBookmark of(AttractionInfo info, Member member) {
        return AttractionBookmark.builder()
            .attractionInfo(info)
            .member(member)
            .build();
    }

}