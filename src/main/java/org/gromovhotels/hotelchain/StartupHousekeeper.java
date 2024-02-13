package org.gromovhotels.hotelchain;

import org.gromovhotels.hotelchain.consoleapp.ConsoleApp;
import org.gromovhotels.hotelchain.graphicsapp.JavaFxApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.extractSingleton;
import static org.gromovhotels.hotelchain.utils.fx.UiAppExecutor.executeGraphicsApplicationDrivenBy;
import static org.gromovhotels.hotelchain.utils.shell.ShellExecutor.executeShellDrivenBy;

@Component
public class StartupHousekeeper {

    @Autowired private Environment environment;
    @Autowired private ConsoleApp consoleApp;
    @Autowired private JavaFxApp javaFxApp;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        switch (getSingleActiveProfile(environment)) {
            case CONSOLE -> executeShellDrivenBy(consoleApp);
            case UI -> executeGraphicsApplicationDrivenBy(javaFxApp);
            default -> throw new AssertionError("Должен быть указан один активный Spring Profile (либо 'ui', либо 'console')");
        }
    }

    private static Profile getSingleActiveProfile(Environment environment) {
        var singleActiveProfile = extractSingleton(List.of(environment.getActiveProfiles()));
        return Profile.getByString(singleActiveProfile);
    }

    enum Profile {
        UI, CONSOLE;

        static Profile getByString(String name) {
            return Arrays.stream(Profile.values())
                    .filter(e -> e.name().equalsIgnoreCase(name))
                    .findAny().orElseThrow();
        }
    }
}
