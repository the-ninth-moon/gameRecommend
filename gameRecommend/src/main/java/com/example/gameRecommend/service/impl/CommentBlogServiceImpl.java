package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Blog;
import com.example.gameRecommend.entity.CommentBlog;
import com.example.gameRecommend.entity.CommentGame;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.mapper.BlogMapper;
import com.example.gameRecommend.mapper.CommentBlogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.mapper.CommentGameMapper;
import com.example.gameRecommend.mapper.GameMapper;
import com.example.gameRecommend.service.CommentBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-01-27
 */
@Service
public class CommentBlogServiceImpl extends ServiceImpl<CommentBlogMapper, CommentBlog> implements CommentBlogService {
    @Autowired
    private CommentBlogMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    //存放迭代找出的所有子代的集合
    private List<CommentBlog> tempReplys = new ArrayList<>();

    /**
     * @Description: 查询评论
     * @Param: blogId：博客id
     * @Return: 评论消息
     */
    @Override
    public List<CommentBlog> listCommentByBlogId(Long blogId) {
        //查询出所有评论
        List<CommentBlog> comments = commentMapper.findByBlogIdParentIdNull(blogId, Long.parseLong("-1"));
        for(CommentBlog comment : comments){
            Long id = comment.getId();
            List<CommentBlog> childComments = commentMapper.findByBlogIdParentIdNotNull(blogId,id);
            //查询出子评论
            combineChildren(blogId, childComments);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }
    @Override
    public Page<CommentBlog> getBlogCommentByPage(Long blogId,Long current, Long size) {
        // 创建分页对象
        Page<CommentBlog> commentPage = new Page<>(current, size);

        // 调用自定义的分页查询
        return commentMapper.selectBlogCommentByPage(commentPage, blogId);
    }
    /**
     * @Description: 查询出子评论
     * @Auther: ONESTAR
     * @Date: 10:43 2020/6/23
     * @Param: childComments：所有子评论
     * @Param: parentNickname1：父评论姓名
     * @Return:
     */
    private void combineChildren(Long blogId, List<CommentBlog> childComments) {
        //判断是否有一级子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(CommentBlog childComment : childComments){
                tempReplys.add(childComment);
                Long childId = childComment.getId();
                //查询出子二级评论
                recursively(blogId, childId);
            }
        }
    }

    /**
     * @Description: 循环迭代找出子集回复
     * @Auther: ONESTAR
     * @Date: 10:44 2020/6/23
     * @Param: chileId:子评论id
     * @Param: parentNickname1:子评论姓名
     * @Return:
     */
    private void recursively(Long blogId, Long childId) {
        //根据子一级评论的id找到子二级评论
        List<CommentBlog> replayComments = commentMapper.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(CommentBlog replayComment : replayComments){
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId);
            }
        }
    }

    //新增评论
    @Override
    public int saveComment(CommentBlog comment) {
        comment.setCreateTime(LocalDateTime.now());
        int comments = commentMapper.saveComment(comment);
        //文章评论计数
//        blogMapper.getCommentCountById(comment.getBlogId());
        return comments;
    }

    //删除评论
    @Override
    public void deleteComment(Long id) {
        CommentBlog comment = commentMapper.selectById(id);
        commentMapper.deleteComment(id);
        Blog tBlog = blogMapper.selectById(comment.getBlogId());
        blogMapper.updateById(tBlog);
//        blogMapper.getCommentCountById(comment.getBlogId());
    }

    //根据昵称查询评论
    @Override
    public List<CommentBlog> findCommentByNickname(String nickname) {
        QueryWrapper<CommentBlog> queryWrapper = new QueryWrapper<CommentBlog>();
        queryWrapper.like("nickname",nickname);
        //以创建时间排序（降序）
        queryWrapper.orderByDesc("create_time");
        List<CommentBlog> tBlogList = commentMapper.selectList(queryWrapper);
        return tBlogList;
    }

    @Override
    public Page<CommentBlog> getCommentByPage(Long current, Long size) {
        //创建Page对象
        Page<CommentBlog> commentPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<CommentBlog> wrapper = new QueryWrapper<>();
        //排序
        wrapper.orderByDesc("create_time");
        //调用mybatis plus分页方法进行查询
        commentMapper.selectPage(commentPage,wrapper);
        //通过Page对象获取分页信息
        List<CommentBlog> typeList = commentPage.getRecords(); //每页的数据 list集合
        for(int i=0;i<typeList.size();i++){
            CommentBlog a = typeList.get(i);
            Blog blog = blogMapper.getByBlogId(String.valueOf(a.getBlogId()));
            if(blog!=null)
                a.setParentNickname(blog.getTitle());
            else
                a.setParentNickname("没有这篇博客，这是个异常评论");
        }
        long pagesize = commentPage.getSize(); //每页显示的条数
        long total = commentPage.getTotal(); //总记录数
        long pages = commentPage.getPages(); //总页数
        return commentPage;
    }
}
