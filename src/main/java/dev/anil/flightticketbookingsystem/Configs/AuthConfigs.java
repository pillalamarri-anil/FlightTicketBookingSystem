package dev.anil.flightticketbookingsystem.Configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class AuthConfigs {

    @Bean
    public SecretKey secretKey() {
        return new SecretKeySpec(
                "super-long-secret-key-anil-1234-super-long-key".getBytes(),
                "HmacSHA256"
        );
    }

    @Bean
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer() {
        return new OAuth2AuthorizationServerConfigurer();
    }
}
