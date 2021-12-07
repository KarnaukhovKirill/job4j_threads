package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (integer, base) -> {
            if (model.getVersion() != base.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            base.setVersion(base.getVersion() + 1);
            base.setName(model.getName());
            return base;
            }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public int size() {
        return memory.size();
    }
}
