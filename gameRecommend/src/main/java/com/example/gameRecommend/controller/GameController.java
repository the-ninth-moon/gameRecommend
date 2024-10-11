package com.example.gameRecommend.controller;


import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.form.GameForm;
import com.example.gameRecommend.mapper.GameMapper;
import com.example.gameRecommend.service.GameService;
import com.example.gameRecommend.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/game")
public class GameController {


    @Autowired
    GameService tGameService;
    @Autowired
    GameMapper gameMapper;

    Result result = Result.build();

    /**
     * 分页查询
     * @param current
     * @param size
     * @param published
     * @param flag
     * @param share_statement
     * @param is_delete
     * @return
     */
    //给swagger2加注解的
    @GetMapping("/getByPage")
    @ApiOperation("游戏信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "published",value = "是否公开"),
            @ApiImplicitParam(name = "flag",value = "原创或转载"),
            @ApiImplicitParam(name = "share_statement",value = "草稿"),
            @ApiImplicitParam(name = "is_delete",value = "是否已删除"),
            @ApiImplicitParam(name = "typeId",value = "分类ID"),
    })
    public Result getByPage(Long current, Long size, Boolean published,
                            String flag, Boolean share_statement, Boolean is_delete,Long typeId){
        return tGameService.pageGames(current, size,published,flag,share_statement,is_delete,typeId);
    }

    /**
     * 根据博客标题查询
     * @param title
     * @return
     */
    @GetMapping("/getByTitle")
    @ApiOperation("通过文章标题查询")
    @ApiImplicitParam(name = "title",value = "文章的标题")
    public Result findByTitle(String title){
        return tGameService.getByTitle(title);
    }

    /**
     * 保存文章和标签
     * @param params
     * @return
     */
    @PostMapping("/saveGame")
    public Result saveGameTag(@RequestBody HashMap<String,Object> params) {
        //对博客需要进行校验
        Result check = GameForm.check(params);
        if (check.getCode() == 500) {
            System.out.println(params);
            return check;
        }else {
            return tGameService.saveGame(params);
        }
    }

    /**
     * 暂时保存博客
     * @param params
     * @return
     */
    @PostMapping("/temporarySave")
    public Result temporarySave(@RequestBody HashMap<String,Object> params){
        Result checkTemporaryGame = GameForm.checkTemporaryGame(params);
        if (checkTemporaryGame.getCode() == 500) {
            return checkTemporaryGame;
        }
        else {
            return tGameService.temporarySave(params);
        }
    }

    /**
     * 更新博客和标签
     * @param params
     * @return
     */
    @PutMapping("/updateGame")
    public Result updateGame(@RequestBody HashMap<String,Object> params){
        Result checkUpdateGame = GameForm.check(params);
        if (checkUpdateGame.getCode() == 500) {
            return checkUpdateGame;
        }
        else {
            return tGameService.updateGame(params);
        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/logicDeleteGame")
    public Result logicDeleteGame(String id){
        Game tGame = tGameService.getById(id);
        if (tGame != null){
            return tGameService.logicDeleteGame(id);
        }else {
            result.setCode(500);
            result.setMsg("没有数据");
            return result;
        }
    }

    /**
     * 删除博客和对应标签
     * @param id
     * @return
     */
    @DeleteMapping("/deleteGame")
    public Result deleteGame(String id){
        Game tGame = tGameService.getById(id);
        if (tGame != null){
            return tGameService.deleteBT(id);
        }else {
            result.setCode(500);
            result.setMsg("没有数据");
            return result;
        }
    }

    /**
     * 还原删除的博客
     * @param id
     * @return
     */
    @GetMapping("/recoveryGame")
    public Result recoveryGame(String id){
        return tGameService.recoveryGame(id);
    }

    /**
     * 根据id查找博客
     * @param id
     * @return
     */
    @GetMapping("/getByGameId")
    @ResponseBody
    public Result getByGameId(String id){
        result.setCode(200);
        result.setMsg("查询成功");
        result.setData(tGameService.getByGameId(id));
        return result;
    }


}
