/**
 * @Copyright (C)
 * @Description:
 */
package com.wecom.corejoin.openai.service;

import cn.hutool.json.JSONUtil;
import com.wecom.corejoin.openai.bo.TextMessage;
import com.wecom.corejoin.openai.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author: chenyumeng
 * @CreateTime: 2023/3/2 17:22
 */
@Service
public class CoreService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CoreService.class);

    @Autowired
    private OpenAIService openAIService;


    public String processRequest(String msg) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(msg);

            log.info("requestMap=="+ JSONUtil.toJsonStr(requestMap));

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content");
                respContent = "您发送的是文本消息,内容是："+content+"\n";
                //TODO OpenAI
                String aiResp = "ChatAI的回复:\n"+openAIService.send(content);
                respContent+=aiResp;
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息,目前ChatAI只支持处理文字消息，图片处理敬请期待！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息,目前ChatAI只支持处理文字消息，地理位置处理敬请期待！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息,目前ChatAI只支持处理文字消息，链接处理敬请期待！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息,目前ChatAI只支持处理文字消息，音频处理敬请期待！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 自定义菜单点击事件
                if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");
                    log.info("EventKey="+eventKey);
                    respContent = "您点击的菜单KEY是"+eventKey;
                }
            }

            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CoreService处理出现异常",e);
            respMessage="处理出现异常，请稍后再试";
        }
        return respMessage;
    }
}
