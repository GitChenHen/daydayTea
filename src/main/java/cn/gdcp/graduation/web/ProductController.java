package cn.gdcp.graduation.web;

import cn.gdcp.graduation.pojo.Product;
import cn.gdcp.graduation.service.CategoryService;
import cn.gdcp.graduation.service.ProductService;
import cn.gdcp.graduation.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private PropertyValueService propertyValueService;

    @GetMapping("/categories/{cid}/products")
    public Object list(@PathVariable("cid") int cid, Long start) {
        return productService.list(cid, start);
    }

    @GetMapping("/products/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        return productService.get(id);
    }

    @PostMapping("/products")
    public Object add(@RequestBody Product bean) throws Exception {
        bean.setCreateTime(new Date());
        productService.add(bean);
        return bean;
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    public Object update(@RequestBody Product bean) throws Exception {
        productService.update(bean);
        return bean;
    }
}
