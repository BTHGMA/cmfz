package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AlbumService;
import com.baizhi.service.CarouselService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("queryAll")
    public Map<String , Object> queryAll(Integer page , Integer rows){
        Map<String, Object> stringObjectMap = albumService.queryAll(page,rows);
        return stringObjectMap;
    }
    @RequestMapping("edit")
    public String edit(String oper , String[] id,Album album) {
        if ("edit".equals(oper)) {
            //执行修改代码
            albumService.changeAlbum(album);
            return album.getId();
        } else if ("add".equals(oper)) {
            //执行增加代码
            String s = albumService.addAlbum(album);
            return s;
        } else {

            albumService.removeAlbum(album,id);
            return album.getId();

        }

    }
    @RequestMapping("upload")
    public void upload(String id ,MultipartFile cover, HttpServletRequest request , HttpServletResponse response){
        //文件上传
        String originalFilename = cover.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("albumPic");
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        try {
            cover.transferTo(new File(path,originalFilename));
            //修改数据库的路径
            Album album = new Album();
            album.setId(id);
            album.setCover(originalFilename);
            albumService.modifyImgPath(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
