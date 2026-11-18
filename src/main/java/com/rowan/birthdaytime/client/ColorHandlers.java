package com.rowan.birthdaytime.client;

import com.rowan.birthdaytime.item.ModItems;
import com.rowan.birthdaytime.item.custom.PresentItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ColorHandlers {

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event){
        event.register(PresentItem::getItemColor, ModItems.PRESENT);
    }


}
