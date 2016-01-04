package koh.game.fights.effects.buff;

import koh.game.fights.Fighter;
import koh.game.fights.effects.EffectCast;
import koh.protocol.client.enums.FightDispellableEnum;
import koh.protocol.types.game.actions.fight.AbstractFightDispellableEffect;
import koh.protocol.types.game.actions.fight.FightTriggeredEffect;
import org.apache.commons.lang3.mutable.MutableInt;

/**
 *
 * @author Neo-Craft
 */
public class BuffIncreaseSpellJet extends BuffEffect {

    public BuffIncreaseSpellJet(EffectCast CastInfos, Fighter Target) {
        super(CastInfos, Target, BuffActiveType.ACTIVE_ATTACK_AFTER_JET, BuffDecrementType.TYPE_ENDTURN);

    }

    @Override
    public int applyEffect(MutableInt DamageValue, EffectCast DamageInfos) {
        if (DamageInfos.SpellId == castInfos.effect.diceNum) {
            DamageValue.add(castInfos.effect.value);
        }
        return super.applyEffect(DamageValue, DamageInfos);
    }

    @Override
    public AbstractFightDispellableEffect getAbstractFightDispellableEffect() {
        return new FightTriggeredEffect(this.GetId(), this.target.getID(), (short) this.castInfos.effect.duration, FightDispellableEnum.DISPELLABLE, this.castInfos.SpellId, this.castInfos.effect.effectUid, 0, (short) this.castInfos.effect.diceNum, (short) this.castInfos.effect.diceSide, (short) this.castInfos.effect.value, (short) this.castInfos.effect.delay);
    }

}
