package com.slaand.site.patterns.factory;

import com.slaand.site.model.entity.CategoryEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebElementFactoryTest {

    private WebElementFactory<CategoryEntity> dummyElements = new WebElementFactory<>();

    @Test
    void fillWithDummyElements() {

        List<CategoryEntity> entities = dummyElements.fillWithDummyElements(ElementType.CATEGORY, 10, 2);
        assertEquals(8, entities.size());
        assertEquals("Dummy category", entities.get(0).getName());
        assertNotNull(entities.get(0).getImage().getBase64());

    }
}