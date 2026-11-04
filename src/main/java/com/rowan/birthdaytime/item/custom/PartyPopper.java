package com.rowan.birthdaytime.item.custom;

import com.rowan.birthdaytime.particle.ModParticles;
import com.rowan.birthdaytime.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class PartyPopper extends Item {

    public PartyPopper(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        int randInt = RandomSource.create().nextIntBetweenInclusive(1, 1000/4);
        if(randInt <=333/4) {
            level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.POP_ONE.get(), SoundSource.PLAYERS);
        }else if(randInt <=666/4) {
            level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.POP_TWO.get(), SoundSource.BLOCKS);
        }else if(randInt <=999/4) {
            level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.POP_THREE.get(), SoundSource.BLOCKS);
        }else if(randInt <= 1000/4) {
            level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.YAY.get(), SoundSource.BLOCKS);
        }
        level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.YAY.get(), SoundSource.BLOCKS);

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
