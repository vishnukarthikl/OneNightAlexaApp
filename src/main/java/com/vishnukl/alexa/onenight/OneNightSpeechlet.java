package com.vishnukl.alexa.onenight;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneNightSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(OneNightSpeechlet.class);
    private AmazonDynamoDBClient amazonDynamoDBClient;
    private OneNightGameNarrator oneNightGameNarrator;

    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        initializeComponents();
    }

    private void initializeComponents() {
        if (amazonDynamoDBClient == null) {
            amazonDynamoDBClient = new AmazonDynamoDBClient();
            oneNightGameNarrator = new OneNightGameNarrator(amazonDynamoDBClient);
        }
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return null;
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        Intent intent = intentRequest.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("AddRoleIntent".equals(intentName)) {
            return oneNightGameNarrator.addRole(intent, session);

        } else if ("StartGameIntent".equals(intentName)) {
            return oneNightGameNarrator.startGame(session);
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {

    }
}
