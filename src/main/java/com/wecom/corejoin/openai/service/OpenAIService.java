/**
 * @Copyright (C)
 * @Description:
 */
package com.wecom.corejoin.openai.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wecom.corejoin.openai.bo.ChatCompletionRequest;
import com.wecom.corejoin.openai.bo.ChatCompletionResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @Author: chenyumeng
 * @CreateTime: 2023/3/2 18:13
 */
@Service
public class OpenAIService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OpenAIService.class);

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    @Value("${openai.apiKey}")
    private String API_KEY;

    public String send(String content){
        if (content.length()>100){
            return "此应用为试用阶段，限制了输入字数，请暂时先不要输入那么多字";
        }

        //header
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("Authorization","Bearer "+API_KEY);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        List<ChatCompletionRequest.MessagesDTO> messages = new ArrayList<>();
        ChatCompletionRequest.MessagesDTO defaultMessage = new ChatCompletionRequest.MessagesDTO();
        defaultMessage.setRole("system");
        defaultMessage.setContent("You are ChatGPT, a large language model trained by OpenAI. Respond conversationally");
        messages.add(defaultMessage);

        ChatCompletionRequest.MessagesDTO contentMessage = new ChatCompletionRequest.MessagesDTO();
        contentMessage.setRole("user");
        contentMessage.setContent(content);
        messages.add(contentMessage);

        chatCompletionRequest.setMessages(messages);
        chatCompletionRequest.setModel("gpt-3.5-turbo");

        log.info("发送到open api的数据: {}", JSONUtil.toJsonStr(chatCompletionRequest));
        String body = HttpUtil.createPost(API_URL).addHeaders(headers).body(JSONUtil.toJsonStr(chatCompletionRequest)).execute().body();
        log.info("返回: {}",body);

        if(StringUtils.isNotEmpty(body)){
            ChatCompletionResult result = JSONUtil.toBean(body,ChatCompletionResult.class);
            if(result!=null){
                List<ChatCompletionResult.ChoicesDTO> choices = result.getChoices();
                if(CollectionUtil.isNotEmpty(choices)){
                    ChatCompletionResult.ChoicesDTO choicesDTO = choices.get(0);
                    return choicesDTO.getMessage().getContent();
                }
            }
            return "调用OpenAI没有拿到理想结果,请稍后再试";
        }
        return "调用OpenAI出现异常，请稍后再试";
    }
}
