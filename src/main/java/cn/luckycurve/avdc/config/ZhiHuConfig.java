package cn.luckycurve.avdc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "zhi-hu")
public class ZhiHuConfig {
    private String cookie;
}
