package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Links;
import com.example.gameRecommend.mapper.LinksMapper;
import com.example.gameRecommend.service.LinksService;
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
public class LinksServiceImpl extends ServiceImpl<LinksMapper, Links> implements LinksService {
    @Autowired
    LinksMapper tLinksMapper;

    @Override
    public Result pageLinks(Long current, Long size) {
        Result result = Result.build();
        //创建Page对象
        Page<Links> tLinksPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<Links> wrapper = new QueryWrapper<>();
        //以创建时间排序（降序）
        wrapper.orderByDesc("create_time");
        //调用mybatis plus分页方法进行查询
        tLinksMapper.selectPage(tLinksPage,wrapper);
        //通过Page对象获取分页信息
        List<Links> typeList = tLinksPage.getRecords(); //每页的数据 list集合
        long pagesize = tLinksPage.getSize(); //每页显示的条数
        long total = tLinksPage.getTotal(); //总记录数
        long pages = tLinksPage.getPages(); //总页数

        result.setCode(200);
        result.setData(tLinksPage);
        return result;
    }

    @Override
    public Result getLinksByName(String name) {
        Result result = Result.build();
        QueryWrapper<Links> queryWrapper = new QueryWrapper<Links>();
        queryWrapper.like("blog_name",name);
        List<Links> tLinksList = tLinksMapper.selectList(queryWrapper);
        result.setCode(200);
        result.setData(tLinksList);
        return result;
    }

    @Override
    public Result updateLink(Links tLinks) {
        Result result = Result.build();
        if (tLinksMapper.updateById(tLinks) == 1){
            result.setMsg("更新友链成功");
            result.setCode(200);
            return result;
        }
        result.setMsg("更新友链失败");
        return result;
    }

    @Override
    public Result deleteLinkById(Long id) {
        Result result = Result.build();
        if (tLinksMapper.deleteById(id) == 1){
            result.setCode(200);
            result.setMsg("删除友链成功");
            return result;
        }
        result.setMsg("删除友链失败");
        return result;
    }

    @Override
    public Result getLinksById(Long id) {
        Result result = Result.build();
        Links tLinks = tLinksMapper.selectById(id);
        result.setCode(200);
        result.setData(tLinks);
        return result;
    }
}
