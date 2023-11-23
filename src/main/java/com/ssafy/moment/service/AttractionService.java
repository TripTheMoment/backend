package com.ssafy.moment.service;

import com.ssafy.moment.domain.dto.request.SearchReq;
import com.ssafy.moment.domain.dto.response.AttractionDetailRes;
import com.ssafy.moment.domain.dto.response.AttractionOverviewRes;
import com.ssafy.moment.domain.dto.response.ReviewRes;
import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.domain.entity.AttractionReview;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.AttractionBookmarkRepository;
import com.ssafy.moment.repository.AttractionDescriptionRepository;
import com.ssafy.moment.repository.AttractionInfoRepository;
import com.ssafy.moment.repository.AttractionReviewRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionInfoRepository infoRepository;
    private final AttractionDescriptionRepository descriptionRepository;
    private final AttractionReviewRepository reviewRepository;
    private final AttractionBookmarkRepository bookmarkRepository;

    public Page<AttractionOverviewRes> getAttractionList(SearchReq searchReq, Pageable pageable) {
        Page<AttractionInfo> infos;
        if (searchReq.getSido() == 0 && searchReq.getType() == 0) {
            infos = infoRepository.findByTitleContaining(searchReq.getTitle(), pageable);
        } else if (searchReq.getSido() == 0) {
            infos = infoRepository.findByContentTypeIdAndTitleContaining(searchReq.getType(), searchReq.getTitle(), pageable);
        } else if (searchReq.getType() == 0) {
            infos = infoRepository.findBySidoCodeAndTitleContaining(searchReq.getSido(), searchReq.getTitle(), pageable);
        } else {
            infos = infoRepository.findBySidoCodeAndContentTypeIdAndTitleContaining(
                searchReq.getSido(), searchReq.getType(), searchReq.getTitle(), pageable);
        }

        return infos.map(i -> AttractionOverviewRes.from(i));
    }

    public AttractionDetailRes getById(int id) {
        AttractionInfo info = getAttractionInfoById(id);
        List<AttractionReview> reviews = getReviewsByContentId(info);

        int bookmarkCnt = Long.valueOf(bookmarkRepository.countByAttractionInfo(info)).intValue();

        AttractionDetailRes res = AttractionDetailRes.from(info);
        res.setDescription(descriptionRepository.findById(id).get().getOverview());
        res.setReviewResList(reviews.stream()
                .sorted(Comparator.comparing(AttractionReview::getCreatedAt).reversed())
                .map(r -> ReviewRes.from(r))
                .collect(Collectors.toList()));
        res.setBookmarkCnt(bookmarkCnt);

        return res;
    }

    private AttractionInfo getAttractionInfoById(int id) {
        return infoRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
    }

    private List<AttractionReview> getReviewsByContentId(AttractionInfo info) {
        return reviewRepository.findByAttractionInfo(info);
    }

}