package com.example.gameRecommend.controller;


import com.example.gameRecommend.mapper.TLikeGameMapper;
import com.example.gameRecommend.service.ITLikeGameService;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/likes-game")
public class TLikeGameController {
    @Autowired
    private ITLikeGameService likeGameService;
    @Autowired
    private TLikeGameMapper likeGameMapper;

    @PostMapping("/game")//添加
    public Result likeGame(@RequestParam Long userId, @RequestParam Long gameId) {
        boolean liked = likeGameService.likeGame(userId, gameId);
        Result result=Result.build();
        if (!liked) {
            result.setCode(220);
            return result;
        }
        result.setCode(200);
        return result;
    }

    @GetMapping("/islike")//添加
    public Result isLikeGame(@RequestParam Long userId, @RequestParam Long gameId) {
        int liked = likeGameMapper.countLikes(userId, gameId);
        Result result=Result.build();
        if (liked==0) {
            result.setCode(200);
            result.setData(0);
            return result;
        }
        result.setCode(200);
        result.setData(1);
        return result;
    }

    @GetMapping("/game/count")
    public Result getLikeCountByGameId(@RequestParam Long gameId) {
        int likeCount = likeGameService.getLikeCountByGameId(gameId);
        Result result=Result.build();
        result.setData(likeCount);
        result.setCode(200);
        return result;
    }
}
