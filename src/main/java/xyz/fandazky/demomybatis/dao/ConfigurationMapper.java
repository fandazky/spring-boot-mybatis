package xyz.fandazky.demomybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.fandazky.demomybatis.model.Configuration;

@Mapper
public interface ConfigurationMapper {

    @Select("SELECT * FROM CONFIGURATIONS WHERE CONFIG_KEY = #{key}")
    Configuration getConfigByKey(@Param("key") String key);
}