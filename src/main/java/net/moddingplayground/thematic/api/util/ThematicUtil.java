package net.moddingplayground.thematic.api.util;

import com.google.common.collect.Maps;
import net.minecraft.util.Util;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;

public class ThematicUtil extends Util {
    /**
     * A {@link TriFunction}, of which the return value is cached.
     */
    public static <T, U, V, R> TriFunction<T, U, V, R> memoize(final TriFunction<T, U, V, R> function) {
        return new TriFunction<>() {
            private final Map<Triple<T, U, V>, R> cache = Maps.newHashMap();

            @Override
            public R apply(T left, U middle, V right) {
                return this.cache.computeIfAbsent(Triple.of(left, middle, right), triple -> function.apply(triple.getLeft(), triple.getMiddle(), triple.getRight()));
            }

            public String toString() {
                return "memoize/3[function=" + function + ", size=" + this.cache.size() + "]";
            }
        };
    }
}
