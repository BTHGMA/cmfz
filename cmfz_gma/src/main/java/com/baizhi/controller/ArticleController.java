package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("queryAll")
    public Map<String , Object> queryAll(Integer page, Integer rows){
        Map<String, Object> stringObjectMap = articleService.queryAll(page-1, rows);
        return stringObjectMap;
    }
//    public  List<Article> queryAllByPage(Integer page,Integer size){
//        List<Article> articles = articleService.queryAllByPage(page, size);
//        return articles;
//    }
    @RequestMapping("edit")
    public String edit(String oper , String[] id, Article article) {
        if ("edit".equals(oper)) {
            //执行修改代码


        } else if ("add".equals(oper)) {
            //执行增加代码
            String s = articleService.addArticle(article);
            return s;

        } else {

            articleService.removeArticle(article);


        }
        return article.getId();
    }
    @RequestMapping("upload")
    public Map<String , Object> upload(MultipartFile file, HttpServletRequest request){
        String originalFilename = file.getOriginalFilename();
        String articlePic = request.getSession().getServletContext().getRealPath("articlePic");
        File file1 = new File(articlePic);
        if (!file1.exists()){
            file1.mkdir();
        }
        Map<String , Object> map = new HashMap<>();

        try {
            file.transferTo(new File(articlePic,originalFilename));
            map.put("error",0);
            map.put("url","http://localhost:8888/cmfz/articlePic/"+originalFilename);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("url","http://localhost:8888/cmfz/articlePic/"+originalFilename);
            return map;
        }

    }
    @RequestMapping("showAll")
    public Map<String , Object> showAll(HttpServletRequest request){
        String articlePic = request.getSession().getServletContext().getRealPath("articlePic");
        File file = new File(articlePic);
        String[] list = file.list();
        Map<String , Object> map = new HashMap<>();
        map.put("current_url","http://localhost:8888/cmfz/articlePic/");
        map.put("total_count",list.length);
        List<Object> lists = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            Map<String , Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            File file1 = new File(articlePic,s);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("is_photo",true);
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",substring);
            map1.put("filename",s);
            map1.put("datetime",new Date());
            lists.add(map1);
        }
        map.put("file_list",lists);
        return map;
    }
    @RequestMapping("addArticle")
    public String addArticle(Article article){
        String s = articleService.addArticle(article);
        return s;
    }
    @RequestMapping("querybycontent")
    public List<Article> queryByContent(Article article){
        List<Article> articles = articleService.queryByContent(article);
        return articles;
    }
    @RequestMapping("queryByKeywordAndPage")
    public List<Article> queryByKeywordAndPage(String name, Integer page, Integer size){
        List<Article> articles = articleService.queryByKeywordAndPage(name, 1, 3);
        return articles;
    }
}
