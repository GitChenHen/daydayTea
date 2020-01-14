package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CategoryMapper extends Mapper<Category> {

    @Select("SELECT * FROM c_category cc ORDER BY cc.id DESC LIMIT #{index},5")
    List<Category> selectList(@Param("index") Long index);

    @Select("SELECT COUNT(*) FROM c_category cc ")
    Long selectListCount();

    @Select("SELECT * FROM c_category")
    List<Map<String, Object>> findCategoryList();
}
