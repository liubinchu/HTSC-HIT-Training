package org.StreamingMapReduce;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liubi
 * @date 2020-08-25 19:10
 **/
@Data
@AllArgsConstructor
class MappingElement {
    private String key;
    private Integer value;
}
