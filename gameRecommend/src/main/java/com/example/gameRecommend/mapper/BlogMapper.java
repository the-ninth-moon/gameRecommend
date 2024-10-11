package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    //根据id查找博客
    Blog getByBlogId(@Param("id") String id);

    Blog process(@Param("id") String id);
}
