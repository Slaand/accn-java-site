package com.slaand.site.patterns.observer;

import com.slaand.site.patterns.decorator.Printer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EmailPrinterService implements EmailInformationReceived {

    private Printer notificationPrinter;
    private Printer promotionPrinter;
    private EmailNotificationService emailNotificationService;

    public EmailPrinterService(@Qualifier("notification") Printer notificationPrinter,
                               @Qualifier("promotion") Printer promotionPrinter,
                               EmailNotificationService emailNotificationService) {
        this.notificationPrinter = notificationPrinter;
        this.promotionPrinter = promotionPrinter;
        this.emailNotificationService = emailNotificationService;
        this.emailNotificationService.subscribe(this);
    }

    @Override
    public void receivedInformation(EmailInformation contents) {
        if (contents.getType() == MessageType.NOTIFICATION) {
            notificationPrinter.print(contents);
        } else {
            promotionPrinter.print(contents);
        }
    }
}
