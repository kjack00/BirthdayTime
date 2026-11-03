package com.rowan.birthdaytime.item;


import com.rowan.birthdaytime.BirthdayTime;
import com.rowan.birthdaytime.item.custom.ConfettiCannon;
import com.rowan.birthdaytime.item.custom.PartyPopper;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BirthdayTime.MOD_ID);

    public static final DeferredItem<Item> PARTY_POPPER = ITEMS.registerItem("party_popper",
            PartyPopper::new, new Item.Properties());

    public static final DeferredItem<CrossbowItem> CONFETTI_CANNON = ITEMS.register("confetti_cannon",
            () -> new ConfettiCannon( new Item.Properties().stacksTo(1)));

public static void register(IEventBus eventBus){
    ITEMS.register(eventBus);
}



}
