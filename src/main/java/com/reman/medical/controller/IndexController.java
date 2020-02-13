package com.reman.medical.controller;


import com.reman.medical.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/index")
    public String index() {

        return "index";
    }

    @GetMapping("/user")
    public String sendUser(Long id, Model model) {

        model.addAttribute("id",id);
        return "user";
    }

    @MessageMapping("/message")
    public void sendMessageToUser(Message message) {
        template.convertAndSendToUser(message.getToUser(),"/message",message);
    }

}
