package com.huajun123.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListQuery extends Blog {
    private int limit;
    private int page;
    private String sort;
    private String time;
    private String status;
}
