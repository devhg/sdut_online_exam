package com.sdut.onlinejudge.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.ResultKit;
import com.sdut.onlinejudge.model.User;
import com.sdut.onlinejudge.model.UserInfo;
import com.sdut.onlinejudge.service.UserService;
import com.sdut.onlinejudge.utils.JwtUtils;
import com.sdut.onlinejudge.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public ResultKit<Object> loginCheck(@RequestBody String param, HttpServletRequest req) {

        JSONObject json = JSON.parseObject(param);
        String username = (String) json.get("username");
        String password = (String) json.get("password");

        System.out.println(json);
        ResultKit<Object> resultKit = new ResultKit<>();
        User user = userService.loginCheck(username, password);
        if (user != null) {
            // 签发token
            String token = JwtUtils.createJWT(UUID.randomUUID().toString(), user.getUsername(), 120 * 60 * 1000);
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("登录成功");
            resultKit.setData(token);
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("登录失败，账号或密码错误！");
        }
        return resultKit;
    }

    @GetMapping("standing")
    @ResponseBody
    public ResultKit getStanding(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "college", required = false) String college) {
        ResultKit<Map> resultKit = new ResultKit<>();
        // pageNum:表示第几页  pageSize:表示一页展示的数据
//        String orderBy = "score" + " desc";//按照（数据库）排序字段 倒序 排序
//        PageHelper.startPage(pageNum, 10, orderBy);
        PageHelper.startPage(pageNum, 10);
        System.out.println("name" + name + "college" + college);
        List<UserInfo> allUsers = userService.findAllUsers(name, college);
        // 将查询到的数据封装到PageInfo对象
        PageInfo<UserInfo> pageInfo = new PageInfo(allUsers, 10);
        // 分割数据成功

        long total = pageInfo.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageInfo", allUsers);

        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取成功");
        resultKit.setData(map);
        return resultKit;
    }

    @GetMapping("info/{uid}")
    @ResponseBody
    public ResultKit<Object> getUserInfo(@PathVariable("uid") String username) {
        ResultKit<Object> resultKit = new ResultKit<>();
        UserInfo userInfo = userService.getUserInfo(username);
        List<Integer> cidsByUsername = userService.getCidsByUsername(username);

        HashMap<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        map.put("cidsByUsername", cidsByUsername);

        resultKit.setCode(ResultCode.SUCCESS.code());
        resultKit.setMessage("获取单个用户信息成功");
        resultKit.setData(map);
        return resultKit;
    }

    @PostMapping("register")
    @ResponseBody
    public ResultKit<Integer> reg(@RequestBody String param, HttpServletRequest req) {

        JSONObject json = JSON.parseObject(param);
        String username = (String) json.get("username");
        String password = (String) json.get("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        int login = userService.register(user);
        ResultKit<Integer> integerResultKit = new ResultKit<>();
        integerResultKit.setData(login);
        return integerResultKit;
    }

    @PostMapping("updatePwd/{uid}")
    @ResponseBody
    public ResultKit updatePwd(@PathVariable("uid") String uid,
                               @RequestBody String param, HttpServletRequest req) {

        JSONObject json = JSON.parseObject(param);
        String oldPwd = (String) json.get("oldPassword");
        String newPwd = (String) json.get("newPassword");


        int i = userService.updatePwd(uid, oldPwd, newPwd);
        System.out.println("==" + i);
        ResultKit resultKit = new ResultKit<>();
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("修改密码成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("修改密码失败，请重试。");
        }
        return resultKit;
    }

    @PostMapping("feedback")
    @ResponseBody
    public ResultKit feedBack(@RequestBody String param, HttpServletRequest req) {
        JSONObject json = JSON.parseObject(param);
        String contact = (String) json.get("contact");
        String userFeedback = (String) json.get("content");

        FeedBack feedBack = new FeedBack();
        feedBack.setContact(contact);
        feedBack.setContent(userFeedback);
        int i = userService.addFeedBack(feedBack);
        ResultKit resultKit = new ResultKit<>();
        if (i == 1) {
            resultKit.setCode(ResultCode.SUCCESS.code());
            resultKit.setMessage("反馈成功");
        } else {
            resultKit.setCode(ResultCode.WRONG_UP.code());
            resultKit.setMessage("反馈失败，请重试。");
        }
        return resultKit;
    }
}
