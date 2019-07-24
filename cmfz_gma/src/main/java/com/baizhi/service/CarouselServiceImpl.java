package com.baizhi.service;

import com.baizhi.dao.CarouselDao;
import com.baizhi.entity.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselDao carouselDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String , Object> map = new HashMap<>();
        Integer records = carouselDao.selectRecords();
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<Carousel> carousels = carouselDao.selectAll(begin, rows);
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
    public String addCarousel(Carousel carousel) {
        String s = UUID.randomUUID().toString();
        carousel.setId(s);
        carousel.setCreateTime(new Date());
        carouselDao.insert(carousel);
        return s;
    }

    @Override
    public void modifyImgPath(Carousel carousel) {

        carouselDao.updateImgPath(carousel);
    }

    @Override
    public void changeCarousel(Carousel carousel) {

        carouselDao.update(carousel);
    }

    @Override
    public void removeCarousel(Carousel carousel) {
        System.out.println(carousel);
        carouselDao.delete(carousel);
    }
}
