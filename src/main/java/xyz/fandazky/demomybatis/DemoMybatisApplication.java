package xyz.fandazky.demomybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.fandazky.demomybatis.dao.BookMapper;
import xyz.fandazky.demomybatis.model.Book;

import java.util.List;

@SpringBootApplication
public class DemoMybatisApplication implements CommandLineRunner {

	@Autowired
	private BookMapper bookMapper;

	public static void main(String[] args) {
		SpringApplication.run(DemoMybatisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Book> books =  bookMapper.findAll();
		System.out.println("BOOKSRESULT"+books);
	}
}
