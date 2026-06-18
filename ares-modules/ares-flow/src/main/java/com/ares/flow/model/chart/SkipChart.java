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


import org.dromara.warm.flow.core.utils.ObjectUtil;
import org.dromara.warm.flow.core.utils.StringUtils;

import java.awt.*;
import java.util.Arrays;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.SkipChart.java
 **/
@SuppressWarnings("checkstyle:VisibilityModifier")
public class SkipChart extends FlowChart {
    public Color c;
    private int[] xPoints;
    private int[] yPoints;
    private TextChart textChart;

    public SkipChart(int[] xPoints, int[] yPoints, Color c, TextChart textChart) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.c = c;
        this.textChart = textChart;
    }

    public void draw(Graphics2D graphics, String modelValue) {
        graphics.setColor(this.c);
        this.xPoints = Arrays.stream(this.xPoints).map((x) -> x * this.n).toArray();
        this.yPoints = Arrays.stream(this.yPoints).map((y) -> y * this.n).toArray();
        graphics.drawPolyline(this.xPoints, this.yPoints, this.xPoints.length);
        int xEndOne = this.xPoints[this.xPoints.length - 1];
        int xEndTwo = this.xPoints[this.xPoints.length - 2];
        int yEndOne = this.yPoints[this.yPoints.length - 1];
        int yEndTwo = this.yPoints[this.yPoints.length - 2];
        int xArrowLength = 5 * this.n;
        int yArrowLength = 10 * this.n;
        int[] xArrow;
        int[] yArrow;
        if (xEndOne == xEndTwo) {
            xArrow = new int[]{xEndOne - xArrowLength, xEndOne, xEndOne + xArrowLength};
            if (yEndOne > yEndTwo) {
                yArrow = new int[]{yEndOne - yArrowLength, yEndOne, yEndOne - yArrowLength};
            } else {
                yArrow = new int[]{yEndOne + yArrowLength, yEndOne, yEndOne + yArrowLength};
            }
        } else {
            yArrow = new int[]{yEndOne - xArrowLength, yEndOne, yEndOne + xArrowLength};
            if (xEndOne < xEndTwo) {
                xArrow = new int[]{xEndOne + yArrowLength, xEndOne, xEndOne + yArrowLength};
            } else {
                xArrow = new int[]{xEndOne - yArrowLength, xEndOne, xEndOne - yArrowLength};
            }
        }

        graphics.fillPolygon(xArrow, yArrow, 3);
        if (ObjectUtil.isNotNull(this.textChart) && StringUtils.isNotEmpty(this.textChart.getTitle())) {
            this.textChart.setY(this.textChart.getY() - 5);
            this.textChart.setN(this.n).draw(graphics, modelValue);
        }

    }

    public void toOffset(int offsetW, int offsetH) {
        for (int i = 0; i < this.xPoints.length; ++i) {
            this.xPoints[i] += offsetW;
        }

        for (int i = 0; i < this.yPoints.length; ++i) {
            this.yPoints[i] += offsetH;
        }

        if (ObjectUtil.isNotNull(this.textChart) && StringUtils.isNotEmpty(this.textChart.getTitle())) {
            this.textChart.offset(offsetW, offsetH);
        }

    }

    public Color getC() {
        return this.c;
    }

    public int[] getXPoints() {
        return this.xPoints;
    }

    public int[] getYPoints() {
        return this.yPoints;
    }

    public TextChart getTextChart() {
        return this.textChart;
    }

    public SkipChart setC(Color c) {
        this.c = c;
        return this;
    }

    public SkipChart setXPoints(int[] xPoints) {
        this.xPoints = xPoints;
        return this;
    }

    public SkipChart setYPoints(int[] yPoints) {
        this.yPoints = yPoints;
        return this;
    }

    public SkipChart setTextChart(TextChart textChart) {
        this.textChart = textChart;
        return this;
    }
}
