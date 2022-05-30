package com.example.springshiro.controller;

import com.example.springshiro.entity.Account;
import com.example.springshiro.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return url;
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        Subject subject = SecurityUtils.getSubject(); //创建一个shiro框架的Subject对象，注意导入类时一定要选择shiro包
        UsernamePasswordToken token = new UsernamePasswordToken(username, password); //将用户输入的username与password放入token中
        /**
         * 将token交给shiro，进入我们自定义的AccountRealm中的doGetAuthenticationInfo方法中去进行登录认证（这里的token就是方法中的authenticationToken）
         * 当登录成功后跳转到index页面，登录不成功跳转回login页面。
         * 使用try-catch捕获异常，并利用Model类接收异常信息，传给前端网页。
         */
        try {
            subject.login(token);
            //将用户信息存入session 的 account对象中，便于后续在网页上取出用户信息
            Account account = (Account)subject.getPrincipal();
            subject.getSession().setAttribute("username",account.getUsername());
            return "index";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg","用户名错误！");
            return "login";
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            model.addAttribute("errorMsg","密码错误！");
            return "login";
        }
    }

    /**
     * 返回未授权信息（空白页面加字符串）
     * @return
     */
    @GetMapping("/unauth")
    @ResponseBody
    public String unAuth(){
        return "页面未授权，无法访问";
    }

    /**
     * logout功能
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
