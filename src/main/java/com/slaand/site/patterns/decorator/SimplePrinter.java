package com.slaand.site.patterns.decorator;

import com.slaand.site.patterns.observer.EmailInformation;
import org.springframework.stereotype.Service;

@Service("simple")
public class SimplePrinter implements Printer {

    @Override
    public void print(EmailInformation msg) {
        System.out.println(msg);
    }
}
