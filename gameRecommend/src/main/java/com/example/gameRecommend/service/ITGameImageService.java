package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.TGameImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-10-05
 */
public interface ITGameImageService extends IService<TGameImage> {

    List<String> getImagesByGameId(Long gameId);

    void updateImages(Long gameId,List<String> imageUrls);
}
