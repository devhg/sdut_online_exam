package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.UserMapper;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Devhui
 * @Date: 2019-12-02 19:16
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginCheck(String username, String password) {
        return userMapper.loginCheck(username, password);
    }

    @Override
    public List<UserInfo> findAllUsers(String Uname, String CollegeName) {
        return userMapper.findAllUsers(Uname, CollegeName);
    }

    @Override
    public List<TrainStat> getTrainStat(String uid) {
        return userMapper.getTrainStat(uid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int register(User user) {
        return userMapper.register(user);
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return userMapper.getUserInfo(username);
    }

    @Override
    public List<Integer> getCidsByUsername(String username) {
        return userMapper.getCidsByUsername(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int updatePwd(String username, String oldPwd, String newPwd) {
        return userMapper.updatePwd(username, oldPwd, newPwd);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int addFeedBack(FeedBack feedBack) {
        return userMapper.addFeedBack(feedBack);
    }

    @Override
    public List<Notice> fetchNotices() {
        return userMapper.fetchNotices();
    }
}
