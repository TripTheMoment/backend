package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.request.ArticleForm;
import com.ssafy.moment.domain.dto.response.ArticleRes;
import com.ssafy.moment.domain.dto.response.MemberOverviewRes;
import com.ssafy.moment.domain.dto.response.ReplyRes;
import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.ArticleRepository;
import com.ssafy.moment.security.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Value("${cloud.aws.s3.url}")
    private String defaultUrl;

    private final S3UploadService s3UploadService;
    private final ArticleRepository articleRepository;
    private final TokenProvider tokenProvider;

    public Page<ArticleRes> getList(String title, Pageable pageable) {
        Page<Article> articles = articleRepository.findByTitleContainingOrderByCreatedAtDesc(title, pageable);
        return toArticleOverviewRes(articles);
    }

    private Page<ArticleRes> toArticleOverviewRes(Page<Article> articles) {
        return articles.map(e -> ArticleRes.builder()
                .id(e.getId())
                .title(e.getTitle())
                .content(e.getContent())
                .imgUrl((e.getImgKeyName() == null) ? null : (defaultUrl + e.getImgKeyName()))
                .member(MemberOverviewRes.from(e.getMember()))
                .createdAt(e.getCreatedAt())
                .build());
    }

    public ArticleRes get(int articleId) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
        return toArticleRes(article);
    }

    private ArticleRes toArticleRes(Article article) {
        return ArticleRes.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .imgUrl((article.getImgKeyName() == null) ? null : (defaultUrl + article.getImgKeyName()))
                .member(MemberOverviewRes.from(article.getMember()))
                .replies(article.getReplies().stream()
                        .map(r -> ReplyRes.of(r, r.getMember()))
                        .sorted(Comparator.comparing(ReplyRes::getCreatedAt).reversed())
                        .collect(Collectors.toList()))
                .createdAt(article.getCreatedAt())
                .build();
    }

    @Transactional
    public int create(HttpServletRequest request, MultipartFile file, ArticleForm form) {
        Member member = tokenProvider.getMemberFromToken(request);
        String keyName = s3UploadService.upload(file, "article");
        Article article = Article.of(form, keyName, member);
        return articleRepository.save(article).getId();
    }

    @Transactional
    public int update(HttpServletRequest request, int articleId, ArticleForm form) {
        Member member = tokenProvider.getMemberFromToken(request);
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
        article.update(form);

        if (member.getId() != article.getMember().getId()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        return articleRepository.save(article).getId();
    }

    @Transactional
    public void delete(HttpServletRequest request, int articleId) {
        Member member = tokenProvider.getMemberFromToken(request);
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));

        s3UploadService.delete(article.getImgKeyName());

        if (member.getId() != article.getMember().getId()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        articleRepository.deleteById(articleId);
    }

}
