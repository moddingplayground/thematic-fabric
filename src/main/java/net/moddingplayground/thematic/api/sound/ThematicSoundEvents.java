package net.moddingplayground.thematic.api.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

public interface ThematicSoundEvents {
    SoundEvent BLOCK_RUSTIC_CHEST_TREASURE_OPEN = rusticChest("treasure_open");
    SoundEvent BLOCK_RUSTIC_CHEST_TREASURE_CLOSE = rusticChest("treasure_close");
    private static SoundEvent rusticChest(String id) {
        return block("rustic_chest", id);
    }

    SoundEvent BLOCK_CHEST_METAL_OPEN = chestMetal("open");
    SoundEvent BLOCK_CHEST_METAL_CLOSE = chestMetal("close");
    private static SoundEvent chestMetal(String id) {
        return block("chest_metal", id);
    }

    SoundEvent UI_DECORATORS_TABLE_TAKE_RESULT = uiDecoratorsTable("take_result");
    private static SoundEvent uiDecoratorsTable(String id) {
        return ui("decorators_table.%s".formatted(id));
    }

    private static SoundEvent block(String block, String id) {
        return register("block.%s.%s".formatted(block, id));
    }

    private static SoundEvent ui(String id) {
        return register("ui.%s".formatted(id));
    }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Thematic.MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }
}
