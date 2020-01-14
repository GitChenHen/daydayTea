package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.*;
import cn.gdcp.graduation.pojo.*;
import cn.gdcp.graduation.service.OrderItemService;
import cn.gdcp.graduation.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private OrderItemProductKeyMapper orderItemProductKeyMapper;
    @Autowired
    private UserOrderItemKeyMapper userOrderItemKeyMapper;

    @Override
    public int buyoneAndAddCart(int pid, int num, HttpSession session) {
        Product product = productMapper.selectByPrimaryKey(pid);
        int oiid = 0;

        User user =(User)  session.getAttribute("user");
        boolean found = false;
        List<Map<String,Object>> ois = orderItemMapper.findOrderItemByUsername(user.getName());
        for (Map<String,Object> map : ois) {
            if(Integer.parseInt(map.get("pid").toString()) == product.getId()){
                map.put("number", Integer.parseInt(map.get("number").toString()) + num);
                orderItemMapper.updateByOrderItemId(
                        Integer.parseInt(map.get("id").toString()),map.get("number").toString());
                found = true;
                oiid = Integer.parseInt(map.get("id").toString());
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            Integer orderId = orderItemMapper.findMaxId();
            if (null == orderId){
                oi.setId(1);
            }else {
                oi.setId(orderId + 1);
            }
            oi.setProduct(product);
            oi.setNumber(num);
            orderItemMapper.add(oi);
            OrderItemProductKey orderItemProductKey = new OrderItemProductKey();
            orderItemProductKey.setOiId(oi.getId());
            orderItemProductKey.setpId(product.getId());
            orderItemProductKeyMapper.insertSelective(orderItemProductKey);
            UserOrderItemKey userOrderItemKey = new UserOrderItemKey();
            userOrderItemKey.setuId(user.getId());
            userOrderItemKey.setOiId(oi.getId());
            userOrderItemKeyMapper.insertSelective(userOrderItemKey);
            oiid = oi.getId();
        }
        return oiid;
    }

    @Override
    public List<OrderItem> findOrderItemsByUser(User user) {
        List<OrderItem> ois = orderItemMapper.findOrderItemsByUsername(user.getName());
        for (OrderItem oi : ois) {
            Product product = productMapper.findProductByOrderItemId(oi.getId());
            Integer firstId = productImageMapper.findProductImageIdByProductId(product.getId(),ProductImageServiceImpl.type_single);
            product.setFirstProductImage(firstId);
            oi.setProduct(product);
        }
        return ois;
    }

    @Override
    public Object changeOrderItem(int pid, int num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user){
            return Result.fail("未登录");
        }
        List<Map<String,Object>> ois = orderItemMapper.findOrderItemByUsername(user.getName());
        for (Map<String,Object> map : ois){
            if (Integer.parseInt(map.get("pid").toString()) == pid){
                map.put("number",num);
                orderItemMapper.updateByOrderItemId(
                        Integer.parseInt(map.get("id").toString()),map.get("number").toString());
                break;
            }
        }
        return Result.success();
    }

    @Override
    public Object deleteOrderItem(HttpSession session, int oiid) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        List<OrderItem> ois = this.findOrderItemsByUser(user);
        for (OrderItem oi : ois) {
            if (oi.getId() == oiid) {
                orderItemProductKeyMapper.deleteByOrderItemId(oiid);
                userOrderItemKeyMapper.deleteByOrderItemId(oiid);
                orderItemMapper.deleteByOrderItemId(oiid);
                break;
            }
        }
        return Result.success();
    }

    @Override
    public Object buy(String[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiid) {
            Integer id = Integer.parseInt(strid);
            OrderItem oi= orderItemMapper.selectByPrimaryKey(id);
            Product product = productMapper.findProductByOrderItemId(Integer.parseInt(strid));
            total += product.getPromotePrice()*oi.getNumber();
            product.setFirstProductImage(productImageMapper.findProductImageIdByProductId(product.getId(), ProductImageServiceImpl.type_single));
            oi.setProduct(product);
            orderItems.add(oi);
        }

        session.setAttribute("ois", orderItems);

        Map<String,Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return map;
    }
}
