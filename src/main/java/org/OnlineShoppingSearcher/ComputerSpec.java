package org.OnlineShoppingSearcher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liubi
 * @date 2020-08-26 18:11
 **/
@NoArgsConstructor
@ToString
public class ComputerSpec {

    @Getter
    private Map<Class, Object> properties = new HashMap<>();

    public Object getProperty(Class property) {
        return this.properties.get(property);
    }

    public ComputerSpec addProperty(Class propertyType, Object property) {
        this.properties.put(propertyType, property);
        return this;
    }

    // 单一职责原则 matches只负责匹配
    // search 函数负责搜索
    public boolean matches(ComputerSpec other) {
        for (Class matchKey : this.properties.keySet()) {
            if (other.getProperties().containsKey(matchKey)) {
                if (!this.properties.get(matchKey).equals(other.getProperties().get(matchKey))) {
                    return false;
                }
            }
        }
        return true;
    }
}
