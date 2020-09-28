package com.slaand.site.patterns.factory;

import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ItemEntity;

import java.util.function.Supplier;

public enum ElementType {

    /**
     * Enumeration for different types of web elements.
     */
    CATEGORY(CategoryEntity::new),
    ITEM(ItemEntity::new);

    private final Supplier<WebElement> constructor;

    ElementType(Supplier<WebElement> constructor) {
        this.constructor = constructor;
    }

    public Supplier<WebElement> getConstructor() {
        return this.constructor;
    }

}
