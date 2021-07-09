package mavenproject.modules.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jinghong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboBoxEntity<K, V> implements Serializable {

    private K key;

    private V value;
}
