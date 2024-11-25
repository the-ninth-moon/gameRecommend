package com.example.gameRecommend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.entity.GameTags;
import com.example.gameRecommend.entity.Tag;
import com.example.gameRecommend.mapper.GameMapper;
import com.example.gameRecommend.mapper.GameTagsMapper;
import com.example.gameRecommend.mapper.TagMapper;
import com.example.gameRecommend.service.GameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.gameRecommend.service.TypeService;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Transactional(rollbackFor=Exception.class)  //开启事务
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {

    @Autowired
    GameMapper tGameMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    GameTagsMapper GameTagsMapper;
    @Autowired
    TypeService typeService;
    Result result = Result.build();

    @Override
    public Result pageGames(Long current, Long limit, Boolean published, String flag,Boolean share_statement,
                             Boolean is_delete,Long typeId) {
        Result result = Result.build();
        //创建Page对象
        Page<Game> tGamePage = new Page<>(current,limit);
        //构建条件
        QueryWrapper<Game> wrapper = new QueryWrapper<>();
        //获取传入讲师的条件是否为空
        //多条件组合查询
        //判断条件值是否为空,如果不为空拼接条件
        if (published != null){
            //构建条件
            wrapper.eq("published",published);
        }
        if (flag != null){
            //构建条件
            wrapper.eq("flag",flag);
        }
        if (share_statement != null){
            //构建条件
            wrapper.eq("share_statement",share_statement);
        }
        if (is_delete != null){
            //构建条件
            wrapper.eq("is_delete",is_delete);
        }
        if (typeId != null && typeId != 9){
            //构建条件
            wrapper.eq("typeId",typeId);//9 作为一个特殊分类表示按热度排序
        }
        if(typeId != null && typeId == 9){
            wrapper.orderByDesc("views");
        }
        else{
            wrapper.orderByDesc("sell_time");
        }

        //调用mybatis plus分页方法进行查询
        tGameMapper.selectPage(tGamePage,wrapper);

        //通过Page对象获取分页信息
        List<Game> tGameList = tGamePage.getRecords(); //每页的数据 list集合
        long size = tGamePage.getSize(); //每页显示的条数
        long total = tGamePage.getTotal(); //总记录数
        long pages = tGamePage.getPages(); //总页数

        result.setCode(200);
        result.setData(tGamePage);
        return result;
    }
    /**
     * 根据标题查询
     * @param title
     * @return
     */
    @Override
    public Result getByTitle(String title) {
        Result result = Result.build();
        QueryWrapper<Game> queryWrapper = new QueryWrapper<Game>();
        queryWrapper.like("name",title);
        List<Game> tGameList = tGameMapper.selectList(queryWrapper);
        result.setCode(200);
        result.setData(tGameList);
        return result;
    }

    /**
     * 保存游戏和标签
     * @param params
     * @return
     */
    @Override
    public Result saveGame(HashMap<String, Object> params) {
        Result result = Result.build();
        Game tGame = new Game();
        //tGame.setId(Long.parseLong(params.get("id").toString()));
        tGame.setName((String) params.get("name"));
        tGame.setDescription((String) params.get("description"));
        tGame.setDeveloper((String) params.get("developer"));
        tGame.setSellTime(LocalDateTime.parse(((String) params.get("sellTime"))+"T00:00:00"));
        tGame.setCollectTime(LocalDateTime.parse(((String) params.get("collectTime"))+"T00:00:00"));
        tGame.setPlatform((String) params.get("platform"));
        tGame.setOfficialWeb((String) params.get("officialWeb"));
        tGame.setConfigRequire((String) params.get("configRequire"));
        tGame.setFirstPicture((String) params.get("first_picture"));
        tGame.setGameContent((String) params.get("gameContent"));
        tGame.setTypeId(Long.parseLong(params.get("typeId").toString()));
        tGame.setPublished(Objects.equals(params.get("published").toString(), "1"));
       // System.out.println(("!!!!!!!!!!!!!!!!!!!!!" + Objects.equals(params.get("published").toString(), "1") + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"));
        //System.out.println(("!!!!!!!!!!!!!!!!!!!!!" +params.get("published").toString().getClass()  + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"));
        tGame.setShareStatement(true); //设置状态为已发布
        tGame.setUpdateTime(LocalDateTime.now());
        //System.out.println(tGame.toString());
        //保存文章
        int result_Game = tGameMapper.insert(tGame);

        int result_tag = 0;
        int resule_bt = 0;
        List<String> tags = (List) params.get("tags");
        //保存tag 并建立tag和Game的对应，存储在GameTags的表中
        for (String tag : tags) {
            //保存标签
            Tag tTag = new Tag();
            tTag.setName(tag);

            GameTags tGameTags = new GameTags();
            tGameTags.setGamesId(tGame.getId());

            Tag search = tagMapper.loadTagByTagname(tag);
            if(search==null) {
                result_tag = tagMapper.insert(tTag);
                tGameTags.setTagsId(tTag.getId());
            }
            else{
                tGameTags.setTagsId(search.getId());
                result_tag = 1;
            }
            //保存中间表记录
            tGameTags.setGamesId(tGame.getId());
            resule_bt = GameTagsMapper.insert(tGameTags);
        }
        if (result_Game != 0 && result_tag != 0 && resule_bt !=0){
            result.setCode(200);
            result.setMsg("添加成功！");
            return result;
        }
        result.setMsg("添加失败");
        return result;
    }

    /**
     * 暂时保存游戏
     * @param params
     * @return
     */
    @Override
    public Result temporarySave(HashMap<String, Object> params) {
        Result result = Result.build();
        Game tGame = new Game();
//        if(params.get("id").toString()!=null)
//        {
//            return tempUpdateGame(params);
//        }
        tGame.setName((String) params.get("name"));
        tGame.setDescription((String) params.get("description"));
        tGame.setDeveloper((String) params.get("developer"));
        tGame.setSellTime(LocalDateTime.parse(((String) params.get("sellTime"))+"T00:00:00"));
        tGame.setCollectTime(LocalDateTime.parse(((String) params.get("collectTime"))+"T00:00:00"));
        tGame.setPlatform((String) params.get("platform"));
        tGame.setOfficialWeb((String) params.get("officialWeb"));
        tGame.setConfigRequire((String) params.get("configRequire"));
        tGame.setFirstPicture((String) params.get("first_picture"));
        tGame.setGameContent((String) params.get("gameContent"));
        //保存文章
        int result_Game = tGameMapper.insert(tGame);
        if (result_Game != 0){
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
    public Result tempUpdateGame(HashMap<String, Object> params) {
        Result result = Result.build();
        //根据id查询文章,几乎和插入只有下面一句区别
        Game tGame = new Game();
        tGame.setId(Long.parseLong(params.get("id").toString()));
        tGame.setName((String) params.get("name"));
        tGame.setDescription((String) params.get("description"));
        tGame.setDeveloper((String) params.get("developer"));
        tGame.setSellTime(LocalDateTime.parse(((String) params.get("sellTime"))+"T00:00:00"));
        tGame.setCollectTime(LocalDateTime.parse(((String) params.get("collectTime"))+"T00:00:00"));
        tGame.setPlatform((String) params.get("platform"));
        tGame.setOfficialWeb((String) params.get("officialWeb"));
        tGame.setConfigRequire((String) params.get("configRequire"));
        tGame.setFirstPicture((String) params.get("first_picture"));
        tGame.setGameContent((String) params.get("gameContent"));
        tGame.setTypeId(Long.parseLong(params.get("typeId").toString()));
        tGame.setPublished(Objects.equals(params.get("published").toString(), "1"));
        tGame.setShareStatement(Boolean.valueOf(params.get("shareStatement").toString()) || Objects.equals(params.get("shareStatement").toString(), "发布"));
        tGame.setUpdateTime(LocalDateTime.now());

        //更新文章
        int result_Game = tGameMapper.updateById(tGame);
        return result;
    }
    @Override
    public Result updateGame(HashMap<String, Object> params) {
        Result result = Result.build();
        //根据id查询文章,几乎和插入只有下面一句区别
        Game tGame = tGameMapper.selectById(Long.parseLong(params.get("id").toString()));
        //tGame.setId(Long.parseLong(params.get("id").toString()));
        tGame.setName((String) params.get("name"));
        tGame.setDescription((String) params.get("description"));
        tGame.setDeveloper((String) params.get("developer"));
        tGame.setSellTime(LocalDateTime.parse(((String) params.get("sellTime"))+"T00:00:00"));
        tGame.setCollectTime(LocalDateTime.parse(((String) params.get("collectTime"))+"T00:00:00"));
        tGame.setPlatform((String) params.get("platform"));
        tGame.setOfficialWeb((String) params.get("officialWeb"));
        tGame.setConfigRequire((String) params.get("configRequire"));
        tGame.setFirstPicture((String) params.get("first_picture"));
        tGame.setGameContent((String) params.get("gameContent"));
        tGame.setTypeId(Long.parseLong(params.get("typeId").toString()));
        tGame.setPublished(Boolean.valueOf(params.get("published").toString()));
        tGame.setShareStatement(Boolean.valueOf(params.get("shareStatement").toString()) || Objects.equals(params.get("shareStatement").toString(), "发布"));
        tGame.setUpdateTime(LocalDateTime.now());

        //更新文章
        int result_Game = tGameMapper.updateById(tGame);

        int result_tag = 0;
        int resule_bt = 0;
        //在重新建立标签和游戏的对应前，要先删除原来的
        //先删除与这篇文章相关的标签和中间表记录
        QueryWrapper<GameTags> queryWrapper1 = new QueryWrapper<GameTags>();
        QueryWrapper<Tag> queryWrapper2 = new QueryWrapper<Tag>();
        queryWrapper1.eq("gamesId",tGame.getId());
        List<GameTags> GameTagsList =  GameTagsMapper.selectList(queryWrapper1);
        List<Tag> id = tagMapper.selectList(queryWrapper2);
        for (GameTags bt : GameTagsList) {
            //先删除标签表的数据
            //tagMapper.deleteById(bt.getTagsId());
            //再删除游戏标签关联表的数据
            QueryWrapper<GameTags> queryWrapper = new QueryWrapper<GameTags>();
            queryWrapper.eq("tagsId",bt.getTagsId());
            queryWrapper.eq("gamesId",tGame.getId());
            //再删除游戏标签关联表的数据
            GameTagsMapper.delete(queryWrapper);
        }
        //然后再重新添加标签
        List<String> tags = (List) params.get("tGameTags");
        for (String tag : tags) {
            //保存标签
            Tag tTag = new Tag();
            tTag.setName(tag);

            GameTags tGameTags = new GameTags();
            tGameTags.setGamesId(tGame.getId());

            Tag search = tagMapper.loadTagByTagname(tag);
            if(search==null) {
                result_tag = tagMapper.insert(tTag);
                tGameTags.setTagsId(tTag.getId());
            }
            else{
                tGameTags.setTagsId(search.getId());
                result_tag = 1;
            }
            //保存中间表记录
            tGameTags.setGamesId(tGame.getId());
            resule_bt = GameTagsMapper.insert(tGameTags);
        }
        if (result_Game != 0 && result_tag != 0 && resule_bt !=0){
            result.setCode(200);
            result.setMsg("更新游戏成功！");
            return result;
        }
        result.setMsg("更新游戏失败");
        return result;
    }

    /**
     * 逻辑删除游戏
     * @param id
     * @return
     */
    @Override
    public Result logicDeleteGame(String id) {
        Result result = Result.build();
        Game tGame = tGameMapper.selectById(id);
        if (tGame != null){
            tGame.setIsDelete(Boolean.TRUE);
            tGame.setUpdateTime(LocalDateTime.now());
            System.out.println("----------------------------------------------");
            System.out.println(tGame.toString());
            System.out.println("----------------------------------------------");
            //更新文章
            tGameMapper.updateById(tGame);
            result.setCode(200);
            result.setMsg("删除游戏成功！");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("删除游戏失败");
            return result;
        }
    }

    /**
     * 删除游戏和标签（彻底删除）
     * @param id
     * @return
     */
    @Override
    public Result deleteBT(String id) {
        Result result = Result.build();
        Game tGame = tGameMapper.selectById(id);
        //删除与这篇文章相关的标签和中间表记录
        QueryWrapper<GameTags> queryWrapper1 = new QueryWrapper<GameTags>();
        queryWrapper1.eq("Games_id",tGame.getId());
        List<GameTags> GameTagsList =  GameTagsMapper.selectList(queryWrapper1);
        int r_tag = 0;
        int r_Game_tag = 0;
        for (GameTags bt : GameTagsList) {
            //先删除标签表的数据
            r_tag = tagMapper.deleteById(bt.getTagsId());
            //再删除游戏标签关联表的数据
            QueryWrapper<GameTags> queryWrapper = new QueryWrapper<GameTags>();
            queryWrapper.eq("tags_id",bt.getTagsId());
            //再删除游戏标签关联表的数据
            r_Game_tag = GameTagsMapper.delete(queryWrapper);
        }
        //删除游戏文章
        int r_Game = tGameMapper.deleteById(id);
        if (r_tag != 0 && r_Game_tag!= 0 && r_Game!= 0){
            result.setCode(200);
            result.setMsg("删除游戏和标签成功");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("删除游戏和标签失败");
            return result;
        }

    }

    /**
     * 还原删除的游戏
     * @param id
     * @return
     */
    @Override
    public Result recoveryGame(String id) {
        Result result = Result.build();
        Game tGame = tGameMapper.selectById(id);
        if (tGame != null){
            tGame.setIsDelete(false);
            tGame.setUpdateTime(LocalDateTime.now());
            tGameMapper.updateById(tGame);
            result.setCode(200);
            result.setMsg("还原游戏成功！");
            return result;
        }else {
            result.setCode(500);
            result.setMsg("还原游戏失败");
            return result;
        }
    }

    /**
     * 根据id查找游戏
     * @param id
     * @return
     */
    @Override
    public Result getByGameId(String id) {
        Result result = Result.build();
        result.setCode(200);
        Game game = tGameMapper.selectById(id);
        game.setViews(game.getViews() + 1);
        tGameMapper.updateById(game);
        result.setData(game);

        return result;
    }
    @Override
    public void updateByComment(Game tGame) {
        tGameMapper.updateById(tGame);
    }

    @Override
    public Result getGamesByTypeName(String typeName) {
        // 通过分类名查找分类ID
        Result typeId = typeService.getTypeByName(typeName);
        Result games = Result.build();
        if (typeId.getData() == null) {
            games.setData(Collections.emptyList());
            return games; // 如果分类不存在，返回空列表
        }
        // 根据分类ID查找所有游戏
        games.setData(tGameMapper.getGamesByTypeId((Long) typeId.getData()));
        return games ;
    }

}