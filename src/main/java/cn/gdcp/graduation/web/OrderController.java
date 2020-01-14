package cn.gdcp.graduation.web;

import cn.gdcp.graduation.pojo.Order;
import cn.gdcp.graduation.service.OrderItemService;
import cn.gdcp.graduation.service.OrderService;
import cn.gdcp.graduation.service.impl.OrderServiceImpl;
import cn.gdcp.graduation.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public Object list(@RequestParam(value = "start", defaultValue = "1") Long start) throws Exception {
        start = start<1?1:start;
        return orderService.list(start);
    }
    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderServiceImpl.waitConfirm);
        orderService.update(o);
        return Result.success();
    }
}
