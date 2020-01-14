package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void add(Product bean);

    void delete(int id);

    void update(Product bean);

    Object list(int cid, Long start);

    String findCategoryByProductId(int id);

    Integer findCategoryIdByProductId(int pid);

    String findProductNameByProductId(int pid);

    Object findHotSale();

    Object findProductDetail(int pid);

    Object findProductByCid(int cid);

    Object get(int id);

    Object getProductDetailAndReview(int pid);

    Object search(String keyword);
}
