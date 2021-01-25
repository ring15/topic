package com.example.test;

public class Topic {

    private String topic;
    private String tag;
    private int position;

    public Topic() {
    }

    public Topic(String topic, String tag, int position) {
        this.topic = topic;
        this.tag = tag;
        this.position = position;
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public int getPosition() {
        return position;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
