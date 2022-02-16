package com.ares.system.model;

import java.io.Serializable;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/14
 * @see: com.ares.system.persistence.model PanelGroup.java
 **/
public class PanelGroup implements Serializable {
    private static final long serialVersionUID = 7775446339832013970L;
    private String name;
    private String type;
    private String icon;
    private Number startVal;
    private Number endVal;
    private Number duration;

    public static PanelGroupBuild newInstance() {
        return new PanelGroupBuild();
    }

    protected PanelGroup(PanelGroupBuild builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.icon = builder.icon;
        this.startVal = builder.startVal;
        this.endVal = builder.endVal;
        this.duration = builder.duration;
    }

    public static class PanelGroupBuild {
        private String name;
        private String type;
        private String icon;
        private Number startVal;
        private Number endVal;
        private Number duration;

        public PanelGroupBuild() {
        }

        public PanelGroupBuild setName(String name) {
            this.name = name;
            return this;
        }

        public PanelGroupBuild setType(String type) {
            this.type = type;
            return this;
        }

        public PanelGroupBuild setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public PanelGroupBuild setStartVal(Number startVal) {
            this.startVal = startVal;
            return this;
        }

        public PanelGroupBuild setEndVal(Number endVal) {
            this.endVal = endVal;
            return this;
        }

        public PanelGroupBuild setDuration(Number duration) {
            this.duration = duration;
            return this;
        }

        public PanelGroup build() {
            return new PanelGroup(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public Number getStartVal() {
        return startVal;
    }

    public Number getEndVal() {
        return endVal;
    }

    public Number getDuration() {
        return duration;
    }
}
