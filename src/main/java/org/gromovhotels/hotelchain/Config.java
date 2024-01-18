package org.gromovhotels.hotelchain;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    Faker faker() {
        return new Faker();
    }
}
