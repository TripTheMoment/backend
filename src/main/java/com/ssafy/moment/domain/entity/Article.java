package com.ssafy.moment.domain.entity;

import com.ssafy.moment.domain.dto.request.ArticleForm;
import java.util.List;
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
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer id;

    @Column(name = "article_title")
    private String title;
    @Column(name = "article_content")
    private String content;
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Reply> replies;

    public static Article of(ArticleForm form, Member member) {
        return Article.builder()
            .title(form.getTitle())
            .content(form.getContent())
            .imgUrl(form.getImgUrl())
            .member(member)
            .build();
    }

    public void update(ArticleForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.imgUrl = form.getImgUrl();
    }

}