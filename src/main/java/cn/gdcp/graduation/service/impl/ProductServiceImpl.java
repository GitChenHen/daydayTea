package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.ProductImageMapper;
import cn.gdcp.graduation.dao.ProductMapper;
import cn.gdcp.graduation.dao.PropertyValueMapper;
import cn.gdcp.graduation.dao.ReviewMapper;
import cn.gdcp.graduation.pojo.Product;
import cn.gdcp.graduation.pojo.PropertyValue;
import cn.gdcp.graduation.pojo.Review;
import cn.gdcp.graduation.service.ProductService;
import cn.gdcp.graduation.utils.page.PageResponse;
import cn.gdcp.graduation.utils.page.PageUtils;
import cn.gdcp.graduation.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private PropertyValueMapper propertyValueMapper;
    @Autowired
    private ReviewMapper reviewMapper;


    @Override
    public void add(Product bean) {
        productMapper.insertSelective(bean);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPid(id);
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product bean) {
        productMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public Object list(int cid, Long start) {
        PageUtils pageUtils = new PageUtils();
        Long count = productMapper.selectListCount(cid);
        pageUtils.init(count,start);
        List<Map<String,Object>> data = productMapper.selectList(cid, pageUtils.getIndex());
        for (Map<String, Object> map : data){
            Integer firstProductImage = productImageMapper.findProductImageIdByProductId(map.get("id"),"single");
            map.put("firstProductImage",firstProductImage);
        }
        return new PageResponse(start,5l,pageUtils.getTotal(),data);
    }

    @Override
    public String findCategoryByProductId(int id) {
        return productMapper.findCategoryByProductId(id);
    }

    @Override
    public Integer findCategoryIdByProductId(int pid) {
        return productMapper.findCategoryIdByProductId(pid);
    }

    @Override
    public String findProductNameByProductId(int pid) {
        return productMapper.findProductNameByProductId(pid);
    }


    @Override
    public Object findHotSale() {
        List<Map<String, Object>> ps = productMapper.findHotSale();
        this.setFirstProductImage(ps);
        Map<String, Object> map = new HashMap<>();
        map.put("ps", ps);
        return Result.success(map);
    }

    @Override
    public Object findProductDetail(int pid) {
        Product product = productMapper.selectByPrimaryKey(pid);
        product.setFirstProductImage(productImageMapper.findProductImageIdByProductId(pid, ProductImageServiceImpl.type_single));
        List<Integer> pis = productImageMapper.findProductImageIdsByProductId(pid, ProductImageServiceImpl.type_single);
        Integer reviewCount = reviewMapper.findCountByProductId(product.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("pis", pis);
        map.put("reviewCount",reviewCount);
        return Result.success(map);
    }

    @Override
    public Object findProductByCid(int cid) {
        List<Map<String, Object>> ps = productMapper.findProductByCid(cid);
        this.setFirstProductImage(ps);
        Map<String, Object> map = new HashMap<>();
        map.put("ps", ps);
        return Result.success(map);
    }

    @Override
    public Object get(int id) {
        Product bean=productMapper.selectByPrimaryKey(id);
        String categoryName = productMapper.findCategoryByProductId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("bean",bean);
        map.put("categoryName",categoryName);
        return Result.success(map);
    }

    @Override
    public Object getProductDetailAndReview(int pid) {
        PropertyValue pv = propertyValueMapper.get(pid);
        List<Review> rs = reviewMapper.findReviewByProductId(pid);
        Map<String, Object> map = new HashMap<>();
        map.put("pv", pv);
        map.put("rs", rs);
        return Result.success(map);
    }

    @Override
    public Object search(String keyword) {
        /*String key = "";
        if(null==keyword){
            key = "%%";
        }else{
            char[] arr = keyword.toCharArray();
            for (int i = 0; i < arr.length; i++){
                key += "%" + arr[i];
            }
            key += "%";
        }*/
        keyword = "%" + keyword + "%";

        List<Map<String, Object>> ps= productMapper.search(keyword,0,50);
        this.setFirstProductImage(ps);
        return Result.success(ps);
    }

    /**
     *  设置产品第一张图片
     * @param data
     */
    private void setFirstProductImage(List<Map<String, Object>> data) {
        for (Map<String, Object> map : data){
            Integer firstProductImage = productImageMapper.findProductImageIdByProductId(map.get("id"),"single");
            map.put("firstProductImage",firstProductImage);
        }
    }

}
