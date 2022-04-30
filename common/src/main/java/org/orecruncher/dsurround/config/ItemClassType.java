package org.orecruncher.dsurround.config;

import com.mojang.serialization.Codec;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.sound.ISoundFactory;
import org.orecruncher.dsurround.sound.SoundFactoryBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ItemClassType implements StringIdentifiable {
    NONE("none",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.utility.equip")))
                .category(SoundCategory.PLAYERS).volume(0.3F).build(),
            null),
    TOOL("tool",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.tool.equip")))
                .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.tool.swing")))
                    .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build()),
    SWORD("sword",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.sword.equip")))
                .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.sword.swing")))
                    .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build()),
    SHIELD("shield",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.shield.equip")))
                .category(SoundCategory.PLAYERS).volume(0.25F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.shield.equip")))
                    .category(SoundCategory.PLAYERS).volume(0.25F).pitchRange(0.4F, 0.6F).build()),
    AXE("axe",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.blunt.equip")))
                .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.blunt.swing")))
                    .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build()),
    BOW("bow",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.bow.equip")))
                .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.blunt.swing")))
                    .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.4F, 0.6F).build()),
    CROSSBOW("crossbow",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.bow.equip")))
                .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.blunt.swing")))
                    .category(SoundCategory.PLAYERS).volume(0.5F).pitchRange(0.4F, 0.6F).build()),
    POTION("potion",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.potion.equip")))
                .category(SoundCategory.PLAYERS).volume(0.8F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "item.potion.equip")))
                    .category(SoundCategory.PLAYERS).volume(1F).pitchRange(0.8F, 1.2F).build()),
    BOOK("book",
            SoundFactoryBuilder
                .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "pageflip")))
                .category(SoundCategory.PLAYERS).volume(0.8F).pitchRange(0.8F, 1.2F).build(),
            SoundFactoryBuilder
                    .create(new SoundEvent(new Identifier(DynamicSurroundings.MOD_ID, "pageflipheavy")))
                    .category(SoundCategory.PLAYERS).volume(0.8F).pitchRange(0.8F, 1.2F).build());

    private static final Map<String, ItemClassType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(ItemClassType::getName, (category) -> category));
    public static final Codec<ItemClassType> CODEC = StringIdentifiable.createCodec(ItemClassType::values, ItemClassType::byName);

    private final String name;
    private final ISoundFactory toolBarSound;
    private final @Nullable ISoundFactory swingSound;

    ItemClassType(String name, ISoundFactory toolBarSound, @Nullable ISoundFactory swingSound) {
        this.name = name;
        this.toolBarSound = toolBarSound;
        this.swingSound = swingSound;
    }

    public String getName() {
        return this.name;
    }

    public ISoundFactory getToolBarSound() {
        return this.toolBarSound;
    }

    public @Nullable ISoundFactory getSwingSound() {
        return this.swingSound;
    }

    public static ItemClassType byName(String name) {
        return BY_NAME.get(name);
    }

    @Override
    public String asString() {
        return this.name;
    }
}
