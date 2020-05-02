package com.course.server.service;

import com.course.server.domain.Test;
import com.course.server.domain.TestExample;
import com.course.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        TestExample te = new TestExample();
        //等同于where条件
        te.createCriteria().andIdEqualTo("1");
        te.setOrderByClause("id desc");

        return testMapper.selectByExample(te);
    }
}
