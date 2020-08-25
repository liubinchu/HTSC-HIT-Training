package org.ConcurrentSimpleMapReduce;

import lombok.Data;

/**
 * @author liubi
 * @date 2020-08-25 19:10
 **/
@Data
class MappingElement {
    String key;
    Integer value;

    public MappingElement(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
