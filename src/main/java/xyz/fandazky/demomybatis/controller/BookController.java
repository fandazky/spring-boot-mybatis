package xyz.fandazky.demomybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fandazky.demomybatis.dao.BookMapper;
import xyz.fandazky.demomybatis.model.Book;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookMapper bookMapper;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookMapper.findAll();
    }

    @GetMapping("/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookMapper.findByGenre(genre);
    }
}