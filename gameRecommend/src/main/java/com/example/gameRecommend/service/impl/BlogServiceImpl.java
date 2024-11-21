package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Blog;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.mapper.BlogMapper;
import com.example.gameRecommend.mapper.GameMapper;
import com.example.gameRecommend.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Transactional(rollbackFor=Exception.class)  //开启事务
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    BlogMapper tBlogMapper;
    @Autowired
    GameMapper gameMapper;
    Result result = Result.build();

    @Override
    public Result pageBlogs(Long current, Long limit, Boolean published, Boolean processed, String original,
                              Boolean share_statement, Boolean isDelete,Long typeId) {
        Result result = Result.build();
        //创建Page对象
        Page<Blog> tBlogPage = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        //获取传入讲师的条件是否为空
        //多条件组合查询
        //判断条件值是否为空,如果不为空拼接条件
        if (published != null){
            //构建条件
            wrapper.eq("published",published);
        }
        if (original != null){
            //构建条件
            wrapper.eq("original",original);
        }
        if (share_statement != null){
            //构建条件
            wrapper.eq("share_statement",share_statement);
        }
        if (processed != null){
            //构建条件
            wrapper.eq("processed",processed);
        }
        if (isDelete != null){
            //构建条件
            wrapper.eq("isDelete",isDelete);
        }
        if(typeId != null &&typeId == 9){
            wrapper.orderByDesc("views");
        }
        else{
            //以更新时间排序（降序）
            wrapper.orderByDesc("update_time");
        }


        //调用mybatis plus分页方法进行查询
        tBlogMapper.selectPage(tBlogPage,wrapper);

        //通过Page对象获取分页信息
        List<Blog> tBlogList = tBlogPage.getRecords(); //每页的数据 list集合
        long size = tBlogPage.getSize(); //每页显示的条数
        long total = tBlogPage.getTotal(); //总记录数
        long pages = tBlogPage.getPages(); //总页数

        System.out.println("-----------------------------------------------");
        System.out.println(tBlogPage.getRecords());
        System.out.println("-----------------------------------------------");

        result.setCode(200);
        result.setData(tBlogPage);
        return result;
    }
    @Override
    public Result writerPageBlogs(String id,Long current, Long limit, Boolean published, Boolean processed, String original,
                                  Boolean share_statement, Boolean isDelete) {
        {
            Result result = Result.build();
            //创建Page对象
            Page<Blog> tBlogPage = new Page<>(current, limit);
            //构建条件
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            //获取传入讲师的条件是否为空
            //多条件组合查询
            //判断条件值是否为空,如果不为空拼接条件
            wrapper.eq("userId", id);
            if (published != null) {
                //构建条件
                wrapper.eq("published", published);
            }
            if (original != null) {
                //构建条件
                wrapper.eq("original", original);
            }
            if (share_statement != null) {
                //构建条件
                wrapper.eq("share_statement", share_statement);
            }
            if (processed != null) {
                //构建条件
                wrapper.eq("processed", processed);
            }
            if (isDelete != null) {
                //构建条件
                wrapper.eq("isDelete", isDelete);
            }

            //以更新时间排序（降序）
            wrapper.orderByDesc("update_time");

            //调用mybatis plus分页方法进行查询
            tBlogMapper.selectPage(tBlogPage, wrapper);

            //通过Page对象获取分页信息
            List<Blog> tBlogList = tBlogPage.getRecords(); //每页的数据 list集合
            long size = tBlogPage.getSize(); //每页显示的条数
            long total = tBlogPage.getTotal(); //总记录数
            long pages = tBlogPage.getPages(); //总页数

            System.out.println("-----------------------------------------------");
            System.out.println(tBlogPage.getRecords());
            System.out.println("-----------------------------------------------");

            result.setCode(200);
            result.setData(tBlogPage);
            return result;
        }
    }

    /**
     * 根据标题查询
     * @param title
     * @return
     */
    @Override
    public Result getByTitle(String title) {
        Result result = Result.build();
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>();
        queryWrapper.like("title",title);
        List<Blog> tBlogList = tBlogMapper.selectList(queryWrapper);
        result.setCode(200);
        result.setData(tBlogList);
        return result;
    }

    /**
     * 保存博客和标签
     * @param params
     * @return
     */
    @Override
    public Result saveBT(HashMap<String, Object> params) {
        Result result = Result.build();
        Blog tBlog = new Blog();
        //tBlog.setId(Long.parseLong(params.get("id").toString()));
        tBlog.setTitle((String) params.get("title"));
        tBlog.setUserId(Long.valueOf(params.get("userId").toString()));
        tBlog.setDescription((String) params.get("description"));
        tBlog.setFirstPicture((String) params.get("first_picture"));
        tBlog.setContent((String) params.get("content"));
        tBlog.setPublished(Objects.equals(params.get("published").toString(), "1"));
        tBlog.setShareStatement(true); //设置状态为已发布
        tBlog.setProcessed(false);

        Game game= gameMapper.getGameIdByName((String) params.get("gameName"));
        if(game==null)
        {
            result.setCode(500);
            result.setMsg("没有这个游戏，请确认游戏名！已经尝试保存为默认游戏");
            tBlog.setGameId(0L);
            tBlogMapper.insert(tBlog);
            return result;
        }
        tBlog.setGameId(game.getId());

        //保存文章
        int result_blog = tBlogMapper.insert(tBlog);
        if (result_blog != 0 ){
            result.setCode(200);
            result.setMsg("添加成功！");
            return result;
        }
        result.setMsg("添加失败");
        return result;
    }

    /**
     * 暂时保存博客
     * @param params
     * @return
     */
    @Override
    public Result temporarySave(HashMap<String, Object> params) {
        Result result = Result.build();
        Blog tBlog = new Blog();
        //tBlog.setId(Long.parseLong(params.get("id").toString()));
        tBlog.setTitle((String) params.get("title"));
        tBlog.setUserId(Long.valueOf((Integer) params.get("userId")));
        tBlog.setDescription((String) params.get("description"));
        tBlog.setFirstPicture((String) params.get("first_picture"));
        tBlog.setContent((String) params.get("content"));
        tBlog.setPublished(Objects.equals(params.get("published").toString(), "1"));
        tBlog.setShareStatement(false);
        tBlog.setProcessed(false);

        Game game= gameMapper.getGameIdByName((String) params.get("gameName"));
        if(game==null)
        {
            result.setCode(500);
            result.setMsg("没有这个游戏，请确认游戏名！已经尝试保存为默认游戏");
            tBlog.setGameId(0L);
            tBlogMapper.insert(tBlog);
            return result;
        }
        tBlog.setGameId(game.getId());

        //保存文章
        int result_blog = tBlogMapper.insert(tBlog);
        if (result_blog != 0 ){
            result.setCode(200);
            result.setMsg("添加成功！");
            return result;
        }
        result.setMsg("添加失败");
        return result;
    }
    /**
     * 更新文章
     * @param params
     * @return
     */
    @Override
    public Result updateBlog(HashMap<String, Object> params) {
        //根据id查询文章,几乎和插入只有下面一句区别
        Blog tBlog = tBlogMapper.selectById(Long.parseLong(params.get("id").toString()));
        Result result = Result.build();
        //tBlog.setId(Long.parseLong(params.get("id").toString()));
        tBlog.setTitle((String) params.get("title"));
        //tBlog.setUserId((Long) params.get("userId"));
        tBlog.setDescription((String) params.get("description"));
        tBlog.setFirstPicture((String) params.get("first_picture"));
        tBlog.setContent((String) params.get("content"));
        tBlog.setPublished(Objects.equals(params.get("published").toString(), "1"));
        tBlog.setShareStatement(Objects.equals(params.get("shareStatement").toString(), "1")); //设置状态为已发布
        tBlog.setProcessed(false);

        Game game= gameMapper.getGameIdByName((String) params.get("gameName"));
        if(game==null)
        {
            result.setCode(500);
            result.setMsg("没有这个游戏，请确认游戏名！已经尝试保存为默认游戏");
            tBlog.setGameId(0L);
            tBlogMapper.updateById(tBlog);
            return result;
        }
        tBlog.setGameId(game.getId());

        //保存文章
        int result_blog = tBlogMapper.updateById(tBlog);
        if (result_blog != 0 ){
            result.setCode(200);
            result.setMsg("添加成功！");
            return result;
        }
        result.setMsg("添加失败");
        return result;
    }

    /**
     * 逻辑删除博客
     * @param id
     * @return
     */
    @Override
    public Result logicDeleteBlog(String id) {
        Result result = Result.build();
        Blog tBlog = tBlogMapper.selectById(id);
        if (tBlog != null){
            tBlog.setIsDelete(Boolean.TRUE);
            tBlog.setUpdateTime(LocalDateTime.now());
            System.out.println(tBlog.toString());
            tBlogMapper.updateById(tBlog);
            result.setCode(200);
            result.setMsg("删除博客成功！");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("删除博客失败");
            return result;
        }
    }

    /**
     * 删除博客和标签（彻底删除）
     * @param id
     * @return
     */
    @Override
    public Result deleteBT(String id) {
        Result result = Result.build();
        Blog tBlog = tBlogMapper.selectById(id);
        //删除博客文章
        int r_blog = tBlogMapper.deleteById(id);
        if (r_blog!= 0){
            result.setCode(200);
            result.setMsg("删除博客成功");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("删除博客失败");
            return result;
        }

    }

    /**
     * 还原删除的博客
     * @param id
     * @return
     */
    @Override
    public Result recoveryBlog(String id) {
        Result result = Result.build();
        Blog tBlog = tBlogMapper.selectById(id);
        if (tBlog != null){
            tBlog.setIsDelete(false);
            tBlog.setUpdateTime(LocalDateTime.now());
            tBlogMapper.updateById(tBlog);
            result.setCode(200);
            result.setMsg("还原博客成功！");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("还原博客失败");
            return result;
        }
    }

    /**
     * 根据id查找博客
     * @param id
     * @return
     */
    @Override
    public Result getByBlogId(String id) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(tBlogMapper.getByBlogId(id));
        return result;
    }

    @Override
    public Result processBlog(String id) {
        Result result = Result.build();
        result.setCode(200);
        result.setData(tBlogMapper.process(id));
        return result;
    }

    @Override
    public void updateByComment(Blog tBlog) {
        tBlogMapper.updateById(tBlog);
    }
}