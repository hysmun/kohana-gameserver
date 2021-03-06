package koh.game.fights.effects.buff;

import koh.game.fights.Fighter;
import koh.game.fights.effects.EffectCast;
import static koh.protocol.client.enums.ActionIdEnum.ACTION_CARRY_CHARACTER;
import koh.protocol.client.enums.FightDispellableEnum;
import koh.protocol.client.enums.FightStateEnum;
import koh.protocol.client.enums.StatsEnum;
import koh.protocol.messages.game.actions.fight.GameActionFightCarryCharacterMessage;
import koh.protocol.types.game.actions.fight.AbstractFightDispellableEffect;
import koh.protocol.types.game.actions.fight.FightTemporaryBoostStateEffect;
import org.apache.commons.lang3.mutable.MutableInt;

/**
 *
 * @author Neo-Craft
 */
public class BuffPorteur extends BuffEffect {

    public BuffPorteur(EffectCast CastInfos, Fighter Target) {
        super(CastInfos, Target, BuffActiveType.ACTIVE_ENDMOVE, BuffDecrementType.TYPE_ENDMOVE);
        this.duration = -1;
        CastInfos.caster.getStates().fakeState(FightStateEnum.CARRIER, true);
        this.castInfos.effectType = StatsEnum.ADD_STATE;
        this.caster.getFight().sendToField(new GameActionFightCarryCharacterMessage(ACTION_CARRY_CHARACTER, caster.getID(),Target.getID(), caster.getCellId()));
    }

    @Override
    public AbstractFightDispellableEffect getAbstractFightDispellableEffect() {
        return new FightTemporaryBoostStateEffect(this.getId(), this.caster.getID(), (short) this.duration, FightDispellableEnum.REALLY_NOT_DISPELLABLE, (short) this.castInfos.spellId, (short)/*this.castInfos.getEffectUID()*/ 2, this.castInfos.parentUID, (short) 1, (short) 3);
    }

    @Override
    public int applyEffect(MutableInt DamageValue, EffectCast DamageInfos) {
        // Si effet finis
        if (!this.target.getStates().hasState(FightStateEnum.CARRIED)) {
            this.duration = 0;
            return -1;
        }

        // On affecte la meme cell pour la cible porté
        return this.target.setCell(this.caster.getMyCell());
    }

    @Override
    public int removeEffect() {
        castInfos.caster.getStates().fakeState(FightStateEnum.CARRIER, false);
        this.duration = 0;
        return super.removeEffect();
    }

}
