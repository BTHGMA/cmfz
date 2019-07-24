package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;

import com.baizhi.entity.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String , Object> map = new HashMap<>();
        Integer records = albumDao.selectRecords() ;
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<Album> carousels = albumDao.selectAll(begin, rows);
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
    public void modifyImgPath(Album album) {

        albumDao.updateImgPath(album);
    }

    @Override
    public String addAlbum(Album album) {
        System.out.println(album);
        String s = UUID.randomUUID().toString();
        album.setId(s);
        albumDao.insert(album);
        return s;
    }

    @Override
    public void changeAlbum(Album album) {
        albumDao.update(album);
    }

    @Override
    public void removeAlbum(Album album,String[] id) {
        for (String s : id) {
            Album album1 = new Album();
            album1.setId(s);
            albumDao.delete(album);
        }

    }
}
