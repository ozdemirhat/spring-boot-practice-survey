package com.trendyol.controller;


import com.trendyol.model.Question;
import com.trendyol.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @RequestMapping(value="/surveys/{surveyId}/questions", method= RequestMethod.GET)
    public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyId){
        return surveyService.retrieveQuestions(surveyId);
    }

    @RequestMapping(value="/surveys/{surveyId}/questions/{questionId}", method= RequestMethod.GET)
    public Question retrieveDetailsForQuestion(@PathVariable String surveyId, @PathVariable String questionId){
        return surveyService.retrieveQuestion(surveyId, questionId);
    }

    @RequestMapping(value="/surveys/{surveyId}/questions", method= RequestMethod.POST)
    public ResponseEntity addQuestionToSurvey (@PathVariable String surveyId, @RequestBody Question newQuestion){
        Question question =surveyService.addQuestion(surveyId, newQuestion);

        if(question == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
