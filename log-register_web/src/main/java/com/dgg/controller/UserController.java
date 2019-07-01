package com.dgg.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dgg.api.IUserService;
import com.dgg.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("register")
    public String register(){
        return "register";
    }


    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("getCode")
    @ResponseBody
    public String getCode(@RequestParam String email, HttpSession session) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("dgg768815928@sina.com");
        messageHelper.setTo(email);
        Random r = new Random();
        int code = r.nextInt(8999)+1000;
        messageHelper.setSubject("验证码！");
        messageHelper.setText("验证码是"+code,true);
        messageHelper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);
        session.setAttribute("code",String.valueOf(code));
        return "OK";
    }

    @Reference
    private IUserService userService;
    @RequestMapping("queryByusername")
    public String queryByusername(User user,@RequestParam String code, HttpSession session, HttpServletResponse response) throws IOException {
        String getcode = (String) session.getAttribute("code");
        if(code.equals(getcode)){
          User u = userService.queryByusername(user.getUsername());
            if(u!=null){
                response.getWriter().write("<base href='/'/><script>alert('User name registered');location.href='/user/register'</script>");
            }else{
                userService.addUser(user);
                return "redirect:login";
            }
        }else{
            response.getWriter().write("<base href='/'/><script>alert('Verification code error');location.href='/user/register'</script>");
        }
        return null;
    }
    @RequestMapping("queryByuser")
    private String queryByuser(User user, HttpServletResponse response) throws IOException {
        User u = userService.queryByuser(user);
        if (u != null) {
           response.getWriter().write("<script>alert('success')</script>");
        } else {
            return "redirect:login";
        }
        return null;
    }
    @RequestMapping("toforget")
    public String toforget(){
        return "forget";
    }

    @RequestMapping("getEmail")
    @ResponseBody
    public String getEmail(@RequestParam String username) throws MessagingException {
        User u = userService.queryByusername(username);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("dgg768815928@sina.com");
        messageHelper.setTo(u.getEmail());
        messageHelper.setSubject("忘记密码！");
        messageHelper.setText("请点此<a href='http://localhost:8080/user/toUpdate/"+u.getId()+"'>这里</a>",true);
        messageHelper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);
        System.out.println("ok");
        return "ok";
    }

    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable int id, Model model){
        model.addAttribute("id",id);
        return "updatePassword";
    }

    @RequestMapping("updatePassword")
    public String updatePassword(User user, HttpServletResponse response) throws IOException {
        int count  = userService.updatePassword(user);
        if(count!=0){
            response.getWriter().write("<base href='/'/><script>alert('success');location.href=''</script>");
        }
        return null;
    }
}
