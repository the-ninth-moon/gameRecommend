package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.utils.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
public interface BlogService extends IService<Blog> {
    //分页查询
    Result pageBlogs(Long current, Long limit, Boolean published,  Boolean porcessed,String flag, Boolean share_statement, Boolean is_delete,Long typeId);
    Result writerPageBlogs(String id,Long current, Long limit, Boolean published,  Boolean porcessed,String flag, Boolean share_statement, Boolean is_delete);
    Result getByTitle(String title);

    //添加博客
    Result saveBT(HashMap<String, Object> params);
    //暂时保存博客
    Result temporarySave(HashMap<String, Object> params);
    //更新博客
    Result updateBlog(HashMap<String,Object> params);
    //删除博客（逻辑删除）
    Result logicDeleteBlog(String id);
    //删除博客（彻底删除）
    Result deleteBT(String id);
    //还原博客
    Result recoveryBlog(String id);
    //根据博客id查询
    Result getByBlogId(String id);
    Result processBlog(String id);

    //更新评论总数
    void updateByComment(Blog tBlog);
}
