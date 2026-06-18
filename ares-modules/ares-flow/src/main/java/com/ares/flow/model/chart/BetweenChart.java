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
import org.dromara.warm.flow.core.enums.ChartStatus;
import org.dromara.warm.flow.core.utils.CollUtil;
import org.dromara.warm.flow.core.utils.ObjectUtil;
import org.dromara.warm.flow.core.utils.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.BetweenChart.java
 **/
@SuppressWarnings("checkstyle:VisibilityModifier")
public class BetweenChart extends FlowChart {
    public Color c;
    private int x;
    private int y;
    private int width = 100;
    private int height = 80;
    private int arcWidth = 20;
    private List<TextChart> textCharts;
    private NodeJson nodeJson;

    public BetweenChart(int x, int y, Color c, List<TextChart> textCharts, NodeJson nodeJson) {
        this.x = x;
        this.y = y;
        this.c = c;
        if (CollUtil.isNotEmpty(textCharts)) {
            this.textCharts = textCharts;
        } else {
            this.textCharts = new ArrayList();
        }

        this.nodeJson = nodeJson;
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public void draw(Graphics2D graphics, String modelValue) {
        graphics.setColor(this.lightColor(this.c, modelValue));
        graphics.fillRoundRect((this.x - 50) * this.n, (this.y - 40) * this.n, this.width * this.n, this.height * this.n, this.arcWidth * this.n, this.arcWidth * this.n);
        graphics.setColor(this.c);
        Stroke originalStroke = graphics.getStroke();
        if (ChartStatus.getToDo(modelValue).equals(this.c)) {
            float[] dashPattern = new float[]{10.0F, 5.0F};
            BasicStroke dashedStroke = new BasicStroke(2.5F, 0, 0, 10.0F, dashPattern, 0.0F);
            graphics.setStroke(dashedStroke);
        }

        graphics.drawRoundRect((this.x - 50) * this.n, (this.y - 40) * this.n, this.width * this.n, this.height * this.n, this.arcWidth * this.n, this.arcWidth * this.n);
        graphics.setStroke(originalStroke);
        if (CollUtil.isNotEmpty(this.textCharts)) {
            for (int i = 0; i < this.textCharts.size(); ++i) {
                TextChart textChart = (TextChart) this.textCharts.get(i);
                if (ObjectUtil.isNotNull(textChart) && StringUtils.isNotEmpty(textChart.getTitle())) {
                    String[] lines = textChart.getTitle().split("\n");

                    for (int j = 0; j < lines.length; ++j) {
                        TextChart textChartNew = this.copyText(lines[j], textChart);
                        if (j == 0) {
                            this.textCharts.set(i, textChartNew);
                        } else {
                            ++i;
                            this.textCharts.add(i, textChartNew);
                        }
                    }
                }
            }

            int y = this.y - this.height / 2;
            int unitHeight = this.height / (this.textCharts.size() + 1);

            for (int i = 0; i < this.textCharts.size(); ++i) {
                TextChart textChart = (TextChart) this.textCharts.get(i);
                if (ObjectUtil.isNotNull(textChart) && StringUtils.isNotEmpty(textChart.getTitle())) {
                    if (textChart.getX() == null) {
                        textChart.setX(this.x);
                    }

                    if (textChart.getY() == null) {
                        textChart.setY(y + (i + 1) * unitHeight);
                    }
                }
            }

            this.textCharts.forEach((textChartx) -> {
                if (ObjectUtil.isNotNull(textChartx) && StringUtils.isNotEmpty(textChartx.getTitle())) {
                    textChartx.setN(this.n).draw(graphics, modelValue);
                }

            });
        }

    }

    public void toOffset(int offsetW, int offsetH) {
        this.x += offsetW;
        this.y += offsetH;
    }

    public TextChart copyText(String title, TextChart orgText) {
        TextChart textChart = new TextChart(title, orgText.getFont());
        if (orgText.getX() != null) {
            textChart.setX(orgText.getX());
        }

        if (orgText.getY() != null) {
            textChart.setY(orgText.getY());
        }

        textChart.setN(orgText.n);
        textChart.setC(orgText.c);
        return textChart;
    }

    public void topText(String title) {
        this.textCharts.add((new TextChart(title)).setY(this.y - this.height / 2 - 10));
    }

    public void topText(String title, Color c) {
        this.textCharts.add((new TextChart(title)).setC(c).setY(this.y - this.height / 2 - 10));
    }

    public void addText(String title) {
        this.textCharts.add(new TextChart(title));
    }

    public void addText(String title, Color c) {
        this.textCharts.add((new TextChart(title)).setC(c));
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

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getArcWidth() {
        return this.arcWidth;
    }

    public List<TextChart> getTextCharts() {
        return this.textCharts;
    }

    public NodeJson getNodeJson() {
        return this.nodeJson;
    }

    public BetweenChart setC(Color c) {
        this.c = c;
        return this;
    }

    public BetweenChart setX(int x) {
        this.x = x;
        return this;
    }

    public BetweenChart setY(int y) {
        this.y = y;
        return this;
    }

    public BetweenChart setWidth(int width) {
        this.width = width;
        return this;
    }

    public BetweenChart setHeight(int height) {
        this.height = height;
        return this;
    }

    public BetweenChart setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
        return this;
    }

    public BetweenChart setTextCharts(List<TextChart> textCharts) {
        this.textCharts = textCharts;
        return this;
    }

    public BetweenChart setNodeJson(NodeJson nodeJson) {
        this.nodeJson = nodeJson;
        return this;
    }
}
