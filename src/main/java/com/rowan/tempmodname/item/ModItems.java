package com.rowan.tempmodname.item;


import com.rowan.tempmodname.TempModName;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(TempModName.MOD_ID);

public static void register(IEventBus eventBus){
    ITEMS.register(eventBus);
}



}
