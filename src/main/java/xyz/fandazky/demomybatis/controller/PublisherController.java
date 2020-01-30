package xyz.fandazky.demomybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fandazky.demomybatis.dao.PublisherMapper;
import xyz.fandazky.demomybatis.model.Publisher;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherMapper publisherMapper;

    @GetMapping
    public List<Publisher> getAllPublisher() {
        return publisherMapper.findAll();
    }
}