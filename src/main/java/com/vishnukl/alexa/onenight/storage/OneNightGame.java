package com.vishnukl.alexa.onenight.storage;

import com.amazon.speech.speechlet.Session;

import java.util.List;
import java.util.Map;

/**
 * Represents a score keeper game.
 */
public final class OneNightGame {
    private Session session;
    private OneNightGameData gameData;

    private OneNightGame() {
    }

    public static OneNightGame newInstance(Session session, OneNightGameData gameData) {
        OneNightGame game = new OneNightGame();
        game.setSession(session);
        game.setGameData(gameData);
        return game;
    }

    protected void setSession(Session session) {
        this.session = session;
    }

    protected Session getSession() {
        return session;
    }

    protected OneNightGameData getGameData() {
        return gameData;
    }

    protected void setGameData(OneNightGameData gameData) {
        this.gameData = gameData;
    }

    public boolean hasPlayers() {
        return !gameData.getRoles().isEmpty();
    }

    public int getNumberOfPlayers() {
        return gameData.getRoles().size();
    }

    public void addRole(String role, int count) {
        gameData.increment(role, count);
    }

    public boolean hasRole(String playerName) {
        return gameData.getRoles().contains(playerName);
    }

    public long getCountForRole(String roleName) {
        return gameData.getCount().get(roleName);
    }


    public List<String> getRoles() {
        return this.gameData.getRoles();
    }

    public void removeRole(String role, Integer count) {
        Map<String, Integer> roleCounter = this.gameData.getCount();
        if (roleCounter.containsKey(role)) {
            int newCount = roleCounter.get(role) - count;
            if (newCount < 0) {
                newCount = 0;
                this.gameData.getRoles().remove(role);
            }
            roleCounter.put(role, newCount);
        }
    }
}
