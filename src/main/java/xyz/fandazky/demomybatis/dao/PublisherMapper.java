package xyz.fandazky.demomybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.fandazky.demomybatis.model.Publisher;

import java.util.List;

@Mapper
public interface PublisherMapper {

    @Select("SELECT *, PHONE as phoneNumber from PUBLISHERS") //SQL
    List<Publisher> findAll();
}

