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


import org.dromara.warm.flow.core.enums.ChartStatus;

import java.awt.*;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.FlowChart.java
 **/
@SuppressWarnings("checkstyle:VisibilityModifier")
public class FlowChart {
    public int n;
    public boolean offsetEnable = true;

    public FlowChart() {
    }

    public void draw(Graphics2D var1, String modelValue) {

    }

    public Color lightColor(Color c, String modelValue) {
        if (ChartStatus.getNotDone(modelValue).equals(c)) {
            return Color.WHITE;
        } else {
            float red = (float) c.getRed() / 255.0F;
            float green = (float) c.getGreen() / 255.0F;
            float blue = (float) c.getBlue() / 255.0F;
            float alpha = 0.15F;
            return new Color(red, green, blue, alpha);
        }
    }

    public void offset(int offsetW, int offsetH) {
        if (this.offsetEnable) {
            this.toOffset(offsetW, offsetH);
        }

    }

    public void toOffset(int offsetW, int offsetH) {
    }

    public int getN() {
        return this.n;
    }

    public boolean isOffsetEnable() {
        return this.offsetEnable;
    }

    public FlowChart setN(int n) {
        this.n = n;
        return this;
    }

    public FlowChart setOffsetEnable(boolean offsetEnable) {
        this.offsetEnable = offsetEnable;
        return this;
    }
}
