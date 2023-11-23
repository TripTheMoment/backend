package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.request.ReviewForm;
import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.domain.entity.AttractionReview;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.AttractionInfoRepository;
import com.ssafy.moment.repository.AttractionReviewRepository;
import com.ssafy.moment.security.TokenProvider;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttractionReviewService {

    private final AttractionReviewRepository reviewRepository;
    private final AttractionInfoRepository attractionInfoRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void create(ReviewForm reviewForm, int contentId, HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        AttractionInfo attractionInfo = getAttractionInfoById(contentId);
        AttractionReview review = AttractionReview.of(member, attractionInfo, reviewForm);

        reviewRepository.save(review);
    }

    private AttractionInfo getAttractionInfoById(int contentId) {
        return attractionInfoRepository.findById(contentId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void delete(int reviewId, HttpServletRequest request) {
        Member member = tokenProvider.getMemberFromToken(request);
        AttractionReview review = getReviewById(reviewId);

        if (review.getMember().getId() != member.getId()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        reviewRepository.delete(review);
    }

    private AttractionReview getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
    }

}
