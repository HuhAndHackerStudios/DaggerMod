package net.huhandhacker.daggermod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.huhandhacker.daggermod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {

                shaped(RecipeCategory.MISC, ModItems.ROPE)
                        .pattern("SSS")
                        .pattern("SFS")
                        .pattern("SSS")
                        .define('S', Items.STRING)
                        .define('F', Items.FEATHER)
                        .unlockedBy(getHasName(Items.STRING),has(ModItems.ROPE))
                        .group("dagger mod")
                        .save(output);

                shaped(RecipeCategory.TOOLS, ModItems.GRAPPLE_HOOK)
                        .pattern("  H")
                        .pattern(" R ")
                        .pattern("A  ")
                        .define('R', ModItems.ROPE)
                        .define('H', ModItems.IRON_GRAPPLE_HEAD)
                        .define('A', ModItems.IRON_HANDLE)
                        .unlockedBy(getHasName(ModItems.ROPE), has(ModItems.GRAPPLE_HOOK))
                        .group("dagger mod")
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.RDUT)
                        .pattern("DTD")
                        .pattern("DPD")
                        .pattern("DDD")
                        .define('D', Items.DIAMOND)
                        .define('P', Items.DARK_PRISMARINE)
                        .define('T', Items.TRIDENT)
                        .unlockedBy(getHasName(Items.TRIDENT), has(ModItems.RDUT))
                        .group("dagger mod")
                        .save(output);


            }
        };
    }

    @Override
    public String getName() {
        return "Dagger Mod Recipes";
    }
}
