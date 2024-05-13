package com.lms.global.util;

import com.lms.global.cosntant.ExcelColumnName;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.thymeleaf.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
//엑셀을 다운로드를 관리하는 유틸 클래스
public final class ExcelUtils implements ExcelSupport{
    private static final int MAX_ROW = 1000;

    @Override
    public void download(Class<?> clazz, List<?> data, String fileName, HttpServletResponse response) {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            int loop = 1;
            int listSize = data.size();

            //반복문을 사용해 하나의 시트당 최대 1000개의 데이터를 그려줌
            //또한 데이터를 이용해 내용을 다 그리면 다운로드 시키고 -> 사용했던 자원들 반납하기

            for (int start = 0; start < listSize; start += MAX_ROW) {
                int nextPage = MAX_ROW * loop;
                if (nextPage > listSize) nextPage = listSize -1;
                List<?> list = new ArrayList<>(data.subList(start, nextPage));
                getWorkBook(clazz, workbook, start, findHeaderNames(clazz), list, listSize);
                list.clear();
                loop++;
            }
        } catch (IOException | IllegalAccessException e) {
            log.info("Excel Download Error Message = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //workBook에 기존 시트명이 존재하지 않으면 새로운 시트 작성, 시트명이 존재하면 해당 시트 가져와서 이어적기
    private SXSSFWorkbook getWorkBook(Class<?> clazz, SXSSFWorkbook workbook, int rowIdx, List<String> headerNames, List<?> data, int maxSize) throws IllegalAccessException, IOException {
        //각 시트 당 MAX_ROW 개씩
        String sheetName = "Sheet" + (rowIdx / MAX_ROW + 1);


        return workbook;
    }

    //각 시트당 헤더를 그려주기 위한 메서드
    private void createHeaders(SXSSFWorkbook workbook, Row row, Cell cell, List<String> headerNames) {
        //header font style
        Font font = workbook.createFont();
        font.setColor((short) 255);

        //header cell style
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER); //가로 가운데 정렬
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬

        // 테두리 설정
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);

        // 배경 설정
        headerCellStyle.setFillForegroundColor((short) 102);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(font);

        for (int i = 0, size = headerNames.size(); i < size; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerCellStyle);
            cell.setCellValue(headerNames.get(i));
        }
    }

    //엑셀의 내용을 그려주는 메서드
    //데이터를 그리면서 주기적으로 flush
    private void createBody(Class<?> clazz, List<?> data, Sheet sheet, Row row, Cell cell, int rowNo) throws IllegalAccessException, IOException {
        int startRow = 0;
        for (Object o : data) {
            List<Object> fields = findFieldValue(clazz, o);
            row = sheet.createRow(++startRow);
            for (int i = 0, fieldSize = fields.size(); i < fieldSize; i++) {
                cell = row.createCell(i);
                cell.setCellValue(String.valueOf(fields.get(i)));

                // 주기적인 flush 진행
                if (rowNo % MAX_ROW == 0) {
                    ((SXSSFSheet) sheet).flushRows(MAX_ROW);
                }
            }
        }
    }

    //엑셀의 헤더 명칭을 찾는 로직
    private List<String> findHeaderNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumnName.class))
                .map(field -> field.getAnnotation(ExcelColumnName.class).name())
                .collect(Collectors.toList());
    }

    //데이터의 값을 추출하는 메서드
    private List<Object> findFieldValue(Class<?> clazz, Object obj) throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            result.add(field.get(obj));
        }
        return result;
    }

}
