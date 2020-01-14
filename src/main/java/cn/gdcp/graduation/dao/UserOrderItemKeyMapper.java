package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.UserOrderItemKey;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

public interface UserOrderItemKeyMapper extends Mapper<UserOrderItemKey> {

    @Delete("DELETE FROM u_user_orderitem_key WHERE oi_id = #{oiid}")
    void deleteByOrderItemId(int oiid);
}
