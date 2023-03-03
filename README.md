# ChatGPT-on-Wechat-Business
企业微信-应用聊天机器人对接OpenAI API接口

打开application.yml文件，配置好如下参数：

```yaml
wecom:
  token: 应用机器人的token
  encodingAESKey: AES的key
  corpId: 企业id

openai:
  apiKey: openAI API key
```

⚠️注意：

企业微信成为管理员后，创建好应用，生成token和encodingAESKey后，设置好callback调用url后，部署服务，然后方可点击确定接收信息成功。
