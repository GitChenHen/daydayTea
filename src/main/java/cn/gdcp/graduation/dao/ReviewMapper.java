package cn.gdcp.graduation.dao;

import cn.gdcp.graduation.pojo.Review;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReviewMapper extends Mapper<Review> {
    @Select("SELECT" +
            "   ur.*" +
            " FROM" +
            "   u_review ur" +
            " LEFT JOIN u_review_product_key urpk ON urpk.ur_id = ur.id" +
            " WHERE urpk.p_id = #{pid}")
    List<Review> findReviewByProductId(@Param("pid") Integer pid);

    @Select("SELECT MAX(id) FROM u_review")
    Integer findMaxId();

    @Select("SELECT COUNT(*) FROM u_review_product_key rpk WHERE rpk.p_id = #{pid}")
    Integer findCountByProductId(@Param("pid")Integer pid);
}
