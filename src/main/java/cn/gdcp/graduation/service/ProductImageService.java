package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> listSingleProductImages(int pid, String type);

    List<ProductImage> listDetailProductImages(int pid,String type);

    void add(ProductImage bean);

    ProductImage get(int id);

    void delete(int id);

    Integer findProductImageIdByProductId(Integer pid, String single);

    List<Integer> findProductImageIdsByProductId(Integer pid, String single);
}
