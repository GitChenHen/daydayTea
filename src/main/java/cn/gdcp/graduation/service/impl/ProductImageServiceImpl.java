package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.ProductImageMapper;
import cn.gdcp.graduation.dao.ProductProductImageKeyMapper;
import cn.gdcp.graduation.pojo.ProductImage;
import cn.gdcp.graduation.pojo.ProductProductImageKey;
import cn.gdcp.graduation.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductImageServiceImpl implements ProductImageService {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private ProductProductImageKeyMapper productProductImageKeyMapper;

    @Override
    public List<ProductImage> listSingleProductImages(int pid, String type) {
        return productImageMapper.listSingleProductImages(pid,type);
    }

    @Override
    public List<ProductImage> listDetailProductImages(int pid,String type) {
        return productImageMapper.listDetailProductImages(pid,type);
    }

    @Override
    public void add(ProductImage bean) {
        productImageMapper.insertSelective(bean);
        ProductProductImageKey productProductImageKey = new ProductProductImageKey();
        productProductImageKey.setpId(bean.getPid());
        productProductImageKey.setPiId(bean.getId());
        productProductImageKeyMapper.insertSelective(productProductImageKey);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        productProductImageKeyMapper.deleteByProductImageId(id);
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer findProductImageIdByProductId(Integer pid, String single) {
        return productImageMapper.findProductImageIdByProductId(pid, single);
    }

    @Override
    public List<Integer> findProductImageIdsByProductId(Integer pid, String single) {
        return productImageMapper.findProductImageIdsByProductId(pid,single);
    }

}
