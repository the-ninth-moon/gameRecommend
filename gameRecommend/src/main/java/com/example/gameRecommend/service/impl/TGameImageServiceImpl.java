package com.example.gameRecommend.service.impl;

import com.example.gameRecommend.entity.TGameImage;
import com.example.gameRecommend.mapper.TGameImageMapper;
import com.example.gameRecommend.service.ITGameImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-10-05
 */
@Service
public class TGameImageServiceImpl extends ServiceImpl<TGameImageMapper, TGameImage> implements ITGameImageService {
    @Autowired
    TGameImageMapper gameImageMapper;
    @Override
    public List<String> getImagesByGameId(Long gameId) {
        return gameImageMapper.getGameImage(gameId);
    }

    @Override
    public void updateImages(Long gameId,List<String> imageUrls) {
        // 清空现有的记录
        gameImageMapper.deleteGameImage(gameId);
        for(int i=0;i<imageUrls.size();++i){
            gameImageMapper.insert(new TGameImage(gameId, imageUrls.get(i)));
        }
    }
}
