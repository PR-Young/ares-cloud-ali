package com.ares.core.model.base;


import com.ares.core.model.system.SysTemplate;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/06/19
 **/
public class DefaultMessage {
    private String sender;
    private List receiver;
    private Map vars;
    private SysTemplate template;

    public static MessageBuilder newInstance() {
        return new MessageBuilder();
    }

    protected DefaultMessage(MessageBuilder builder) {
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.vars = builder.vars;
        this.template = builder.template;
    }

    public static class MessageBuilder {
        private String sender;
        private List receiver;
        private Map vars;
        private SysTemplate template;

        public MessageBuilder() {
        }

        public MessageBuilder sender(String value) {
            this.sender = value;
            return this;
        }

        public MessageBuilder receivers(List value) {
            this.receiver = value;
            return this;
        }

        public MessageBuilder vars(Map value) {
            this.vars = value;
            return this;
        }

        public MessageBuilder template(SysTemplate value) {
            this.template = value;
            return this;
        }

        public DefaultMessage build() {
            return new DefaultMessage(this);
        }
    }

    public String getSender() {
        return sender;
    }

    public List getReceiver() {
        return receiver;
    }

    public Map getVars() {
        return vars;
    }

    public SysTemplate getTemplate() {
        return template;
    }
}
