package moe.ksmz.rodentraid.Foundation;

import java.util.Map;

public class Builder<K, V> {
    private final Map<K, V> map;

    public Builder(Map<K, V> map) {
        this.map = map;
    }

    public Builder<K, V> put(K k, V v) {
        map.put(k, v);
        return this;
    }
}
