package org.gromovhotels.hotelchain;

import net.datafaker.Faker;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Bean
    Faker faker() {
        return new Faker();
    }

    @Bean
    FxWeaver fxWeaver() {
        return new FxWeaver(applicationContext::getBean, applicationContext::close);
    }
}
