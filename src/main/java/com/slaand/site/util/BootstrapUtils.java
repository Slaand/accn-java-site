package com.slaand.site.util;

import com.slaand.site.util.bootstrap.Alert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BootstrapUtils {

    public static void setAlertModel(Model model, Alert alertType, String message) {
        model.addAttribute("messageType", alertType.getType());
        model.addAttribute("message", message);
    }

}
