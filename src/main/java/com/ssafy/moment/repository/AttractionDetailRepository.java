package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.AttractionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionDetailRepository extends JpaRepository<AttractionDetail, Integer> {

}
