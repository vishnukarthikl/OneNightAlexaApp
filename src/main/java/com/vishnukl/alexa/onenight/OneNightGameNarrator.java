package com.vishnukl.alexa.onenight;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.vishnukl.alexa.onenight.storage.OneNightGame;
import com.vishnukl.alexa.onenight.storage.OneNightGameData;
import com.vishnukl.alexa.onenight.storage.RoleCountDao;
import com.vishnukl.alexa.onenight.storage.RoleCountDynamoDbClient;

import java.util.List;

public class OneNightGameNarrator {
    private static final String SLOT_ROLE_NAME = "RoleName";
    private static final String SLOT_ROLE_COUNT = "RoleCount";
    private final RoleCountDao roleCountDao;
    private final AllRoles allRoles = new AllRoles();

    public OneNightGameNarrator(AmazonDynamoDBClient amazonDynamoDBClient) {
        RoleCountDynamoDbClient dynamoDbClient =
                new RoleCountDynamoDbClient(amazonDynamoDBClient);
        roleCountDao = new RoleCountDao(dynamoDbClient);
    }


    public SpeechletResponse addRole(Intent intent, Session session) {
        String newRoleName = intent.getSlot(SLOT_ROLE_NAME).getValue();
        Integer roleCount = getCount(intent);
        OneNightGame game = roleCountDao.getOneNightGame(session);
        if (game == null) {
            game = OneNightGame.newInstance(session, OneNightGameData.newInstance());
        }

        game.addRole(newRoleName, roleCount);
        roleCountDao.saveGame(game);

        String speechText = newRoleName + " has been added.";
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        return SpeechletResponse.newTellResponse(speech);
    }

    public SpeechletResponse removeRole(Intent intent, Session session) {
        String newRoleName = intent.getSlot(SLOT_ROLE_NAME).getValue();
        Integer roleCount = getCount(intent);

        OneNightGame game = roleCountDao.getOneNightGame(session);
        if (game == null) {
            game = OneNightGame.newInstance(session, OneNightGameData.newInstance());
        }

        game.removeRole(newRoleName, roleCount);
        roleCountDao.saveGame(game);

        String speechText = newRoleName + " has been removed.";
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        return SpeechletResponse.newTellResponse(speech);
    }

    private Integer getCount(Intent intent) {
        String value = intent.getSlot(SLOT_ROLE_COUNT).getValue();
        Integer roleCount;
        if (value != null)
            roleCount = Integer.valueOf(value);
        else
            roleCount = 1;
        return roleCount;
    }

    public SpeechletResponse startGame(Session session) {
        OneNightGame game = roleCountDao.getOneNightGame(session);
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        if (game == null) {
            outputSpeech.setText("No roles were added");
            return SpeechletResponse.newTellResponse(outputSpeech);
        }
        return narrate(game.getRoles());
    }

    private SpeechletResponse narrate(List<String> roles) {
        SsmlOutputSpeech ssmlOutputSpeech = new SsmlOutputSpeech();
        StringBuilder speechBuilder = new StringBuilder();
        speechBuilder.append("<speak>");
        for (Role role : this.allRoles.getRoles()) {
            if (roles.contains(role.getName())) {
                speechBuilder.append(role.getSsml());
            }
        }
        speechBuilder.append("</speak>");
        ssmlOutputSpeech.setSsml(speechBuilder.toString());
        return SpeechletResponse.newTellResponse(ssmlOutputSpeech);
    }
}
