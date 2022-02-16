package com.ares.core.utils;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2021/03/17
 * @see: com.ares.core.utils ExcelUtils.java
 **/
public class ExcelUtils {
    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            return response.getOutputStream();
        } catch (Exception e) {
            throw new Exception("导出文件失败！");
        }
    }

    public static void writeExcel(HttpServletResponse response, List<?> list, String fileName,
                                  String sheetName, Class clazz) throws Exception {
        EasyExcel.write(getOutputStream(fileName, response), clazz).sheet(sheetName).doWrite(list);
    }
}
