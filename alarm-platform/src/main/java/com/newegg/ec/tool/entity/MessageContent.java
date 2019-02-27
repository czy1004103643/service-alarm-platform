package com.newegg.ec.tool.entity;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class MessageContent {

    private String title;

    private String content;

    // TODO: add other fields

    public MessageContent() {}

    public MessageContent(String content) {
        this.content = content;
    }

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MessageContent{");
        sb.append("title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
