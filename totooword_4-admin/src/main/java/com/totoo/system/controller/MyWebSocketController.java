package com.totoo.system.controller;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.totoo.common.core.domain.model.LoginUser;
import com.totoo.framework.web.service.TokenService;
import com.totoo.system.domain.FunChatMessage;
import com.totoo.system.domain.FunGroupMember;
import com.totoo.system.domain.WebSocketResult;
import com.totoo.system.service.IFunChatMessageService;
import com.totoo.system.service.IFunGroupMemberService;
import com.totoo.system.service.IFunMessageListService;
import com.totoo.system.tool.StringOP;
import org.apache.logging.log4j.message.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnError;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MyWebSocketController extends TextWebSocketHandler {


    private static IFunChatMessageService funChatMessageService;

    private static IFunGroupMemberService funGroupMemberService;
//    private static IFunMessageListService funMessageListService;
    private static TokenService tokenService;
    @Autowired
    public void setFunChatMessageService(IFunChatMessageService funChatMessageService) {
        MyWebSocketController.funChatMessageService = funChatMessageService;
    }
    @Autowired
    public void setFunGroupMemberService(IFunGroupMemberService funGroupMemberService) {
        MyWebSocketController.funGroupMemberService = funGroupMemberService;
    }
//    @Autowired
//    public void setFunMessageListService(IFunMessageListService funMessageListService) {
//        MyWebSocketController.funMessageListService = funMessageListService;
//    }
    @Autowired
    public void setTokenService(TokenService tokenService1){
        tokenService= tokenService1;
    }
    // 存储所有连接的会话
    private static final ConcurrentHashMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> sessionId_userId = new ConcurrentHashMap<>();

    public LoginUser validate(WebSocketSession session,String token) throws IOException {

        if(token==null) {
            close(session,1000,"token为空");
            return null;
        }
        LoginUser user=tokenService.getLoginUserByToken(token.substring(7));//除去前面的Bearer[空格]
        if(user==null) {
            close(session,1000,"token无效");
            return null;
        }
        // 新连接建立时，将 session 存储起来
        System.out.println(">>>New connection established: " + session.getId());
        //若已建立连接，且新旧连接不同，则断开旧连接
        if (sessions.get(user.getUserId()) != null && !sessions.get(user.getUserId()).getId().equals(session.getId())) {
            System.out.println(">>>重复连接: " + user.getUserId());
            close(sessions.get(user.getUserId()),1000,"其他地方登陆，被迫下线");
            sessions.remove(user.getUserId());
            sessionId_userId.remove(session.getId());
        }
        sessions.put(user.getUserId(), session);
        sessionId_userId.put(session.getId(), user.getUserId());
        System.out.println(">>>身份校验完毕，连接用户为:"+user.getUserId());
        return user;}

    public void close(WebSocketSession session,int code,String msg) throws IOException {
        session.sendMessage(new TextMessage(msg));
        try{        // 使用 CloseStatus.NORMAL 表示正常关闭，并添加关闭原因
            send(session,"close",msg);
            CloseStatus closeStatus = new CloseStatus(code, msg);
            session.close(closeStatus);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)   {
//        validate(session);
//        System.out.println(">>>afterConnectionEstablished>>>session"+session);
//        System.out.println(""+session.getHandshakeHeaders()+session.getExtensions());
    }
//message:{require:String,message:{}}
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
        System.out.println(">>>handleTextMessage>>>session:"+session);

        // 解析客户端发送的消息
        JSONObject json = JSON.parseObject(message.getPayload());
        LoginUser user = validate(session, json.getString("Authorization"));

        switch (json.getString("type")){
            case "sendMsg":sendMessage(session,json.getJSONObject("message"),user);break;
            case "validate":break;
            case "test":test(session,json.getString("message"),user);break;
            default:close(session,1000,"未知请求");
                System.out.println(">>>未知请求:"+json);;break;
        }}catch (Exception e){
            close(session,1000, String.valueOf(e));
        }


        // 处理 sendMessage 请求
//        if ("sendMessage".equals(json.getString("type"))) {

//        }
    }
    public void send(WebSocketSession session,String type,Object message) throws IOException {
        Map t=new HashMap();
        t.put("type",type);t.put("message",message);
        session.sendMessage(new TextMessage(JSON.toJSONString(t)));
    }
    public void test(WebSocketSession session,String json,LoginUser user) throws IOException {
        System.out.println(">>>test:"+json);
        send(session,"test",json);
    }
    public void sendMessage(WebSocketSession session,JSONObject json,LoginUser user) throws IOException {
        System.out.println(">>>sendMessage>>>session:"+json);
        Long receiverId = json.getLong("receiverId"),groupId=json.getLong("groupId");
        String content = json.getString("content"),messageType=json.getString("messageType");
        FunChatMessage funChatMessage=new FunChatMessage();
        funChatMessage.setContent(content); funChatMessage.setGroupId(groupId);funChatMessage.setReceiverId(receiverId);funChatMessage.setMessageType(messageType);
        funChatMessage.setSenderId(user.getUserId());funChatMessage.setSendTime(new Date());
        funChatMessageService.insertFunChatMessage(funChatMessage);
        pushMessage(funChatMessage);
    }
    @OnError
    public void onError(WebSocketSession session, Throwable error) throws IOException {
        System.out.println(">>>onError>>>session:"+session);
        System.out.println(">>>onError>>>error:"+error);
        close(session,1000,"连接异常");

    }

    // 向客户端推送消息列表
    private void pushMessage(FunChatMessage funChatMessage) throws IOException {
        System.out.println(">>>pushMessage>>>funChatMessage:"+funChatMessage);
        System.out.println(">>>pushMessage>>>sessions.get(funChatMessage.getSenderId())!=null:"+(sessions.get(funChatMessage.getSenderId())!=null));
        if(Objects.equals(funChatMessage.getMessageType(), "0")){//好友私聊
            if(sessions.get(funChatMessage.getReceiverId())!=null)
                send(sessions.get(funChatMessage.getReceiverId()),"pushMsg",funChatMessage);
                send(sessions.get(funChatMessage.getSenderId()),"pushMsg",funChatMessage);
        }else{//群聊
            FunGroupMember funGroupMember=new FunGroupMember();funGroupMember.setGroupId(funChatMessage.getGroupId());
            List<FunGroupMember> funGroupMembers= funGroupMemberService.selectFunGroupMemberList(funGroupMember);
            for(FunGroupMember funGroupMember1:funGroupMembers){
                if(sessions.get(funGroupMember1.getUserId())!=null)
                    send(sessions.get(funGroupMember1.getUserId()),"pushMsg",funChatMessage);
        }
    }
    }
    //断开连接事件
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(">>>afterConnectionClosed>>>session:"+session);
//        LoginUser user = validate(session);
        // 连接关闭时，从 sessions 中移除 session
        Long userId= sessionId_userId.get(session.getId());
        sessions.remove(userId);
    }
}
