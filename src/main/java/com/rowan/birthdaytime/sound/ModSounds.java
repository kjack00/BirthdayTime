package com.rowan.birthdaytime.sound;

import com.rowan.birthdaytime.BirthdayTime;
import com.rowan.birthdaytime.item.custom.ConfettiCannon;
import com.rowan.birthdaytime.item.custom.PartyPopper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BirthdayTime.MOD_ID);

    public static final Supplier<SoundEvent> POP_ONE = registerSoundEvent("pop_one");
    public static final Supplier<SoundEvent> POP_TWO = registerSoundEvent("pop_two");
    public static final Supplier<SoundEvent> POP_THREE = registerSoundEvent("pop_three");
    public static final Supplier<SoundEvent> YAY = registerSoundEvent("yay");


    private static Supplier<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BirthdayTime.MOD_ID, name);
        return SOUND_EVENTS.register(name, ()-> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }



}