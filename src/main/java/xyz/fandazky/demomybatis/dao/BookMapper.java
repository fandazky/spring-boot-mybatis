package xyz.fandazky.demomybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.fandazky.demomybatis.model.Book;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("SELECT * FROM BOOKS") //SQL
    List<Book> findAll();

    @Select("SELECT * FROM BOOKS WHERE GENRE = #{genre}")
    List<Book> findByGenre(@Param("genre") String genre);
}

