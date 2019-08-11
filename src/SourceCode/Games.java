/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Games.java
*
*   Description: @TODO
*
*   Notes: N/A
*
*   Contact: Alberto Martin Cajal, amartin.glimpse23<AT>gmail.com
*
*   URL: https://github.com/amcajal/lol_assembler
*
*   License: GNU GPL v3.0
*
*   Copyright (C) 2018 Alberto Martin Cajal
*
*   This file is part of LoL Assembler project.
*
*   LoL Assembler is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   LoL Assembler is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*******************************************************************************/


package SourceCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Alberto
 */
public class Games {

    // SHARED VARIABLES (NORMAL AND RANKEDS) /////////////////////////////////////////////
    Information information;

    Map<String, Double> scores; // Champions with scores
    Map<String, Double> scoresP; // Champions with scores using personal information
    Map<String, String> tags; // Champions with tags
    Map<String, String> tagsP; // Champions with tags using personal information
    Map<String, Double> combos; // Possible combos
    Map<String, Double> combosP; // Possible combos using personal information

    Map<String, String> comboList;

    // Mathematical values
    Double order; // Score by order: 0.2 first champ, and then, -0.02 until 0.12
    Double bonus; // Bonus = + 0.15, if champion is hot, hated, loved or has a shared combo

    
    // RANKED GAMES VARIABLES /////////////////////////////////////////////
    Map<String, Integer> rankedBans;
    List<String> enemyBans;
    List<String> allyBans;

    Map<String, Integer> rankedPicks;
    List<String> enemyPicks;
    List<String> allyPicks;

    public Games(Information info, Flags gameType, Flags playerNumber) {

        this.information = info;

        scores = new HashMap<String, Double>();
        tags = new HashMap<String, String>();
        combos = new HashMap<String, Double>();
        
        scoresP = new HashMap<String, Double>();
        tagsP = new HashMap<String, String>();
        combosP = new HashMap<String, Double>();

        comboList = new HashMap<String, String>();

        order = 0.2;
        bonus = 0.15;

        if (gameType.equals(gameType.NORMAL)) {
            if (playerNumber.equals(playerNumber.VS5)) {
                // DO SOMETHING RELATED WITH MAXIMUM PICKS 5 players
            }
            else { // player number equals to vs3
                // DO SOMETHING RELATED WITH MAXIMUM PICKS 3 players
            }
        }
        else { // gameType equals to RANKED
            //if (playerNumber.equals(playerNumber.VS5)) {
                rankedBans = new HashMap<String, Integer>();
                enemyBans = new ArrayList<String>();
                allyBans = new ArrayList<String>();

                rankedPicks = new HashMap<String, Integer>();
                enemyPicks = new ArrayList<String>();
                allyPicks = new ArrayList<String>();

                // DO SOMETHING RELATED WITH MAXIMUM PICKS 5 players

            //}
        }
    }

