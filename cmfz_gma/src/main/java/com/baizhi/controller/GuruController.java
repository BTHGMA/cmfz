package com.baizhi.controller;

import com.baizhi.entity.Carousel;
import com.baizhi.entity.Guru;
import com.baizhi.service.CarouselService;
import com.baizhi.service.GuruService;
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
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;
    @RequestMapping("queryAll")
    public Map<String , Object> queryAll(Integer page , Integer rows){
        Map<String, Object> stringObjectMap = guruService.queryAll(page, rows);
        return stringObjectMap;
    }
    @RequestMapping("edit")
    public String edit(Guru guru , String oper , String[] id){
        if ("edit".equals(oper)){
            //执行修改代码
                guruService.changeGuru(guru);
                return guru.getId();
        }else if("add".equals(oper)){
            //执行增加代码
            String s = guruService.addGuru(guru);
            return s;
        }else{
            //执行删除代码
        }

        return null;
    }
    @RequestMapping("upload")
    public void upload(String id , MultipartFile profile, HttpServletRequest request , HttpServletResponse response){
        //文件上传
        String originalFilename = profile.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("guruPic");
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        try {
            profile.transferTo(new File(path,originalFilename));
            //修改数据库的路径
            Guru guru = new Guru();
            guru.setId(id);
            guru.setProfile(originalFilename);
            guruService.modifyprofile(guru);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
