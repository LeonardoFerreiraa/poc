package br.com.leonardoferreira.poc;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LFUCache<T> implements Cache<T> {

    private final int capacity;

    private final Map<T, AtomicInteger> internalCache;

    private LFUCache(final int capacity) {
        this.capacity = capacity;
        this.internalCache = new LinkedHashMap<>(capacity);
    }

    public static <T> LFUCache<T> from(final int capacity) {
        return new LFUCache<>(capacity);
    }

    @Override
    public boolean contains(final T t) {
        final AtomicInteger entry = internalCache.get(t);
        if (entry == null) {
            return false;
        }

        entry.incrementAndGet();

        return true;
    }

    @Override
    public void add(final T t) {
        if (internalCache.size() >= capacity) {
            Map.Entry<T, AtomicInteger> toRemove = null;

            for (final Map.Entry<T, AtomicInteger> entry : internalCache.entrySet()) {
                if (toRemove == null) {
                    toRemove = entry;
                } else if (toRemove.getValue().get() > entry.getValue().get()) {
                    toRemove = entry;
                }
            }

            if (toRemove != null) {
                internalCache.remove(toRemove.getKey());
            }
        }

        internalCache.put(t, new AtomicInteger(0));
    }

    @Override
    public String toString() {
        return "LFUCache(capacity=%s, internalCache=%s)"
                .formatted(capacity, internalCache);
    }

}
