package me.luligabi.elementalcreepers.common.entity.tnt;

import me.luligabi.elementalcreepers.common.entity.ExplosionEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class ElectricTntEntity extends ElementalTntEntity {

    public ElectricTntEntity(EntityType<? extends ElementalTntEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void explode() {
        ExplosionEffects.electricExplosionEffect(getWorld(), getX(), getY(), getZ());
    }

}