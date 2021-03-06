package koh.game.actions.interactive;

import koh.game.actions.GameGuildCreation;
import koh.game.controllers.PlayerController;
import koh.game.entities.actors.Player;
import koh.protocol.messages.connection.BasicNoOperationMessage;

/**
 *
 * @author Neo-Craft
 */
public class InteractiveUsage implements InteractiveAction {

    public enum ActionType {

        TELEPORT(0),
        CREATE_GUILDE(2);

        int Value;

        private ActionType(int Type) {
            this.Value = Type;
        }

        public int value() {
            return Value;
        }

        public static ActionType valueOf(int value) {
            for (ActionType failure : values()) {
                if (failure.Value == value) {
                    return failure;
                }
            }
            return null;
        }
    }

    @Override
    public boolean isEnabled(Player actor) {
        return true;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public void execute(Player actor, int element) {
        if (!this.isEnabled(actor)) {
            actor.send(new BasicNoOperationMessage());
            return;
        }
        if (actor.getCurrentMap().getDoor(element) == null) {
            PlayerController.sendServerMessage(actor.getClient(), "Door not implanted yet ..." + element);
            return;
        }
        switch (ActionType.valueOf(actor.getCurrentMap().getDoor(element).getType())) {
            case TELEPORT:
                actor.teleport(Integer.parseInt(actor.getCurrentMap().getDoor(element).getParameters().split(",")[0]), Integer.parseInt(actor.getCurrentMap().getDoor(element).getParameters().split(",")[1]));
                break;
            case CREATE_GUILDE:
                //if (actor.client.canGameAction(GameActionTypeEnum.CREATE_GUILD)) {
                actor.getClient().addGameAction(new GameGuildCreation(actor));
                //}
                break;
            default:
                PlayerController.sendServerMessage(actor.getClient(), "Door not parametered ... "+element);
                return;
        }

    }

    @Override
    public void leave(Player player, int element) {

    }

    @Override
    public void abort(Player player, int element) {

    }

}
