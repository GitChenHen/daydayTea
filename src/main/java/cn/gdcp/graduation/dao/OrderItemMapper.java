package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.OrderItem;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OrderItemMapper extends Mapper<OrderItem> {

    @Select("SELECT" +
            "   ooi.id," +
            "   ooi.number," +
            "   cp.id pid" +
            " FROM" +
            "   o_orderitem ooi" +
            " LEFT JOIN o_orderitem_product_key oopk ON oopk.oi_id = ooi.id" +
            " LEFT JOIN c_product cp ON cp.id = oopk.p_id" +
            " LEFT JOIN u_user_orderitem_key uuok ON uuok.oi_id = ooi.id" +
            " LEFT JOIN u_user uu ON uu.id = uuok.u_id" +
            " WHERE uu.`name` = #{name}")
    List<Map<String,Object>> findOrderItemByUsername(@Param("name") String name);

    @Select("SELECT MAX(id) FROM o_orderitem")
    Integer findMaxId();

    @Insert("INSERT INTO o_orderitem (id,number) VALUES (#{oi.id},#{oi.number})")
    void add(@Param("oi") OrderItem oi);

    @Update("UPDATE o_orderitem SET number = #{number} WHERE id = #{id}")
    void updateByOrderItemId(@Param("id") int id, @Param("number") String number);

    @Select("SELECT" +
            "   ooi.*" +
            " FROM" +
            "   o_orderitem ooi" +
            " LEFT JOIN u_user_orderitem_key uuok ON uuok.oi_id = ooi.id" +
            " LEFT JOIN u_user uu ON uu.id = uuok.u_id" +
            " WHERE uu.`name` = #{name}")
    List<OrderItem> findOrderItemsByUsername(@Param("name") String name);

    @Delete("DELETE FROM o_orderitem WHERE id = #{oiid}")
    void deleteByOrderItemId(@Param("oiid") int oiid);

    @Select("SELECT" +
            "   oo.*" +
            " FROM" +
            "   o_orderitem oo " +
            " LEFT JOIN o_order_orderitem_key ook ON ook.oi_id = oo.id " +
            " WHERE" +
            "   ook.o_id = #{orderId}")
    List<OrderItem> findOrderItemByOrderId(@Param("orderId") String orderId);

    @Select("SELECT" +
            "   oo.*" +
            " FROM" +
            "   o_orderitem oo " +
            " LEFT JOIN o_order_orderitem_key ook ON ook.oi_id = oo.id " +
            " WHERE" +
            "   ook.o_id = #{orderId}")
    List<Map<String, Object>> findDataByOrderId(@Param("orderId")String orderId);
}
