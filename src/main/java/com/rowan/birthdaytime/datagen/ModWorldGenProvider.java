package com.rowan.birthdaytime.datagen;


import com.rowan.birthdaytime.BirthdayTime;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            //.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            //.add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)
    ;

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BirthdayTime.MOD_ID));
    }
}
