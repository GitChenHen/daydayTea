package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ProductMapper extends Mapper<Product> {
    @Select("SELECT COUNT(*) FROM c_product cp WHERE cp.cid = #{cid}")
    Long selectListCount(@Param("cid") int cid);

    @Select("SELECT * FROM c_product cp WHERE cp.cid = #{cid} ORDER BY cp.id DESC LIMIT #{index},5")
    List<Map<String,Object>> selectList(@Param("cid")int cid, @Param("index")Long index);

    @Select("SELECT" +
            "   cc.`name`" +
            " FROM" +
            "   c_product cp" +
            " LEFT JOIN c_category cc ON cc.id = cp.cid" +
            " WHERE" +
            "   cp.id = #{pid}")
    String findCategoryByProductId(@Param("pid")int pid);

    @Select("SELECT" +
            "   cc.id" +
            " FROM" +
            "   c_product cp" +
            " LEFT JOIN c_category cc ON cc.id = cp.cid" +
            " WHERE" +
            "   cp.id = #{pid}")
    Integer findCategoryIdByProductId(@Param("pid") int pid);

    @Select("SELECT" +
            "   cp.`name`" +
            " FROM" +
            "   c_product cp" +
            " WHERE" +
            "   cp.id = #{pid}")
    String findProductNameByProductId(@Param("pid")int pid);

    @Select("SELECT * FROM c_product cp WHERE cp.cid = #{cid}")
    List<Map<String, Object>> findProductByCid(@Param("cid") int cid);

    @Select("SELECT" +
            " *" +
            " FROM" +
            " c_product cp" +
            " ORDER BY cp.sales_volume DESC" +
            " LIMIT 0,8")
    List<Map<String, Object>> findHotSale();

    @Select("SELECT" +
            "   cp.*" +
            " FROM" +
            "   c_product cp" +
            " LEFT JOIN o_orderitem_product_key oopk ON oopk.p_id = cp.id" +
            " WHERE oopk.oi_id = #{oiid}")
    Product findProductByOrderItemId(@Param("oiid") Integer oiid);

    @Select("SELECT * FROM c_product cp WHERE cp.`name` LIKE #{keyword} LIMIT #{pageIndex},#{pageCount}")
    List<Map<String, Object>> search(String keyword, int pageIndex, int pageCount);
}
