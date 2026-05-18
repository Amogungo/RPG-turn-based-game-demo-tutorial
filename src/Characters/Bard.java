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
public class Bard implements ClassType{
    
            // Fields
    private String name;
    private int hp;
    private int defense;
    private int attack;
    private ArrayList<String> skills = new ArrayList<>();;
    
    
    //stats
    public Bard() {
        String[] SkillName = { "Sing","Gun","Flirt", "Back" };
        this.name = "Bard";
        this.hp = 245;
        this.defense = 35;
        this.attack = 10;
        
        for(int i =  0; i < SkillName.length; i++){
            skills.add(SkillName[i]); 
        }
        
    }
    
    @Override public String getName()    { return name; }
    @Override public int getHp()         { return hp; }
    @Override public int getDefense()    { return defense; }
    @Override public int getAttack()    { return attack; }
    @Override public ArrayList<String> getSkill(){ return skills;}
    @Override public String getImagePath() {return "/images/Ally/Bard.gif";}
    
    
            // Skills
        class Sing{
            
        }
    
    
        class Gun{
            
        }
        
        class Flirt {
            
        }
}
