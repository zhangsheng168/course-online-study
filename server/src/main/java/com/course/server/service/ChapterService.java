package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper ChapterMapper;

    public void list(PageDto pageDto){
        //分页（该插件往下遇到的第一个selectXX则生效）
        PageHelper.startPage(pageDto.getPage() ,pageDto.getSize());
        //等同于where条件
       /* chapterExample.createCriteria().andIdEqualTo("1");
        chapterExample.setOrderByClause("id desc");*/
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = ChapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        //设置总行数
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = new ArrayList<ChapterDto>();
        for (int i = 0,l=chapterList.size(); i<l ; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);
    }
}
