/**
 * @Copyright (C)
 * @Description:
 */
package com.wecom.corejoin.openai.controller;

import com.wecom.corejoin.openai.service.CoreService;
import com.wecom.corejoin.openai.util.AesException;
import com.wecom.corejoin.openai.util.WXBizMsgCrypt;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @Description:
 * @Author: chenyumeng
 * @CreateTime: 2023/3/2 17:01
 */
@Controller
@RequestMapping("/wecom")
public class WecomController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WecomController.class);

    @Value("${wecom.token}")
    private String token;
    @Value("${wecom.encodingAESKey}")
    private String encodingAESKey ;
    @Value("${wecom.corpId}")
    private String corpId;

    @Autowired
    private CoreService coreService;

    @RequestMapping(value = { "/coreJoin" }, method = RequestMethod.GET)
    public void coreJoinGet(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        log.info("request=" + request.getRequestURL());

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
        } catch (AesException e) {
           log.error("verify error",e);
        }
        if (result == null) {
            result = token;
        }
        out.print(result);
        out.close();
        out = null;
    }

    @RequestMapping(value = { "/coreJoin" }, method = RequestMethod.POST)
    public void coreJoinPost(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 微信加密签名
        String msg_signature = request.getParameter("msg_signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        //从请求中读取整个post数据
        InputStream inputStream = request.getInputStream();
        String postData = IOUtils.toString(inputStream, "UTF-8");
        log.info(postData);

        String msg = "";
        WXBizMsgCrypt wxcpt = null;
        try {
            wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            //解密消息
            msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
        } catch (AesException e) {
            log.error("decryptMsg error",e);
        }
        log.info("获取到消息msg=" + msg);

        // 调用核心业务类接收消息、处理消息
        String respMessage = coreService.processRequest(msg);

        String encryptMsg = "";
        try {
            //加密回复消息
            encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
        } catch (AesException e) {
            log.error("Aes加密返回消息失败",e);
        }

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(encryptMsg);
        out.close();

    }
}
