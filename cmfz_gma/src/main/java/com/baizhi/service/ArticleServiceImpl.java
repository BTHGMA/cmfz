package com.baizhi.service;


import com.baizhi.conf.ArticleReposiroty;
import com.baizhi.conf.CustomizedRepository;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;
    @Autowired
    CustomizedRepository customizedRepository;
    @Autowired
    ArticleReposiroty articleReposiroty;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String , Object> map = new HashMap<>();
        Integer records = articleDao.selectRecords();
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<Album> carousels = articleDao.selectAll(begin, rows);
        //当前页
        map.put("page",page);
        //总记录数
        map.put("records",records);
        //总页数
        map.put("total",total);
        //查询出的集合
        map.put("rows",carousels);
        return map;
    }

    @Override
    public String addArticle(Article article) {
        System.out.println(article);
        String s = UUID.randomUUID().toString();
        article.setId(s);
        articleDao.insert(article);
        return s;
    }

    @Override
    public void removeArticle(Article article) {
        articleDao.delete(article);
    }

    @Override
    public List<Article> queryByContent(Article article) {
        List<Article> articles = articleDao.selectByContent(article);
        return articles;
    }

    @Override
    public String addOneArticle(Article article) {
        String id = UUID.randomUUID().toString();
        article.setId(id);
        articleReposiroty.save(article);
        return id;
    }

    @Override
    public Iterable<Article> queryAll() {
        Iterable<Article> all = articleReposiroty.findAll();
        return all;
    }

    @Override
    public List<Article> queryAllByPage(Integer page, Integer size) {
        List<Article> articles = customizedRepository.findByPageable(page - 1, size);
        return articles;
    }

    @Override
    public List<Article> queryByKeywordAndPage(String name, Integer page, Integer size) {
        List<Article> byNameAndHighlightAndPageable = customizedRepository.findByNameAndHighlightAndPageable(name, page - 1, size);
        return byNameAndHighlightAndPageable;
    }


}
