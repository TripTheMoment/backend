package com.ssafy.moment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer id;

    @Column(name = "article_title")
    private String title;
    @Column(name = "article_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}