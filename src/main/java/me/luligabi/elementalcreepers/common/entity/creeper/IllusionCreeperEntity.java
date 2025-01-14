package me.luligabi.elementalcreepers.common.entity.creeper;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class IllusionCreeperEntity extends ElementalCreeperEntity {

    boolean hasDuplicated = false;

    public IllusionCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperIgniteGoal(this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new IllusionCreeperFollowTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    public void onExplode() {
        getWorld().createExplosion(this, getX(), getY(), getZ(),
                3,  getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ?
                World.ExplosionSourceType.TNT : World.ExplosionSourceType.NONE);
        super.onExplode();
        hasDuplicated = false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compoundTag) {
        super.writeCustomDataToNbt(compoundTag);
        compoundTag.putBoolean("hasDuplicated", hasDuplicated);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compoundTag) {
        super.readCustomDataFromNbt(compoundTag);
        this.hasDuplicated = compoundTag.getBoolean("hasDuplicated");
    }

    public boolean hasDuplicated() { return hasDuplicated; }
    public void setHasDuplicated(boolean hasDuplicated) { this.hasDuplicated = hasDuplicated; }

    static class IllusionCreeperFollowTargetGoal extends ActiveTargetGoal<PlayerEntity> {

        public IllusionCreeperFollowTargetGoal(MobEntity mobEntity, Class<PlayerEntity> targetClass, boolean bl) {
            super(mobEntity, targetClass, bl);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.targetEntity);
            if(!((IllusionCreeperEntity) this.mob).hasDuplicated()) {
                for (int i = 0; i < 4; ++i) {
                    FakeIllusionCreeperEntity fakeIllusionCreeperEntity = new FakeIllusionCreeperEntity(CreeperRegistry.FAKE_ILLUSION_CREEPER, this.mob.getWorld());
                    fakeIllusionCreeperEntity.refreshPositionAfterTeleport(this.mob.getX(), this.mob.getY(), this.mob.getZ());
                    this.mob.getWorld().spawnEntity(fakeIllusionCreeperEntity);
                    fakeIllusionCreeperEntity.setVelocity(0, 0.5D, 0);
                }
                double d = this.mob.getWorld().random.nextDouble() * 6.2831854820251465;
                this.mob.setVelocity(-Math.sin(d) * 0.02, 0.20000000298023224, -Math.cos(d) * 0.02);
                ((IllusionCreeperEntity) this.mob).setHasDuplicated(true);
            }
            super.start();
        }
    }


}