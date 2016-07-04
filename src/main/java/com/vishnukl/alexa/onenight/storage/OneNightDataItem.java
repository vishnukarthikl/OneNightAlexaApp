package com.vishnukl.alexa.onenight.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Model representing an item of the ScoreKeeperUserData table in DynamoDB for the ScoreKeeper
 * skill.
 */
@DynamoDBTable(tableName = "RoleCountUserData")
public class OneNightDataItem {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String customerId;

    private OneNightGameData gameData;

    @DynamoDBHashKey(attributeName = "CustomerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDBAttribute(attributeName = "Data")
    @DynamoDBMarshalling(marshallerClass = RoleCounterMarshaller.class)
    public OneNightGameData getGameData() {
        return gameData;
    }

    public void setGameData(OneNightGameData gameData) {
        this.gameData = gameData;
    }

    /**
     * A {@link DynamoDBMarshaller} that provides marshalling and unmarshalling logic for
     * {@link OneNightGameData} values so that they can be persisted in the database as String.
     */
    public static class RoleCounterMarshaller implements
            DynamoDBMarshaller<OneNightGameData> {

        @Override
        public String marshall(OneNightGameData gameData) {
            try {
                return OBJECT_MAPPER.writeValueAsString(gameData);
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("Unable to marshall game data", e);
            }
        }

        @Override
        public OneNightGameData unmarshall(Class<OneNightGameData> clazz, String value) {
            try {
                return OBJECT_MAPPER.readValue(value, new TypeReference<OneNightGameData>() {
                });
            } catch (Exception e) {
                throw new IllegalStateException("Unable to unmarshall game data value", e);
            }
        }
    }
}
