package com.thinhnguyen.provider.telecom.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum DataType {
    D10(1, 2),
    D20(2, 5),
    D30(3, 10),
    D50(4, 20),
    D100(5, 30),
    D200(6, 45),
    D300(7, 70),
    D500(8, 115)
    ;

    int code;
    long days;
}
