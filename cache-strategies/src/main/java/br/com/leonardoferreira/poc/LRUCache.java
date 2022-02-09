package br.com.leonardoferreira.poc;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Least recently used
 */
public class LRUCache<T> implements Cache<T> {

    private final int capacity;

    private final Set<T> internalCache;

    private LRUCache(final int capacity) {
        this.capacity = capacity;
        this.internalCache = new LinkedHashSet<>(capacity);
    }

    public static <T> LRUCache<T> from(final int capacity) {
        return new LRUCache<>(capacity);
    }

    @Override
    public boolean contains(final T t) {
        final boolean contains = internalCache.contains(t);
        if (contains) {
            internalCache.remove(t);
            internalCache.add(t);
        }

        return contains;
    }

    @Override
    public void add(final T t) {
        if (internalCache.size() >= capacity) {
            final T first = internalCache.iterator().next();
            internalCache.remove(first);
        }

        internalCache.add(t);
    }

    @Override
    public String toString() {
        return "LRUCache(capacity=%s, internalCache=%s)"
                .formatted(capacity, internalCache);
    }

}
