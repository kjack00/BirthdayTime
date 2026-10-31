package com.rowan.tempmodname.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        //ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRING_BLOCK.get())
        //        .pattern("BBB")
        //        .pattern("BBB")
        //        .pattern("BBB")
        //        .define('B', Items.STRING)
        //        .unlockedBy("has_string", has(Items.STRING)).save(recipeOutput);

        //ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 9)
        //        .requires(ModBlocks.STRING_BLOCK.get())
        //        .unlockedBy("has_string_block", has(ModBlocks.STRING_BLOCK.get())).save(recipeOutput, "modid:string_from_string_block");

    }
}
