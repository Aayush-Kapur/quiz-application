package com.aayush.questionservice.dao;


import com.aayush.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> getQuestionsByCategory(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:queCategory ORDER BY RANDOM() LIMIT :numQ",
            nativeQuery = true)
    List<Question> getRandomQuestionsByCategory(String queCategory, int numQ);
}
