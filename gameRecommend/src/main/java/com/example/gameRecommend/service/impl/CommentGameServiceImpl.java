package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.entity.CommentGame;
import com.example.gameRecommend.mapper.GameMapper;
import com.example.gameRecommend.mapper.CommentGameMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.service.CommentGameService;
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
public class CommentGameServiceImpl extends ServiceImpl<CommentGameMapper, CommentGame> implements CommentGameService {
    @Autowired
    private CommentGameMapper commentMapper;

    @Autowired
    private GameMapper gameMapper;

    //存放迭代找出的所有子代的集合
    private List<CommentGame> tempReplys = new ArrayList<>();

    /**
     * @Description: 查询评论
     * @Param: gameId：博客id
     * @Return: 评论消息
     */
    //不管父评论了了
    @Override
    public List<CommentGame> listCommentByGameId(Long gameId) {
        //查询出所有评论
        List<CommentGame> comments = commentMapper.findByGameIdParentIdNull(gameId);
//        for(CommentGame comment : comments){
//            Long id = comment.getId();
//            List<CommentGame> childComments = commentMapper.findByGameIdParentIdNotNull(gameId,id);
//            //查询出子评论
//            combineChildren(gameId, childComments);
//            comment.setReplyComments(tempReplys);
//            tempReplys = new ArrayList<>();
//        }

        return comments;
    }

    /**
     * @Description: 查询出子评论
     * @Auther: ONESTAR
     * @Date: 10:43 2020/6/23
     * @Param: childComments：所有子评论
     * @Param: parentNickname1：父评论姓名
     * @Return:
     */
    private void combineChildren(Long gameId, List<CommentGame> childComments) {
        //判断是否有一级子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(CommentGame childComment : childComments){
                tempReplys.add(childComment);
                Long childId = childComment.getId();
                //查询出子二级评论
                recursively(gameId, childId);
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
    private void recursively(Long gameId, Long childId) {
        //根据子一级评论的id找到子二级评论
        List<CommentGame> replayComments = commentMapper.findByGameIdAndReplayId(gameId,childId);

        if(replayComments.size() > 0){
            for(CommentGame replayComment : replayComments){
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(gameId,replayId);
            }
        }
    }

    //新增评论
    @Override
    public int saveComment(CommentGame comment) {
        comment.setcreateTime(LocalDateTime.now());
        int comments = commentMapper.saveComment(comment);
        //文章评论计数
//        gameMapper.getCommentCountById(comment.getGameId());
        return comments;
    }

    //删除评论
    @Override
    public void deleteComment(Long id) {
        CommentGame comment = commentMapper.selectById(id);
        commentMapper.deleteComment(id);
        Game tGame = gameMapper.selectById(comment.getGameId());
        gameMapper.updateById(tGame);
//        gameMapper.getCommentCountById(comment.getGameId());
    }

    //根据昵称查询评论
    @Override
    public List<CommentGame> findCommentByNickname(String nickname) {
        QueryWrapper<CommentGame> queryWrapper = new QueryWrapper<CommentGame>();
        queryWrapper.like("nickname",nickname);
        //以创建时间排序（降序）
        queryWrapper.orderByDesc("create_time");
        List<CommentGame> tGameList = commentMapper.selectList(queryWrapper);
        return tGameList;
    }

    @Override
    public Page<CommentGame> getCommentByPage(Long current, Long size) {
        //创建Page对象
        Page<CommentGame> commentPage = new Page<>(current,size);
        //构建条件
        QueryWrapper<CommentGame> wrapper = new QueryWrapper<>();
        //排序
        wrapper.orderByDesc("create_time");
        //调用mybatis plus分页方法进行查询
        commentMapper.selectPage(commentPage,wrapper);
        //通过Page对象获取分页信息
        List<CommentGame> typeList = commentPage.getRecords(); //每页的数据 list集合
        for(int i=0;i<typeList.size();i++){
            CommentGame a = typeList.get(i);
            Game game = gameMapper.getByGameId(String.valueOf(a.getGameId()));
            if(game!=null)
                a.setParentNickname(game.getName());
            else
                a.setParentNickname("没有这个游戏，这是个异常评论");

        }
        long pagesize = commentPage.getSize(); //每页显示的条数
        long total = commentPage.getTotal(); //总记录数
        long pages = commentPage.getPages(); //总页数
        return commentPage;
    }

    @Override
    public Page<CommentGame> getGameCommentByPage(Long gameId,Long current, Long size) {
        // 创建分页对象
        Page<CommentGame> commentPage = new Page<>(current, size);

        // 调用自定义的分页查询
        return commentMapper.selectGameCommentByPage(commentPage, gameId);
    }
}
