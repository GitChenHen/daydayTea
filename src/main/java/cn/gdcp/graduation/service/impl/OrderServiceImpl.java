package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.*;
import cn.gdcp.graduation.pojo.*;
import cn.gdcp.graduation.service.OrderService;
import cn.gdcp.graduation.utils.page.PageResponse;
import cn.gdcp.graduation.utils.page.PageUtils;
import cn.gdcp.graduation.utils.response.Result;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private UserOrderKeyMapper userOrderKeyMapper;
    @Autowired
    private OrderItemProductKeyMapper orderItemProductKeyMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private OrderOrderItemKeyMapper orderOrderItemKeyMapper;
    @Autowired
    private UserOrderItemKeyMapper userOrderItemKeyMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private ReviewProductKeyMapper reviewProductKeyMapper;
    @Autowired
    private UserReviewKeyMapper userReviewKeyMapper;

    @Override
    public Object list(Long start) {
        Long count = orderMapper.selectListCount();
        PageUtils pageUtils = new PageUtils();
        pageUtils.init(count, start);

        List<Map<String, Object>> maps = orderMapper.selectList(pageUtils.getIndex());
        for (Map<String, Object> map : maps) {
            Integer userId = userOrderKeyMapper.findUserIdByOrderId(map.get("id").toString());
            User user = userMapper.selectByPrimaryKey(userId);
            map.put("user", user);
            String statusDesc = "";
            String desc = "未知";
            switch (map.get("status").toString()) {
                case OrderServiceImpl.waitPay:
                    desc = "待付款";
                    break;
                case OrderServiceImpl.waitDelivery:
                    desc = "待发货";
                    break;
                case OrderServiceImpl.waitConfirm:
                    desc = "待收货";
                    break;
                case OrderServiceImpl.waitReview:
                    desc = "待评价";
                    break;
                case OrderServiceImpl.finish:
                    desc = "已完成";
                    break;
                case OrderServiceImpl.delete:
                    desc = "已刪除";
                    break;
                default:
                    desc = "未知";
            }
            statusDesc = desc;
            map.put("statusDesc", statusDesc);

            List<Map<String, Object>> orderProductOrderItemList =
                    orderItemProductKeyMapper.selectByOrderId(map.get("id").toString());
            List<OrderItem> orderItems = new ArrayList<>();
            for (Map<String, Object> orderItemMap : orderProductOrderItemList) {
                OrderItem orderItem = orderItemMapper.selectByPrimaryKey(orderItemMap.get("oiid"));
                Product product = productMapper.selectByPrimaryKey(orderItemMap.get("pid"));
                product.setFirstProductImage(productImageMapper.findProductImageIdByProductId(orderItemMap.get("pid"), "single"));
                orderItem.setProduct(product);
                orderItems.add(orderItem);
            }


            map.put("orderItems", orderItems);
        }

        return new PageResponse(start, 5l, pageUtils.getTotal(), maps);
    }

    @Override
    public void update(Order o) {
        orderMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    public Order get(int oid) {
        return orderMapper.selectByPrimaryKey(oid);
    }

    @Override
    public Object createOrder(Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user)
            return Result.fail("未登录");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        Integer orderId = orderMapper.findMaxOrderId();
        if (null == orderId) {
            order.setId(1);
        } else {
            order.setId(orderId + 1);
        }
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setStatus(OrderServiceImpl.waitPay);
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        orderMapper.insertSelective(order);
        float total = 0;
        Integer num = 0;
        for (OrderItem oi : ois) {
            Product product = productMapper.findProductByOrderItemId(oi.getId());
            product.setStock(product.getStock() - oi.getNumber());
            productMapper.updateByPrimaryKeySelective(product);
            num = num + oi.getNumber();
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            OrderOrderItemKey orderOrderItemKey = new OrderOrderItemKey();
            orderOrderItemKey.setoId(order.getId());
            orderOrderItemKey.setOiId(oi.getId());
            orderOrderItemKeyMapper.insertSelective(orderOrderItemKey);
            userOrderItemKeyMapper.deleteByOrderItemId(oi.getId());
        }
        UserOrderKey userOrderKey = new UserOrderKey();
        userOrderKey.setoId(order.getId());
        userOrderKey.setuId(user.getId());
        userOrderKeyMapper.insertSelective(userOrderKey);

        order.setTotalMoney(String.valueOf(total));
        order.setTotalNumber(String.valueOf(num));
        orderMapper.updateByPrimaryKeySelective(order);

        Map<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);

        return Result.success(map);
    }

    @Override
    public Object payed(int oid) {
        Order order = orderMapper.selectByPrimaryKey(oid);
        order.setStatus(OrderServiceImpl.waitDelivery);
        order.setPayDate(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    @Override
    public Object orderList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user)
            return Result.fail("未登录");
        List<Map<String,Object>> os = orderMapper.findOrdersByUserId(user.getId());
        for (Map<String,Object> map : os) {
            List<OrderItem> ois = orderItemMapper.findOrderItemByOrderId(map.get("id").toString());
            this.setProductByOrderItems(ois);
            map.put("ois",ois);
        }

        return os;
    }

    @Override
    public Object confirmPay(int oid) {
        Order o = orderMapper.selectByPrimaryKey(oid);
        List<OrderItem> ois = orderItemMapper.findOrderItemByOrderId(String.valueOf(o.getId()));
        this.setProductByOrderItems(ois);
        this.cacl(o);
        Map<String, Object> map = new HashMap<>();
        map.put("o",o);
        map.put("ois",ois);
        return map;
    }

    @Override
    public Object orderConfirmed(int oid) {
        Order o = orderMapper.selectByPrimaryKey(oid);
        o.setStatus(OrderServiceImpl.waitReview);
        o.setConfirmDate(new Date());
        orderMapper.updateByPrimaryKeySelective(o);
        return Result.success();
    }

    @Override
    public Object deleteOrder(int oid) {
        Order o = orderMapper.selectByPrimaryKey(oid);
        o.setStatus(OrderServiceImpl.delete);
        orderMapper.updateByPrimaryKeySelective(o);
        return Result.success();
    }

    @Override
    public Object review(int oid) {
        Order o = orderMapper.selectByPrimaryKey(oid);

        List<Map<String,Object>> ois = orderItemMapper.findDataByOrderId(String.valueOf(o.getId()));

        for (Map<String,Object> oi : ois) {
            Product product = productMapper.findProductByOrderItemId(Integer.parseInt(oi.get("id").toString()));
            product.setFirstProductImage(productImageMapper.findProductImageIdByProductId(
                    product.getId(),ProductImageServiceImpl.type_single));
            oi.put("product",product);
            Product p = ((Product)oi.get("product"));
            p.setSalesVolume(p.getSalesVolume() + Integer.parseInt(oi.get("number").toString()));
            List<Review> reviews = reviewMapper.findReviewByProductId(p.getId());
            productMapper.updateByPrimaryKeySelective(p);
            Integer reviewCount = reviews.size();
            oi.put("reviews",reviews);
            oi.put("reviewCount", reviewCount);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("ois", ois);
        map.put("o", o);
        return Result.success(map);
    }

    @Override
    public Object doreview(HttpSession session, int oid, int pid, String content) {
        Order o = orderMapper.selectByPrimaryKey(oid);
        o.setStatus(OrderServiceImpl.finish);
        orderMapper.updateByPrimaryKeySelective(o);

        Product p = productMapper.selectByPrimaryKey(pid);
        content = HtmlUtils.htmlEscape(content);

        User user =(User)  session.getAttribute("user");
        Integer rid = reviewMapper.findMaxId();
        if (null == rid){
            rid = 1;
        }else{
            rid = rid + 1;
        }
        Review review = new Review();
        review.setId(rid);
        review.setContent(content);
        review.setCreateTime(new Date());
        reviewMapper.insertSelective(review);
        ReviewProductKey reviewProductKey = new ReviewProductKey();
        reviewProductKey.setUrId(review.getId());
        reviewProductKey.setpId(p.getId());
        reviewProductKeyMapper.insertSelective(reviewProductKey);
        UserReviewKey userReviewKey = new UserReviewKey();
        userReviewKey.setuId(user.getId());
        userReviewKey.setUrId(review.getId());
        userReviewKeyMapper.insertSelective(userReviewKey);
        return Result.success();
    }

    public void cacl(Order o) {
        List<OrderItem> orderItems = orderItemMapper.findOrderItemByOrderId(String.valueOf(o.getId()));
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total+=orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
        }
        o.setTotalMoney(String.valueOf(total));
    }

    /**
     *  给每个订单项设置商品
     * @param ois
     */
    public void setProductByOrderItems( List<OrderItem> ois){
        for (OrderItem oi : ois) {
            Product product = productMapper.findProductByOrderItemId(oi.getId());
            product.setFirstProductImage(productImageMapper.findProductImageIdByProductId(
                    product.getId(),ProductImageServiceImpl.type_single));
            oi.setProduct(product);
        }
    }
}
