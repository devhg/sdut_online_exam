package com.sdut.onlinejudge.service.ServiceImpl;

import com.sdut.onlinejudge.mapper.StatMapper;
import com.sdut.onlinejudge.model.FeedBack;
import com.sdut.onlinejudge.model.StatKit;
import com.sdut.onlinejudge.model.SubmitStat;
import com.sdut.onlinejudge.model.TrainStat;
import com.sdut.onlinejudge.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019/12/23 20:02
 * @Email: devhui@ihui.ink
 * @Version: 1.0
 */
@Service
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {
    @Autowired
    private StatMapper statMapper;

    @Override
    public StatKit getStat() {
        return statMapper.getStat();
    }

    @Override
    public List<TrainStat> getSubmitStat(String uid) {
        return statMapper.getSubmitStat(uid);
    }

    @Override
    public List<SubmitStat> noSubmitStat(Integer cid) {
        return statMapper.noSubmitStat(cid);
    }

    @Override
    public List<SubmitStat> getStatByCid(int cid) {
        return statMapper.getStatByCid(cid);
    }

    @Override
    public List<FeedBack> getFeedBacks() {
        return statMapper.getFeedBacks();
    }

    @Override
    public List<Map> getSingleHint() {
        return statMapper.getSingleHint();
    }

    @Override
    public List<Map> getMultiHint() {
        return statMapper.getMultiHint();
    }

    @Override
    public List<Map> getJudgeHint() {
        return statMapper.getJudgeHint();
    }
}
