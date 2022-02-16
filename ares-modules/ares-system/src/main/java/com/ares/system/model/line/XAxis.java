package com.ares.system.model.line;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/14
 * @see: com.ares.system.persistence.model.line XAxis.java
 **/
@Data
public class XAxis {
    private String type;
    private String[] data;
    private boolean boundaryGap = false;
    private Map<String, Object> axisTick;

    public void isShow(boolean show) {
        this.axisTick = new HashMap<>();
        this.axisTick.put("show", show);
    }
}
