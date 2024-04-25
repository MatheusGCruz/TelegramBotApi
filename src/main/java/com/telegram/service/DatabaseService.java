package com.telegram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.dto.MessageDto;
import com.telegram.repository.MessageRepository;

@Service
public class DatabaseService {
	
	@Autowired
    MessageRepository messageRepository;// = new MessageRepositoryImpl();

    
    //public DatabaseService(MessageRepository messageRepository) {
      //  this.messageRepository = messageRepository;
    //}
	
	
	//private MessageRepository messageRepository = new MessageRepository();
	

	public void insert(MessageDto message) {
		System.out.println(message.getMessage());
		messageRepository.save(message);
	}

}
