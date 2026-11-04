package com.rowan.birthdaytime.item.custom;

import com.rowan.birthdaytime.components.ModDataComponents;
import com.rowan.birthdaytime.item.ModItems;
import com.rowan.birthdaytime.particle.ModParticles;
import com.rowan.birthdaytime.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge.EXPLOSION_DAMAGE_CALCULATOR;

public class ConfettiCannon extends CrossbowItem {
    public ConfettiCannon(Properties properties) {
        super(properties);
    }
    public static final Predicate<ItemStack> POPPER_ONLY = (stack) -> stack.is(ModItems.PARTY_POPPER);
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    private boolean shot = false;



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ChargedProjectiles chargedprojectiles = (ChargedProjectiles)itemstack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
            int ammo =  itemstack.getOrDefault(ModDataComponents.AMMO, 0);
            shot = true;
            if(ammo <= 0){
                itemstack.set(ModDataComponents.AMMO, 1);
                EnchantmentHelper.runIterationOnItem(itemstack, ((enchantmentHolder, enchlevel) -> {
                    if(enchantmentHolder.is(Enchantments.MULTISHOT)){
                        itemstack.set(ModDataComponents.AMMO, 1+2*enchlevel);
                    }
                }));
                ammo =  itemstack.getOrDefault(ModDataComponents.AMMO, 0);
            }

            if (ammo<=1) {
                itemstack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
                shot = false;
            }
            if(!level.isClientSide) {
                itemstack.set(ModDataComponents.AMMO, --ammo);
                //System.out.println(ammo);
            }
            //System.out.println(isBarVisible(itemstack));

            Vec3 look = player.getLookAngle();
            Vec3 velo = look.multiply(3, 3, 3);
            Vec3 pos = look.add(player.position().add(0, player.getEyeHeight(), 0));
            for (int i = 0; i < 500; i++) {
                Vec3 pos1 = pos.offsetRandom(RandomSource.create(), 0.2f);
                Vec3 velocity1 = velo.offsetRandom(RandomSource.create(), 2f);
                level.addParticle(ModParticles.CONFETTI_PARTICLES.get(), pos1.x, pos1.y, pos1.z, velocity1.x, velocity1.y, velocity1.z);

            }

            int randInt = RandomSource.create().nextIntBetweenInclusive(1, 1000);
            if(randInt <=333) {
                level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.POP_ONE.get(), SoundSource.PLAYERS);
            }else if(randInt <=666) {
                level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.POP_TWO.get(), SoundSource.PLAYERS);
            }else if(randInt <=999) {
                level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.POP_THREE.get(), SoundSource.PLAYERS);
            }else if(randInt == 1000) {
                level.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z) , ModSounds.YAY.get(), SoundSource.PLAYERS);
            }

            level.explode(null, null, EXPLOSION_DAMAGE_CALCULATOR, pos.x(), pos.y(), pos.z(), 1.2F, false, Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST, ParticleTypes.SMALL_GUST, SoundEvents.WIND_CHARGE_BURST);

            return InteractionResultHolder.consume(itemstack);

        } else if (!player.getProjectile(itemstack).isEmpty()) {
            player.startUsingItem(hand);
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        int ammo = stack.getOrDefault(ModDataComponents.AMMO, 0);
        ChargedProjectiles chargedprojectiles = (ChargedProjectiles)stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
        return ammo > 0 || !chargedprojectiles.isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        AtomicInteger ammo = new AtomicInteger(stack.getOrDefault(ModDataComponents.AMMO, 3));
        AtomicInteger maxAmmo = new AtomicInteger(1);

        EnchantmentHelper.runIterationOnItem(stack, ((enchantmentHolder, enchlevel) -> {
            if(enchantmentHolder.is(Enchantments.MULTISHOT)){
                maxAmmo.set(1 + 2 * enchlevel);
            }
        }));

        ChargedProjectiles chargedprojectiles = (ChargedProjectiles)stack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);

        if (!chargedprojectiles.isEmpty() && ammo.intValue() <= 0) {
            ammo.set(1);
            EnchantmentHelper.runIterationOnItem(stack, ((enchantmentHolder, enchlevel) -> {
                if (enchantmentHolder.is(Enchantments.MULTISHOT)) {
                    ammo.set(1 + 2 * enchlevel);
                }
            }));
        }
        return Math.min(1 + Mth.mulAndTruncate( Fraction.getFraction(ammo.get(), maxAmmo.get()), 12), 13);
    }

    public int getBarColor(ItemStack stack) {
        return Mth.color(0.4F, 0.4F, 1.0F);
    }


    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        System.out.println(ammo);
        return null;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles(ItemStack stack) {
        return POPPER_ONLY;
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return ModItems.PARTY_POPPER.toStack();
    }
    static {
        ARROW_ONLY.or((p_43015_) -> p_43015_.is(Items.FIREWORK_ROCKET));
    }
}
