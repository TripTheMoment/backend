package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.request.ReplyForm;
import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.domain.entity.Reply;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.ArticleRepository;
import com.ssafy.moment.repository.ReplyRepository;
import com.ssafy.moment.security.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleReplyService {

    private final ReplyRepository replyRepository;
    private final ArticleRepository articleRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void create(HttpServletRequest request, int articleId, ReplyForm req) {
        Member member = tokenProvider.getMemberFromToken(request);
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));

        Reply reply = Reply.builder()
            .content(req.getContent())
            .member(member)
            .article(article)
            .build();

        replyRepository.save(reply);
    }

    @Transactional
    public void delete(HttpServletRequest request, int replyId) {
        Member member = tokenProvider.getMemberFromToken(request);
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));

        if (member.getId() != reply.getMember().getId()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        replyRepository.delete(reply);
    }


}
