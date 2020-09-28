package com.slaand.site.config;

import com.slaand.site.patterns.decorator.NotificationPrinterDecorator;
import com.slaand.site.patterns.decorator.Printer;
import com.slaand.site.patterns.decorator.PrinterDecorator;
import com.slaand.site.patterns.decorator.PromotionPrinterDecorator;
import com.slaand.site.patterns.observer.EmailNotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("notification")
    PrinterDecorator notificationPrinterDecorator(@Qualifier("simple") final Printer printer) {
        return new NotificationPrinterDecorator(printer);
    }

    @Bean
    @Qualifier("promotion")
    PrinterDecorator promotionPrinterDecorator(@Qualifier("simple") final Printer printer) {
        return new PromotionPrinterDecorator(printer);
    }

    @Bean
    @Qualifier("emailService")
    EmailNotificationService emailNotificationService() {
        return new EmailNotificationService();
    }

}
