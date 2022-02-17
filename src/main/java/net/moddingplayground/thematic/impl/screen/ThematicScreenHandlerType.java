package net.moddingplayground.thematic.impl.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.impl.client.screen.DecoratorsTableScreen;

public class ThematicScreenHandlerType {
    public static final ScreenHandlerType<DecoratorsTableScreenHandler> DECORATORS_TABLE = simple("decorators_table", DecoratorsTableScreenHandler::new);

    @Environment(EnvType.CLIENT)
    public static void onInitializeClient() {
        ScreenRegistry.register(DECORATORS_TABLE, DecoratorsTableScreen::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> simple(String id, ScreenHandlerRegistry.SimpleClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(Thematic.MOD_ID, id), factory);
    }
}
