package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.ReviewForm;
import com.ssafy.moment.domain.dto.request.SearchReq;
import com.ssafy.moment.domain.dto.response.AttractionDetailRes;
import com.ssafy.moment.domain.dto.response.AttractionOverviewRes;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.service.AttractionService;
import com.ssafy.moment.service.AttractionBookmarkService;
import com.ssafy.moment.service.AttractionReviewService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;
    private final AttractionReviewService reviewService;
    private final AttractionBookmarkService bookmarkService;

    @PostMapping
    public ResponseDto<?> getList(
        @RequestBody SearchReq searchReq,
        @PageableDefault(size = 16, sort = "id") Pageable pageable) {
        try {
            Page<AttractionOverviewRes> infos = attractionService.getAttractionList(searchReq, pageable);
            return ResponseDto.success(infos);
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/{contentId}")
    public ResponseDto<?> getById(@PathVariable Integer contentId) {
        try {
            return ResponseDto.success(attractionService.getById(contentId));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PostMapping("/{contentId}/reviews")
    public ResponseDto<?> createReview(@RequestBody ReviewForm reviewForm, @PathVariable int contentId, HttpServletRequest request) {
        try {
            reviewService.create(reviewForm, contentId, request);
            return ResponseDto.success("CREATE REVIEW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @DeleteMapping("/{contentId}/reviews/{reviewId}")
    public ResponseDto<?> deleteReview(@PathVariable int contentId, @PathVariable int reviewId, HttpServletRequest request) {
        try {
            reviewService.delete(reviewId, request);
            return ResponseDto.success("DELETE REVIEW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PostMapping("/{contentId}/bookmarks")
    public ResponseDto<?> createBookmark(@PathVariable int contentId, HttpServletRequest request) {
        try {
            bookmarkService.create(request, contentId);
            return ResponseDto.success("CREATE REVIEW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @DeleteMapping("/{contentId}/bookmarks")
    public ResponseDto<?> deleteBookmark(@PathVariable int contentId, HttpServletRequest request) {
        try {
            bookmarkService.delete(request, contentId);
            return ResponseDto.success("DELETE REVIEW SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

}