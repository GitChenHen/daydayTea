package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.Order;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Object list(Long start);

    void update(Order o);

    Order get(int oid);

    Object createOrder(Order order, HttpSession session);

    Object payed(int oid);

    Object orderList(HttpSession session);

    Object confirmPay(int oid);

    Object orderConfirmed(int oid);

    Object deleteOrder(int oid);

    Object review(int oid);

    Object doreview(HttpSession session, int oid, int pid, String content);
}
