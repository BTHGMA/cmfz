package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;

import java.util.Map;

public interface AlbumService {
    Map<String , Object> queryAll(Integer page , Integer rows);
    void modifyImgPath(Album album);
    String  addAlbum(Album album);
    void changeAlbum(Album album);
    void removeAlbum(Album album,String[] id);
}

