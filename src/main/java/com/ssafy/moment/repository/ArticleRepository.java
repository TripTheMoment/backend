package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.Article;
import com.ssafy.moment.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Page<Article> findByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);
    Page<Article> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);

}
