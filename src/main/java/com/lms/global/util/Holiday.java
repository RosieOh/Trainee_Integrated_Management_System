package com.lms.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Holiday {

    private String name;
    private String date;

    public Holiday (String name, String date) {
        this.name = name;
        this.date = date;
    }

}
