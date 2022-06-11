package com.example.projectkursova.controller;

import com.example.projectkursova.domain.Message;
import com.example.projectkursova.serial.SerialCommunication;
import com.example.projectkursova.serial.SerialRead;
import com.example.projectkursova.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequestMapping(value = "/message")
@Controller
public class MessageController {

    protected MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    private String getData;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home(Model model) {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/decode")
    public String mainPage() {
        return "decode";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tap_start")
    public String startReadData(Model model) {
        SerialCommunication.openPort();
        getData = SerialRead.readDatFromArduino();
        return "decoding";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tap_stop")
    public String stopDecoding(Model model) {
        System.out.println(getData);
        String currentDate = LocalDate.now().toString();
        Message newMessage = new Message(messageService.getAll().size() + 1, currentDate, getData);
        messageService.create(newMessage);
        return "decoding";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/show_decoded")
    public String showDecoded(Model model) {
        Message lastMessage = messageService.getAll().stream().max(Comparator.comparing(Message::getId)).get();
        List<Message> list = new ArrayList<>();
        list.add(lastMessage);
        model.addAttribute("message", list);
        return "result";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/show_all")
    public String showAll(Model model) {
        List<Message> messageList = messageService.getAll();
        model.addAttribute("message", messageList);
        return "show";
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Message>> getAll() {
        List<Message> objectsDto = messageService.getAll();
        if (objectsDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(objectsDto, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public @ResponseBody
    ResponseEntity<Message> getById(@PathVariable Integer id) {
        Message object = messageService.getById(id);
        if (object == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<Void> create(@RequestBody Message newObject) {
        messageService.create(newObject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<Message> update(@PathVariable Integer id, @RequestBody Message object) {
        if (messageService.getById(id) != null) {
            messageService.update(id, object);
            return new ResponseEntity<>(messageService.update(id, object), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        if (messageService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else {
            messageService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
