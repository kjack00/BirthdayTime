package com.rowan.birthdaytime.item.custom;

import com.rowan.birthdaytime.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PartyPopper extends Item {

    public PartyPopper(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Vec3 look = player.getLookAngle();
        Vec3 velocity = look.multiply(3,3,3);
        Vec3 pos = look.add(player.position().add(0,player.getEyeHeight(),0));
        for (int i = 0; i < 80; i++) {
            Vec3 pos1 = pos.offsetRandom(RandomSource.create(), 0.2f);
            Vec3 velocity1 = velocity.offsetRandom(RandomSource.create(), 2f);
            level.addParticle(ModParticles.CONFETTI_PARTICLES.get(), pos1.x, pos1.y, pos1.z, velocity1.x, velocity1.y, velocity1.z);
        }
        player.getItemInHand(hand).consume(1, player);
        return super.use(level, player, hand);
    }
}
