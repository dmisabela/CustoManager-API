package com.customanagerapi.domain.utils;

import java.io.Serializable;
import java.util.List;

import com.customanagerapi.enums.FieldTypeEnum;
import com.customanagerapi.enums.FilterOperatorEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FilterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private FilterOperatorEnum operator;

    private FieldTypeEnum fieldType;

    private transient Object value;

    private transient Object valueTo;

    private transient List<Object> values;

}
