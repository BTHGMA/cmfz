package com.baizhi;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AdminService;
import com.baizhi.service.CarouselService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzGmaApplicationTests {
    @Test
    public void test1() throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("表一");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("编号");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        CellStyle red = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        red.setFont(font);
        cell.setCellStyle(red);
        File file = new File("D:/testPOI/");
        if (!file.exists()){
            file.mkdir();
        }

        workbook.write(new FileOutputStream("D:/testPOI/第一个Excel文档.xls"));
        workbook.close();
    }


}
