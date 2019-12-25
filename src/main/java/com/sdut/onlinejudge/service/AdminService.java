package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.Admin;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 18:30
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */


public interface AdminService {
    // 管理员登陆检查
    Admin loginCheck(String username, String password);

    // 删除用户
    int deleteUser(String username);
}