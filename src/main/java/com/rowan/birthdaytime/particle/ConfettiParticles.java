package com.rowan.birthdaytime.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ConfettiParticles extends TextureSheetParticle {
    protected ConfettiParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.setSpriteFromAge(spriteSet);
        this.lifetime = 300;
        this.gravity = 0.2f;
        this.setPower(2.5f);
        this.setColor(this.random.nextFloat(),this.random.nextFloat(),this.random.nextFloat());
        this.scale(this.random.nextFloat()+1);



    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
     public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

         public Provider(SpriteSet spriteSet) {
             this.spriteSet = spriteSet;
         }

         @Override
         public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                                                  double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new ConfettiParticles(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed );

         }
     }
}
