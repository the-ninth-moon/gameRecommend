package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.gameRecommend.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
public interface TagService extends IService<Tag> {
    Result pageTags(Long current, Long size);

    Result getTagByName(String name);

    Result updateTag(Tag tTag);

    Result deleteTagById(Long id);

    Result getTagById(Long id);

    Result getTagByGameId(String id);
}
