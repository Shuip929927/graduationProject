package cn.yangcy.pzc_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("userAccount") String account, @RequestParam("userPassword") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(account) && "1234".equals(password)){
            session.setAttribute("account",account);
//            return "redirect:index";
            return "index";
        } else {
            session.invalidate();
            map.put("errMsg","用户名或密码错误！");
            return "login";
        }
    }

}
