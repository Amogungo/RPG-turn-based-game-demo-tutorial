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
public class Paladin implements ClassType{
    
        // Fields
    
    private String name;
    private int hp;
    private int defense;
    private int attack;
    private ArrayList<String> skills = new ArrayList<>();;
    
    
    //stats
    public Paladin() {
        String[] SkillName = { "Shield","Holy Light","Slam", "back" };
        this.name = "Paladin";
        this.hp = 300;
        this.defense = 60;
        this.attack = 20;
        
        for(int i =  0; i < SkillName.length; i++){
            skills.add(SkillName[i]);  
        }
    }
    
    @Override public String getName()    { return name; }
    @Override public int getHp()         { return hp; }
    @Override public int getDefense()    { return defense; }
    @Override public int getAttack()    { return attack; }
    @Override public ArrayList<String> getSkill(){ return skills;}
    @Override public String getImagePath() {return "/images/Ally/Paladin.gif";}
    
        // Skills
    class Shield{
        
    }
    class Break{
        
    }
    class Stomp{
        
    }
    
}
