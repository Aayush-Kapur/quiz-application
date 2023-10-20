package com.aayush.questionservice.controller;


import com.aayush.questionservice.model.Question;
import com.aayush.questionservice.model.QuestionWrapper;
import com.aayush.questionservice.model.Response;
import com.aayush.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
      return questionService.addQuestion(question);
    }

    @GetMapping("generateQuiz")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String queCategory,
                                                             @RequestParam int numQ) {
        return questionService.getQuestionsForQuiz(queCategory, numQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> queIds) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsById(queIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<String> getQuizScore(@RequestBody  List<Response> responses, @RequestParam Integer quizSize) {
        return questionService.getScore(responses, quizSize);
    }

}
