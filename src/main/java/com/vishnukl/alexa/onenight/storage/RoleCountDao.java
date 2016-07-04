package com.vishnukl.alexa.onenight.storage;

import com.amazon.speech.speechlet.Session;

/**
 * Contains the methods to interact with the persistence layer for OneNight in DynamoDB.
 */
public class RoleCountDao {
    private final RoleCountDynamoDbClient dynamoDbClient;

    public RoleCountDao(RoleCountDynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    /**
     * Reads and returns the {@link OneNightGame} using user information from the session.
     * <p>
     * Returns null if the item could not be found in the database.
     * 
     * @param session
     * @return
     */
    public OneNightGame getOneNightGame(Session session) {
        OneNightDataItem item = new OneNightDataItem();
        item.setCustomerId(session.getUser().getUserId());

        item = dynamoDbClient.loadItem(item);

        if (item == null) {
            return null;
        }

        return OneNightGame.newInstance(session, item.getGameData());
    }

    /**
     * Saves the {@link OneNightGame} into the database.
     * 
     * @param game
     */
    public void saveGame(OneNightGame game) {
        OneNightDataItem item = new OneNightDataItem();
        item.setCustomerId(game.getSession().getUser().getUserId());
        item.setGameData(game.getGameData());

        dynamoDbClient.saveItem(item);
    }
}
