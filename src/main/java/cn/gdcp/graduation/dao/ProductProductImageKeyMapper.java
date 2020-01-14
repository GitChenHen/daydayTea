package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.ProductProductImageKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ProductProductImageKeyMapper extends Mapper<ProductProductImageKey> {
    @Delete("DELETE FROM\tc_product_productimage_key WHERE pi_id = #{piid}")
    void deleteByProductImageId(@Param("piid") int piid);
}
