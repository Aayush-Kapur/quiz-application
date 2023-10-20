package com.aayush.questionservice.service;


import com.aayush.questionservice.dao.QuestionDao;
import com.aayush.questionservice.model.Question;
import com.aayush.questionservice.model.QuestionWrapper;
import com.aayush.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
       try {
           return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
       }catch (Exception e) {
           e.printStackTrace();
       }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.getQuestionsByCategory(category), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
       try {
           if(!questionDao.existsById(question.getId())) {
               questionDao.save(question);
               return new ResponseEntity<>("Success!",HttpStatus.CREATED);
           }
           else {
               return new ResponseEntity<>("Id already exists!", HttpStatus.BAD_REQUEST);
           }
       }catch (Exception e) {
           e.printStackTrace();
       }
        return new ResponseEntity<>("Failed!", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String queCategory, int numQ) {
       try{
           List<Question> questionList = questionDao.getRandomQuestionsByCategory(queCategory,numQ);
           List<Integer> questionIdList = new ArrayList<>();
           for(Question question : questionList) {
               questionIdList.add(question.getId());
           }
           return new ResponseEntity<>(questionIdList,HttpStatus.OK);
       }catch (Exception e) {
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> queIds) {
       try {
           List<QuestionWrapper> questionWrappers = new ArrayList<>();
           for(Integer id : queIds) {
               if(questionDao.findById(id).isPresent()) {
                   Question question = questionDao.findById(id).get();
                   QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(),
                           question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());

                   questionWrappers.add(questionWrapper);
               }
           }
           return new ResponseEntity<>(questionWrappers,HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<String> getScore(List<Response> responses, Integer quizSize) {
        try {
            int countOfCorrectAnswers = 0;
            for (Response response : responses) {
                if(questionDao.findById(response.getId()).isPresent()) {
                    Question question = questionDao.findById(response.getId()).get();
                    if (response.getResponse().equals(question.getCorrectAnswer())) {
                        countOfCorrectAnswers++;
                    }
                }
            }
            String finalAnswer = countOfCorrectAnswers + "/" + quizSize;
            return new ResponseEntity<>(finalAnswer, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to get results", HttpStatus.BAD_REQUEST);
    }
}
