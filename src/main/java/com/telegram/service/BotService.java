package com.telegram.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.telegram.dto.MessageDto;
import com.telegram.repository.MessageRepository;

@Service
public class BotService extends TelegramLongPollingBot {
	
	private final String botToken = System.getenv("bot_token");
    private final String chatId = System.getenv("base_chat_id");
    
    private boolean botStarted = false;
        
    @Autowired
    private MessageRepository messageRepository;
    

    

    public String sendNewMessage(BotService bot) {    	
    	
    	
    	MessageDto messageDto = new MessageDto();
    	messageDto.setBotToken(botToken);
    	messageDto.setChatId(chatId);
    	
    	if(!botStarted) {
    		messageDto.setMessage("Creating new instance");
    		messageDto.setSendedAt(LocalDateTime.now());
    		messageDto.setOutbound(1);   	
        	
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(bot);
                bot.sendMessage(messageDto);
                messageRepository.save(messageDto);
                botStarted = true;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    	else {

    		messageDto.setMessage("Instance Already created");
    		messageDto.setSendedAt(LocalDateTime.now());
    		messageDto.setOutbound(1);   	
        	bot.sendMessage(messageDto);
    	}
    	return messageDto.getMessage();

    }

    @Override
    public void onUpdateReceived(Update update) {
  	
		MessageDto messageDto = new MessageDto();
		messageDto.setBotToken(botToken);
		messageDto.setChatId(update.getMessage().getChatId().toString());
		messageDto.setMessage(update.getMessage().getText());
		messageDto.setSendedAt(LocalDateTime.now());
		messageDto.setOutbound(0);		
		messageRepository.save(messageDto);
    }

    public void sendMessage(MessageDto message) {
    	
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(message.getChatId());
        newMessage.setText(message.getMessage());
        	
        try {
        	execute(newMessage);
        	messageRepository.save(message);
            System.out.println("Message sent successfully!");
      
        } catch (TelegramApiException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    

	
    

}

