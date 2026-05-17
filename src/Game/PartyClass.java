/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Characters.ClassType;
import demog.Classes;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class PartyClass {

    public PartyClass(ArrayList<ClassType> party1) {
    }
    public static ArrayList<ClassType> party = new ArrayList<>();
    int turnIndex = 0;
    ClassType ActiveChar;
    
      public ClassType getActiveChar() {
        return ActiveChar;
    }

    public void nextTurn() {
        if (party.isEmpty()) return;

        turnIndex++;
        if (turnIndex >= party.size()) {
            turnIndex = 0;
        }

        ActiveChar = party.get(turnIndex);
    }
    
    public ArrayList<ClassType> getParty() {
    return party;
    }
    
}
