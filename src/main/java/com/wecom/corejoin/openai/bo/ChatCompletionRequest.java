/**
 * @Copyright (C)
 * @Description:
 */
package com.wecom.corejoin.openai.bo;

import java.util.List;

/**
 * @Description:
 * @Author: chenyumeng
 * @CreateTime: 2023/3/2 14:34
 */
public class ChatCompletionRequest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChatCompletionRequest.class);
    /**
     * ID of the model to use. Currently, only and are supported.gpt-3.5-turbogpt-3.5-turbo-0301
     */
    private String model;

    /**
     * ID of the model to use. Currently, only and are supported.gpt-3.5-turbogpt-3.5-turbo-0301
     */
    private List<MessagesDTO> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<MessagesDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesDTO> messages) {
        this.messages = messages;
    }

    public static class MessagesDTO {
        private String role;
        private String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
