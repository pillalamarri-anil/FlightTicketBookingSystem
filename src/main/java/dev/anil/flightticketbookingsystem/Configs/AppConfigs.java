package dev.anil.flightticketbookingsystem.Configs;

import dev.anil.flightticketbookingsystem.InventoryProvider.AmadeusAuth;
import dev.anil.flightticketbookingsystem.InventoryProvider.AmadeusInventoryAdapter;
import dev.anil.flightticketbookingsystem.InventoryProvider.InventoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfigs {

    @Bean
    InventoryAdapter getAmadeusInventoryAdapter() {
        return new AmadeusInventoryAdapter(new AmadeusAuth());
    }

    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
