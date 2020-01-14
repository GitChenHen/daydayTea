package cn.gdcp.graduation.service;

import cn.gdcp.graduation.pojo.RegisterParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {
    Object list(Long start);

    Object register(RegisterParam registerParam, HttpServletRequest request) throws Exception;

    Object sendCode(HttpServletRequest request, String address, String username) throws Exception, javax.mail.MessagingException;

    Object login(Map<String, Object> condition, HttpServletRequest request,  HttpSession session) throws Exception;

}
