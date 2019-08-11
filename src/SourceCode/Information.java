/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Information.java
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

import java.util.LinkedHashMap;

/**
 * @author Alberto Martin Cajal
 * 2015
 */
public class Information {

    //VARIABLES
    LinkedHashMap<String, String> championList;
    LinkedHashMap<String, String> strongAgainst;
    LinkedHashMap<String, String> weakAgainst;
    LinkedHashMap<String, String> evenAgainst;
    LinkedHashMap<String, String> worksWell;
    LinkedHashMap<String, String> generalTips;

    // The files which contain this information are NOT deleted in the update operation
    LinkedHashMap<String, String> hotOnes;
    LinkedHashMap<String, String> lovedOnes;
    LinkedHashMap<String, String> hatedOnes;
    LinkedHashMap<String, String> personalCombos;

    LinkedHashMap<String, String> personalStrong;
    LinkedHashMap<String, String> personalWeak;
    LinkedHashMap<String, String> personalEven;
    LinkedHashMap<String, String> personalWell;

    int totalFiles;
    int totalPersonalFiles;
    
    Flags fileType;

    boolean loaded; // This variable, when true, means that Information class has the data already avaiable
                    // so there's no need to load it again (memory consumption)
    
    // These variables establish which data will be displayed in normal and ranked games windows
    boolean community; // The Strong against, weak against, even and well against data from ChampionSelect
    boolean personal; // The strong against, weak, even and well against data created by the user
    
    // HOT, LOVED, HATED AND COMBOS are always displayed


    public Information () {
        championList = new LinkedHashMap<String, String>();
        
        // Total files starts here
        strongAgainst = new LinkedHashMap<String, String>();
        weakAgainst = new LinkedHashMap<String, String>();
        evenAgainst = new LinkedHashMap<String, String>();
        worksWell = new LinkedHashMap<String, String>();
        generalTips = new LinkedHashMap<String, String>(); // ??????????????????
        
        hotOnes = new LinkedHashMap<String, String>();
        lovedOnes = new LinkedHashMap<String, String>();
        hatedOnes = new LinkedHashMap<String, String>();
        personalCombos = new LinkedHashMap<String, String>();

        personalStrong = new LinkedHashMap<String, String>();
        personalWeak = new LinkedHashMap<String, String>();
        personalEven = new LinkedHashMap<String, String>();
        personalWell = new LinkedHashMap<String, String>();

        // CRITICAL VALUE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CRITICAL VALUE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        totalFiles = 4; // Manual fill. Total files WITH AVAIABLE DATA
        totalPersonalFiles = 8; // Manual fill. (4 are hot, loved, hated, combos; 4 are personal strong, even, etc)
        // CRITICAL VALUE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // CRITICAL VALUE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        loaded = false;

        // By default, community information is displayed, but personal is disabled
        community = true; 
        personal = false;
    }

    public void release () { // Release the information, so it can be loaded again
        championList.clear();
        strongAgainst.clear();
        weakAgainst.clear();
        evenAgainst.clear();
        worksWell.clear();
        generalTips.clear();
        hotOnes.clear();
        lovedOnes.clear();
        hatedOnes.clear();
        personalCombos.clear();

        personalStrong.clear();
        personalWeak.clear();
        personalEven.clear();
        personalWell.clear();

        loaded = false;
    }
    public void loadData (LinkedHashMap<String, String> map, Flags type) {

        switch (type) {

            case LIST:
                this.setChampionList(map);
                break;

            case STRONG:
                this.setStrongAgainst(map);
                break;

            case WEAK:
                this.setWeakAgainst(map);
                break;

            case EVEN:
                this.setEvenAgainst(map);
                break;

            case WELL:
                this.setWorksWell(map);
                break;

            case TIPS:
                this.setGeneralTips(map);
                break;

            case HOT:
                this.setHotOnes(map);
                break;

            case LOVED:
                this.setLovedOnes(map);
                break;

            case HATED:
                this.setHatedOnes(map);
                break;

            case COMBOS:
                this.setPersonalCombos(map);
                break;

            case PSTRONG:
                this.setPersonalStrong(map);
                break;

            case PWEAK:
                this.setPersonalWeak(map);
                break;

            case PEVEN:
                this.setPersonalEven(map);
                break;

            case PWELL:
                this.setPersonalWell(map);
                break;

            default:
                // Control error
                System.out.println("FATAL ERROR LOADING" + type + "DATA");
                System.out.println("NO CHANGES HAS BEEN MADE");
            break;
        }

        //dataToLoad.clear();
    }

