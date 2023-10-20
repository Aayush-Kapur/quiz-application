package com.aayush.quizservice.feign;

import com.aayush.quizservice.model.QuestionWrapper;
import com.aayush.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("question/generateQuiz")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String queCategory,
                                                             @RequestParam int numQ);

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> queIds);

    @PostMapping("question/getScore")
    public ResponseEntity<String> getQuizScore(@RequestBody  List<Response> responses, @RequestParam Integer quizSize);
}
