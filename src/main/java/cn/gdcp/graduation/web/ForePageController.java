package cn.gdcp.graduation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {
    @GetMapping("/fore_logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }

    @GetMapping(value="/")
    public String index(){
        return "redirect:home";
    }
    @GetMapping(value="/home")
    public String home(){
        return "views/home";
    }

    @GetMapping(value="/product_detail")
    public String productDetail(){
        return "views/goodsDetail";
    }

    @GetMapping(value = "/good_sort_list")
    public String goodSortList(){
        return "views/goodSortList";
    }

    @GetMapping(value="/register")
    public String register(){
        return "views/register";
    }

    @GetMapping(value="/registerSuccess")
    public String registerSuccess(){
        return "views/registerSuccess";
    }

    @GetMapping(value="/login")
    public String login(){
        return "views/login";
    }

    @GetMapping(value="/cartList")
    public String cart(){
        return "views/cartList";
    }

    @GetMapping(value="/searchResult")
    public String search(){
        return "views/searchShow";
    }

    @GetMapping(value="/payment")
    public String payment(){
        return "views/payment";
    }

    @GetMapping(value="/orderList")
    public String bought(){
        return "views/orderList";
    }

    @GetMapping(value="/buy")
    public String buy(){
        return "views/buy";
    }

    @GetMapping(value="/paymented")
    public String payed(){
        return "views/paymented";
    }

    @GetMapping(value="/confirmPay")
    public String confirmPay(){
        return "views/confirmPay";
    }

    @GetMapping(value="/orderConfirmed")
    public String orderConfirmed(){
        return "views/orderConfirmed";
    }

    @GetMapping(value="/review")
    public String review(){
        return "views/review";
    }
}
