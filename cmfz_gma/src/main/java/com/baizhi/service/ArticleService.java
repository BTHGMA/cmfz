package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String , Object> queryAll(Integer page, Integer rows);
    String addArticle(Article article);
    void removeArticle(Article article);
    List <Article> queryByContent(Article article);
}

