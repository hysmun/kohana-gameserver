package koh.game.fights.effects;

import koh.game.fights.FightCell;
import koh.game.fights.Fighter;
import static koh.protocol.client.enums.ActionIdEnum.ACTION_CHARACTER_TELEPORT_ON_SAME_MAP;
import koh.protocol.messages.game.actions.fight.GameActionFightTeleportOnSameMapMessage;

/**
 *
 * @author Melancholiax
 */
public class EffectTeleportSymetric extends EffectBase {

    @Override
    public int ApplyEffect(EffectCast CastInfos) {
        int toReturn = -1;
        FightCell cell;
        for (Fighter Target : CastInfos.Targets) {
            cell = CastInfos.Caster.Fight.GetCell(Target.MapPoint().pointSymetry(CastInfos.Caster.MapPoint()).get_cellId());

            if (cell != null && cell.IsWalkable()) {
                Target.Fight.sendToField(new GameActionFightTeleportOnSameMapMessage(ACTION_CHARACTER_TELEPORT_ON_SAME_MAP, CastInfos.Caster.ID, Target.ID, cell.Id));

                toReturn = Target.SetCell(cell);
                if (toReturn != -1) {
                    break;
                }
            }
        }
        return toReturn;
    }

}