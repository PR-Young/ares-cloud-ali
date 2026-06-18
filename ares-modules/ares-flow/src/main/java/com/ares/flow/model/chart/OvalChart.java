/*
 *
 *  * ****************************************************************************
 *  * * Copyright (c) 2021 - 9999, ARES
 *  * *
 *  * * Licensed under the Apache License, Version 2.0 (the "License");
 *  * * you may not use this file except in compliance with the License.
 *  * * You may obtain a copy of the License at
 *  * *
 *  * *        http://www.apache.org/licenses/LICENSE-2.0
 *  * *
 *  * * Unless required by applicable law or agreed to in writing, software
 *  * * distributed under the License is distributed on an "AS IS" BASIS,
 *  * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * * See the License for the specific language governing permissions and
 *  * * limitations under the License.
 *  * ***************************************************************************
 *
 */

package com.ares.flow.model.chart;


import org.dromara.warm.flow.core.dto.NodeJson;
import org.dromara.warm.flow.core.utils.ObjectUtil;
import org.dromara.warm.flow.core.utils.StringUtils;

import java.awt.*;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.OvalChart.java
 **/
@SuppressWarnings("checkstyle:VisibilityModifier")
public class OvalChart extends FlowChart {
    public Color c;
    private int x;
    private int y;
    private TextChart textChart;
    private NodeJson nodeJson;

    public OvalChart(int x, int y, Color c, TextChart textChart, NodeJson nodeJson) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.textChart = textChart;
        this.nodeJson = nodeJson;
    }

    public void draw(Graphics2D graphics, String modelValue) {
        graphics.setColor(this.lightColor(this.c, modelValue));
        graphics.fillOval((this.x - 20) * this.n, (this.y - 20) * this.n, 40 * this.n, 40 * this.n);
        graphics.setColor(this.c);
        graphics.drawOval((this.x - 20) * this.n, (this.y - 20) * this.n, 40 * this.n, 40 * this.n);
        if (ObjectUtil.isNotNull(this.textChart) && StringUtils.isNotEmpty(this.textChart.getTitle())) {
            this.textChart.setY(this.y + 5);
            this.textChart.setN(this.n).draw(graphics, modelValue);
        }

    }

    public void toOffset(int offsetW, int offsetH) {
        this.x += offsetW;
        this.y += offsetH;
        if (ObjectUtil.isNotNull(this.textChart) && StringUtils.isNotEmpty(this.textChart.getTitle())) {
            this.textChart.offset(offsetW, offsetH);
        }

    }

    public Color getC() {
        return this.c;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public TextChart getTextChart() {
        return this.textChart;
    }

    public NodeJson getNodeJson() {
        return this.nodeJson;
    }

    public OvalChart setC(Color c) {
        this.c = c;
        return this;
    }

    public OvalChart setX(int x) {
        this.x = x;
        return this;
    }

    public OvalChart setY(int y) {
        this.y = y;
        return this;
    }

    public OvalChart setTextChart(TextChart textChart) {
        this.textChart = textChart;
        return this;
    }

    public OvalChart setNodeJson(NodeJson nodeJson) {
        this.nodeJson = nodeJson;
        return this;
    }
}
