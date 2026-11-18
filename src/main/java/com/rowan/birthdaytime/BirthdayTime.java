package com.rowan.birthdaytime;

import com.rowan.birthdaytime.block.ModBlocks;
import com.rowan.birthdaytime.client.ColorHandlers;
import com.rowan.birthdaytime.components.ModDataComponents;
import com.rowan.birthdaytime.item.ModItems;
import com.rowan.birthdaytime.particle.ConfettiParticles;
import com.rowan.birthdaytime.particle.ModParticles;
import com.rowan.birthdaytime.recipies.ModRecipies;
import com.rowan.birthdaytime.sound.ModSounds;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.rowan.birthdaytime.item.custom.PresentItem.setPresentColor;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BirthdayTime.MOD_ID)
public class BirthdayTime {

    public static final String MOD_ID = "birthdaytime";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BIRTHDAY_TIME = CREATIVE_MODE_TABS.register("birthdaytime", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.birthdaytime"))
            .displayItems((parameters, output) -> {

            }).build());


    public BirthdayTime(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModParticles.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModSounds.register(modEventBus);
        modEventBus.addListener(ColorHandlers::registerItemColor);
        ModRecipies.SERIALIZERS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);


        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            for (DyeColor color : DyeColor.values()){
                for (DyeColor color1 : DyeColor.values()) {
                    ItemStack itemStack = new ItemStack((ItemLike) ModItems.PRESENT);
                    setPresentColor(itemStack, color.getTextColor(), color1.getTextColor());
                    event.accept(itemStack);
                }
            }
        }
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = BirthdayTime.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
        }
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(ModParticles.CONFETTI_PARTICLES.get(), ConfettiParticles.Provider::new);
        }
    }
}
