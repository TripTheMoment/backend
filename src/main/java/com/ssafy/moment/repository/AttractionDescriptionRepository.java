package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.AttractionDescription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionDescriptionRepository extends JpaRepository<AttractionDescription, Integer> {

    Optional<AttractionDescription> findById(int contentId);

}
