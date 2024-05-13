package com.lms.global.util;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ExcelUtils implements ExcelSupport{
    @Override
    public void download(Class<?> clazz, List<?> data, String fileName, HttpServletResponse response) {

    }
}
