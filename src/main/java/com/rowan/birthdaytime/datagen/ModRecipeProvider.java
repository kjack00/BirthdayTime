package com.rowan.birthdaytime.datagen;


import com.rowan.birthdaytime.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
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


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PARTY_POPPER.toStack(16))
                .pattern(" P ")
                .pattern("RGB")
                .pattern(" S ")
                .define('R', Items.RED_DYE)
                .define('G', Items.GREEN_DYE)
                .define('B', Items.BLUE_DYE)
                .define('P', Items.PAPER)
                .define('S', Items.STRING)


                .unlockedBy("has_string", has(Items.PAPER)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CONFETTI_CANNON.toStack(1))
                .pattern("RBY")
                .pattern("PBP")
                .pattern("PBP")
                .define('R', Items.RED_CONCRETE)
                .define('B', Items.BREEZE_ROD)
                .define('Y', Items.YELLOW_CONCRETE)
                .define('P', ModItems.PARTY_POPPER)



                .unlockedBy("has_string", has(Items.PAPER)).save(recipeOutput);

    }
}
