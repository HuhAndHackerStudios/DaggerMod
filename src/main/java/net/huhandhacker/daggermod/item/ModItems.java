package net.huhandhacker.daggermod.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.huhandhacker.daggermod.DaggerMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import java.util.function.Function;

public class ModItems {

    public static final Item SHARKTOOTH = registerItem("sharktooth",
            properties -> new Item(properties.fireResistant().durability(10).rarity(Rarity.RARE).stacksTo(1)));
    public static final Item MALLEABLEIRONINGOT = registerItem("malleable_iron_ingot", Item::new);
    public static final Item IRON_HANDLE = registerItem("iron_handle", Item::new);
    public static final Item IRONGRAPPLEHEAD = registerItem("iron_grapple_head", Item::new);
    public static final Item ROPE = registerItem("rope", Item::new);
    public static final Item GRAPPLEHOOK = registerItem("grapple_hook", Item::new);
    public static final Item BTSDUT = registerItem("btsdut", Item::new);

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(DaggerMod.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DaggerMod.MOD_ID, name)))));
    }

    public static void registerItems(){
        DaggerMod.LOGGER.info("Registering Mod Items" + DaggerMod.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(item -> {
            //item.accept(SHARKTOOTH);
        });
    }


}
