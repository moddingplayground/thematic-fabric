package net.moddingplayground.thematic.api.theme;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.moddingplayground.thematic.Thematic;

import java.util.function.Consumer;

import static net.minecraft.block.Block.*;

public enum Theme {
    RUSTIC(new Data(
        false,
        VoxelShapes.union(
            createCuboidShape(5, 0, 5, 11, 6, 11),
            createCuboidShape(4, 6, 4, 12, 8, 12),
            createCuboidShape(6, 8, 6, 10, 10, 10)
        ),
        VoxelShapes.union(
            createCuboidShape(5, 1, 5, 11, 7, 11),
            createCuboidShape(4, 7, 4, 12, 9, 12),
            createCuboidShape(6, 9, 6, 10, 11, 10)
        )
    )),
    SUNKEN(new Data(
        true,
        VoxelShapes.union(
            createCuboidShape(6, 9, 6, 10, 11, 10),
            createCuboidShape(5, 0, 5, 11, 9, 11)
        ),
        VoxelShapes.union(
            createCuboidShape(6, 9, 6, 10, 11, 10),
            createCuboidShape(5, 0, 5, 11, 9, 11)
        )
    )),
    MECHANICAL(new Data(
        true,
        createCuboidShape(4.5, 0, 4.5, 11.5, 7, 11.5),
        createCuboidShape(4.5, 3, 4.5, 11.5, 10, 11.5)
    ));

    private static final Theme[] THEMES = Theme.values();

    private final String id;
    private final Data data;

    Theme(Data data) {
        this.id = this.name().toLowerCase();
        this.data = data;
    }

    public String getId() {
        return this.id;
    }

    public Data getData() {
        return this.data;
    }

    public Block get(Decoratable decoratable) {
        return Registry.BLOCK.get(Thematic.defaultedId(decoratable.format(this)));
    }

    public static void forEach(Consumer<Theme> action) {
        for (Theme theme : THEMES) action.accept(theme);
    }

    public record Data(boolean metallic, VoxelShape lanternShape, VoxelShape lanternShapeHanging) {
        public static final Data DEFAULT = new Data(false, null, null);
    }
}
