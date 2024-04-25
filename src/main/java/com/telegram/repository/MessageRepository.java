package com.telegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telegram.dto.MessageDto;

@Repository
public interface MessageRepository extends JpaRepository<MessageDto, Long>{


}

