package com.ares.flowable.persistence.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlowViewerDto implements Serializable {

    private String key;
    private boolean completed;
}
