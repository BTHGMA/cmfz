package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Guru;

import java.util.Map;

public interface GuruService {
    Map<String , Object> queryAll(Integer page, Integer rows);
    void modifyprofile(Guru guru);
    String   addGuru(Guru guru);
    void changeGuru(Guru guru);

}

