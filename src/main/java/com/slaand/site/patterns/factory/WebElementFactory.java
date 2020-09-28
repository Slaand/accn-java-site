package com.slaand.site.patterns.factory;

import com.slaand.site.model.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
public class WebElementFactory<T> {

    /**
     * Factory method takes as parameter a web element type and initiate the appropriate class.
     */
    @SneakyThrows
    public List<T> fillWithDummyElements(ElementType type, int maxAmount, int currentAmount) {

        final int amountToFill = maxAmount - currentAmount;
        if (amountToFill > 0) {
            List<T> appendedElements = new ArrayList<>();
            for (int i = 0; i < amountToFill; i++) {
                WebElement element = type.getConstructor().get();

                File blankPicture = ResourceUtils.getFile("classpath:static/img/150.png");
                byte[] fileContent = FileUtils.readFileToByteArray(blankPicture);

                String encodedPicture = Base64.getEncoder().encodeToString(fileContent);
                String nameOfElement = element.getClass() == CategoryEntity.class ? "Dummy category" : "Dummy item";

                element.setName(nameOfElement);
                element.setEncodedPicture(encodedPicture);
                appendedElements.add((T) element);
            }
            return appendedElements;
        }
        return Collections.emptyList();
    }
}
