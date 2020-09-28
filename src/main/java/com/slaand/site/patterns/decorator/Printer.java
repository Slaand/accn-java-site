package com.slaand.site.patterns.decorator;

import com.slaand.site.patterns.observer.EmailInformation;

public interface Printer {
    void print(EmailInformation msg);
}
