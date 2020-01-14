package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.OrderItemProductKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OrderItemProductKeyMapper extends Mapper<OrderItemProductKey> {
    @Select("SELECT" +
            "   oook.oi_id oiid," +
            "   oopk.p_id pid" +
            " FROM" +
            "   o_order oo" +
            " LEFT JOIN o_order_orderitem_key oook ON oook.o_id = oo.id" +
            " LEFT JOIN o_orderitem_product_key oopk ON oopk.oi_id = oook.oi_id" +
            " WHERE oo.id = #{orderId}")
    List<Map<String, Object>> selectByOrderId(@Param("orderId") String orderId);

    @Delete("DELETE FROM o_orderitem_product_key WHERE oi_id = #{oiid}")
    void deleteByOrderItemId(@Param("oiid") int oiid);

    @Select("SELECT oopk.p_id pid FROM o_orderitem_product_key oopk WHERE oopk.oi_id = #{oiid}")
    Integer findProductIdByOrderItemId(@Param("oiid") Integer oiid);
}
