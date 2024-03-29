package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.SubmitMapper;
import com.sdut.onlinejudge.model.Submit;
import com.sdut.onlinejudge.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Devhui
 * @Date: 2019-12-07 16:42
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    private SubmitMapper submitMapper;

    @Override
    public Submit getSubmit(String uid, int cid) {
        return submitMapper.getSubmit(uid, cid);
    }

    @Override
    public Submit hasSubmit(String uid, int cid) {
        return submitMapper.hasSubmit(uid, cid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int addSubmit(Submit submit) {
        return submitMapper.addSubmit(submit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
    public int addScore(float score, String username) {
        return submitMapper.addScore(score, username);
    }
}
