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

import java.awt.*;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.ParallelChart.java
 **/
@SuppressWarnings("checkstyle:VisibilityModifier")
public class ParallelChart extends FlowChart {
    public Color c;
    private int x;
    private int y;
    private NodeJson nodeJson;

    public ParallelChart(int x, int y, Color c, NodeJson nodeJson) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.nodeJson = nodeJson;
    }

    public void draw(Graphics2D graphics, String modelValue) {
        int[] xParallels = new int[]{(this.x - 20) * this.n, this.x * this.n, (this.x + 20) * this.n, this.x * this.n};
        int[] yParallels = new int[]{this.y * this.n, (this.y - 20) * this.n, this.y * this.n, (this.y + 20) * this.n};
        graphics.setColor(this.lightColor(this.c, modelValue));
        graphics.fillPolygon(xParallels, yParallels, 4);
        graphics.setColor(this.c);
        graphics.drawPolygon(xParallels, yParallels, 4);
        int[] xPoints1 = new int[]{(this.x - 8) * this.n, (this.x + 8) * this.n};
        int[] yPoints1 = new int[]{this.y * this.n, this.y * this.n};
        graphics.drawPolyline(xPoints1, yPoints1, xPoints1.length);
        int[] xPoints2 = new int[]{this.x * this.n, this.x * this.n};
        int[] yPoints2 = new int[]{(this.y - 8) * this.n, (this.y + 8) * this.n};
        graphics.drawPolyline(xPoints2, yPoints2, xPoints2.length);
    }

    public void toOffset(int offsetW, int offsetH) {
        this.x += offsetW;
        this.y += offsetH;
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

    public NodeJson getNodeJson() {
        return this.nodeJson;
    }

    public ParallelChart setC(Color c) {
        this.c = c;
        return this;
    }

    public ParallelChart setX(int x) {
        this.x = x;
        return this;
    }

    public ParallelChart setY(int y) {
        this.y = y;
        return this;
    }

    public ParallelChart setNodeJson(NodeJson nodeJson) {
        this.nodeJson = nodeJson;
        return this;
    }
}
