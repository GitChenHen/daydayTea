package cn.gdcp.graduation.web;

import cn.gdcp.graduation.pojo.PropertyValue;
import cn.gdcp.graduation.service.ProductService;
import cn.gdcp.graduation.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;


    @GetMapping("/products/{pid}/propertyValues")
    public Object get(@PathVariable("pid") int pid) throws Exception {
        PropertyValue bean = propertyValueService.get(pid);
        Integer cid = productService.findCategoryIdByProductId(pid);
        String categoryName = productService.findCategoryByProductId(pid);
        String productName = productService.findProductNameByProductId(pid);
        Map<String, Object> map = new HashMap<>();
        if (null == bean){
            PropertyValue propertyValue = new PropertyValue();
            propertyValue.setId(null);
            propertyValue.setPid(pid);
            propertyValueService.insert(propertyValue);
            map.put("bean",propertyValue);
        }else{
            map.put("bean",bean);
        }
        map.put("cid",cid);
        map.put("productName",productName);
        map.put("categoryName",categoryName);
        return map;
    }

    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }
}
