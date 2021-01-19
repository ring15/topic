package com.example.test;

/**
 * Created by ring on 2020/9/22.
 */
public class PublishPostContent {
    private String content;
    //0:photo; 1:@; 2:#; -1:normal
    private int type;
    private int photoIndex;

    public PublishPostContent() {
    }

    public PublishPostContent(String content, int type, int photoIndex) {
        this.content = content;
        this.type = type;
        this.photoIndex = photoIndex;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getPhotoIndex() {
        return photoIndex;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int photo) {
        type = photo;
    }

    public void setPhotoIndex(int photoIndex) {
        this.photoIndex = photoIndex;
    }
}
