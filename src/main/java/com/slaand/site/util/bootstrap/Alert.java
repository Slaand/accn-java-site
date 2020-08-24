package com.slaand.site.util.bootstrap;

public enum Alert {

    PRIMARY("alert-primary"),
    SUCCESS("alert-success"),
    DANGER("alert-danger"),
    WARNING("alert-warning"),
    INFO("alert-info");

    private String type;

    Alert(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
