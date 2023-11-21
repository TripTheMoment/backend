package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.Member;
import com.ssafy.moment.domain.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}