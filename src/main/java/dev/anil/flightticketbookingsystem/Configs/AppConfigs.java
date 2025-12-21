package dev.anil.flightticketbookingsystem.Configs;

import dev.anil.flightticketbookingsystem.InventoryProvider.AmadeusAuth;
import dev.anil.flightticketbookingsystem.InventoryProvider.AmadeusInventoryAdapter;
import dev.anil.flightticketbookingsystem.InventoryProvider.InventoryAdapter;
import dev.anil.flightticketbookingsystem.PriceCalculationStrategy.DynamicPricingStrategy;
import dev.anil.flightticketbookingsystem.PriceCalculationStrategy.PriceCalculationStrategy;
import dev.anil.flightticketbookingsystem.Services.PriceCalculationService;
import jdk.jfr.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfigs {

    @Bean
    AmadeusAuth createAmadeusAuth() {
        return new AmadeusAuth();
    }

    @Bean
    InventoryAdapter getAmadeusInventoryAdapter(AmadeusAuth amadeusAuth) {
        return new AmadeusInventoryAdapter(amadeusAuth);
    }

    @Bean
    PriceCalculationStrategy getPriceCalculationStrategy() {
        return new DynamicPricingStrategy();
    }

    @Bean
    BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
