package net.moddingplayground.thematic.api.screen;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

public interface ThematicScreenHandlerType {
    ScreenHandlerType<DecoratorsTableScreenHandler> DECORATORS_TABLE = simple("decorators_table", DecoratorsTableScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> simple(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, new Identifier(Thematic.MOD_ID, id), new ScreenHandlerType<>(factory));
    }
}
