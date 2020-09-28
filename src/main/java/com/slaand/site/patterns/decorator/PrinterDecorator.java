package com.slaand.site.patterns.decorator;

public abstract class PrinterDecorator implements Printer {

    protected Printer printer;

    public PrinterDecorator(Printer printer) {
        this.printer = printer;
    }
}
