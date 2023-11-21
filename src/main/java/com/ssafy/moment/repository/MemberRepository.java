package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findById(Integer id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAuthKey(String uuid);

}
