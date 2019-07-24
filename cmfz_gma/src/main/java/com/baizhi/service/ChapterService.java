package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    Map<String , Object> queryAll(Integer page, Integer rows,String id);
    void modifyDownpath(Chapter chapter);
    String   addChapter(Chapter chapter);
    void changeChapter(Chapter chapter);
    void removeChapter(Chapter chapter);
}
