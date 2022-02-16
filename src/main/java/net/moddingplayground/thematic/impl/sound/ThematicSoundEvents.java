package net.moddingplayground.thematic.impl.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

public class ThematicSoundEvents {
    public static final SoundEvent BLOCK_RUSTIC_CHEST_TREASURE_OPEN = rusticChest("treasure_open");
    public static final SoundEvent BLOCK_RUSTIC_CHEST_TREASURE_CLOSE = rusticChest("treasure_close");
    private static SoundEvent rusticChest(String id) {
        return block("rustic_chest", id);
    }

    public static final SoundEvent BLOCK_CHEST_METAL_OPEN = chestMetal("open");
    public static final SoundEvent BLOCK_CHEST_METAL_CLOSE = chestMetal("close");
    private static SoundEvent chestMetal(String id) {
        return block("chest_metal", id);
    }

    private static SoundEvent block(String block, String id) {
        return register("block.%s.%s".formatted(block, id));
    }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Thematic.MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }
}
