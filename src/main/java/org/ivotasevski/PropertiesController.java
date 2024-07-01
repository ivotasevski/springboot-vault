package org.ivotasevski;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.vault.core.env.LeaseAwareVaultPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@Data
public class PropertiesController {

    @Value("${spring.cloud.vault.kv.backend}")
    private String secretBackend;

    @Value("${spring.cloud.vault.kv.application-name}")
    private String secretContext;

    @Value("${myCustomProperty}")
    private String myProp;

    private final Environment env;

    @GetMapping("/props")
    public Map<String, Object> get() {

        log.info("myProp={}", myProp);

        // extract only the properties that were retrieved from Vault
        var propSources = ((StandardServletEnvironment)env).getPropertySources();
        LeaseAwareVaultPropertySource src = (LeaseAwareVaultPropertySource) propSources.get(secretBackend + "/" + secretContext);
        return Arrays.stream(src.getPropertyNames()).collect(Collectors.toMap(
                s -> s,
                s -> env.getProperty(s)
                ));
    }
}
