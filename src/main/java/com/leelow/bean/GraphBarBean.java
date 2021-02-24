package com.leelow.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GraphBarBean implements Comparable<GraphBarBean>{
    private String name;
    private int fromAbroad;

    @Override
    public int compareTo(GraphBarBean o) {
        return o.fromAbroad - this.fromAbroad;
    }
}
