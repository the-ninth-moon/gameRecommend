package com.example.gameRecommend.mapper;

import com.example.gameRecommend.entity.Game;
import com.example.gameRecommend.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-07-28
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    Tag loadTagByTagname(String tagname);

    List<Tag> getTagByGameId(@Param("id") String id);
}
