package com.newegg.ec.tool.entity;

import java.util.StringJoiner;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class MessageContent {

    private String title;

    private String content;

    private String description;

    private String url;

    private String btntxt;

    // TODO: add other fields

    public MessageContent() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBtntxt() {
        return btntxt;
    }

    public void setBtntxt(String btntxt) {
        this.btntxt = btntxt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MessageContent.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("content='" + content + "'")
                .add("description='" + description + "'")
                .add("url='" + url + "'")
                .add("btntxt='" + btntxt + "'")
                .toString();
    }
}
