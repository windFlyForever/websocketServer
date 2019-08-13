package com.reman.medical.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    Logger log= LoggerFactory.getLogger(this.getClass());

    private String sid;

    private Session session;

    private static int onLineCount=0;

    private static Map<String,WebSocketServer> allClients=new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session){
        if(sid!=null&&!allClients.containsKey(sid)){
            log.info("监听到新的连接："+sid+",连接创建成功");
            this.sid=sid;
            this.session=session;
            addOnLineCount();
            log.info("客户端"+sid+"上线，现在在线人数为："+getOnLineCount());
            allClients.put(sid,this);
        }
    }

    //关闭连接
    @OnClose
    public void onClose() throws IOException {
        this.session.close();
        allClients.remove(sid);
        subOnLineCount();
        log.info("客户端"+sid+"下线，现在在线人数为："+getOnLineCount());

    }

    @OnError
    public void onEorror(Session session,Throwable error) throws IOException {
        log.error("连接发生错误"+error.getMessage());

    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        log.info("收到来自"+sid+"的消息:"+message);
        JSONObject jsonObject = JSON.parseObject(message);
        String to =jsonObject.getString("to");
        String toMessage=jsonObject.getString("message");
        WebSocketServer toSocket=allClients.get(to);
        if(toSocket!=null){
            toSocket.session.getAsyncRemote().sendText(toMessage);
        }else{
            log.info(to+"不在线");
        }

    }

    //在线人数+1
    public static synchronized void addOnLineCount(){

        WebSocketServer.onLineCount++;
    }

    //在线人数-1
    public static synchronized void subOnLineCount(){
        WebSocketServer.onLineCount--;
    }

    public static synchronized int getOnLineCount(){
        return onLineCount;
    }



}
