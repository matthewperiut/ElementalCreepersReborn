package me.luligabi.elementalcreepers.common.entity.tnt;

import me.luligabi.elementalcreepers.common.entity.ExplosionEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class AirTntEntity extends ElementalTntEntity  {

    public AirTntEntity(EntityType<? extends ElementalTntEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void explode() {
        ExplosionEffects.airExplosionEffect(this, getWorld(), getX(), getY(), getZ());
    }

}