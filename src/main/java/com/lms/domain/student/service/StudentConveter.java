package com.lms.domain.student.service;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class StudentConveter implements AttributeConverter<List<String>, String>{

        private static final String SPLIT_CHAR = ",";

        @Override
        public String convertToDatabaseColumn(List<String> attribute) {
            if(attribute == null){
                return "내용없음";
            }
            return String.join(SPLIT_CHAR, attribute);
        }

        @Override
        public List<String> convertToEntityAttribute(String dbData) {
            if(dbData == null){
                dbData = "내용없음";
            } else {
                dbData.replace("내용없음", "");
            }
            return Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}
