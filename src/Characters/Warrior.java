/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Characters;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public abstract class Warrior implements ClassType{
    
    // Fields 
    
    private String name;
    private int hp;
    private int defense;
    private int attack;
    private ArrayList<String> skills = new ArrayList<>();;
    
    
    //stats
    public Warrior(String name) {
        String[] SkillName = { "rage","FireBall","Magic Missile", "Small Heal" };
        this.name = "Warrior";
        this.hp = 150;
        this.defense = 30;
        this.attack = 50;
        
        for(int i =  0; i < SkillName.length; i++){
            skills.add(SkillName[i]);  
        }
        
    }
    
    @Override public String getName()    { return name; }
    @Override public int getHp()         { return hp; }
    @Override public int getDefense()    { return defense; }
    @Override public int getAttack()    { return attack; }
    @Override public ArrayList<String> getSkill(){ return skills;}
    
    // Skills
        class rage{
                
            }
        class BloodyFight{
            
        }
        class Counter{
            
        }
}
