package com.rowan.birthdaytime.components;


import com.mojang.serialization.Codec;
import com.rowan.birthdaytime.BirthdayTime;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(BirthdayTime.MOD_ID); //this is what i used before idk what to replace my depreceatid stuff with so ima just leave it


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> AMMO =
            register("ammo", integerBuilder -> integerBuilder.persistent(Codec.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> WRAPPED =
            register("wrapped", booleanBuilder -> booleanBuilder.persistent(Codec.BOOL));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BASECOLOR =
            register("basecolor", integerBuilder -> integerBuilder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RIBBONCOLOR =
            register("ribboncolor", integerBuilder -> integerBuilder.persistent(Codec.INT));


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, ()-> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus iEventBus){
        DATA_COMPONENT_TYPES.register(iEventBus);
    }
}




