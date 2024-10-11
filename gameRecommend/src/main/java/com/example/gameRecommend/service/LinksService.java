package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.Links;
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
public interface LinksService extends IService<Links> {

    Result pageLinks(Long current, Long size);

    Result getLinksByName(String name);

    Result updateLink(Links tLinks);

    Result deleteLinkById(Long id);

    Result getLinksById(Long id);
}
