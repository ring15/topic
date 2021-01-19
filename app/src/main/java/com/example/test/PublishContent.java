package com.example.test;

import java.util.List;

/**
 * Created by ring on 2021/1/19.
 */
public class PublishContent {
    private String content;
    private List<Person> personList;

    public PublishContent(String content, List<Person> personList) {
        this.content = content;
        this.personList = personList;
    }

    public String getContent() {
        return content;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
