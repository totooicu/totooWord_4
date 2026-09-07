package com.totoo.system.domain;

import com.alibaba.fastjson2.JSON;
import org.springframework.web.socket.TextMessage;

import java.io.Serializable;
import java.util.HashMap;

public class WebSocketResult implements Serializable {

    private String type;
    private  Object message;//<T>
    public static WebSocketResult Init(){return new WebSocketResult();}

    @Override
    public String toString() {
        return "rst{" +
                "type:'" + type + '\'' +
                ", message:" + message +
                '}';
    }

    public WebSocketResult setType(String type){this.type=type;return this;}
    public WebSocketResult setMessage(Object msg){this.message=msg;return this;}
    public TextMessage toTextMessage(){
        message= JSON.toJSONString(message);
        return  new TextMessage(JSON.toJSONString(this));}
}
