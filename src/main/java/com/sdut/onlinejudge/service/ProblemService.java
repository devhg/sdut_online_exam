package com.sdut.onlinejudge.service;

import com.sdut.onlinejudge.model.JudgeProblem;
import com.sdut.onlinejudge.model.MultiSelect;
import com.sdut.onlinejudge.model.SingleSelect;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Devhui
 * @Date: 2019-12-04 19:34
 * @Version 1.0
 */

@Repository
public interface ProblemService {
    // 拉取整套题
    Map<String, Object> fetchProblems(Map<String, String> contestInfo);

    Map<String, Object> fetchProblemsSelf(Map<String, Object> contestInfo);

    // 拉取单选题
    List<SingleSelect> getSingleSelects(String keyWords, String label);

    // 拉取多选题
    List<MultiSelect> getMultiSelects(String keyWords, String label);

    // 拉取判断选题
    List<JudgeProblem> getJudgeProblem(String keyWords, String label);


    // 添加单选题
    int addSingleSelects(SingleSelect ss);

    // 添加多选题
    int addMultiSelects(MultiSelect ms);

    // 添加判断选题
    int addJudgeProblem(JudgeProblem jp);

    // 删除判断题
    int delJudgeProblem(String jpid);

    // 删除单选题
    int delSingleSelect(String spid);

    // 删除多选题
    int delMultiSelect(String mpid);
}
