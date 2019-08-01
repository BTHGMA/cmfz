package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.github.mustachejava.Code;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> stringObjectMap = userService.queryAll(page, rows);
        return stringObjectMap;
    }

    @RequestMapping("edit")
    public String edit(User user, String oper, String[] id) {
        if ("edit".equals(oper)) {
            //执行修改代码
            userService.changeUser(user);
            return user.getId();
        } else if ("add".equals(oper)) {
            //执行增加代码

        } else {

        }

        return null;
    }

    @RequestMapping("queryByeasyPoi")
    public void queryByeasyPoi(User user, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("用户数据表".getBytes("gbk"), "iso8859-1") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        List<User> users = userService.queryByeasyPoi(user);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), User.class, users);
        workbook.write(response.getOutputStream());
    }

    @RequestMapping("addByeasyPoi")
    public void addByeasyPoi(MultipartFile file) throws Exception {
        Workbook workbook = new HSSFWorkbook(file.getInputStream());
        //获取第一个表
        Sheet sheet = workbook.getSheetAt(0);
        //获取有多少行
        int lastRowNum = sheet.getLastRowNum();
        //创建一个集合取接受
        List<User> users = new ArrayList<>();

        for (int i = 0; i < lastRowNum - 1; i++) {
            Row row = sheet.getRow(i + 1);
            Cell cell = row.getCell(0);
            User user = new User();
            String id = cell.getStringCellValue();

            user.setId(UUID.randomUUID().toString());
            Cell cell1 = row.getCell(1);
            String phone = cell1.getStringCellValue();
            user.setPhone(phone);
            Cell cell2 = row.getCell(2);
            String password = cell2.getStringCellValue();
            user.setPassword(password);
            Cell cell3 = row.getCell(3);
            String dharmaName = cell3.getStringCellValue();
            user.setDharmaName(dharmaName);
            Cell cell4 = row.getCell(4);
            String province = cell4.getStringCellValue();
            user.setProvince(province);
            Cell cell5 = row.getCell(5);
            String city = cell5.getStringCellValue();
            user.setCity(city);
            Cell cell6 = row.getCell(6);
            String gender = cell6.getStringCellValue();
            user.setGender(gender);
            Cell cell7 = row.getCell(7);
            String personalSign = cell7.getStringCellValue();
            user.setPersonalSign(personalSign);
            Cell cell8 = row.getCell(8);
            String profile = cell8.getStringCellValue();
            user.setProfile(profile);
            Cell cell9 = row.getCell(9);
            String status = cell9.getStringCellValue();
            user.setStatus(status);
            Cell cell10 = row.getCell(10);
//            Date dateCellValue = cell10.getDateCellValue();
//            user.setRegistTime(dateCellValue);
            user.setRegistTime(new SimpleDateFormat("yyyy-MM-dd").parse(cell10.getStringCellValue()));
            users.add(user);
        }
        for (User user1 : users) {

            userService.addByeasyPoi(user1);
        }
    }

    @RequestMapping("login")
    public Object login(User user, HttpSession session, ModelMap map) {
        Object login = userService.login(user);
        return login;

    }
    @RequestMapping("addByUser")
    public Object addByUser(User user){
        Object o = userService.addByUser(user);
        return o;
    }
    @RequestMapping("countRegist")
    public Object countRegist() {
        Map<String, List<String>> map = userService.countRegist();
        return map;
    }
    @RequestMapping("userDistribution")
    public Object userDistribution(){
        Map<String, Object> stringObjectMap = userService.userDistribution();

        return stringObjectMap;
    }
}
