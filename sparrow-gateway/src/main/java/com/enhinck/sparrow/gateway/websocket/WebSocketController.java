package com.enhinck.sparrow.gateway.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.extern.slf4j.Slf4j;

/**
 * Stomp  websocket
 * @author hueb
 *
 */
@Controller 
@CrossOrigin
@Slf4j
public class WebSocketController {
	public SimpMessagingTemplate template;

	@Autowired
	public WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}


	@MessageMapping("/welcome")
	public String welcome(String userMessage) throws Exception {
		if(log.isDebugEnabled()) {
			log.debug(userMessage);
		}
		//template.convertAndSend("/topic/event", JSONObject.toJSONString(eventMessage));
		template.convertAndSendToUser("1", "/message", "111");
		return userMessage;
	}

}
