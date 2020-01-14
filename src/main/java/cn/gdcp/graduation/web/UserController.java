package cn.gdcp.graduation.web;

import cn.gdcp.graduation.pojo.LoginParam;
import cn.gdcp.graduation.pojo.RegisterParam;
import cn.gdcp.graduation.pojo.SendCodeParam;
import cn.gdcp.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Object list(Long start) {
        return userService.list(start);
    }

}
