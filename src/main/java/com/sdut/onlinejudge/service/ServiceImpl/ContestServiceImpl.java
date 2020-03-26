package com.sdut.onlinejudge.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.sdut.onlinejudge.mapper.ContestMapper;
import com.sdut.onlinejudge.model.*;
import com.sdut.onlinejudge.service.ContestService;
import com.sdut.onlinejudge.service.ProblemService;
import com.sdut.onlinejudge.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-11-28 17:05
 * @Version 1.0
 */
@Service
@Transactional
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Contest> findAll(String keyWords) {
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        int hour = DateUtil.getHour(new Date());
//        int minute = DateUtil.getHour(new Date());
//        int seconds = DateUtil.getHour(new Date());
//
//        if (hour == 23 && minute == 59 && seconds == 59) {
//
//        } else {
//            if (redisTemplate.hasKey("pv")) {
//                ops.increment("pv");
//            } else {
//                ops.setIfAbsent("pv", "1");
//            }
//        }
        return contestMapper.findAll(keyWords);
    }

    @Override
    public int deployContest(Map<String, String> contestInfo) {
        System.out.println("fetch");
        Map<String, Object> problems = problemService.fetchProblems(contestInfo); // 题目
        System.out.println("problem");
        Contest contest = separate(contestInfo, problems);
        System.out.println(contest);
        return contestMapper.deployContest(contest);
    }

    @Override
    public int deployContestSelf(Map<String, Object> contestInfo) {
        Map<String, Object> problems = problemService.fetchProblemsSelf(contestInfo);
        Contest contest = separate(contestInfo, problems);
        System.out.println(contest);
        return contestMapper.deployContest(contest);
    }


    @Override
    public Map<String, Object> getContestByCid(int cid) {
//        String ccid = "contest:" + cid;
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        boolean hasKey = redisTemplate.hasKey(ccid);
//        if (hasKey) {
//            String jsonString = ops.get(ccid);
//            System.out.println("========从缓存中获取数据========");
//            System.out.println("=============================");
//            Map map = JSON.parseObject(jsonString, Map.class);
//            return map;
//        } else {
        String contest = contestMapper.getContestByCid(cid);
        System.out.println("========从数据库中获取数据========");
        System.out.println("=============================");
//            ops.set(ccid, contest, 60, TimeUnit.MINUTES);
        Map map = JSON.parseObject(contest, Map.class);
        return map;
//        }
    }

    @Override
    public Map<String, Object> getAnswerByCid(int cid) {
//        String ccid = "answer:" + cid;
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        boolean hasKey = redisTemplate.hasKey(ccid);
//        if (hasKey) {
//            String jsonString = ops.get(ccid);
//            System.out.println("========从缓存中获取数据========");
//            System.out.println("=============================");
//            Map map = JSON.parseObject(jsonString, Map.class);
//            return map;
//        } else {
        String answer = contestMapper.getAnswerByCid(cid);
        System.out.println("========从数据库中获取数据========");
        System.out.println("=============================");
//            ops.set(ccid, answer, 60, TimeUnit.MINUTES);
        Map map = JSON.parseObject(answer, Map.class);
        return map;
//        }
    }

    @Override
    public Map<String, Float> getProblemScore(int cid) {
        return contestMapper.getProblemScore(cid);
    }


    private Contest separate(Map contestInfo, Map map) {
        Answer answer = new Answer(); // 答案
        List<SingleSelect> singleSelects = (List<SingleSelect>) map.get("singleSelects");
        List<JudgeProblem> judgeProblems = (List<JudgeProblem>) map.get("judgeProblems");
        List<MultiSelect> multiSelects = (List<MultiSelect>) map.get("multiSelects");

        // 题目答案分离
        ArrayList<String> sans = new ArrayList<>();
        for (SingleSelect s : singleSelects) {
            sans.add(s.getAnswer());
            s.setAnswer(null);
        }
        ArrayList<String> mans = new ArrayList<>();
        for (MultiSelect s : multiSelects) {
            mans.add(s.getAnswer());
            s.setAnswer(null);
        }
        ArrayList<String> jans = new ArrayList<>();
        for (JudgeProblem s : judgeProblems) {
            jans.add(s.getAnswer());
            s.setAnswer(null);
        }
        // 题目
        map.put("singleSelects", singleSelects);
        map.put("judgeProblems", judgeProblems);
        map.put("multiSelects", multiSelects);

        // 答案
        answer.setSingleSelectsAns(sans);
        answer.setJudgeAns(jans);
        answer.setMultiSelectsAns(mans);

        String startTime = (String) contestInfo.get("startTime");
        String endTime = (String) contestInfo.get("endTime");

        Contest contest = new Contest();
        try {
            contest.setCname((String) contestInfo.get("cname"));
            contest.setStartTime(DateUtil.dateFormat(startTime));
            contest.setEndTime(DateUtil.dateFormat(endTime));
            contest.setJudgeScore(new Float((String) contestInfo.get("judgeScore")));
            contest.setSingleScore(new Float((String) contestInfo.get("singleScore")));
            contest.setMultiScore(new Float((String) contestInfo.get("multiScore")));
            contest.setProblems(JSON.toJSONString(map));
            contest.setAnswers(JSON.toJSONString(answer));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return contest;
    }

}
