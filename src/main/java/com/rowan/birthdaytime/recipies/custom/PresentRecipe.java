package com.rowan.birthdaytime.recipies.custom;

import com.google.common.collect.Lists;
import com.rowan.birthdaytime.components.ModDataComponents;
import com.rowan.birthdaytime.item.ModItems;
import com.rowan.birthdaytime.recipies.ModRecipies;
import com.rowan.birthdaytime.recipies.serializers.PresentRecipeSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class PresentRecipe extends CustomRecipe {
    public PresentRecipe(CraftingBookCategory category) {
        super(category);
    }

    public boolean matches(CraftingInput input, Level level) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemstack1 = input.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.is(ModItems.PRESENT)) {
                    if (!itemstack.isEmpty()) {
                        return false;
                    }

                    itemstack = itemstack1;
                } else {
                    if (!(itemstack1.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    list.add(itemstack1);

                }
            }
        }

        return !itemstack.isEmpty() && !list.isEmpty();
    }

    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        net.minecraft.world.item.DyeItem baseDye = null;
        net.minecraft.world.item.DyeItem  ribbonDye = null;
        ItemStack itemstack = ItemStack.EMPTY;

        System.out.println(input.width());
        System.out.println(input.height());

        for(int i = 0; i < input.size(); ++i) {
            if(input.size() == 3) {
                ItemStack itemstack1 = input.getItem(i);
                if (!itemstack1.isEmpty()) {
                    if (itemstack1.is(ModItems.PRESENT)) {
                        if (!itemstack.isEmpty()) {
                            return ItemStack.EMPTY;
                        }

                        itemstack = itemstack1.copy();
                    } else {
                        Item dye = itemstack1.getItem();
                        if (!(dye instanceof DyeItem)) {
                            return ItemStack.EMPTY;
                        }

                        if (input.getItem(0).is(dye)) {
                            ribbonDye = (DyeItem) dye;
                        } else if (input.getItem(2).is(dye)) {
                            baseDye = (DyeItem) dye;
                        } else {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }else if (input.size() == 2){
                ItemStack itemstack1 = input.getItem(i);
                if (!itemstack1.isEmpty()) {
                    if (itemstack1.is(ModItems.PRESENT)) {
                        if (!itemstack.isEmpty()) {
                            return ItemStack.EMPTY;
                        }

                        itemstack = itemstack1.copy();
                    } else {
                        Item dye = itemstack1.getItem();
                        if (!(dye instanceof DyeItem)) {
                            return ItemStack.EMPTY;
                        }

                        if (input.getItem(0).is(dye)) {
                            ribbonDye = (DyeItem) dye;
                        } else if (input.getItem(1).is(dye)) {
                            baseDye = (DyeItem) dye;
                        }
                    }
                }
            }
        }

        if(baseDye == null ){
            itemstack.set(ModDataComponents.BASECOLOR, itemstack.getOrDefault(ModDataComponents.BASECOLOR, ChatFormatting.RED.getColor()));
        }else{
            itemstack.set(ModDataComponents.BASECOLOR, baseDye.getDyeColor().getTextColor());
        }
        if(ribbonDye == null){
            itemstack.set(ModDataComponents.RIBBONCOLOR, itemstack.getOrDefault(ModDataComponents.RIBBONCOLOR, ChatFormatting.YELLOW.getColor()));
        }else{
            itemstack.set(ModDataComponents.RIBBONCOLOR, ribbonDye.getDyeColor().getTextColor());
        }





        return !itemstack.isEmpty()  ? itemstack : ItemStack.EMPTY;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipies.PRESENT.get();
    }
}


