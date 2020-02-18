package xyz.fandazky.demomybatis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.fandazky.demomybatis.dao.BookMapper;
import xyz.fandazky.demomybatis.dao.ConfigurationMapper;
import xyz.fandazky.demomybatis.model.Book;
import xyz.fandazky.demomybatis.model.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private static final String CACHE_KEY_CONFIG = "cache_ttl";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static AtomicLong TTL = new AtomicLong(0);

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ConfigurationMapper configurationMapper;

    private List<Book> booksCached = new ArrayList<>();

    private long lastUpdatedCache = 0L;

    public Book createBook(Book book) {
        this.bookMapper.save(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return bookMapper.findAll();
    }

    public List<Book> getCachedBooks() {

        System.out.println("TTL:::" + TTL.toString());
        if (System.currentTimeMillis() - lastUpdatedCache > TTL.get()) {
            log.info("Update cache!!");
            this.updateCache();
        }

        return this.booksCached;
    }

    public List<Book> getBooksByTitle(String title) {
        return getCachedBooks()
                .stream()
                .filter(book -> book.getTitle() != null && book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookMapper.findByGenre(genre);
    }

    private synchronized void updateCache() {
        if (System.currentTimeMillis() - lastUpdatedCache <= TTL.get()) {
            return;
        }

        this.booksCached = this.bookMapper.findAll();
        this.lastUpdatedCache = System.currentTimeMillis();

        Configuration config = configurationMapper.getConfigByKey(CACHE_KEY_CONFIG);
        TTL.set(Long.valueOf(config.getConfigValue()));
        log.info("Cache already updated!!");
    }
}