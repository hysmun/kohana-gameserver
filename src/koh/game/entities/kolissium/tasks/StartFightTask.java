package koh.game.entities.kolissium.tasks;

import koh.game.entities.actors.Player;
import koh.game.entities.environments.DofusMap;
import koh.game.entities.kolissium.KolizeumExecutor;
import koh.game.fights.Fight;
import koh.game.fights.FightTypeEnum;
import koh.game.fights.types.KoliseoFight;
import koh.game.network.WorldServer;
import koh.game.utils.PeriodicContestExecutor;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Melancholia on 6/10/16.
 */
public class StartFightTask implements Runnable {

    private final DofusMap map;
    private final List<Player> team1;
    private final List<Player> team2;
    private final PeriodicContestExecutor periodicContest;
    private final FightTypeEnum fightType;
    private volatile int arenaFightN = 1;

    public StartFightTask(PeriodicContestExecutor PeriodicContest, FightTypeEnum fightType, DofusMap map, List<Player> team1, List<Player> team2) {
        this.fightType = fightType;
        this.map = map;
        this.team1 = team1;
        this.team2 = team2;
        this.periodicContest = PeriodicContest;
    }

    @Override
    public void run() {
        try{
            final Iterator<Player> team1Iterator = team1.iterator();
            final Iterator<Player> team2Iterator = team2.iterator();
            final Player p1 = team1Iterator.next();
            final Player p2 = team2Iterator.next();
            final Fight fight;
            switch (fightType){
                case FIGHT_TYPE_PVP_ARENA:
                default:
                    fight = new KoliseoFight(map,p1.getClient(),p2.getClient());
            }
            new JoinFightTask(periodicContest, team1Iterator, fight, p1.getID());
            new JoinFightTask(periodicContest, team2Iterator, fight, p2.getID());
        }
        catch (Exception e){
            e.printStackTrace();
            team1.forEach(KolizeumExecutor::teleportLastPosition);
            team2.forEach(KolizeumExecutor::teleportLastPosition);
        }
    }
}
