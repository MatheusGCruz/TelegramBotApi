package com.telegram.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telegram.dto.AuthUser;
import com.telegram.dto.MessageDto;
import com.telegram.service.BotService;
import com.telegram.service.CommsService;

@RestController
@RequestMapping("/telegram")
public class TelegramApiController {

    @Autowired
	private BotService botService;
    
    @Autowired
	private CommsService commsService;    
    
    private final String botToken = System.getenv("bot_token");
    private final String baseChatId = System.getenv("base_chat_id");
    
    
    @GetMapping
	public String sendMessage(@RequestHeader("user_name") String userName, @RequestHeader("password") String password, @RequestHeader("message") String message) {
    	
    	AuthUser user = new AuthUser();
    	user.setUsername(userName);
    	user.setPassword(password);    	
    	
		MessageDto messageDto = new MessageDto();
		messageDto.setBotToken(botToken);
		messageDto.setChatId(commsService.getChatId(user));
		messageDto.setMessage(message);
		messageDto.setSendedAt(LocalDateTime.now());
		messageDto.setOutbound(1);
		
		try {
			botService.sendMessage(messageDto);
			return "Sended:"+messageDto.getMessage();
		}
		catch(Exception ex) {
			return ex.getMessage() ;
		}
	}
	
	@GetMapping("/initiate")
	public String initiateBot() {

		MessageDto messageDto = new MessageDto();
		messageDto.setBotToken(botToken);
		messageDto.setChatId(baseChatId);
		messageDto.setMessage("Creating new instance");
		messageDto.setSendedAt(LocalDateTime.now());
		messageDto.setOutbound(1);
		
		try {			
			return commsService.initiateBot();
		}
		catch(Exception ex) {
			return ex.getMessage() ;
		}
	}

	
}
