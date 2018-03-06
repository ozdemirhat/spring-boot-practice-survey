package com.trendyol.controller;

import java.util.Arrays;
import java.util.List;

import com.trendyol.Application;
import com.trendyol.model.Question;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.plaf.synth.SynthEditorPaneUI;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before
    public void before() {

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testRetrieveSurveyQuestion() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions/Question1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{id:Question1,description:Largest Country in the World,correctAnswer:Russia}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void retrieveAllSurveyQuestions() throws Exception {

        ResponseEntity<List<Question>> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.GET, new HttpEntity<String>("DUMMY_DOESNT_MATTER",
                        headers),
                new ParameterizedTypeReference<List<Question>>() {
                });

        Question sampleQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));

        assertTrue(response.getBody().contains(sampleQuestion));
    }

    @Test
    public void addQuestion() {

        Question question = new Question("DOESNTMATTER", "Question1", "Russia",
                Arrays.asList("India", "Russia", "United States", "China"));

        HttpEntity entity = new HttpEntity<Question>(question, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/surveys/Survey1/questions/"));

    }

    private String createURLWithPort(final String uri) {
        return "http://localhost:" + port + uri;
    }

}