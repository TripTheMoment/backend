package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.request.ArticleForm;
import com.ssafy.moment.domain.dto.response.ArticleRes;
import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.ArticleRepository;
import com.ssafy.moment.security.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TokenProvider tokenProvider;

    public Page<ArticleRes> getList(String title, Pageable pageable) {
        Page<Article> articles = articleRepository.findByTitleContaining(title, pageable);
        return articles.map(e -> ArticleRes.of(e, e.getMember()));
    }

    public ArticleRes get(int articleId) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
        return ArticleRes.of(article, article.getMember());
    }

    @Transactional
    public int create(HttpServletRequest request, ArticleForm form) {
        Member member = tokenProvider.getMemberFromToken(request);
        Article article = Article.of(form, member);
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

        if (member.getId() != article.getMember().getId()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        articleRepository.deleteById(articleId);
    }

}
