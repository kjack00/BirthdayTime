package com.rowan.birthdaytime.item.custom;

import com.rowan.birthdaytime.components.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class PresentItem extends BundleItem {
    public PresentItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if(player.isCrouching()){
            itemstack.set(ModDataComponents.WRAPPED, true);
            System.out.println(itemstack.get(ModDataComponents.WRAPPED));
            return InteractionResultHolder.success(itemstack);
        }
        if (dropContents(itemstack, player)) {
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    private static boolean dropContents(ItemStack stack, Player player) {
        BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
        if (bundlecontents != null && !bundlecontents.isEmpty()) {
            stack.set(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
            if (player instanceof ServerPlayer) {
                bundlecontents.itemsCopy().forEach((p_330078_) -> player.drop(p_330078_, true));
            }

            return true;
        } else {
            return false;
        }
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BundleContents bundlecontents = (BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS);
        if (bundlecontents != null && !stack.getOrDefault(ModDataComponents.WRAPPED, false)) {
            int i = Mth.mulAndTruncate(bundlecontents.weight(), 64);
            tooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", new Object[]{i, 64}).withStyle(ChatFormatting.GRAY));
        }else {
            tooltipComponents.add(Component.literal("Wrapped").withStyle(ChatFormatting.BOLD).withColor(stack.getOrDefault(ModDataComponents.BASECOLOR, 11184810)));
            tooltipComponents.add(Component.literal("\uD83C\uDF80").withColor(stack.getOrDefault(ModDataComponents.RIBBONCOLOR, 11184810)));
        }

    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return !stack.getOrDefault(ModDataComponents.WRAPPED, false) && !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP) ? Optional.ofNullable((BundleContents)stack.get(DataComponents.BUNDLE_CONTENTS)).map(BundleTooltip::new) : Optional.empty();
    }

    public static int getBasePresentColor(ItemStack itemStack){
        if(itemStack.get(ModDataComponents.BASECOLOR) != null) {
            int color = itemStack.getOrDefault(ModDataComponents.BASECOLOR, ChatFormatting.RED.getColor());

            return FastColor.ARGB32.opaque(color);
        }
        return 0xFFFFFFFF;
    }

    public static int getRibbonPresentColor(ItemStack itemStack){
        if(itemStack.get(ModDataComponents.BASECOLOR) != null) {
            int color = itemStack.getOrDefault(ModDataComponents.RIBBONCOLOR, ChatFormatting.YELLOW.getColor());

            return FastColor.ARGB32.opaque(color);
        }
        return 0xFFFFFFFF;
    }

    public static void setPresentColor(ItemStack itemStack, int baseColor, int ribbonColor){

        itemStack.set(ModDataComponents.BASECOLOR, baseColor);
        itemStack.set(ModDataComponents.RIBBONCOLOR, ribbonColor);
    }

    public static int getItemColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 0) {
            return getBasePresentColor(itemStack);

        } else if (tintIndex == 1) {

            return getRibbonPresentColor(itemStack);
        }
        return 0xFFFFFFFF;
    }
}
