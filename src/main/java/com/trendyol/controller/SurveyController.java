package com.trendyol.controller;


import com.trendyol.model.Question;
import com.trendyol.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
