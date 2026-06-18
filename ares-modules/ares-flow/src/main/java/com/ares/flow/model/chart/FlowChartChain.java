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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.FlowChartChain.java
 **/
public class FlowChartChain {
    private int width;
    private int height;
    private int n;
    private final List<FlowChart> flowChartList = new ArrayList();

    public FlowChartChain() {
    }

    public void addFlowChart(FlowChart flowChart) {
        this.flowChartList.add(flowChart);
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    public void draw(int width, int height, int offsetW, int offsetH, Graphics2D graphics, int n, String modelValue) {
        this.width = width;
        this.height = height;
        this.n = n;
        this.setExample(width, n);
        this.setFlowTitle(width, height, n);

        for (FlowChart flowChart : this.flowChartList) {
            flowChart.offset(offsetW, offsetH);
        }

        graphics.setStroke(new BasicStroke(2.5F));

        for (FlowChart flowChart : this.flowChartList) {
            flowChart.setN(n).draw(graphics, modelValue);
        }

    }

    private void setFlowTitle(int width, int height, int n) {
        int textX = (width - 400) / n;
        int textY = (height - 50) / n;
        Font font = new Font("微软雅黑", 1, 20 * n);
        String title = "Warm-Flow";
        TextChart textChart = (new TextChart(textX, textY, title, font)).setAlpha(0.8F);
        textChart.setOffsetEnable(false);
        this.addFlowChart(textChart);
    }

    private void setExample(int width, int n) {
        int tmp = width - 600;

        for (ChartStatus value : ChartStatus.values()) {
            int nodeX = tmp / n + 50;
            int nodeY = 40;
            int textX = nodeX - 20;
            int textY = nodeY - 25;
            TextChart textChart = new TextChart(textX, textY, value.getValue());
            this.addFlowChart((new BetweenChart(nodeX, nodeY, ChartStatus.getColorByKey(value.getKey()), CollUtil.toList(new TextChart[]{textChart}), (NodeJson) null)).setWidth(60).setHeight(20).setArcWidth(5).setOffsetEnable(false));
            tmp += 140;
        }

    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getN() {
        return this.n;
    }

    public List<FlowChart> getFlowChartList() {
        return this.flowChartList;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setN(int n) {
        this.n = n;
    }
}
