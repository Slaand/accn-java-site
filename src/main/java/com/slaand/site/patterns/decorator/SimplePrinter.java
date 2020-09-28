package com.slaand.site.patterns.decorator;

import com.slaand.site.patterns.observer.EmailInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("simple")
public class SimplePrinter implements Printer {

    @Override
    public void print(EmailInformation msg) {
        log.info("Simple log: " + msg);
    }
}
