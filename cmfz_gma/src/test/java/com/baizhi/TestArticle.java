package com.baizhi;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestArticle {
    @Autowired
    ArticleService articleService;
    @Test
    public void test(){
        articleService.addOneArticle(new Article(null,"1","西游记","讲述了一个人,三个动物去取经的故事,特别精彩",new Date()));
        articleService.addOneArticle(new Article(null,"1","钢铁是怎样炼成的","讲述了一个人有钢铁般的意志的故事,特别精彩",new Date()));
        articleService.addOneArticle(new Article(null,"1","哈姆雷特","讲述了一个宫廷内部斗争,争风吃醋的故事,特别精彩",new Date()));
        articleService.addOneArticle(new Article(null,"1","福尔摩斯","讲述了一个侦探探案的故事,特别精彩",new Date()));
    }
    @Test
    public void test2(){
        Iterable<Article> articles = articleService.queryAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }
//    @Test
//    public void test3(){
//        List<Article> articles = articleService.queryAllByPage(1, 3);
//        for (Article article : articles) {
//            System.out.println(article);
//        }
//    }
    @Test
    public void test4(){
        List<Article> articles = articleService.queryByKeywordAndPage("故事", 1, 3);
        for (Article article : articles) {
            System.out.println(article);
        }
    }
}
