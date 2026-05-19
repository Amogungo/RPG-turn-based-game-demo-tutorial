/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Characters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hp
 */
public class Wizard implements ClassType{
    
    
            // Fields 
    private String name;
    private int hp;
    private int defense;
    private int attack;
    private ArrayList<String> skills = new ArrayList<>();;
    
    
    //stats
    public Wizard() {
        String[] SkillName = { "Lightning","FireBall","Magic Missile", "Eruption" };
        this.name = "Wizard";
        this.hp = 100;
        this.defense = 25;
        this.attack = 25;
        
        for(int i =  0; i < SkillName.length; i++){
            skills.add(SkillName[i]);  
        }
        
    }
    
    @Override public String getName()    { return name; }
    @Override public int getHp()         { return hp; }
    @Override public int getDefense()    { return defense; }
    @Override public int getAttack()    { return attack; }
    @Override public ArrayList<String> getSkill(){ return skills;}
    @Override public String getImagePath() {return "/images/Ally/wizardc.gif";}
    
    
    class Lightning {
        
    }
    
    class Fireball {
        
    }
    
    class Mmissile {
        
    }
    
    class SHeal{
        
    }
    
}
