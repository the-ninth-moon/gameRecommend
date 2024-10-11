package com.example.gameRecommend.controller;


import com.example.gameRecommend.entity.TGameImage;
import com.example.gameRecommend.service.ITGameImageService;
import com.example.gameRecommend.service.impl.TGameImageServiceImpl;
import com.example.gameRecommend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2024-10-05
 */
@RestController
@RequestMapping("/game-image")
public class TGameImageController {
    @Autowired
    private ITGameImageService gameImageService;

    @GetMapping("/gameImage/{gameId}")
    public Result getImagesForGame(@PathVariable("gameId") Long gameId) {
        List<String> images = gameImageService.getImagesByGameId(gameId);
        Result result = Result.build();
        result.setCode(200);
        result.setData(images);
        return result;
    }

    @PostMapping("/update/{gameId}")
    public Result updateGameImages(@PathVariable("gameId") Long gameId,@RequestBody List<String> imageUrls) {
        gameImageService.updateImages(gameId,imageUrls);
        Result result = Result.build();
        result.setCode(200);
        return result;
    }
}
