package com.telegram.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MessageDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String botToken;
	
	@Column
	private String chatId;
	
	@Column 
	private String message;
	
	@Column
	private LocalDateTime sendedAt;
	
	@Column
	private Integer outbound;
}
