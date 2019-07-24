package com.baizhi.controller;

import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;
    @RequestMapping("queryAll")
    public Map<String , Object> queryAll(Integer page , Integer rows){
        Map<String, Object> stringObjectMap = carouselService.queryAll(page, rows);
        return stringObjectMap;
    }
    @RequestMapping("edit")
    public String edit(Carousel carousel , String oper , String[] id){
        if ("edit".equals(oper)){
            //执行修改代码
            carouselService.changeCarousel(carousel);
            return carousel.getId();
        }else if("add".equals(oper)){
            //执行增加代码
            String s = carouselService.addCarousel(carousel);
            return s;
        }else{
            for (String asd : id) {
                Carousel carousel1 = new Carousel();
                carousel1.setId(asd);
                carouselService.removeCarousel(carousel);
            }
            return  carousel.getId();
        }

    }
    @RequestMapping("upload")
    public void upload(String id , MultipartFile imgPath, HttpServletRequest request , HttpServletResponse response){
        //文件上传
        String originalFilename = imgPath.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("carouselPic");
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        try {
            imgPath.transferTo(new File(path,originalFilename));
            //修改数据库的路径
            Carousel carousel = new Carousel();
            carousel.setId(id);
            carousel.setCreateTime(new Date());
            carousel.setImgPath(originalFilename);
            carouselService.modifyImgPath(carousel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
