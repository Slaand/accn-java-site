package com.slaand.site.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OtherUtils {

    @SneakyThrows
    public static String getBase64FromImageFile(MultipartFile file) {
        return StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false));
    }
}
