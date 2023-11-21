package com.ssafy.moment.repository;

import com.ssafy.moment.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Page<Article> findByTitleContaining(String title, Pageable pageable);

}
