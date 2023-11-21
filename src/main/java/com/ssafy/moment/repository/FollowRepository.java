package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.Follow;
import com.ssafy.moment.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);
    Optional<Follow> findByFromMemberAndToMember(Member fromMember, Member toMember);
    Long countByFromMember(Member fromMember);
    Long countByToMember(Member toMember);

}
