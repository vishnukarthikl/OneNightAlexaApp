package com.vishnukl.alexa.onenight;

public class Role {
    private final String name;
    private final String ssml;

    public Role(String name, String ssml) {
        this.name = name;
        String intro = String.format("%s, open your eyes. <break time=\"1s\"/>", name);
        String outro = String.format("%s, close your eyes.", name);
        this.ssml = String.format("%s %s <break time=\"2s\"/> %s <break time=\"1s\"/>", intro, ssml, outro);
    }

    public String getName() {
        return name;
    }

    public String getSsml() {
        return ssml;
    }
}
