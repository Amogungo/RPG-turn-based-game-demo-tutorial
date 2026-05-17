package Characters;

public class Enemy {

    public String name;
    public int hp;
    public int attack;
    public int defense;

    public boolean isTaunting = false;

    public Enemy(String name, int hp, int attack, int defense) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }

    public boolean isAlive() {
        return hp > 0;
    }
}