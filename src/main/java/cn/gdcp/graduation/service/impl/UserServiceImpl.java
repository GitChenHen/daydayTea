package cn.gdcp.graduation.service.impl;

import cn.gdcp.graduation.dao.UserMapper;
import cn.gdcp.graduation.pojo.RegisterParam;
import cn.gdcp.graduation.pojo.User;
import cn.gdcp.graduation.service.UserService;
import cn.gdcp.graduation.utils.MailUtils;
import cn.gdcp.graduation.utils.Md5Utils;
import cn.gdcp.graduation.utils.page.PageResponse;
import cn.gdcp.graduation.utils.page.PageUtils;
import cn.gdcp.graduation.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    // 获取发送人邮箱
    @Value("${spring.mail.username}")
    private String springMailUsername;

    @Override
    public Object list(Long start) {
        Long count = userMapper.selectListCount();
        PageUtils pageUtils = new PageUtils();
        pageUtils.init(count,start);
        return new PageResponse(
                start,
                5l,
                pageUtils.getTotal(),
                userMapper.selectList(pageUtils.getIndex()));
    }

    @Override
    public Object register(RegisterParam registerParam, HttpServletRequest request) throws Exception {
        // 数据校验
        Object result = this.checkRegister(registerParam, request);
        if (result != null) {
            return result;
        }
        // 判断用户提交的账号是否唯一性
        User user = new User();
        user.setName(registerParam.getName());
        List<User> userList = userMapper.select(user);
        if (userList != null && !userList.isEmpty()) {
            return "账号已经存在,请输入新的账户";
        }
        saveUser(registerParam, user);
        return Result.success();
    }
    /*
     * 数据校验
     */
    private Object checkRegister(RegisterParam registerParam,HttpServletRequest request) {
        String username = registerParam.getName();
        String password = registerParam.getPassword();
        String code = registerParam.getCode();
        String phone = registerParam.getPhone();
        String email = registerParam.getEmail();
        // 校验账号的合法性
//        if (username == null || username.isEmpty()) {
//            return "账号不能为空";
//        }
//        if (!username.matches("[0-9a-zA-Z]{6,20}")) {
//            return "账号不合法，必须6-20的数字或字母";
//        }
//        if(username.length()>12||username.length()<3){
//            return "账号不合法，必须3-12的数字或字母";
//        }
//        // 校验密码合法性
//        if (password == null || password.isEmpty()) {
//            return "密码不能为空";
//        }
//        if (!password.matches("[0-9a-zA-Z]{6,16}")) {
//            return "密码不合法，必须6-16的数字或字母";
//        }
//        if(email==null || email.isEmpty() ) {
//            return "邮箱不能为空";
//        }
//        if(!email.matches("[\\da-z_]+@[\\da-z]+\\.[a-z]{1,10}")) {
//            return "邮箱不合法";
//        }
//        if(phone == null || phone.isEmpty()) {
//            return "手机号不能为空";
//        }
//        if(phone.matches("/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$/")) {
//            return "手机号格式不对";
//        }
//
//        if(code==null || code.isEmpty()) {
//            return "验证码不能为空";
//        }
    //     校验邮箱验证码合法性
        HttpSession sessioncode2 = request.getSession();

        Object codeObj = sessioncode2.getAttribute(username);
        if(codeObj==null) {
            return "验证码过期";
        }
        if (!codeObj.toString().equals(code)) {
            return "验证码错误";
        }
        return null;
    }
    private void saveUser(RegisterParam registerParam, User user) throws Exception {
        // 保存用户数据
        user.setPassword(Md5Utils.getPasswrod(registerParam.getPassword(), registerParam.getName()));
        // user.setPassword(registerParam.getPassword());
        // 通过uuid获取唯一的值
        user.setId(null);
        user.setName(registerParam.getName());
        user.setEmail(registerParam.getEmail());
        user.setPhone(registerParam.getPhone());
        userMapper.insertSelective(user);
    }

    @Override
    public Object sendCode(HttpServletRequest request, String address, String username) throws MessagingException {
        HttpSession session = request.getSession();
        // result 校验结果
        Object result = this.checkSendCode(request, address, username);
        if (result != null) {
            return result;
        }
        //产生的验证码存入session中
        String randomCodeForSession = this.randomCodeForSession(session, username);
        // 将随机生成的验证码通过邮箱形式发送出去
        this.sendCodeForMail(address, username, randomCodeForSession);
        return "发送成功";
    }
    /*
     * 随机获取一个验证码
     */
    private String randomCodeForSession(HttpSession requestsession, String username) {
        // 随机获取一个验证码，用于发短信
        int code = new Random().nextInt(1000000); // 随机生成0-1000000的数
        String codeformat = String.format("%06d", code); // 如果没有6位数，就用0补齐
        requestsession.setAttribute(username, codeformat);
        requestsession.setMaxInactiveInterval(360);
        return codeformat;
    }
    /*
     * 调用邮箱接口发送邮箱
     */
    public void sendCodeForMail(String address, String username, String codeformat) throws MessagingException {
        String mailContent = "您正在注册" + username + "账户" + "验证码为:" + codeformat;
        MailUtils.sendHtmlMail(javaMailSender, springMailUsername, address, MailUtils.CODE_TITLE, mailContent);
    }

    /*
     * 数据的校验方法
     */
    private Object checkSendCode(HttpServletRequest request, String address, String username) {
        // 数据校验
        // 防止重复发送验证码
        HttpSession session = request.getSession();
        Object codeObj = session.getAttribute(username);
        if (codeObj != null) {
            return "你已经发送了邮箱,请稍后再试试";
        }
        if (address == null || address.isEmpty()) {
            return "邮箱不能为空";
        }
        if (!address.matches("[\\da-z_]+@[\\da-z]+\\.[a-z]{1,10}")) {
            return "邮箱不合法,请输入正确的邮箱!";
        }
        if (username == null || username.isEmpty()) {
            return "用户名不能为空,请重新输入";
        }
//        if (!username.matches("[0-9a-zA-Z]{6,20}")) {
//            return "账号不合法,账号长度必须是6-20位,且只能是大小写字母+数字";
//        }
        return null;
    }

    @Override
    public Object login(Map<String, Object> condition,HttpServletRequest request, HttpSession session) throws Exception {
        Object checkLogin = this.checkLogin(condition);
        if(checkLogin != null) {
            return checkLogin;
        }
        String username = condition.get("username").toString();
        String password = condition.get("password").toString();
        //账号
        User user = userMapper.findUserByUsername(username);
        if(user ==null) {
            return Result.fail("用户名或者密码错误");
        }
        //匹配密码
        String passwrodMd5 = Md5Utils.getPasswrod(password, username);
        if(!passwrodMd5.equals(user.getPassword())) {
            return Result.fail("用户名或密码错误");
        }
        session.setAttribute("user",user);
        return Result.success();
    }

    public Object checkLogin(Map<String, Object> condition ) {
        Object passwordobject = condition.get("password");
        Object usernameobject = condition.get("username");
        if(usernameobject==null) {
            return Result.fail("请输入账号");
        }
        if(passwordobject==null) {
            return Result.fail("请输入密码");
        }
//        if(!usernameobject.toString().matches("[0-9a-zA-Z]{6,16}")) {
//            return Result.fail("账号为6-16位数");
//        }
        return null;
    }
}
