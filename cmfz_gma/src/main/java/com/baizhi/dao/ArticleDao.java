package com.baizhi.dao;

import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;

import java.util.List;

public interface ArticleDao extends BaseDao {
List<Article> selectByContent (Article article);
}
