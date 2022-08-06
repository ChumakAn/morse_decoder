package com.example.projectkursova.service;

import com.example.projectkursova.domain.Message;
import com.example.projectkursova.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class MessageService {

    protected MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public Message getById(Integer id) {
        try {
            return messageRepository.getById(id);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public Message create(Message object) {
        return messageRepository.save(object);
    }

    public Message update(Integer id, Message object) {
        if (messageRepository.findById(id).isPresent()) {
            return messageRepository.save(object);
        } else {
            return null;
        }
    }

    public void deleteById(Integer id) {
        if (messageRepository.findById(id).isPresent()) {
            messageRepository.deleteById(id);
        }
    }

}