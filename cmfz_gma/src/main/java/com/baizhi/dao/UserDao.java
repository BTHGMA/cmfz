package com.baizhi.dao;

import com.baizhi.entity.Guru;
import com.baizhi.entity.User;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao {
    void updateprofile(User user);
    List<User> selectByeasyPoi(User user);
    User login(User user);
    User selectByPhone(User user);
    void insertUser(User user);
    User selectById(User user);
    List<Map<String, Object>> countRegistUserByMonth();
    List<Map<String, Object>> userDistributionByGender(String gender);
    Integer userDistributionMaxCount();
    List<User> selectByAll();
}
