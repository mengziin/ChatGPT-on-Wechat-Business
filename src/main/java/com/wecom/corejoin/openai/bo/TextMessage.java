/**
 * @Copyright (C)
 * @Description:
 */
package com.wecom.corejoin.openai.bo;


/**
 * @Description:
 * @Author: chenyumeng
 * @CreateTime: 2023/3/2 17:32
 */
public class TextMessage {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TextMessage.class);

    private String toUserName;
    private String fromUserName;
    private long createTime;
    private String msgType;
    private int FuncFlag;
    private String content;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int funcFlag) {
        FuncFlag = funcFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
