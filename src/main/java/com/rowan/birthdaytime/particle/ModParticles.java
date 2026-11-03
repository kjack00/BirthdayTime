package com.rowan.birthdaytime.particle;

import com.rowan.birthdaytime.BirthdayTime;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BirthdayTime.MOD_ID);

    public static final Supplier<SimpleParticleType> CONFETTI_PARTICLES =
            PARTICLE_TYPES.register("confetti_particles", ()-> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }

}
