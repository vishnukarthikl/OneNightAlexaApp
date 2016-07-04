package com.vishnukl.alexa.onenight.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains player and score data to represent a score keeper game.
 */
public class OneNightGameData {
    private List<String> roles;
    private Map<String, Integer> count;

    public OneNightGameData() {
        // public no-arg constructor required for DynamoDBMapper marshalling
    }

    public static OneNightGameData newInstance() {
        OneNightGameData newInstance = new OneNightGameData();
        newInstance.setRoles(new ArrayList<String>());
        newInstance.setCount(new HashMap<String, Integer>());
        return newInstance;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, Integer> getCount() {
        return count;
    }

    public void setCount(Map<String, Integer> count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "[RoleCountData roles: " + roles + "] count: " + count + "]";
    }

    public void increment(String role, int i) {
        if (count.containsKey(role)) {
            Integer oldCount = count.get(role);
            count.put(role, oldCount + i);
        } else {
            count.put(role, i);
            roles.add(role);
        }
    }
}
