package koh.game.fights.effects;

import koh.game.fights.Fighter;
import koh.game.fights.effects.buff.BuffStats;
import koh.protocol.client.enums.StatsEnum;

/**
 *
 * @author Neo-Craft
 */
public class EffectVitalityPercent extends EffectBase {

    @Override
    public int applyEffect(EffectCast CastInfos) {
        for (Fighter Target : CastInfos.targets) {
            EffectCast SubInfos = new EffectCast(StatsEnum.VITALITY, CastInfos.SpellId, CastInfos.CellId, CastInfos.Chance, null, CastInfos.caster, CastInfos.targets, CastInfos.SpellLevel);
            SubInfos.DamageValue = (int) (((double) Target.getLife() / 100) * CastInfos.randomJet(Target));
            SubInfos.Duration = CastInfos.Duration;
            BuffStats BuffStats = new BuffStats(SubInfos, Target);
            if (BuffStats.applyEffect(null, null) == -3) {
                return -3;
            }

            Target.getBuff().addBuff(BuffStats);
        }

        return -1;
    }

}
