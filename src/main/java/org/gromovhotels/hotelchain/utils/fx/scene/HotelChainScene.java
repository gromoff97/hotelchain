package org.gromovhotels.hotelchain.utils.fx.scene;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gromovhotels.hotelchain.graphicsapp.controllers.HelpController;
import org.gromovhotels.hotelchain.graphicsapp.controllers.MainController;

@AllArgsConstructor
public enum HotelChainScene {
    MAIN(MainController.class),
    HELP(HelpController.class);

    @Getter private final Class<?> controllerClazz;
}
