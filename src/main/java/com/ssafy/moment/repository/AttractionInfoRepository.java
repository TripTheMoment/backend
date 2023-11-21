package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.AttractionInfo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Integer> {

    Page<AttractionInfo> findByTitleContaining(String title, Pageable pageable);
    Page<AttractionInfo> findBySidoCodeAndContentTypeIdAndTitleContaining(Integer sidoCode, Integer contentTypeId, String title, Pageable pageable);
    Page<AttractionInfo> findBySidoCodeAndTitleContaining(Integer sidoCode, String title, Pageable pageable);
    Page<AttractionInfo> findByContentTypeIdAndTitleContaining(Integer contentTypeId, String title, Pageable pageable);
    Optional<AttractionInfo> findById(Integer id);
}