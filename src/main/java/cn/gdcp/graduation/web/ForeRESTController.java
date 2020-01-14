package cn.gdcp.graduation.web;

import cn.gdcp.graduation.pojo.*;
import cn.gdcp.graduation.service.*;
import cn.gdcp.graduation.service.impl.ProductImageServiceImpl;
import cn.gdcp.graduation.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/forehome")
    public Object home() {
        List<Category> cs = categoryService.listCategory();
        Map<String, Object> map = new HashMap<>();
        map.put("cs", cs);
        return map;
    }

    @PostMapping("/find_product_by_cid/{cid}")
    public Object findProductByCid(@PathVariable("cid") int cid) {
        return productService.findProductByCid(cid);
    }

    @GetMapping("/find_hot_sale")
    public Object findHotSale() {
        return productService.findHotSale();
    }

    @GetMapping("/product_detail/{pid}")
    public Object findProductDetail(@PathVariable("pid") int pid) throws Exception {
        return productService.findProductDetail(pid);
    }

    @GetMapping("/sort_category")
    public Object sortCategory() {
        List<Category> cs = categoryService.listCategory();
        Map<String, Object> map = new HashMap<>();
        map.put("cs", cs);
        return map;
    }

    @GetMapping("/product_detail_and_review/{pid}")
    public Object getProductDetailAndReview(@PathVariable("pid") int pid) {
        PropertyValue pv = propertyValueService.get(pid);
        Map<String, Object> map = new HashMap<>();
        map.put("pv", pv);
        return productService.getProductDetailAndReview(pid);
    }

    @GetMapping("/product_img/{pid}")
    public Object getProductDetailImgs(@PathVariable("pid") int pid) {
        List<Integer> pids = productImageService.findProductImageIdsByProductId(pid, ProductImageServiceImpl.type_detail);
        Map<String, Object> map = new HashMap<>();
        map.put("pids", pids);
        return map;
    }

    @PostMapping("/fore_register")
    public Object Register(@RequestBody RegisterParam registerParam, HttpServletRequest request) throws Exception {
        return userService.register(registerParam, request);
    }

    @PostMapping("/fore_send_code")
    public Object sendCode(HttpServletRequest request, @RequestBody SendCodeParam sendCodeParam) throws Exception {
        return userService.sendCode(request, sendCodeParam.getEmail(), sendCodeParam.getName());
    }

    @PostMapping("/fore_login")
    public Object login(HttpServletRequest request, HttpSession session, @RequestBody LoginParam loginParam) throws Exception {
        Map<String, Object> condition = new HashMap<>();
        condition.put("username", loginParam.getName());
        condition.put("password", loginParam.getPassword());
        return userService.login(condition, request, session);
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user)
            return Result.success();
        return Result.fail("未登录");
    }

    @GetMapping("forebuyone")
    public Object buyone(int pid, int num, HttpSession session) {
        return orderItemService.buyoneAndAddCart(pid, num, session);
    }

    @GetMapping("foreaddCart")
    public Object addCart(int pid, int num, HttpSession session) {
        orderItemService.buyoneAndAddCart(pid, num, session);
        return Result.success();
    }

    @GetMapping("forecart")
    public Object cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.findOrderItemsByUser(user);
        return ois;
    }

    @GetMapping("/forechangeOrderItem")
    public Object changeOrderItem(int pid, int num, HttpSession session) {
        orderItemService.changeOrderItem(pid, num, session);
        return Result.success();
    }

    @GetMapping("/foredeleteOrderItem")
    public Object deleteOrderItem(HttpSession session, int oiid) {
        return orderItemService.deleteOrderItem(session, oiid);
    }

    @GetMapping("forebuy")
    public Object buy(String[] oiid,HttpSession session){
        return Result.success(orderItemService.buy(oiid, session));
    }

    @PostMapping("forecreateOrder")
    public Object createOrder(@RequestBody Order order,HttpSession session){
        return orderService.createOrder(order, session);
    }

    @GetMapping("forepayed")
    public Object payed(int oid) {
        return orderService.payed(oid);
    }

    @GetMapping("foreorder")
    public Object orderList(HttpSession session) {
        return orderService.orderList(session);
    }

    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid) {
        return orderService.confirmPay(oid);
    }

    @GetMapping("foreorderConfirmed")
    public Object orderConfirmed( int oid) {
        return orderService.orderConfirmed(oid);
    }

    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid){
        return orderService.deleteOrder(oid);
    }

    @GetMapping("forereview")
    public Object review(int oid) {
        return orderService.review(oid);
    }

    @PostMapping("foredoreview")
    public Object doreview( HttpSession session,int oid,int pid,String content) {
        return orderService.doreview(session, oid, pid, content);
    }

    @PostMapping("foresearch")
    public Object search(String keyword){
        return productService.search(keyword);
    }
}
