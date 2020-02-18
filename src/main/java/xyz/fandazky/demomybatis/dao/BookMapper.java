package xyz.fandazky.demomybatis.dao;

import org.apache.ibatis.annotations.*;
import xyz.fandazky.demomybatis.model.Book;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("SELECT * FROM BOOKS") //SQL
    List<Book> findAll();

    @Select("SELECT * FROM BOOKS WHERE GENRE = #{genre}")
    List<Book> findByGenre(@Param("genre") String genre);

    @Insert("INSERT INTO BOOKS (ISBN,  TITLE, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME, GENRE, PRICE, DESCRIPTION ) " +
            "values(#{isbn}, #{title}, #{authorFirstName}, #{authorLastName}, #{genre}, #{price}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(Book book);

}

