package com.slaand.site.patterns.factory;

/**
 * User web element interface.
 */
public interface WebElement {

    String getName();
    void setName(String value);

    void setEncodedPicture(String base64);

}
