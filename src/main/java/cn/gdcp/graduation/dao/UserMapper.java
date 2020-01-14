package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    @Select("SELECT COUNT(*) FROM u_user uu ")
    Long selectListCount();

    @Select("SELECT * FROM u_user uu ORDER BY uu.id DESC LIMIT #{index},5")
    List<User> selectList(@Param("index") Long index);

    @Select("SELECT * FROM u_user uu WHERE uu.name = #{username}")
    User findUserByUsername(@Param("username") String username);
}
