package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.ProductImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ProductImageMapper extends Mapper<ProductImage> {
    @Select("SELECT" +
            " *" +
            " FROM" +
            " c_productimage cpi" +
            " WHERE" +
            " cpi.type = #{type} AND cpi.pid = #{pid}" +
            " ORDER BY cpi.id DESC")
    List<ProductImage> listSingleProductImages(@Param("pid") int pid, @Param("type") String type);

    @Select("SELECT" +
            " *" +
            " FROM" +
            " c_productimage cpi" +
            " WHERE" +
            " cpi.type = #{type} AND cpi.pid = #{pid}" +
            " ORDER BY cpi.id DESC")
    List<ProductImage> listDetailProductImages(@Param("pid")int pid,@Param("type") String type);

    @Select("SELECT" +
            " pi.id piid" +
            " FROM" +
            " c_productimage pi" +
            " WHERE" +
            " pi.pid = #{pid}" +
            " AND pi.type = #{type}" +
            " ORDER BY" +
            " pi.id DESC" +
            " LIMIT 0," +
            " 1;")
    Integer findProductImageIdByProductId(@Param("pid")Object pid, @Param("type")String single);

    @Delete("DELETE FROM c_propertyvalue WHERE pid = #{pid}")
    void deleteByPid(@Param("pid") int pid);

    @Select("SELECT cpi.id FROM c_productimage cpi WHERE cpi.pid = #{pid} AND cpi.type = #{single} ORDER BY cpi.id DESC")
    List<Integer> findProductImageIdsByProductId(@Param("pid") Integer pid, @Param("single") String single);
}
