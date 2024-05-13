package com.lms.global.util;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ExcelSupport {
    void download(Class<?> clazz, List<?> data, String fileName, HttpServletResponse response);
}
