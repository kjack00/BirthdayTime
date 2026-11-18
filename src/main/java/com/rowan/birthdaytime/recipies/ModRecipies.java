package com.rowan.birthdaytime.recipies;

import com.rowan.birthdaytime.BirthdayTime;
import com.rowan.birthdaytime.recipies.custom.PresentRecipe;
import com.rowan.birthdaytime.recipies.serializers.PresentRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipies {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BirthdayTime.MOD_ID);



    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PresentRecipe>> PRESENT =
            SERIALIZERS.register("present", PresentRecipeSerializer::new);

}
