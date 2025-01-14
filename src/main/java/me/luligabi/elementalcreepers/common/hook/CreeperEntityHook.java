package me.luligabi.elementalcreepers.common.hook;

import me.luligabi.elementalcreepers.common.entity.creeper.AirCreeperEntity;
import me.luligabi.elementalcreepers.common.entity.creeper.ElementalCreeperEntity;
import me.luligabi.elementalcreepers.common.entity.creeper.FakeIllusionCreeperEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


public class CreeperEntityHook {

    public static void onExplode(CreeperEntity creeperEntity, CallbackInfo info) {
        if(creeperEntity instanceof ElementalCreeperEntity) {
           if(creeperEntity instanceof AirCreeperEntity) {
               if(!creeperEntity.getWorld().isClient) {
                   creeperEntity.getWorld().createExplosion(creeperEntity, creeperEntity.getX(), creeperEntity.getY(), creeperEntity.getZ(), 0, World.ExplosionSourceType.NONE);
               }
               ((ElementalCreeperEntity) creeperEntity).onExplode();
               info.cancel();
           } else if(creeperEntity instanceof FakeIllusionCreeperEntity) {
               if(creeperEntity.getWorld().isClient) {
                   for(int i=0; i < 4; ++i) {
                       creeperEntity.getWorld().addParticle(ParticleTypes.SPIT, creeperEntity.getX(), creeperEntity.getY() + 0.75, creeperEntity.getZ(), 0.0, 0.0, 0.0);
                   }
               }
               ((ElementalCreeperEntity) creeperEntity).onExplode();
               info.cancel();
           } else {
               if(!creeperEntity.getWorld().isClient) {
                   ((ElementalCreeperEntity) creeperEntity).onExplode();
                   info.cancel();
               }
           }
        }
    }
}
