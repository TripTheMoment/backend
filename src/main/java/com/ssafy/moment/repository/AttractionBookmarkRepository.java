package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.AttractionBookmark;
import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.domain.entity.Member;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionBookmarkRepository extends JpaRepository<AttractionBookmark, Integer> {

    Optional<AttractionBookmark> findByAttractionInfoAndMember(AttractionInfo info, Member member);
    Page<AttractionBookmark> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);
    void deleteByAttractionInfoAndMember(AttractionInfo info, Member member);
    Long countByAttractionInfo(AttractionInfo info);

}
