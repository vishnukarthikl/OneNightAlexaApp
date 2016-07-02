package com.vishnukl.alexa.onenight;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public final class OneNightSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<>();
        supportedApplicationIds.add("amzn1.echo-sdk-ams.app.c2913716-401f-4d46-a8ee-05ae6284977e");
    }

    public OneNightSpeechletRequestStreamHandler() {
        super(new OneNightSpeechlet(), supportedApplicationIds);
    }
}
