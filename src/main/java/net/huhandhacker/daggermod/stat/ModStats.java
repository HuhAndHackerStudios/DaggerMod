package net.huhandhacker.daggermod.stat;

import net.huhandhacker.daggermod.DaggerMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class ModStats {

    public static final Stat<?> INTERACT_WITH_FORGE = makeCustomStat("forge_interactions");
    public static final Stat<?> BACKSTABS = makeCustomStat("backstabs");

    private static Stat<?> makeCustomStat(String key) {
        Identifier identifier = Identifier.fromNamespaceAndPath(DaggerMod.MOD_ID, key);
        Identifier newStat = Registry.register(BuiltInRegistries.CUSTOM_STAT, key, identifier);

        return Stats.CUSTOM.get(newStat, StatFormatter.DEFAULT);
    }

    public static void registerStats() {
        DaggerMod.LOGGER.info("Registering Stats for: " + DaggerMod.MOD_ID );
    }
}
