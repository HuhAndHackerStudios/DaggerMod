package net.huhandhacker.daggermod.datagen;

import net.huhandhacker.daggermod.DaggerMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Optional;

public class ModPaintings {

    public static final ResourceKey<PaintingVariant> THE_FINALS_KEY = create("the_finals");
    public static final ResourceKey<PaintingVariant> DAGGER_KEY = create("dagger");

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        register(context, THE_FINALS_KEY, 2,2, true);
        register(context, DAGGER_KEY, 2,2, true);
    }

    private static ResourceKey<PaintingVariant> create(final String id) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath(DaggerMod.MOD_ID, id));
    }

    private static void register(final BootstrapContext<PaintingVariant> context, final ResourceKey<PaintingVariant> key, final int width,
                                 final int height, final boolean hasAuthor) {
        context.register(key, new PaintingVariant(width, height, key.identifier(),
                Optional.of(Component.translatable(key.identifier().toLanguageKey("painting", "title")).withStyle(ChatFormatting.YELLOW)),
                hasAuthor ? Optional.of(Component.translatable(key.identifier().toLanguageKey("painting", "author")).withStyle(ChatFormatting.GRAY)) : Optional.empty()));
    }
}
