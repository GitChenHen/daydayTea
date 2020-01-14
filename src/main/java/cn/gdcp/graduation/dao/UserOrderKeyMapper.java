package cn.gdcp.graduation.dao;


import cn.gdcp.graduation.pojo.UserOrderKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserOrderKeyMapper extends Mapper<UserOrderKey> {

    @Select("SELECT uok.u_id FROM u_user_order_key uok WHERE uok.o_id = #{orderId}")
    Integer findUserIdByOrderId(@Param("orderId") String orderId);
}
