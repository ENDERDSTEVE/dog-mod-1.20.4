package net.enderd.dog.entity.custom;

import net.enderd.dog.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class BorzoiEntity
        extends TameableEntity
        implements Angerable {
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(BorzoiEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final Predicate<LivingEntity> FOLLOW_TAMED_PREDICATE = entity -> {
        EntityType<?> entityType = entity.getType();
        return entityType == EntityType.SHEEP || entityType == EntityType.RABBIT || entityType == EntityType.FOX;
    };
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    public BorzoiEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
    @Nullable
    private UUID angryAt;

    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState babyAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
        this.idleAnimationTimeout = this.random.nextInt(40) + 80;
        this.idleAnimationState.start(this.age);
    } else {
        --this.idleAnimationTimeout;
    }
        if(this.isInSittingPose()){
            this.idleAnimationState.stop();
            this.sittingAnimationState.start(this.age);
        }
        else {
            this.sittingAnimationState.stop();
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(40) + 80;
                this.idleAnimationState.start(this.age);
            } else {
                --this.idleAnimationTimeout;
            }

            if (isBaby()) {
                this.babyAnimationState.start(this.age);
            } else {
                this.babyAnimationState.stop();
            }
        }

    }


    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BorzoiEntity.BorzoiEscapeDangerGoal(1.5));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new BorzoiEntity.AvoidLlamaGoal<LlamaEntity>(this, LlamaEntity.class, 24.0f, 1.5, 1.5));
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        this.goalSelector.add(7, new AnimalMateGoal(this,1.15D));
        this.goalSelector.add(8, new TemptGoal(this,1.25D, Ingredient.ofItems(Items.BONE),false));
        this.goalSelector.add(9, new FollowParentGoal(this,1.15D));
        this.goalSelector.add(10, new WanderAroundFarGoal(this,1D));
        this.goalSelector.add(12, new LookAtEntityGoal(this, PlayerEntity.class,4f));
        this.goalSelector.add(12, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]).setGroupRevenge(new Class[0]));
        this.targetSelector.add(4, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(5, new UntamedActiveTargetGoal<AnimalEntity>(this, AnimalEntity.class, false, FOLLOW_TAMED_PREDICATE));
        this.targetSelector.add(6, new UntamedActiveTargetGoal<TurtleEntity>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
        this.targetSelector.add(7, new ActiveTargetGoal<AbstractSkeletonEntity>((MobEntity)this, AbstractSkeletonEntity.class, false));
        this.targetSelector.add(8, new UniversalAngerGoal<BorzoiEntity>(this, true));
    }

    public static DefaultAttributeContainer.Builder createBorzoiAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.25f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,2);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        Item item = stack.getItem();
        return item.isFood() && item.getFoodComponent().isMeat();
    }

    @Override
    @Nullable
    public BorzoiEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        UUID uUID;
        BorzoiEntity borzoiEntity = ModEntities.BORZOI.create(serverWorld);
        if (borzoiEntity != null && (uUID = this.getOwnerUuid()) != null) {
            borzoiEntity.setOwnerUuid(uUID);
            borzoiEntity.setTamed(true);
        }
        return borzoiEntity;
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.hasAngerTime()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        }
        if (this.random.nextInt(3) == 0) {
            if (this.isTamed() && this.getHealth() < 10.0f) {
                return SoundEvents.ENTITY_WOLF_WHINE;
            }
            return SoundEvents.ENTITY_WOLF_PANT;
        }
        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15f, 1.0f);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult actionResult;
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.getWorld().isClient) {
            if (this.isTamed() && this.isOwner(player)) {
                return ActionResult.SUCCESS;
            }
            if (this.isBreedingItem(itemStack) && (this.getHealth() < this.getMaxHealth() || !this.isTamed())) {
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }
        if (this.isTamed()) {
            if (this.isOwner(player)) {
                if (item.isFood() && this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    this.eat(player, hand, itemStack);
                    this.heal(item.getFoodComponent().getHunger());
                    return ActionResult.CONSUME;
                }
                ActionResult actionResult2 = super.interactMob(player, hand);
                if (!actionResult2.isAccepted() || this.isBaby()) {
                    this.setSitting(!this.isSitting());
                }
                return actionResult2;
            }
        } else if (itemStack.isOf(Items.BONE)) {
            this.eat(player, hand, itemStack);
            if (this.random.nextInt(3) == 0) {
                this.setOwner(player);
                this.setSitting(true);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            } else {
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
            }
            this.setPersistent();
            return ActionResult.CONSUME;
        }
        if ((actionResult = super.interactMob(player, hand)).isAccepted()) {
            this.setPersistent();
        }
        return actionResult;
    }
    class BorzoiEscapeDangerGoal
            extends EscapeDangerGoal {
        public BorzoiEscapeDangerGoal(double speed) {
            super(BorzoiEntity.this, speed);
        }

        @Override
        protected boolean isInDanger() {
            return this.mob.shouldEscapePowderSnow() || this.mob.isOnFire();
        }
    }

    class AvoidLlamaGoal<T extends LivingEntity>
            extends FleeEntityGoal<T> {
        private final BorzoiEntity borzoi;

        public AvoidLlamaGoal(BorzoiEntity borzoi, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(borzoi, fleeFromType, distance, slowSpeed, fastSpeed);
            this.borzoi = borzoi;
        }

        @Override
        public boolean canStart() {
            if (super.canStart() && this.targetEntity instanceof LlamaEntity) {
                return !this.borzoi.isTamed() && this.isScaredOf((LlamaEntity) this.targetEntity);
            }
            return false;
        }

        private boolean isScaredOf(LlamaEntity llama) {
            return llama.getStrength() >= BorzoiEntity.this.random.nextInt(5);
        }

    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        Entity entity = source.getAttacker();
        if (!this.getWorld().isClient) {
            this.setSitting(false);
        }
        if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
            amount = (amount + 1.0f) / 2.0f;
        }
        return super.damage(source, amount);
    }
    @Override
    public int getLimitPerChunk() {
        return 8;
    }


    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int angerTime) {

    }

    @Override
    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {

    }
    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof CreeperEntity || target instanceof GhastEntity) {
            return false;
        }
        if (target instanceof net.enderd.dog.entity.custom.BorzoiEntity) {
            net.enderd.dog.entity.custom.BorzoiEntity BorzoiEntity = (net.enderd.dog.entity.custom.BorzoiEntity)target;
            return !BorzoiEntity.isTamed() || BorzoiEntity.getOwner() != owner;
        }
        if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
            return false;
        }
        if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
            return false;
        }
        return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
    }
}
