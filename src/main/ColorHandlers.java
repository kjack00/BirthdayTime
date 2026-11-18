package com.rowan.drawNstuff.client;

import com.rowan.drawNstuff.item.ModItems;
import com.rowan.drawNstuff.item.custom.MarkerItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ColorHandlers {

    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event){
        event.register(MarkerItem::getItemColor, ModItems.MARKER);
    }


}
