package com.newegg.ec.tool.notify.wechat.entity;


import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.notify.wechat.entity.message.WechatMessage;

import java.util.StringJoiner;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class WechatCardMessage extends WechatMessage {

    private MessageContent textcard;

    public WechatCardMessage() {
        this.setMsgtype("textcard");
    }

    public MessageContent getTextcard() {
        return textcard;
    }

    public void setTextcard(MessageContent textcard) {
        this.textcard = textcard;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WechatCardMessage.class.getSimpleName() + "[", "]")
                .add("textcard=" + textcard)
                .toString();
    }
}
