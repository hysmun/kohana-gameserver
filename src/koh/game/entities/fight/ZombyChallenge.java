package koh.game.entities.fight;

import koh.game.entities.environments.MovementPath;
import koh.game.entities.item.Weapon;
import koh.game.entities.spells.SpellLevel;
import koh.game.fights.Fight;
import koh.game.fights.FightTeam;
import koh.game.fights.Fighter;
import koh.game.fights.effects.EffectCast;

/**
 * Created by Melancholia on 8/28/16.
 * 1Utiliser exactement un point de mouvement par tour de jeu.
 */
public class ZombyChallenge extends Challenge {

    public ZombyChallenge(Fight fight, FightTeam team) {
        super(fight,team);
    }

    private int turn;
    private int lastMove, lastTurn;


    @Override
    public void onFightStart() {

    }

    @Override
    public void onTurnStart(Fighter fighter) {
        this.turn = fight.getFightWorker().fightTurn;
    }

    @Override
    public void onTurnEnd(Fighter fighter) {

    }

    @Override
    public void onFighterKilled(Fighter target, Fighter killer) {

    }

    @Override
    public void onFighterMove(Fighter fighter, MovementPath path) {
        if(fighter.getTeam() != team || !fighter.isPlayer())
            return;

        if(lastTurn == turn){
            this.failChallenge();
        }else{
            if(path.transitCells.size() > 2){
                this.failChallenge();
            }else{
                this.lastMove = path.transitCells.size();
                this.lastTurn = turn;
            }
        }
    }

    @Override
    public void onFighterSetCell(Fighter fighter, short startCell, short endCell) {

    }

    @Override
    public void onFighterCastSpell(Fighter fighter, SpellLevel spell) {

    }

    @Override
    public void onFighterCastWeapon(Fighter fighter, Weapon weapon) {

    }

    @Override
    public void onFighterTackled(Fighter fighter) {

    }

    @Override
    public void onFighterLooseLife(Fighter fighter, EffectCast cast, int damage) {

    }

    @Override
    public void onFighterHealed(Fighter fighter, EffectCast cast, int heal) {

    }




}
