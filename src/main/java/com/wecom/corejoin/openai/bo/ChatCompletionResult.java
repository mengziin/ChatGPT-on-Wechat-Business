/**
 * @Copyright (C)
 * @Description:
 */
package com.wecom.corejoin.openai.bo;

import java.util.List;

/**
 * @Description:
 * @Author: chenyumeng
 * @CreateTime: 2023/3/2 14:35
 */
public class ChatCompletionResult {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChatCompletionResult.class);

    private String id;
    private String object;
    private Integer created;
    private List<ChoicesDTO> choices;
    private UsageDTO usage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public List<ChoicesDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoicesDTO> choices) {
        this.choices = choices;
    }

    public UsageDTO getUsage() {
        return usage;
    }

    public void setUsage(UsageDTO usage) {
        this.usage = usage;
    }

    public static class UsageDTO {
        private Integer promptTokens;
        private Integer completionTokens;
        private Integer totalTokens;

        public Integer getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(Integer promptTokens) {
            this.promptTokens = promptTokens;
        }

        public Integer getCompletionTokens() {
            return completionTokens;
        }

        public void setCompletionTokens(Integer completionTokens) {
            this.completionTokens = completionTokens;
        }

        public Integer getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(Integer totalTokens) {
            this.totalTokens = totalTokens;
        }
    }

    public static class ChoicesDTO {
        private Integer index;
        private MessageDTO message;
        private String finishReason;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public MessageDTO getMessage() {
            return message;
        }

        public void setMessage(MessageDTO message) {
            this.message = message;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public static class MessageDTO {
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
}
