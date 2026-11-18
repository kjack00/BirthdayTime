package com.rowan.birthdaytime.recipies.serializers;

import com.rowan.birthdaytime.recipies.custom.PresentRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class PresentRecipeSerializer extends SimpleCraftingRecipeSerializer<PresentRecipe> {

    public PresentRecipeSerializer() {
        super(PresentRecipe::new);
    }
}
