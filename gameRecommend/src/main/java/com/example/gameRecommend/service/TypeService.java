package com.example.gameRecommend.service;

import com.example.gameRecommend.entity.Type;
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
public interface TypeService extends IService<Type> {
    Result pageTypes(Long current, Long size);

    Result getTypeByName(String name);

    Result deleteTypeById(Long id);

    Result updateType(Type tType);

    Result getTypeById(Long id);
}
