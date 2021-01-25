package com.example.test;

import java.util.List;

/**
 * Created by ring on 2021/1/19.
 */
public class PublishContent {
    private String content;
    private List<Person> personListAt;
    private List<Person> personListTopic;
    private List<String> topicList;

    public PublishContent(String content, List<Person> personListAt) {
        this.content = content;
        this.personListAt = personListAt;
    }

    public PublishContent(String content, List<Person> personListAt, List<String> topicList) {
        this.content = content;
        this.personListAt = personListAt;
        this.topicList = topicList;
    }

    public String getContent() {
        return content;
    }

    public List<Person> getPersonListAt() {
        return personListAt;
    }

    public List<Person> getPersonListTopic() {
        return personListTopic;
    }

    public List<String> getTopicList() {
        return topicList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPersonListAt(List<Person> personListAt) {
        this.personListAt = personListAt;
    }

    public void setPersonListTopic(List<Person> personListTopic) {
        this.personListTopic = personListTopic;
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }
}
