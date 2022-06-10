package net.moddingplayground.thematic.api.util;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

public class AccessibleMemoizeFunction<T, R> implements Function<T, R> {
    private final Map<T, R> cache = Maps.newHashMap();
    private final Function<T, R> function;

    public AccessibleMemoizeFunction(Function<T, R> function) {
        this.function = function;
    }

    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    public R getFirst() {
        return this.cache.get(new ArrayList<>(this.cache.keySet()).get(0));
    }

    @Override
    public R apply(T object) {
        return this.cache.computeIfAbsent(object, this.function);
    }

    public String toString() {
        return "memoize/1[function=" + this.function + ", size=" + this.cache.size() + "]";
    }
}
