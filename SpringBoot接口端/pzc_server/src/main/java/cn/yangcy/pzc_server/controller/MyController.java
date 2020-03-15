package cn.yangcy.pzc_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@ResponseBody
@Controller
//以上两个涉及到的类可以用@RestController替换
//@RestController
public class MyController {

    @RequestMapping({"/login","/"})
    public String Login(){
        return "login";
    }

    @RequestMapping("/index")
    public String Index(){
        return "index";
    }
}
