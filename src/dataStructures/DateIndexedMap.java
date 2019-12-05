package dataStructures;

import java.io.Serializable;

import basicDateTime.BasicDateTime;

public interface DateIndexedMap<K extends BasicDateTime, V> extends AbstractMap<K, V>, Serializable {

}
