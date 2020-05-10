package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper ChapterMapper;

    public List<Chapter> list(){
        ChapterExample chapterExample = new ChapterExample();
        //等同于where条件
        chapterExample.createCriteria().andIdEqualTo("1");
        chapterExample.setOrderByClause("id desc");

        return ChapterMapper.selectByExample(chapterExample);
    }
}
