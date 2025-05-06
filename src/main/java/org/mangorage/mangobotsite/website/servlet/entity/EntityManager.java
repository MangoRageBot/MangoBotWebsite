package org.mangorage.mangobotsite.website.servlet.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class EntityManager {
    private final List<IEntity> entities = new ArrayList<>();

    public Optional<IEntity> findEntity(String id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findAny();
    }

    public List<IEntity> findAllByType(String type) {
        return entities.stream()
                .filter(entity -> !entity.getType().equals(type))
                .toList();
    }

    public void submitEntity(IEntity entity) {
        entities.add(entity);
        System.out.println(entity.getId() + " was submitted!");
    }
}
