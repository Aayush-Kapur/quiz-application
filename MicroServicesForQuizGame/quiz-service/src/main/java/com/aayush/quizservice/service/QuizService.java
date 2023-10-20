package com.aayush.quizservice.service;


import com.aayush.quizservice.dao.QuizDao;
import com.aayush.quizservice.feign.QuizInterface;
import com.aayush.quizservice.model.QuestionWrapper;
import com.aayush.quizservice.model.Quiz;
import com.aayush.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
      try{
          List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
          Quiz quiz = new Quiz();
          quiz.setTitle(title);
          quiz.setQuestionsIds(questionIds);
          quizDao.save(quiz);
          String response = "Quiz Created with Id: "  + quiz.getId();
          return new ResponseEntity<>(response, HttpStatus.CREATED);
      }catch (Exception e) {
          e.printStackTrace();
      }
      return new ResponseEntity<>("Failed to create a Quiz", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
       try{
           Optional<Quiz> quiz = quizDao.findById(id);
           List<QuestionWrapper> questionsForUsers = quizInterface.getQuestionsById(quiz.get().getQuestionsIds())
                                                                                    .getBody();

           return new ResponseEntity<>(questionsForUsers, HttpStatus.OK);
       }catch (Exception e) {
           e.printStackTrace();
       }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
       try{
           Optional<Quiz> quiz = quizDao.findById(id);
           List<Integer> quizQuestionIds = quiz.get().getQuestionsIds();
           return quizInterface.getQuizScore(responses,quizQuestionIds.size());
       }catch (Exception e) {
           e.printStackTrace();
       }
       return new ResponseEntity<>("Failed to get results!", HttpStatus.BAD_REQUEST);
    }
}
