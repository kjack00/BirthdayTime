package com.rowan.birthdaytime.item.custom;

import com.mojang.datafixers.util.Either;
import com.rowan.birthdaytime.components.ModDataComponents;
import com.rowan.birthdaytime.item.ModItems;
import com.rowan.birthdaytime.particle.ModParticles;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge.EXPLOSION_DAMAGE_CALCULATOR;

public class ConfettiCannon extends CrossbowItem {
    public ConfettiCannon(Properties properties) {
        super(properties);
    }
    public static final Predicate<ItemStack> POPPER_ONLY = (stack) -> stack.is(ModItems.PARTY_POPPER);
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ChargedProjectiles chargedprojectiles = (ChargedProjectiles)itemstack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
            int ammo =  itemstack.getOrDefault(ModDataComponents.AMMO, 0);
            System.out.println(ammo);
            if (ammo<=1) {
                itemstack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
            }
            if(!level.isClientSide) {
                itemstack.set(ModDataComponents.AMMO, --ammo);
            }
            Vec3 look = player.getLookAngle();
            Vec3 velo = look.multiply(3, 3, 3);
            Vec3 pos = look.add(player.position().add(0, player.getEyeHeight(), 0));
            for (int i = 0; i < 500; i++) {
                Vec3 pos1 = pos.offsetRandom(RandomSource.create(), 0.2f);
                Vec3 velocity1 = velo.offsetRandom(RandomSource.create(), 2f);
                level.addParticle(ModParticles.CONFETTI_PARTICLES.get(), pos1.x, pos1.y, pos1.z, velocity1.x, velocity1.y, velocity1.z);

            }

            level.explode(null, null, EXPLOSION_DAMAGE_CALCULATOR, pos.x(), pos.y(), pos.z(), 1.2F, false, Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST, ParticleTypes.SMALL_GUST, SoundEvents.WIND_CHARGE_BURST);

            return InteractionResultHolder.consume(itemstack);
        } else if (!player.getProjectile(itemstack).isEmpty()) {

            itemstack.set(ModDataComponents.AMMO, 1);
            EnchantmentHelper.runIterationOnItem(itemstack, ((enchantmentHolder, enchlevel) -> {
                if(enchantmentHolder.is(Enchantments.MULTISHOT)){
                    itemstack.set(ModDataComponents.AMMO, 1+2*enchlevel);
                }
            }));


            System.out.println(itemstack.get(ModDataComponents.AMMO));
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
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
