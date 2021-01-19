package com.example.test;

public class Person {

    private String id;
    private String name;
    private int startIndex;
    private int endIndex;
    private String tag;

    public Person() {
    }

    public Person(String id, String name, int startIndex, int endIndex, String tag) {
        this.id = id;
        this.name = name;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public String getTag() {
        return tag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Person) {
            Person person = (Person) obj;
            if (person.id.equals(id)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
