package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Tag;
import com.example.gameRecommend.mapper.TagMapper;
import com.example.gameRecommend.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    TagMapper tTagMapper;

    @Override
    public Result pageTags(Long current, Long size) {
        Result result = Result.build();
        //创建Page对象
        Page<Tag> tagPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        //调用mybatis plus分页方法进行查询
        tTagMapper.selectPage(tagPage,wrapper);
        //通过Page对象获取分页信息
        List<Tag> typeList = tagPage.getRecords(); //每页的数据 list集合
        long pagesize = tagPage.getSize(); //每页显示的条数
        long total = tagPage.getTotal(); //总记录数
        long pages = tagPage.getPages(); //总页数

        result.setCode(200);
        result.setData(tagPage);
        return result;
    }

    @Override
    public Result getTagByName(String name) {
        Result result = Result.build();
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<Tag>();
        queryWrapper.like("name",name);
        List<Tag> tagList = tTagMapper.selectList(queryWrapper);
        result.setCode(200);
        result.setData(tagList);
        return result;
    }

    @Override
    public Result updateTag(Tag tTag) {
        Result result = Result.build();
        if (tTagMapper.updateById(tTag) == 1){
            result.setMsg("更新标签成功");
            result.setCode(200);
            return result;
        }
        result.setMsg("更新标签失败");
        return result;
    }

    @Override
    public Result deleteTagById(Long id) {
        Result result = Result.build();
        if (tTagMapper.deleteById(id) == 1){
            result.setCode(200);
            result.setMsg("删除标签成功");
            return result;
        }
        result.setMsg("删除标签失败");
        return result;
    }

    @Override
    public Result getTagById(Long id) {
        Result result = Result.build();
        Tag tTag = tTagMapper.selectById(id);
        result.setCode(200);
        result.setData(tTag);
        return result;
    }

    @Override
    public Result getTagByGameId(String id) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(tTagMapper.getTagByGameId(id));
        return result;
    }
}
