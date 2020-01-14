package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends Mapper<Order> {
    @Select("SELECT COUNT(*) FROM o_order oo")
    Long selectListCount();

    @Select("SELECT * FROM o_order oo ORDER BY oo.id DESC LIMIT #{index},5")
    List<Map<String, Object>> selectList(@Param("index") Long index);

    @Select("SELECT MAX(id) FROM o_order")
    Integer findMaxOrderId();

    @Select("SELECT" +
            "   *" +
            " FROM" +
            "   o_order oo" +
            " LEFT JOIN u_user_order_key uuok  ON uuok.o_id = oo.id" +
            " WHERE" +
            "   uuok.u_id = #{userId}" +
            "   AND oo.status != 'delete'")
    List<Map<String,Object>> findOrdersByUserId(@Param("userId") Integer userId);
}
