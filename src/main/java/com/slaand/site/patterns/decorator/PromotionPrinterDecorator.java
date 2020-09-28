package com.slaand.site.patterns.decorator;

import com.slaand.site.patterns.observer.EmailInformation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PromotionPrinterDecorator extends PrinterDecorator {

    public PromotionPrinterDecorator(Printer printer) {
        super(printer);
    }

    @Override
    public void print(EmailInformation msg) {
        log.info("===== " + msg.getType() + " =================" +
                "\nTitle: " + msg.getTitle() +
                "\nHello, " + msg.getName() +
                "\n" + msg.getMessage() +
                "\nWith best wishes, " +
                "Your mega ultra shop"
        );
    }

}
