package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public void list(PageDto pageDto){
        //分页（该插件往下遇到的第一个selectXX则生效）
        PageHelper.startPage(pageDto.getPage() ,pageDto.getSize());
        //等同于where条件
       /* chapterExample.createCriteria().andIdEqualTo("1");
        chapterExample.setOrderByClause("id desc");*/
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        //设置总行数
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList = new ArrayList<ChapterDto>();
//        CopyUtil.copyList(chapterList,chapterDtoList);
        for (int i = 0,l=chapterList.size(); i<l ; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);
    }

    /**
     * 根据id来判断新增或修改
     * @param chapterDto
     */
    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtil.copy(chapterDto,Chapter.class);
        if(StringUtils.isEmpty(chapterDto.getId())){//id为空则进行新增
            this.insert(chapter);
        }else{//id不为null则进行修改
            this.update(chapter);
        }
    }

    private void insert(Chapter chapter){
        //设置短id
        chapter.setId(UuidUtils.getShortUuid());
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delete(String id){
        chapterMapper.deleteByPrimaryKey(id);
    }
}
