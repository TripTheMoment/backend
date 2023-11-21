package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.domain.entity.Bookmark;
import com.ssafy.moment.domain.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionBookmarkRepository extends JpaRepository<Bookmark, Integer> {

    Optional<Bookmark> findByAttractionInfoAndMember(AttractionInfo info, Member member);
    List<Bookmark> findByMember(Member member);
    void deleteByAttractionInfoAndMember(AttractionInfo info, Member member);
}
