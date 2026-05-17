/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Characters;

import java.util.ArrayList;

/**
 *
 * @author hp
 */
public interface ClassType {
    String getName();
    int getHp();
    int getDefense();
    int getAttack();
    ArrayList<String> getSkill();
    void displayStats();  // optional: you can give this a default body
}
