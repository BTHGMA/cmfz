package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.User;
import com.baizhi.util.GoEasyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String , Object> map = new HashMap<>();
        Integer records = userDao.selectRecords() ;
        Integer total = records%rows == 0 ?records/rows : records/rows+1;
        Integer begin = (page-1)*rows;
        List<Album> carousels = userDao.selectAll(begin, rows);
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
    public void changeUser(User user) {
        userDao.update(user);
    }

    @Override
    public List<User>queryByeasyPoi(User user) {
        List<User> users = userDao.selectByeasyPoi(user);

        return users;
    }

    @Override
    public void addByeasyPoi(User user) {
        userDao.insert(user);
    }

    @Override
    public Object login(User user) {
        Map<String , Object> map = new HashMap<>();
        User login = userDao.login(user);
        if(login==null){
            map.put("code",200);
            map.put("message","用户名不存在");
        }else if (login.getPassword().equals(user.getPassword())){
            User user1 = userDao.selectByPhone(user);
            return user1;
        }
        return map;

    }

    @Override
    public User queryByPhone(User user) {
        User user1 = userDao.selectByPhone(user);
        return user1;
    }

    @Override
    public Object addByUser(User user) {
        Map<String , Object> map = new HashMap<>();
        User selectById = userDao.selectById(user);
        if(selectById!=null){
            map.put("code",400);
            map.put("message","手机号已存在");
            return map;
        }else {
            String s = UUID.randomUUID().toString();
            user.setId(s);
            user.setRegistTime(new Date());
            userDao.insertUser(user);
            GoEasyUtils.pushMessage("user_regist","addOne");
            return user;
        }

    }

    @Override
    public User queryById(User user) {
        User user1 = userDao.selectById(user);
        return user1;
    }

    @Override
    public Map<String, List<String>> countRegist() {
        List<String> months = new ArrayList<>();
        List<String> counts = new ArrayList<>();
        List<Map<String, Object>> list = userDao.countRegistUserByMonth();
        for (Map<String, Object> map : list) {
            months.add(map.get("month").toString());
            counts.add(map.get("count").toString());
        }
        HashMap<String, List<String>> map = new HashMap<>();
        map.put("months", months);
        map.put("counts", counts);
        return map;
    }

    @Override
    public Map<String, Object> userDistribution() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> men = userDao.userDistributionByGender("男");
        List<Map<String, Object>> women = userDao.userDistributionByGender("女");
        Integer maxCount = userDao.userDistributionMaxCount();
        map.put("men", men);
        map.put("women", women);
        map.put("maxCount", maxCount);
        return map;
    }


}
