package net.moddingplayground.thematic.api.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;

public interface ThematicScreenHandlerType {
    ScreenHandlerType<DecoratorsTableScreenHandler> DECORATORS_TABLE = simple("decorators_table", DecoratorsTableScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> simple(String id, ScreenHandlerRegistry.SimpleClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(Thematic.MOD_ID, id), factory);
    }
}
