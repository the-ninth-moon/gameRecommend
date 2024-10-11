package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Type;
import com.example.gameRecommend.mapper.TypeMapper;
import com.example.gameRecommend.service.TypeService;
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
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    TypeMapper tTypeMapper;

    @Override
    public Result pageTypes(Long current, Long size) {
        Result result = Result.build();
        //创建Page对象
        Page<Type> typePage = new Page<>(current,size);
        //构建条件
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        //调用mybatis plus分页方法进行查询
        tTypeMapper.selectPage(typePage,wrapper);
        //通过Page对象获取分页信息
        List<Type> typeList = typePage.getRecords(); //每页的数据 list集合
        long pagesize = typePage.getSize(); //每页显示的条数
        long total = typePage.getTotal(); //总记录数
        long pages = typePage.getPages(); //总页数

        result.setCode(200);
        result.setData(typePage);
        return result;
    }

    /**
     * 通过名称查询分类
     * @param name
     * @return
     */
    @Override
    public Result getTypeByName(String name) {
        Result result = Result.build();
        QueryWrapper<Type> queryWrapper = new QueryWrapper<Type>();
        queryWrapper.like("name",name);
        List<Type> typeList = tTypeMapper.selectList(queryWrapper);
        result.setCode(200);
        result.setData(typeList);
        return result;
    }

    @Override
    public Result deleteTypeById(Long id) {
        Result result = Result.build();
        if (tTypeMapper.deleteById(id) == 1){
            result.setCode(200);
            result.setMsg("删除分类成功");
            return result;
        }
        result.setMsg("删除分类失败");
        return result;
    }

    @Override
    public Result updateType(Type tType) {
        Result result = Result.build();
        if (tTypeMapper.updateById(tType) == 1){
            result.setMsg("更新分类成功");
            result.setCode(200);
            return result;
        }
        result.setMsg("更新分类失败");
        return result;
    }

    @Override
    public Result getTypeById(Long id) {
        Result result = Result.build();
        Type tType = tTypeMapper.selectById(id);
        result.setCode(200);
        result.setData(tType);
        return result;
    }
}
