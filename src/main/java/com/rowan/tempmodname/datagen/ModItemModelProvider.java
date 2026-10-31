package com.rowan.tempmodname.datagen;


import com.rowan.tempmodname.TempModName;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TempModName.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //basicItem(ModItems.ITEM.get());
    }
}
