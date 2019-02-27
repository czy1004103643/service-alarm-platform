package com.newegg.ec.tool.notify.wechat.entity;


import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.notify.wechat.entity.message.AbstractWechatMessage;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class WechatTextMessage extends AbstractWechatMessage {

    private MessageContent text;

    public WechatTextMessage() {
        this.setMsgtype("text");
    }

    public MessageContent getText() {
        return text;
    }

    public void setText(MessageContent text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("text=").append(text);
        sb.append('}');
        return sb.toString();
    }
}
