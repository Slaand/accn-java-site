package com.slaand.site.annotation;

import com.slaand.site.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RedisConfig.class)
public @interface EnableRedisCache {

}
