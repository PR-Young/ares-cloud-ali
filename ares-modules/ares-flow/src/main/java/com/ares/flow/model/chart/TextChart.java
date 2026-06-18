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


import java.awt.*;

/**
 * @description:
 * @author: Young
 * @date: 2025/7/17
 * @see: com.ares.flow.model.chart.TextChart.java
 **/
@SuppressWarnings("checkstyle:VisibilityModifier")
public class TextChart extends FlowChart {
    public Color c;
    private Integer x;
    private Integer y;
    private String title;
    private Font font;
    private float alpha = 1.0F;

    public TextChart(String title) {
        this.title = title;
    }

    public TextChart(int x, int y, String title) {
        this.x = x;
        this.y = y;
        this.title = title;
    }

    public TextChart(String title, Font font) {
        this.title = title;
        this.font = font;
    }

    public TextChart(int x, int y, String title, Font font) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.font = font;
    }

    public void draw(Graphics2D graphics, String modelValue) {
        graphics.setColor(this.c == null ? Color.BLACK : this.c);
        if (this.font != null) {
            graphics.setFont(this.font);
        }

        graphics.setComposite(AlphaComposite.getInstance(3, this.alpha));
        this.setX(this.getX() - graphics.getFontMetrics().stringWidth(this.title) / (2 * this.n));
        graphics.drawString(this.title, this.x * this.n, this.y * this.n);
    }

    public void toOffset(int offsetW, int offsetH) {
        this.x = this.x + offsetW;
        this.y = this.y + offsetH;
    }

    public Color getC() {
        return this.c;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public String getTitle() {
        return this.title;
    }

    public Font getFont() {
        return this.font;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public TextChart setC(Color c) {
        this.c = c;
        return this;
    }

    public TextChart setX(Integer x) {
        this.x = x;
        return this;
    }

    public TextChart setY(Integer y) {
        this.y = y;
        return this;
    }

    public TextChart setTitle(String title) {
        this.title = title;
        return this;
    }

    public TextChart setFont(Font font) {
        this.font = font;
        return this;
    }

    public TextChart setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }
}
