package org.example.sport;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SportMapper {
    @Select("select id, name from sport order by name")
    List<Sport> findAll();

    @Insert("insert into sport(id, name) values(#{id}, #{name})")
    int insert(Sport sport);

    @Update("update sport set name = #{name} where id = #{id}")
    int update(Sport sport);

    @Delete("delete from sport where id = #{id}")
    int delete(Sport sport);
}
