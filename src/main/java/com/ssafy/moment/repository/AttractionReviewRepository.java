package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.AttractionInfo;
import com.ssafy.moment.domain.entity.AttractionReview;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionReviewRepository extends JpaRepository<AttractionReview, Integer> {
    List<AttractionReview> findByAttractionInfo(AttractionInfo attractionInfo);
}
