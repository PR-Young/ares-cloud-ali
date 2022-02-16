package com.ares.system.model.line;

import com.ares.core.utils.StringUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/14
 * @see: com.ares.system.persistence.model.line Series.java
 **/
@Data
public class Series {
    private String name;
    private Map<String, Object> itemStyle;
    private boolean smooth;
    private String type;
    private Number[] data;
    private Number animationDuration;
    private String animationEasing;

    public void buildItemStyle(String lineColor, int lineWidth, String color, String areaColor) {
        this.itemStyle = new HashMap<>();
        Map<String, Object> normal = new HashMap<>();
        Map<String, Object> areaStyle = new HashMap<>();
        Map<String, Object> lineStyle = new HashMap<>();
        if (StringUtils.isNotEmpty(areaColor)) {
            areaStyle.put("color", areaColor);
            normal.put("areaStyle", areaStyle);
        }
        if (StringUtils.isNotEmpty(lineColor)) {
            lineStyle.put("color", lineColor);
            normal.put("lineStyle", lineStyle);
        }
        if (lineWidth > 0) {
            lineStyle.put("width", lineWidth);
            normal.put("lineStyle", lineStyle);
        }
        normal.put("color", color);
        this.itemStyle.put("normal", normal);
    }
}
