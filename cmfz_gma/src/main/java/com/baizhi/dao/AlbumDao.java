package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;

public interface AlbumDao extends BaseDao{
    void updateImgPath(Album album);
}
