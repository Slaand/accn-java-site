package com.slaand.site.patterns.memento;

import com.slaand.site.model.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryRestoreService {

    private CategoryEntity entity;

    public CategoryRestoreService getInstance(CategoryEntity entity) {
        this.entity = entity;
        return this;
    }

    public void saveState() {
        entity.getStates().add(entity.getMemento());
    }

    public void rollback() {
        entity.setMemento(entity.getStates().pop());
    }

}
