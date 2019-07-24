package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Guru;
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
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String , Object> map = new HashMap<>();
        Integer records = guruDao.selectRecords() ;
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<Album> carousels = guruDao.selectAll(begin, rows);
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
    public void modifyprofile(Guru guru) {
        guruDao.updateprofile(guru);
    }

    @Override
    public String addGuru(Guru guru) {
        String s = UUID.randomUUID().toString();
        guru.setId(s);
        guruDao.insert(guru);
        return s;
    }

    @Override
    public void changeGuru(Guru guru) {

        guruDao.update(guru);

    }


}
