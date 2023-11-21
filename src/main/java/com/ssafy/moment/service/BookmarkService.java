package com.ssafy.moment.service;

import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.domain.entity.Bookmark;
import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import com.ssafy.moment.repository.AttractionInfoRepository;
import com.ssafy.moment.repository.BookmarkRepository;
import com.ssafy.moment.security.TokenProvider;
import java.awt.print.Book;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final AttractionInfoRepository attractionInfoRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void create(HttpServletRequest request, int contentId) {
        Member member = tokenProvider.getMemberFromToken(request);
        AttractionInfo attractionInfo = getAttractionInfoById(contentId);
        Bookmark bookmark = Bookmark.of(attractionInfo, member);
        bookmarkRepository.save(bookmark);
    }

    private AttractionInfo getAttractionInfoById(int contentId) {
        return attractionInfoRepository.findById(contentId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void delete(HttpServletRequest request, int contentId) {
        Member member = tokenProvider.getMemberFromToken(request);
        AttractionInfo attractionInfo = getAttractionInfoById(contentId);

        Optional<Bookmark> optBookmark = bookmarkRepository.findByAttractionInfoAndMember(attractionInfo, member);
        if (optBookmark.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_BOOKMARK);
        }

        bookmarkRepository.delete(optBookmark.get());
    }

}