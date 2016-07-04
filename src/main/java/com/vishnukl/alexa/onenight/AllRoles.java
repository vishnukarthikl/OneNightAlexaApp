package com.vishnukl.alexa.onenight;


import java.util.ArrayList;
import java.util.List;

public class AllRoles {
    private List<Role> roles = new ArrayList<>();

    {
        roles.add(new Role("werewolf", "Look for other werewolves. If you are the lone werewolf you may look at a card from the center"));
        roles.add(new Role("minion", "werewolves, stick out your thumb for minion to see"));
        roles.add(new Role("mason", "masons, see the other mason"));
        roles.add(new Role("seer", "seer, look at another player's card or two card from the center"));
        roles.add(new Role("robber", "robber, you may swap a card from another player and look at your new role"));
        roles.add(new Role("troublemaker", "troublemaker, you may swap two other players' card without looking at them"));
        roles.add(new Role("drunk", "drunk, exchange a card from the center without looking at it"));
        roles.add(new Role("insomniac", "insomniac, look at your own card to see if it got changed"));
    }

    public List<Role> getRoles() {
        return roles;
    }
}
