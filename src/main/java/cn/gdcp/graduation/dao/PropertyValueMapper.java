package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.PropertyValue;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface PropertyValueMapper extends Mapper<PropertyValue> {
    @Select("SELECT * FROM c_propertyvalue cpv WHERE cpv.pid = #{pid}")
    PropertyValue get(@Param("pid") int pid);

}
