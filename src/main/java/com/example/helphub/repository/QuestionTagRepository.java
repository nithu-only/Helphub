package com.example.helphub.repository;

import com.example.helphub.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
    List<QuestionTag> findByTagId(Long id);

    List<QuestionTag> findByQuestionId(Long id);
}
