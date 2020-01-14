package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.OrderItem;
import cn.gdcp.graduation.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderItemService {

    int buyoneAndAddCart(int pid, int num, HttpSession session);

    List<OrderItem> findOrderItemsByUser(User user);

    Object changeOrderItem(int pid, int num, HttpSession session);

    Object deleteOrderItem(HttpSession session, int oiid);

    Object buy(String[] oiid, HttpSession session);
}
