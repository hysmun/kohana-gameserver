package koh.game.fights.effects;

import koh.game.fights.FightCell;
import koh.game.fights.Fighter;
import static koh.protocol.client.enums.ActionIdEnum.ACTION_CHARACTER_TELEPORT_ON_SAME_MAP;
import koh.protocol.messages.game.actions.fight.GameActionFightTeleportOnSameMapMessage;

/**
 *
 * @author Melancholia
 */
public class EffectTpPrevPos extends EffectBase {

    @Override
    public int applyEffect(EffectCast castInfos) {
        int toReturn = -1;
        for (final Fighter target : castInfos.targets) {
            if (target.getPreviousCellPos().isEmpty()) {
                continue;
            }
            final FightCell cell = target.getFight().getCell(target.getPreviousCellPos().get(target.getPreviousCellPos().size() - 1));

            if (cell != null && cell.canWalk()) {
                target.getFight().sendToField(new GameActionFightTeleportOnSameMapMessage(ACTION_CHARACTER_TELEPORT_ON_SAME_MAP, castInfos.caster.getID(), target.getID(), cell.Id));

                toReturn = target.setCell(cell);
            }
            if (toReturn != -1) {
                break;
            }
        }

        return toReturn;
    }

}
