package Game;

import Characters.Enemy;
import java.util.ArrayList;
import java.util.Random;

public class EnemyAI {

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Random random = new Random();

    public EnemyAI() {

        // 🔥 Low-level enemy ideas
        enemies.add(new Enemy("Slime", 30, 5, 1));
        enemies.add(new Enemy("Goblin", 45, 8, 2));
        enemies.add(new Enemy("Rat Bandit", 35, 6, 1));
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    // MAIN TURN SYSTEM
    public ArrayList<EnemyAction> enemyTurn() {

    ArrayList<EnemyAction> actions = new ArrayList<>();

    for (Enemy e : enemies) {

        if (!e.isAlive()) continue;

        int action = random.nextInt(3);

        switch (action) {

            case 0:
                actions.add(new EnemyAction(e, ActionType.ATTACK));
                break;

            case 1:
                actions.add(new EnemyAction(e, ActionType.DEFEND));
                break;

            case 2:
                actions.add(new EnemyAction(e, ActionType.TAUNT));
                break;
        }
    }

    return actions;
}

    private void attack(Enemy e) {
        System.out.println(e.name + " attacks!");
        int dmg = e.attack + random.nextInt(3);
        System.out.println("Deals " + dmg + " damage!");
    }

    private void defend(Enemy e) {
        System.out.println(e.name + " defends!");
        e.defense += 2;
    }

    private void taunt(Enemy e) {
        System.out.println(e.name + " is TAUNTING!");
        e.isTaunting = true;
    }
    
    public static class EnemyAction {
    public Enemy actor;
    public ActionType type;

    public EnemyAction(Enemy actor, ActionType type) {
        this.actor = actor;
        this.type = type;
    }
}

public enum ActionType {
    ATTACK, DEFEND, TAUNT
}
    
}