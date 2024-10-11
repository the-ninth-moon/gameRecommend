package com.example.gameRecommend.controller;



import com.example.gameRecommend.mapper.TFavorGameMapper;
import com.example.gameRecommend.mapper.TLikeGameMapper;
import com.example.gameRecommend.service.ITFavorGameService;
import com.example.gameRecommend.service.ITLikeGameService;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-09-30
 */
@RestController
@RequestMapping("/favors-game")
public class TFavorGameController {
    @Autowired
    private ITFavorGameService favorGameService;
    @Autowired
    private TFavorGameMapper favorGameMapper;

    @PostMapping("/game")//添加
    public Result favorGame(@RequestParam Long userId, @RequestParam Long gameId) {
        boolean favord = favorGameService.favorGame(userId, gameId);
        Result result=Result.build();
        if (!favord) {
            result.setCode(220);
            return result;
        }
        result.setCode(200);
        return result;
    }

    @GetMapping("/isfavor")//添加
    public Result isFavorGame(@RequestParam Long userId, @RequestParam Long gameId) {
        int favord = favorGameMapper.countFavors(userId, gameId);
        Result result=Result.build();
        if (favord==0) {
            result.setCode(200);
            result.setData(0);
            return result;
        }
        result.setCode(200);
        result.setData(1);
        return result;
    }

    @GetMapping("/game/count")
    public Result getFavorCountByGameId(@RequestParam Long gameId) {
        int favorCount = favorGameService.getFavorCountByGameId(gameId);
        Result result=Result.build();
        result.setData(favorCount);
        result.setCode(200);
        return result;
    }
}