    public Map<String, Double> normalCalculateScore(List<String> addeds) {
        // First, the data are cleared
        scores.clear();
        tags.clear();
        comboList.clear();

        Map<String, Double> sortedMap = null;

        double actualOrder = order;
        double toAddD = 0.0;
        String toAddS = "";
        String[] c;
        String combo = "";
        String comboListAux = "";
        String hotLoved = "";

        for (String champion : addeds) {
            if (!this.information.getWorksWell().containsKey(champion)) {
                continue; // If the champion has no data, ignore it.
            }
            c = this.information.getWorksWell().get(champion).split("&&");
            actualOrder = order;

            for (String champ : c) {
                if (!addeds.contains(champ)) { // If the champion is already selected by the user, ignore it
                    if (!scores.containsKey(champ)) {
                        toAddD = actualOrder;
                        if (this.information.getHotOnes().containsKey(champ)) {
                            toAddD += bonus; // +0.15 for being hot
                            toAddS = toAddS + "Hot, ";
                        }
                        if (this.information.getLovedOnes().containsKey(champ)) {
                            toAddD += bonus; // +0.15 for being loved one
                            toAddS = toAddS + "Loved, ";
                        }
                        if (this.information.getHatedOnes().containsKey(champ)) {
                            //toAddD += bonus; HATEDS dont add bonus rate
                            toAddS = toAddS + "Hated, ";
                        }

                        toAddS = toAddS + "";

                        scores.put(champ, toAddD);
                        tags.put(champ, toAddS);

                        if (actualOrder > 0.10) {
                            actualOrder -= 0.02;
                        }
                    }
                    else {
                        toAddD = this.scores.get(champ) + actualOrder;
                        scores.put(champ, toAddD);
                    }

                    toAddD = 0;
                    toAddS = "";
                }
            }
        }

        // ADD HOT AND LOVED CHAMPIONS ALWAYS
        if (!this.information.getHotOnes().isEmpty()) {
            Iterator it2 = this.information.getHotOnes().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair = (Map.Entry)it2.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!scores.containsKey(hotLoved)) && (!addeds.contains(hotLoved))) {
                    scores.put(hotLoved, 0.15);
                    tags.put(hotLoved, "Hot, ");
                }
            }
        }

        if (!this.information.getLovedOnes().isEmpty()) {
            Iterator it3 = this.information.getLovedOnes().entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry pair = (Map.Entry)it3.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!addeds.contains(hotLoved))) {
                    if (this.information.getHotOnes().containsKey(hotLoved)) {
                        scores.put(hotLoved, 0.30);
                        tags.put(hotLoved, "Hot, Loved, ");
                    }
                    else {
                        scores.put(hotLoved, 0.15);
                        tags.put(hotLoved, "Loved, ");
                    }
                }
            }
        }
        
        // CALCULATE COMBOS
        String pairTemp = "";
        
        for (Map.Entry pair : scores.entrySet()) {
            pairTemp = pair.getKey().toString();
            
            if (!this.information.getPersonalCombos().isEmpty()) {
                for (Map.Entry pair2 : this.information.getPersonalCombos().entrySet()) {
                    combo = pair2.getValue().toString();

                    if (combo.contains(pairTemp)) {
                        //toAddD += bonus; // +0.15 for being both champs in a combo
                        comboListAux = comboListAux + pair2.getKey().toString() + ", ";
                    }
                }
            }

            comboList.put(pairTemp, comboListAux);
            comboListAux = "";
        }

        sortedMap= sortByComparator(scores);

        return sortedMap;
    }

    public Map<String, Double> normalCalculateScorePersonal(List<String> addeds) {
        // First, the data are cleared
        scoresP.clear();
        tagsP.clear();
        comboList.clear();
        Map<String, Double> sortedMap = null;

        double actualOrder = order;
        double toAddD = 0.0;
        String toAddS = "";
        String[] c;
        String combo = "";
        String comboListAux = "";
        String hotLoved = "";

        if (!this.information.getPersonalWell().isEmpty()) {
            for (String champion : addeds) {

                if (!this.information.getPersonalWell().containsKey(champion)) {
                    continue;
                }

                c = this.information.getPersonalWell().get(champion).split("&&");
                actualOrder = order;

                for (String champ : c) {
                    if (!addeds.contains(champ)) { // If the champion is already selected by the user, ignore it
                        if (!scoresP.containsKey(champ)) {
                            toAddD = actualOrder;
                            if (this.information.getHotOnes().containsKey(champ)) {
                                toAddD += bonus; // +0.15 for being hot
                                toAddS = toAddS + "Hot, ";
                            }
                            if (this.information.getLovedOnes().containsKey(champ)) {
                                toAddD += bonus; // +0.15 for being loved one
                                toAddS = toAddS + "Loved, ";
                            }
                            if (this.information.getHatedOnes().containsKey(champ)) {
                                //toAddD += bonus; HATEDS dont add bonus rate
                                toAddS = toAddS + "Hated, ";
                            }

                            toAddS = toAddS + "";

                            scoresP.put(champ, toAddD);
                            tagsP.put(champ, toAddS);

                            if (actualOrder > 0.10) {
                                actualOrder -= 0.02;
                            }
                        }
                        else {
                            toAddD = this.scoresP.get(champ) + actualOrder;
                            scoresP.put(champ, toAddD);
                        }

                        toAddD = 0;
                        toAddS = "";
                    }
                }
            }
        }

        // ADD HOT AND LOVED CHAMPIONS ALWAYS
        if (!this.information.getHotOnes().isEmpty()) {
            Iterator it2 = this.information.getHotOnes().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair = (Map.Entry)it2.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!scoresP.containsKey(hotLoved)) && (!addeds.contains(hotLoved))) {
                    scoresP.put(hotLoved, 0.15);
                    tagsP.put(hotLoved, "Hot, ");
                }
            }
        }

        if (!this.information.getLovedOnes().isEmpty()) {
            Iterator it3 = this.information.getLovedOnes().entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry pair = (Map.Entry)it3.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!addeds.contains(hotLoved))) {
                    if (this.information.getHotOnes().containsKey(hotLoved)) {
                        scoresP.put(hotLoved, 0.30);
                        tagsP.put(hotLoved, "Hot, Loved, ");
                    }
                    else {
                        scoresP.put(hotLoved, 0.15);
                        tagsP.put(hotLoved, "Loved, ");
                    }
                }
            }
        }
        
        // CALCULATE COMBOS
        String pairTemp = "";
        
        for (Map.Entry pair : scoresP.entrySet()) {
            pairTemp = pair.getKey().toString();
            
            if(!this.information.getPersonalCombos().isEmpty()) {
                for (Map.Entry pair2 : this.information.getPersonalCombos().entrySet()) {
                    combo = pair2.getValue().toString();

                    if (combo.contains(pairTemp)) {
                        //toAddD += bonus; // +0.15 for being both champs in a combo
                        comboListAux = comboListAux + pair2.getKey().toString() + ", ";
                    }
                }
            }

            comboList.put(pairTemp, comboListAux);
            comboListAux = "";
        }

        if (!scoresP.isEmpty()) {
            sortedMap= sortByComparator(scoresP);
        }
        else {
            sortedMap = new HashMap<String, Double>();
        }
        
        return sortedMap;
    }

    public Map<String, Integer> getBans() {
        return this.rankedBans;
    }

    public List<String> getAllyBans() {
        return this.allyBans;
    }

    public List<String> getEnemyBans() {
        return this.enemyBans;
    }

    public Map<String, Integer> getPicks (){
        return this.rankedPicks;
    }

    public List<String> getAllyPicks() {
        return this.allyPicks;
    }

    public List<String> getEnemyPicks() {
        return this.enemyPicks;
    }

    public void addBan (String champion) {
        this.getBans().put(champion, 1);
    }

    public void addAllyBan (String champion) {
        //this.getAllyBans().add(champion);
        //this.allyBans.add(champion);
        this.allyBans.add(champion);
    }

    public void addEnemyBan (String champion) {
        //this.getEnemyBans().add(champion);
        //this.enemyBans.add(champion);
        this.enemyBans.add(champion);
    }

    public void addPick (String champion) {
        this.getPicks().put(champion, 1);
    }

    public void addAllyPick (String champion) {
        //this.getAllyPicks().add(champion);
        this.allyPicks.add(champion);
    }

    public void addEnemyPick (String champion) {
        //this.getEnemyPicks().add(champion);
        this.enemyPicks.add(champion);
    }

    public Map<String, Double> rankedCalculateBanScore() {
        // First, the data are cleared
        scores.clear();
        tags.clear();
        Map<String, Double> sortedMap = null;

        double actualOrder = order;
        double toAddD = 0.0;
        String toAddS = "";
        String[] c;
        String hotHated = "";

        // START THE CALCULATION
        if (this.enemyBans.isEmpty()) { // This is when allies starts
             
            if (!this.information.getHotOnes().isEmpty()) {
                Iterator it = this.information.getHotOnes().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    scores.put(pair.getKey().toString(), this.bonus);
                    //toAddS = toAddS + "Hot, ";
                    tags.put(pair.getKey().toString(), "Hot, ");
                }
            }

            if (!this.information.getHatedOnes().isEmpty()) {
                Iterator it2 = this.information.getHatedOnes().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair = (Map.Entry)it2.next();
                    if (this.information.getHotOnes().containsKey(pair.getKey().toString())) {
                        scores.put(pair.getKey().toString(), (this.bonus*2));
                        tags.put(pair.getKey().toString(), "Hot, Hated, ");
                    }
                    else {
                        scores.put(pair.getKey().toString(), this.bonus);
                        tags.put(pair.getKey().toString(), "Hated, ");
                    }
                    toAddD = 0.0;
                }
            }
        }
        else { // This is when enemies starts, and for the rest of turns

            for (String champion : this.getEnemyBans()) {

                if (this.information.getStrongAgainst().containsKey(champion)) {
                    c = this.information.getStrongAgainst().get(champion).split("&&");
                    actualOrder = order;

                    for (String champ : c) {
                        if (!this.rankedBans.containsKey(champ)) {
                            if (!this.scores.containsKey(champ)) {
                                toAddD = actualOrder;
                                if (this.information.getHotOnes().containsKey(champ)) {
                                    toAddD += bonus; // +0.15 for being hot
                                    toAddS = toAddS + "Hot, ";
                                }
                                if (this.information.getHatedOnes().containsKey(champ)) {
                                    toAddD += bonus; // +0.15 for being hated
                                    toAddS = toAddS + "Hated, ";
                                }

                                scores.put(champ, toAddD);
                                tags.put(champ, toAddS);

                                if (actualOrder > 0.10) {
                                    actualOrder -= 0.02;
                                }
                            }
                            else {
                                toAddD = this.scores.get(champ) + actualOrder;
                                scores.put(champ, toAddD);
                            }

                            toAddD = 0;
                            toAddS = "";
                        } 
                    }
                }
            }

            // ADD HOT AND HATED ALWAYS
            
            if (!this.information.getHotOnes().isEmpty()) {
                Iterator it2 = this.information.getHotOnes().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair = (Map.Entry)it2.next();
                    hotHated = pair.getKey().toString().replace(" ", "");
                    if ((!enemyBans.contains(hotHated)) && (!allyBans.contains(hotHated)) 
                            && (!this.rankedBans.containsKey(hotHated)) && (!scores.containsKey(hotHated))) {
                        scores.put(hotHated, 0.15);
                        tags.put(hotHated, "Hot, ");
                    }
                }
            }

            if (!this.information.getHatedOnes().isEmpty()) {
                Iterator it3 = this.information.getHatedOnes().entrySet().iterator();
                while (it3.hasNext()) {
                    Map.Entry pair = (Map.Entry)it3.next();
                    hotHated = pair.getKey().toString().replace(" ", "");
                    if ((!enemyBans.contains(hotHated)) && (!allyBans.contains(hotHated)) 
                            && (!this.rankedBans.containsKey(hotHated)) && (!scores.containsKey(hotHated))) {

                        if (this.information.getHotOnes().containsKey(hotHated)) {
                            scores.put(hotHated, 0.30);
                            tags.put(hotHated, "Hot, Hated, ");
                        }
                        else {
                            scores.put(hotHated, 0.15);
                            tags.put(hotHated, "Hated, ");
                        }
                    }
                }
            }
        }

        sortedMap= sortByComparator(scores);

        return sortedMap;
    }
    
    public Map<String, Double> rankedCalculateBanScorePersonal() {
        // First, the data are cleared
        scoresP.clear();
        tagsP.clear();
        Map<String, Double> sortedMap = null;

        double actualOrder = order;
        double toAddD = 0.0;
        String toAddS = "";
        String[] c;
        String hotHated = "";

        // START THE CALCULATION
        if (this.enemyBans.isEmpty()) { // This is when allies starts
             
            if (!this.information.getHotOnes().isEmpty()) {
                Iterator it = this.information.getHotOnes().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    scoresP.put(pair.getKey().toString(), this.bonus);
                    tagsP.put(pair.getKey().toString(), "Hot, ");
                }
            }

            if (!this.information.getHatedOnes().isEmpty()) {
                Iterator it2 = this.information.getHatedOnes().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair = (Map.Entry)it2.next();
                    if (this.information.getHotOnes().containsKey(pair.getKey().toString())) {
                        scoresP.put(pair.getKey().toString(), (this.bonus*2));
                        tagsP.put(pair.getKey().toString(), "Hot, Hated, ");

                    }
                    else {
                        scoresP.put(pair.getKey().toString(), this.bonus);
                        tagsP.put(pair.getKey().toString(), "Hated, ");
                    }
                    toAddD = 0.0;
                }
            }
        }
        else { // This is when enemies starts, and for the rest of turns

            if (!this.information.getPersonalStrong().isEmpty()) {

                for (String champion : this.getEnemyBans()) {

                    if (!this.information.getPersonalStrong().containsKey(champion)) {
                        continue;
                    }

                    c = this.information.getPersonalStrong().get(champion).split("&&");
                    actualOrder = order;

                    for (String champ : c) {
                        if (!this.rankedBans.containsKey(champ)) {
                            if (!this.scoresP.containsKey(champ)) {
                                toAddD = actualOrder;
                                if (this.information.getHotOnes().containsKey(champ)) {
                                    toAddD += bonus; // +0.15 for being hot
                                    toAddS = toAddS + "Hot, ";
                                }
                                if (this.information.getHatedOnes().containsKey(champ)) {
                                    toAddD += bonus; // +0.15 for being hated
                                    toAddS = toAddS + "Hated, ";
                                }

                                scoresP.put(champ, toAddD);
                                tagsP.put(champ, toAddS);

                                if (actualOrder > 0.10) {
                                    actualOrder -= 0.02;
                                }
                            }
                            else {
                                toAddD = this.scoresP.get(champ) + actualOrder;
                                scoresP.put(champ, toAddD);
                            }

                            toAddD = 0;
                            toAddS = "";
                        } 
                    }
                }
            }

            // ADD HOT AND HATED ALWAYS
            if (!this.information.getHotOnes().isEmpty()) {
                Iterator it2 = this.information.getHotOnes().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair = (Map.Entry)it2.next();
                    hotHated = pair.getKey().toString().replace(" ", "");
                    if ((!enemyBans.contains(hotHated)) && (!allyBans.contains(hotHated)) 
                            &&(!this.rankedBans.containsKey(hotHated)) && (!scoresP.containsKey(hotHated))) {
                        scoresP.put(hotHated, 0.15);
                        tagsP.put(hotHated, "Hot, ");
                    }
                }
            }

            if (!this.information.getHatedOnes().isEmpty()) {
                Iterator it3 = this.information.getHatedOnes().entrySet().iterator();
                while (it3.hasNext()) {
                    Map.Entry pair = (Map.Entry)it3.next();
                    hotHated = pair.getKey().toString().replace(" ", "");
                    if ((!enemyBans.contains(hotHated)) && (!allyBans.contains(hotHated)) 
                            &&(!this.rankedBans.containsKey(hotHated)) && (!scoresP.containsKey(hotHated))) {

                        if (this.information.getHotOnes().containsKey(hotHated)) {
                            scoresP.put(hotHated, 0.30);
                            tagsP.put(hotHated, "Hot, Hated, ");
                        }
                        else {
                            scoresP.put(hotHated, 0.15);
                            tagsP.put(hotHated, "Hated, ");
                        }
                    }
                }
            }
        }
        
        if (!scoresP.isEmpty()) {
            sortedMap= sortByComparator(scoresP);
        }
        else {
            sortedMap = new HashMap<String, Double>();
        }

        return sortedMap;
    }

    public Map<String, Double> rankedCalculatePickScore() {
        scores.clear();
        tags.clear();
        comboList.clear();

        Map<String, Double> sortedMap = null;
        
        double actualOrder = order;
        double toAddD = 0.0;
        String toAddS = "";
        String combo = "";
        String comboListAux = "";
        String hotLoved="";
     
        String[] tempList;
        //String[] avoid; // When picking a champ, this champ could be strong against certain enemies, but weak against others
        // This list cointains the champions who are weak against the enemies. So, i.e, if allies picks a champion
        // who is strong against 2 enmies, but is weak against the 3 rest enemies, their score must decrease
        
        
        // First, from the enemy bans, get the list of possible champions who will be picked by the enemy
        // I.E: if enemy ban champ A, B and C; A is strong against f1, f2 f3, B equal... 
        // we have a list of possible picks
        // Now, with that possible picks, we elaborate a list of champions that counterpick that list
            
        for (String ban : this.enemyBans) {

            if (this.information.getStrongAgainst().containsKey(ban)) {
                tempList = this.information.getStrongAgainst().get(ban).split("&&");
                actualOrder = order;

                for (String ban2 : tempList) {

                    if (scores.containsKey(ban2)) {
                        toAddD = scores.get(ban2) + actualOrder;
                        scores.put(ban2, toAddD);
                    }
                    else {
                        if((!this.enemyBans.contains(ban2)) && (!this.allyBans.contains(ban2))
                                &&(!this.enemyPicks.contains(ban2)) &&(!this.allyPicks.contains(ban2))) {
                            if (this.information.getHotOnes().containsKey(ban2)) {
                                toAddD = toAddD + this.bonus;                                
                            }
                            if (this.information.getHatedOnes().containsKey(ban2)) {
                                toAddD = toAddD + this.bonus;                                
                            }

                            toAddD += actualOrder;
                            scores.put(ban2, toAddD);                            
                        }
                    }

                    if (actualOrder > 0.10) {
                        actualOrder -= 0.02;
                    }

                    toAddD = 0;

                }
            }
        }            

        sortedMap= sortByComparator(scores);
        scores.clear(); // Release resources
        tempList = null; // Release resources
        toAddD = 0.0; // Release resources
        // Now, in "sortedMap" we have the intended list

        Iterator it0 = sortedMap.entrySet().iterator();
        
        while (it0.hasNext()) {

            Map.Entry pair = (Map.Entry)it0.next();
            
            if (this.information.getWeakAgainst().containsKey(pair.getKey().toString())){
  
                tempList = this.information.getWeakAgainst().get(pair.getKey().toString()).split("&&");

                actualOrder = order;

                for (String weak: tempList) {
                    if (scores.containsKey(weak)) {
                        toAddD = scores.get(weak) + actualOrder;
                        scores.put(weak, toAddD);
                    }
                    else {
                        if((!this.enemyBans.contains(weak)) && (!this.allyBans.contains(weak))
                                && (!this.enemyPicks.contains(weak)) && (!this.allyPicks.contains(weak))) {
                            if (this.information.getHotOnes().containsKey(weak)) {
                                toAddD = toAddD + this.bonus;
                                toAddS = toAddS + "Hot, ";
                            }
                            if (this.information.getLovedOnes().containsKey(weak)) {
                                toAddD = toAddD + this.bonus;
                                toAddS = toAddS + "Loved, ";
                            }
                            toAddS = toAddS + "";

                            toAddD += actualOrder;
                            scores.put(weak, toAddD);
                            tags.put(weak, toAddS);
                        }
                    }
                }

                if (actualOrder > 0.10) {
                    actualOrder -= 0.02;
                }

                toAddD = 0;
                toAddS = "";
            }
        }
        
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        // At this moment, we have in "scores" the champions who are strong against the
        // possible picks of the enemy
        
        // Now we need to find the maximum value in all the map
        double aux = 0.0;
        double aux2;
        int retval;
        String casted;
        
        Iterator it1 = scores.entrySet().iterator();
        
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry)it1.next();
            
            casted = pair.getValue().toString();
            aux2 = Double.parseDouble(casted);
            retval = Double.compare(aux, aux2);
            
            if(retval < 0) {
                aux = aux2;
            }          
        }
        
        // VALUE AUX HAS NOW THE MAXIMUM VALUE OF THE MAP
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        // Now we have to add to "scores" the champions who are strong against the 
        // actual picks of the enemy, giving them more weight and relevance.
        
        if (!this.enemyPicks.isEmpty()) { // This is when enemies starts the picks
            
            // ADD TO THE PREVIOUS MAP THE NEW INFORMATION
            
            // UNCOMMENT THIS IF THE FOLLOWING DOESNT WORK
            /*
            toAddD = 0.0;
            toAddS = "";
            tempList = null;
            scores.clear();
            tags.clear();
            */
            
            //////////////////////////////////////////////
            //////////////////////////////////////////////
            //////////////////////////////////////////////
            
            for (String pick : this.enemyPicks) {
                
                if (!this.information.getWeakAgainst().containsKey(pick)) {
                    continue;
                }
                
                tempList = this.information.getWeakAgainst().get(pick).split("&&");
                
                actualOrder = order;
                
                for(String pick2: tempList) {
                    if (scores.containsKey(pick2)) {
                        //toAddD = scores.get(pick2) + actualOrder;
                        toAddD = scores.get(pick2) + aux; // If the champion is strong against, and already exist, has much more relevance
                        scores.put(pick2, toAddD);
                    }
                    else {
                        if((!this.enemyBans.contains(pick2)) && (!this.allyBans.contains(pick2))
                                && (!this.enemyPicks.contains(pick2)) && (!this.allyPicks.contains(pick2))) { // The champion is elegible
                            
                            if (this.information.getHotOnes().containsKey(pick2)) {
                                toAddD = toAddD + this.bonus;
                                toAddS = toAddS + "Hot, ";
                            }
                            if (this.information.getLovedOnes().containsKey(pick2)) {
                                toAddD = toAddD + this.bonus;
                                toAddS = toAddS + "Loved, ";
                            }
                            toAddS = toAddS + "";
                            
                            for (String ww: this.allyPicks) { // Check if the champ already has synergic with other one
                                if ((this.information.getWorksWell().containsKey(pick2))
                                        && (this.information.getWorksWell().get(pick2).contains(ww))) {
                                    toAddD = toAddD + (this.bonus + 0.08); // Is the same to say: add 0.07
                                }
                            }
                            
                            
                            for (String wa: this.enemyPicks) { // Check if the champ is weak against other enemy
                                if ((this.information.getWeakAgainst().containsKey(pick2))
                                        &&(this.information.getWeakAgainst().get(pick2).contains(wa))) {
                                    toAddD = toAddD - (this.bonus - 0.08); // Is the same to say: rest 0.07
                                }
                            }
                            
                            toAddD += actualOrder + aux;
                            scores.put(pick2, toAddD);
                            tags.put(pick2, toAddS);
                        }
                    }
                    
                    if (actualOrder > 0.10) {
                        actualOrder -= 0.02;
                    }
                
                    toAddD = 0;
                    toAddS = "";

                }
            }
        }

        // ADD HOT AND LOVED ALWAYS
        if (!this.information.getHotOnes().isEmpty()) {
            Iterator it2 = this.information.getHotOnes().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair = (Map.Entry)it2.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!scores.containsKey(hotLoved)) && (!enemyBans.contains(hotLoved))
                        && (!allyBans.contains(hotLoved)) && (!allyPicks.contains(hotLoved))
                        && (!enemyPicks.contains(hotLoved))) {
                    scores.put(hotLoved, 0.15);
                    tags.put(hotLoved, "Hot, ");
                }
            }
        }

        if (!this.information.getLovedOnes().isEmpty()) {
            Iterator it3 = this.information.getLovedOnes().entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry pair = (Map.Entry)it3.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!scores.containsKey(hotLoved)) && (!enemyBans.contains(hotLoved))
                        && (!allyBans.contains(hotLoved)) && (!allyPicks.contains(hotLoved))
                        && (!enemyPicks.contains(hotLoved))) {

                    if (this.information.getHotOnes().containsKey(hotLoved)) {
                        scores.put(hotLoved, 0.30);
                        tags.put(hotLoved, "Hot, Loved, ");
                    }
                    else {
                        scores.put(hotLoved, 0.15);
                        tags.put(hotLoved, "Loved, ");
                    }
                }
            }
        }
        
        // CALCULATE COMBOS
        String pairTemp = "";
        
        for (Map.Entry pair : scores.entrySet()) {
            pairTemp = pair.getKey().toString();
            
            if (!this.information.getPersonalCombos().isEmpty()) {
                for (Map.Entry pair2 : this.information.getPersonalCombos().entrySet()) {
                    combo = pair2.getValue().toString();

                    if (combo.contains(pairTemp)) {
                        //toAddD += bonus; // +0.15 for being both champs in a combo
                        comboListAux = comboListAux + pair2.getKey().toString() + ", ";
                    }
                }
            }

            comboList.put(pairTemp, comboListAux);
            comboListAux = "";
        }
        
        sortedMap.clear();
        sortedMap= sortByComparator(scores);

        return sortedMap;
    }
    
    public Map<String, Double> rankedCalculatePickScorePersonal() {
        scoresP.clear();
        tagsP.clear();
        comboList.clear();

        Map<String, Double> sortedMap = null;
        
        double actualOrder = order;
        double toAddD = 0.0;
        String toAddS = "";
        String combo = "";
        String comboListAux = "";
        String hotLoved="";
        
        String[] tempList;
        //String[] avoid; // When picking a champ, this champ could be strong against certain enemies, but weak against others
        // This list cointains the champions who are weak against the enemies. So, i.e, if allies picks a champion
        // who is strong against 2 enmies, but is weak against the 3 rest enemies, their score must decrease
        
        
        // First, from the enemy bans, get the list of possible champions who will be picked by the enemy
        // I.E: if enemy ban champ A, B and C; A is strong against f1, f2 f3, B equal... 
        // we have a list of possible picks
        // Now, with that possible picks, we elaborate a list of champions that counterpick that list
            
        if (!this.information.getPersonalStrong().isEmpty()) {
            
            for (String ban : this.enemyBans) {

                if (!this.information.getPersonalStrong().containsKey(ban)){
                    continue;
                }

                tempList = this.information.getPersonalStrong().get(ban).split("&&");
                actualOrder = order;

                for (String ban2 : tempList) {

                    if (scoresP.containsKey(ban2)) {
                        toAddD = scoresP.get(ban2) + actualOrder;
                        scoresP.put(ban2, toAddD);
                    }
                    else {
                        if((!this.enemyBans.contains(ban2)) && (!this.allyBans.contains(ban2))
                                &&(!this.enemyPicks.contains(ban2)) && (!this.allyPicks.contains(ban2))) {
                            if (this.information.getHotOnes().containsKey(ban2)) {
                                toAddD = toAddD + this.bonus;                                
                            }
                            if (this.information.getHatedOnes().containsKey(ban2)) {
                                toAddD = toAddD + this.bonus;                                
                            }

                            toAddD += actualOrder;
                            scoresP.put(ban2, toAddD);                            
                        }
                    }

                    if (actualOrder > 0.10) {
                        actualOrder -= 0.02;
                    }

                    toAddD = 0;

                }
            }
        }

        if (!scoresP.isEmpty()) {
            sortedMap= sortByComparator(scoresP);
        }
        scoresP.clear(); // Release resources
        tempList = null; // Release resources
        toAddD = 0.0; // Release resources
        // Now, in "sortedMap" we have the intended list

        if ((sortedMap != null) || (!this.information.getPersonalWeak().isEmpty())) {
            Iterator it0 = sortedMap.entrySet().iterator();
            while (it0.hasNext()) {

                Map.Entry pair = (Map.Entry)it0.next();
                
                if (!this.information.getPersonalWeak().containsKey(pair.getKey().toString())) {
                    continue;
                }
                
                tempList = this.information.getPersonalWeak().get(pair.getKey().toString()).split("&&");

                actualOrder = order;

                for (String weak: tempList) {
                    if (scoresP.containsKey(weak)) {
                        toAddD = scoresP.get(weak) + actualOrder;
                        scoresP.put(weak, toAddD);
                    }
                    else {
                        if((!this.enemyBans.contains(weak)) && (!this.allyBans.contains(weak))
                                &&(!this.enemyPicks.contains(weak)) &&(!this.allyPicks.contains(weak))) {
                            if (this.information.getHotOnes().containsKey(weak)) {
                                toAddD = toAddD + this.bonus;
                                toAddS = toAddS + "Hot, ";
                            }
                            if (this.information.getLovedOnes().containsKey(weak)) {
                                toAddD = toAddD + this.bonus;
                                toAddS = toAddS + "Loved, ";
                            }
                            toAddS = toAddS + "";

                            toAddD += actualOrder;
                            scoresP.put(weak, toAddD);
                            tagsP.put(weak, toAddS);
                        }
                    }
                }

                if (actualOrder > 0.10) {
                    actualOrder -= 0.02;
                }

                toAddD = 0;
                toAddS = "";
            }
        }
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        // At this moment, we have in "scores" the champions who are strong against the
        // possible picks of the enemy
        
        // Now we need to find the maximum value in all the map
        double aux = 0.0;
        double aux2;
        int retval;
        String casted;
        
        if (!scoresP.isEmpty()) {
            Iterator it1 = scoresP.entrySet().iterator();

            while (it1.hasNext()) {
                Map.Entry pair = (Map.Entry)it1.next();

                casted = pair.getValue().toString();
                aux2 = Double.parseDouble(casted);
                retval = Double.compare(aux, aux2);

                if(retval < 0) {
                    aux = aux2;
                }          
            }
        }
        // VALUE AUX HAS NOW THE MAXIMUM VALUE OF THE MAP
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        //////////////////////////////////////////////
        
        // Now we have to add to "scores" the champions who are strong against the 
        // actual picks of the enemy, giving them more weight and relevance.
        
        if (!this.information.getPersonalWeak().isEmpty()){
            
            if (!this.enemyPicks.isEmpty()) { // This is when enemies starts the picks

                for (String pick : this.enemyPicks) {
                    
                    if (!this.information.getPersonalWeak().containsKey(pick)) {
                        continue;
                    }
                    
                    tempList = this.information.getPersonalWeak().get(pick).split("&&");

                    actualOrder = order;

                    for(String pick2: tempList) {
                        if (scoresP.containsKey(pick2)) {
                            //toAddD = scores.get(pick2) + actualOrder;
                            toAddD = scoresP.get(pick2) + aux; // If the champion is strong against, and already exist, has much more relevance
                            scoresP.put(pick2, toAddD);
                        }
                        else {
                            if((!this.enemyBans.contains(pick2)) && (!this.allyBans.contains(pick2))
                                    && (!this.enemyPicks.contains(pick2)) && (!this.allyPicks.contains(pick2))) { // The champion is elegible

                                if (this.information.getHotOnes().containsKey(pick2)) {
                                    toAddD = toAddD + this.bonus;
                                    toAddS = toAddS + "Hot, ";
                                }
                                if (this.information.getLovedOnes().containsKey(pick2)) {
                                    toAddD = toAddD + this.bonus;
                                    toAddS = toAddS + "Loved, ";
                                }
                                toAddS = toAddS + "";

                                if (!this.information.getPersonalWell().isEmpty()) {
                                    
                                    for (String ww: this.allyPicks) { // Check if the champ already has synergic with other one

                                        if (!this.information.getPersonalWell().containsKey(ww)) {
                                            continue;
                                        }

                                        if ((this.information.getPersonalWell().containsKey(pick2))
                                                &&(this.information.getPersonalWell().get(pick2).contains(ww))) {
                                            toAddD = toAddD + (this.bonus + 0.08); // Is the same to say: add 0.07
                                        }
                                    }
                                }

                                for (String wa: this.enemyPicks) { // Check if the champ is weak against other enemy
                                    
                                    if (!this.information.getPersonalWeak().containsKey(wa)) {
                                        continue;
                                    }

                                    if ((this.information.getPersonalWeak().containsKey(pick2))
                                            &&(this.information.getPersonalWeak().get(pick2).contains(wa))) {
                                        toAddD = toAddD - (this.bonus - 0.08); // Is the same to say: rest 0.07
                                    }
                                }

                                toAddD += actualOrder + aux;
                                scoresP.put(pick2, toAddD);
                                tagsP.put(pick2, toAddS);
                            }
                        }

                        if (actualOrder > 0.10) {
                            actualOrder -= 0.02;
                        }

                        toAddD = 0;
                        toAddS = "";
                    }
                }
            }
        }

        // ADD HOT AND LOVED ALWAYS
        if (!this.information.getHotOnes().isEmpty()) {
            Iterator it2 = this.information.getHotOnes().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry pair = (Map.Entry)it2.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!scoresP.containsKey(hotLoved)) && (!enemyBans.contains(hotLoved))
                        && (!allyBans.contains(hotLoved)) && (!allyPicks.contains(hotLoved))
                        && (!enemyPicks.contains(hotLoved))) {
                    scoresP.put(hotLoved, 0.15);
                    tagsP.put(hotLoved, "Hot, ");
                }
            }
        }

        if (!this.information.getLovedOnes().isEmpty()) {
            Iterator it3 = this.information.getLovedOnes().entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry pair = (Map.Entry)it3.next();
                hotLoved = pair.getKey().toString().replace(" ", "");
                if ((!scoresP.containsKey(hotLoved)) && (!enemyBans.contains(hotLoved))
                        && (!allyBans.contains(hotLoved)) && (!allyPicks.contains(hotLoved))
                        && (!enemyPicks.contains(hotLoved))) {

                    if (this.information.getHotOnes().containsKey(hotLoved)) {
                        scoresP.put(hotLoved, 0.30);
                        tagsP.put(hotLoved, "Hot, Loved, ");
                    }
                    else {
                        scoresP.put(hotLoved, 0.15);
                        tagsP.put(hotLoved, "Loved, ");
                    }
                }
            }
        }
        
        // CALCULATE COMBOS
        String pairTemp = "";
        
        for (Map.Entry pair : scoresP.entrySet()) {
            pairTemp = pair.getKey().toString();
            
            if (!this.information.getPersonalCombos().isEmpty()) {
                for (Map.Entry pair2 : this.information.getPersonalCombos().entrySet()) {
                    combo = pair2.getValue().toString();

                    if (combo.contains(pairTemp)) {
                        //toAddD += bonus; // +0.15 for being both champs in a combo
                        comboListAux = comboListAux + pair2.getKey().toString() + ", ";
                    }
                }
            }

            comboList.put(pairTemp, comboListAux);
            comboListAux = "";
        }
        
        if (!scoresP.isEmpty()) {
            sortedMap.clear();
            sortedMap= sortByComparator(scoresP);
        }
        else {
            sortedMap = new HashMap<String, Double>();
        }

        return sortedMap;
    }
    
    public Map<String, Double> rankedCalculateCombos() {
        Map<String, Double> sortedMap = null;

        if (!information.getPersonalCombos().isEmpty()) {

            Map<String, Double> scoresSortedMap = sortByComparator(this.scores); // We have the recommended picks
            ArrayList<String> combosCopy = new ArrayList<String>();
            combos.clear();
            double aux;

            Iterator it = information.getPersonalCombos().entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                combosCopy.add(pair.getKey().toString() + ". " + pair.getValue().toString());
            }

            // In combos, we will have the combos already started (the picked champions compond the listed combos)
            int complete;
            boolean started;
            
            if (!this.allyPicks.isEmpty()) {
                for (String cmb : combosCopy) {
                    complete = 0;
                    started = false;
                    
                    for (String picked : this.allyPicks) {
                        if (cmb.contains(picked)) {
                            started = true;
                            complete ++;
                        }
                    }

                    if (complete == cmb.split("-").length) {
                        combos.put(cmb, 1000.0); // If a combo is complete, it receives 1000 score, so it will appear first
                    }
                    else {
                        if (started == true) {
                            if (combos.containsKey(cmb)) {
                                aux = combos.get(cmb);
                                combos.put(cmb, aux + 1.0);
                            }
                            else {
                                combos.put(cmb, 200.0); // This to ensure that these combos appear first in the sorted map
                            }
                        }
                    }
                }
            }

            // Now, calculate the combos that could be (considering the recommendations)
            Iterator it1 = scoresSortedMap.entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it1.hasNext()) {
                Map.Entry pair = (Map.Entry)it1.next();
                for (String cmb : combosCopy) {
                    if (cmb.contains(pair.getKey().toString())) {
                        if (combos.containsKey(cmb)) {
                            aux = combos.get(cmb);
                            combos.put(cmb, aux + 1.0);
                        }
                        else {
                            combos.put(cmb, 1.0);
                        }
                    }
                }
            }


            if (!combos.isEmpty()) {
                sortedMap = sortByComparator(combos);
            }
            else {
                sortedMap = new HashMap<String, Double>();
            }
        }
        return sortedMap;
    }
    
    public Map<String, Double> rankedCalculateCombosPersonal() {
        Map<String, Double> sortedMap = null;

        if (!information.getPersonalCombos().isEmpty()) {

            Map<String, Double> scoresSortedMap = sortByComparator(this.scoresP); // We have the recommended picks
            ArrayList<String> combosCopy = new ArrayList<String>();
            combosP.clear();
            double aux;

            Iterator it = information.getPersonalCombos().entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                combosCopy.add(pair.getKey().toString() + ". " + pair.getValue().toString());
            }
            
            // In combos, we will have the combos already started (the picked champions compound the listed combos)
            int complete;
            boolean started;
            
            if (!this.allyPicks.isEmpty()) {
                for (String cmb : combosCopy) {
                    complete = 0;
                    started = false;

                    for (String picked : this.allyPicks) {
                        if (cmb.contains(picked)) {
                            started = true;
                            complete ++;
                        }
                    }

                    if (complete == cmb.split("-").length) {
                        combos.put(cmb, 1000.0); // If a combo is complete, it receives 1000 score, so it will appear first
                    }
                    else {
                        if (started == true) {
                            if (combosP.containsKey(cmb)) {
                                aux = combosP.get(cmb);
                                combosP.put(cmb, aux + 1.0);
                            }
                            else {
                                combosP.put(cmb, 200.0); // This to ensure that these combos appear first in the sorted map
                            }
                        }
                    }
                }
            }

            // Now, calculate the combos that could be (considering strong against and even against)
            Iterator it1 = scoresSortedMap.entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it1.hasNext()) {
                Map.Entry pair = (Map.Entry)it1.next();
                for (String cmb : combosCopy) {
                    if (cmb.contains(pair.getKey().toString())) {
                        if (combosP.containsKey(cmb)) {
                            aux = combosP.get(cmb);
                            combosP.put(cmb, aux + 1.0);
                        }
                        else {
                            combosP.put(cmb, 1.0);
                        }
                    }
                }
            }

            if (!combosP.isEmpty()) {
                sortedMap = sortByComparator(combosP);
            }
            else {
                sortedMap = new HashMap<String, Double>();
            }
        }
        return sortedMap;
    }
    
    public Map<String, Double> normalCalculateCombos(List<String> addeds) {
        Map<String, Double> sortedMap = null;

        if (!information.getPersonalCombos().isEmpty()) {

            Map<String, Double> scoresSortedMap = sortByComparator(this.scores); // We have the recommended picks
            ArrayList<String> combosCopy = new ArrayList<String>();
            combos.clear();
            double aux;

            Iterator it = information.getPersonalCombos().entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                combosCopy.add(pair.getKey() + ". " + pair.getValue().toString());
            }
            
            // In combos, we will have the combos already started (the picked champions compound the listed combos)
            //boolean complete;
            int complete;
            boolean started;
            
            // This is to avoid showing all the combos when pressing "Assemble" with no champions selected
            
            if (!addeds.isEmpty()) {
                for (String cmb : combosCopy) {
                    //complete = false;
                    complete = 0;
                    started = false;

                    for (String picked : addeds) {
                        if (cmb.contains(picked)) {
                            started = true;
                            complete ++;
                        }
                    }
                    
                    if (complete == cmb.split("-").length) {
                        combos.put(cmb, 1000.0); // If a combo is complete, it receives 1000 score, so it will appear first
                    }
                    else {
                        if (started == true) {
                            if (combos.containsKey(cmb)) {
                                aux = combos.get(cmb);
                                combos.put(cmb, aux + 1.0);
                            }
                            else {
                                combos.put(cmb, 200.0); // This to ensure that these combos appear first in the sorted map
                            }
                        }
                    }
                }
            }

            // Now, calculate the combos that could be (considering strong against and even against)
            Iterator it1 = scoresSortedMap.entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it1.hasNext()) {
                Map.Entry pair = (Map.Entry)it1.next();
                //System.out.println(pair.getKey().toString());
                
                for (String cmb : combosCopy) {
                    //System.out.println(cmb);
                    if (cmb.contains(pair.getKey().toString())) {
                        //System.out.println(cmb);
                        //combos.put(cmb, 1.0);
                        
                        if (combos.containsKey(cmb)) {
                            aux = combos.get(cmb);
                            combos.put(cmb, aux + 1.0);
                        }
                        else {
                            combos.put(cmb, 1.0);
                        }
                        
                    }
                }
            }

            if (!combos.isEmpty()) {
                sortedMap = sortByComparator(combos);
            }
            else {
                sortedMap = new HashMap<String, Double>();
            }
        }
        return sortedMap;
    }
    
    public Map<String, Double> normalCalculateCombosPersonal (List<String> addeds) {
        Map<String, Double> sortedMap = null;

        if (!information.getPersonalCombos().isEmpty()) {

            Map<String, Double> scoresSortedMap = sortByComparator(this.scoresP); // We have the recommended picks
            ArrayList<String> combosCopy = new ArrayList<String>();
            combosP.clear();
            double aux;

            Iterator it = information.getPersonalCombos().entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                combosCopy.add(pair.getKey() + ". " + pair.getValue().toString());
            }

            // In combos, we will have the combos already started (the picked champions compond the listed combos)
            //boolean complete;
            int complete;
            boolean started;
            
            if (!addeds.isEmpty()) {
                for (String cmb : combosCopy) {
                    complete = 0;
                    started = false;

                    for (String picked : addeds) {
                        if (cmb.contains(picked)) {
                            started = true;
                            complete ++;
                        }
                    }

                    if (complete == cmb.split("-").length) {
                        combos.put(cmb, 1000.0); // If a combo is complete, it receives 1000 score, so it will appear first
                    }
                    else {
                        if (started == true) {
                            if (combosP.containsKey(cmb)) {
                                aux = combosP.get(cmb);
                                combosP.put(cmb, aux + 1.0);
                            }
                            else {
                                combosP.put(cmb, 200.0); // This to ensure that these combos appear first in the sorted map
                            }
                        }
                    }
                }
            }   

            // Now, calculate the combos that could be (considering strong against and even against)
            Iterator it1 = scoresSortedMap.entrySet().iterator();

            // This is a dirty solution, but is easier and safer to iterate over a ArrayList than using a iterator
            while (it1.hasNext()) {
                Map.Entry pair = (Map.Entry)it1.next();
                for (String cmb : combosCopy) {
                    if (cmb.contains(pair.getKey().toString())) {
                        if (combosP.containsKey(cmb)) {
                            aux = combosP.get(cmb);
                            combosP.put(cmb, aux + 1.0);
                        }
                        else {
                            combosP.put(cmb, 1.0);
                        }
                    }
                }
            }

            if (!combosP.isEmpty()) {
                sortedMap = sortByComparator(combosP);
            }
            else {
                sortedMap = new HashMap<String, Double>();
            }
            
        }
        return sortedMap;
    }
    
    private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o1,
                                   Map.Entry<String, Double> o2) {
                        return (o2.getValue()).compareTo(o1.getValue()); /// ????????????
                }
        });

        // Convert sorted map back to a Map
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) {
                Map.Entry<String, Double> entry = it.next();
                sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public String getTags (String champion) {

        String result = "No tags";

        if ((!this.tags.isEmpty()) && (this.tags.containsKey(champion))
                && (!this.tags.get(champion).equals(""))) {
           result = this.tags.get(champion);
        }

        return result;
    }

    public String getTagsPersonal (String champion) {

        String result = "No tags";

        if ((!this.tagsP.isEmpty()) && (this.tagsP.containsKey(champion))
                && (!this.tagsP.get(champion).equals(""))) {
           result = this.tagsP.get(champion);
        }

        return result;
    }

    public String getCombos (String champion) {

        //String result = "No combos";
        String result = "";

        if (!this.comboList.isEmpty() && (this.comboList.containsKey(champion))
                && (!this.comboList.get(champion).equals(""))) {
           result = this.comboList.get(champion);
        }

        return result;

    }
    
    public String getPositions (String champion, int mode) {
        // If mode is 0, it shows LOVED champions
        // If mode is 1, it shows HATED champions
        String result = "";
        
        if ((!this.information.getHotOnes().isEmpty()) && (this.information.getHotOnes().containsKey(champion))) {
            result = "HOT: " + this.information.getHotOnes().get(champion) + "; ";
        }
        
        if (mode == 0) {
            if ((!this.information.getLovedOnes().isEmpty()) && (this.information.getLovedOnes().containsKey(champion))) {
                result = result + "LOVED: " + this.information.getLovedOnes().get(champion) + " ";
            }    
        }
        else {
            if ((!this.information.getHatedOnes().isEmpty()) && (this.information.getHatedOnes().containsKey(champion))) {
                result = result + "HATED: " + this.information.getHatedOnes().get(champion) + " ";
            }          
        }

        return result;
    }

}