    // GET METHODS ////////////////////////////////////////////////////////////
    public int getTotalFiles () {
        return this.totalFiles;
    }

    public int getTotalPersonalFiles () {
        return this.totalPersonalFiles;
    }
    
    public LinkedHashMap<String, String> getChampionList() {
        return this.championList;
    }

    public LinkedHashMap<String, String> getStrongAgainst () {
         return this.strongAgainst;
    }

    public LinkedHashMap<String, String> getWeakAgainst () {
         return this.weakAgainst;
    }

    public LinkedHashMap<String, String> getEvenAgainst () {
         return this.evenAgainst ;
    }

    public LinkedHashMap<String, String> getWorksWell () {
         return this.worksWell;
    }

    public LinkedHashMap<String, String> getGeneralTips () {
         return this.generalTips;
    }

    public LinkedHashMap<String, String> getHotOnes () {
         return this.hotOnes;
    }

    public LinkedHashMap<String, String> getLovedOnes () {
         return this.lovedOnes;
    }

    public LinkedHashMap<String, String> getHatedOnes () {
         return this.hatedOnes;
    }

    public LinkedHashMap<String, String> getPersonalCombos () {
         return this.personalCombos;
    }

    public LinkedHashMap<String, String> getPersonalStrong () {
         return this.personalStrong;
    }

    public LinkedHashMap<String, String> getPersonalWeak () {
         return this.personalWeak;
    }

    public LinkedHashMap<String, String> getPersonalEven () {
         return this.personalEven;
    }

    public LinkedHashMap<String, String> getPersonalWell () {
         return this.personalWell;
    }

    public boolean getLoaded () {
        return this.loaded;
    }
    
    public boolean getCommunity () {
        return this.community;
    }
    
    public boolean getPersonal () {
        return this.personal;
    }

    // SET METHODS ////////////////////////////////////////////////////////////
    public void setChampionList(LinkedHashMap<String, String> map) {
        //this.championList = map;
        this.championList.putAll(map);
    }

    public void setStrongAgainst (LinkedHashMap<String, String> map) {
         this.strongAgainst.putAll(map);
    }

    public void setWeakAgainst (LinkedHashMap<String, String> map) {
         this.weakAgainst.putAll(map);
    }

    public void setEvenAgainst (LinkedHashMap<String, String> map) {
         this.evenAgainst.putAll(map);
    }

    public void setWorksWell (LinkedHashMap<String, String> map) {
         this.worksWell.putAll(map);
    }

    public void setGeneralTips (LinkedHashMap<String, String> map) {
         this.generalTips.putAll(map);
    }

    public void setHotOnes (LinkedHashMap<String, String> map) {
         this.hotOnes.putAll(map);
    }

    public void setLovedOnes (LinkedHashMap<String, String> map) {
         this.lovedOnes.putAll(map);
    }

    public void setHatedOnes (LinkedHashMap<String, String> map) {
         this.hatedOnes.putAll(map);
    }

    public void setPersonalCombos (LinkedHashMap<String, String> map) {
         this.personalCombos.putAll(map);
    }

    public void setPersonalStrong(LinkedHashMap<String, String> map) {
         this.personalStrong.putAll(map);
    }

    public void setPersonalWeak (LinkedHashMap<String, String> map) {
         this.personalWeak.putAll(map);
    }

    public void setPersonalEven (LinkedHashMap<String, String> map) {
         this.personalEven.putAll(map);
    }

    public void setPersonalWell (LinkedHashMap<String, String> map) {
         this.personalWell.putAll(map);
    }

    public void setLoaded (boolean l) {
        this.loaded = l;
    }
    
    public void setCommunity (boolean b) {
        this.community = b;
    }
    
    public void setPersonal(boolean b) {
        this.personal = b;
    }


}
