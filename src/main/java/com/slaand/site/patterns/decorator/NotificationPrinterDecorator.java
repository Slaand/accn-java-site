package com.slaand.site.patterns.decorator;

import com.slaand.site.patterns.observer.EmailInformation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationPrinterDecorator extends PrinterDecorator {

    public NotificationPrinterDecorator(Printer printer) {
        super(printer);
    }

    @Override
    public void print(EmailInformation msg) {
        log.info("----- " + msg.getType() + " -----------------------" +
                "\nTitle: " + msg.getTitle() +
                "\nDear, " + msg.getName() +
                "\n" + msg.getMessage() +
                "\nSincerely, " +
                "Your mega ultra shop"
        );
    }

}
