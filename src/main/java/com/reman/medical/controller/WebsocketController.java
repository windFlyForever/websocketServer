package com.reman.medical.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebsocketController
 *
 */
@Slf4j
@RestController
public class WebsocketController {
    @Autowired
    private SimpMessagingTemplate template;


    @MessageMapping("/chat")
    public String say(String msg) {
        log.info("websocket msg: {}", msg);
        template.convertAndSend("/topic", msg);
        return msg;
    }

    @GetMapping("/websocket/reply")
    public String msgReply(@RequestParam String msg) {
        log.info("websocket reply: {}", msg);
        template.convertAndSend("/subscribe", msg);
        return msg;
    }
}
