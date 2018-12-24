package com.trotyzyq.websocket;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public  class HandlerRooter {
    public Map<String, Handler> handlerMap = new HashMap<>();
    public Map<String, String> methodMap = new HashMap<>();

    void fullHandlerMap(){}
    void fullMethodMap(){}


    void doHandlerRooter(ChannelHandlerContext ctx, String req) throws Exception{
        String id = JSON.parseObject(req).getString("xId");
        if(id.length() < 8){
            TextWebSocketFrame tws = new TextWebSocketFrame("请输入正确的id");
            // 群发
            ctx.writeAndFlush(tws);
            return;
        }
        /** 需要得到的handler**/
        String arg1 = id.substring(0,4);
        /** 需要得到的方法名**/
        String arg2 = id.substring(4,8);

        String methodString = methodMap.get(arg2);
        /** 执行方法**/
        Handler handler = handlerMap.get(arg1);
        Method method =handler.getClass().getMethod(methodString, ChannelHandlerContext.class, String.class);
        method.invoke(handler, ctx, req);
    }
}
