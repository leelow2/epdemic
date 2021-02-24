package com.leelow.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class GraphBean {
    private String date;
    private int confirm;
    private int heal;
    private int dead;
}
