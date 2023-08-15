/*
 * !******************************************************************************
 *  * Copyright (c) 2021 - 9999, ARES
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.ares.system.websocket;


import com.ares.core.utils.SpringUtils;
import com.ares.system.service.SysNoticeService;
import com.ares.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Young
 * @date: 2023/3/23
 * @see: com.ares.system.websocket.WebSocketServer.java
 **/
@ServerEndpoint(value = "/ws/{id}")
@Component
@Slf4j
public class WebSocketServer {
    private static int onlineCount = 0;
    private Session session;
    private static ConcurrentHashMap<String, WebSocketServer> websocketMap = new ConcurrentHashMap<>();
    private String id;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        if(!websocketMap.containsKey(id)) {
            websocketMap.put(id, this);
            addOnlineCount();
            log.info("有新窗口开始监听:" + id + ",当前在线人数为" + getOnlineCount());
            this.id = id;
            try {
                sendMessage("连接成功");
            } catch (IOException e) {
                e.printStackTrace();
                log.error("websocket IO异常");
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (websocketMap.get(this.id) != null) {
            websocketMap.remove(this.id);  //从map中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法，根据业务要求进行处理，这里就简单地将收到的消息直接群发推送出去
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + id + "的信息:" + message);
        if (StringUtils.isNotBlank(message)) {
            for (WebSocketServer server : websocketMap.values()) {
                try {
                    server.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * 发生错误时的回调函数
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息（用set会方便些）
     */
    public static void sendInfo(String message, @PathParam("id") String id) throws IOException {
        log.info("推送消息到窗口" + id + "，推送内容:" + message);
        if (StringUtils.isNotBlank(message)) {
            for (WebSocketServer server : websocketMap.values()) {
                try {
                    // sid为null时群发，不为null则只发一个
                    if (id == null) {
                        server.sendMessage(message);
                    } else if (server.id.equals(id)) {
                        server.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public static void sendNotice(boolean isUpdate, @PathParam("id") String id) throws IOException {
        log.info("推送消息到窗口" + id);
        if (isUpdate) {
            SysUserService userService = SpringUtils.getBean(SysUserService.class);
            Long userId = userService.getUserByName(id).getId();
            SysNoticeService sysNoticeService = SpringUtils.getBean(SysNoticeService.class);
            int noticeNum = sysNoticeService.noticeNum(userId);
            for (WebSocketServer server : websocketMap.values()) {
                try {
                    // sid为null时群发，不为null则只发一个
                    if (id == null) {
                        server.sendMessage(String.valueOf(noticeNum));
                    } else if (server.id.equals(id)) {
                        server.sendMessage(String.valueOf(noticeNum));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
