/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Substrings.java
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
import java.util.List;

/**
 *
 * @author Alberto
 */
public class Substrings {

    String listSubstring;
    String nameDataSubstring;
    String nameEndDataSubstring;
    String startDataSubstring;
    String endDataSubstring;
    String strongDataSubstring;
    String weakDataSubstring;
    String evenDataSubstring;
    String wellDataSubstring;
    String tipsDataSubstring;

    // NEW VARIABLES
    String startListSubstring;
    String endListSubstring;
    String startStrongSubstring;
    String endStrongSubstring;
    String startWeakSubstring;
    String endWeakSubstring;
    String startEvenSubstring;
    String endEvenSubstring;
    String startWellSubstring;
    String endWellSubstring;

    String startScrapping;
    String endScrapping;

    String softwareVersion;

    // Special strings
    List<String> specialSubstrings = new ArrayList<String>();
    boolean active;
    String activeElement;

    public Substrings (){
        
        // Manual fill
        // Finding the names of the champions requires only 1 substring.
        // listSubstring = "data-sort-value="; DEPRECATED
        listSubstring = "tooltip=";

        // However, gather the other data requires a more complex process
        /*
         * startDataSubstrings stablish from wich line the program will start to gather data, while
         * endDataSubstring stablis the last line to process. So, when the program detect the "start",
         * starts the process, until the "end" string appears.
         */
        startDataSubstring = "class='block3 _all'>"; 
        endDataSubstring = "class='block3 _general'>";

        startListSubstring = "id=\"chL\"";
        endListSubstring = "id=\"end_chL\"";

        startStrongSubstring = "id=\"sA\"";
        endStrongSubstring = "id=\"end_sA\"";

        startWeakSubstring = "id=\"wA\"";
        endWeakSubstring = "id=\"end_wA\"";

        startEvenSubstring = "id=\"eA\"";
        endEvenSubstring = "id=\"end_eA\"";
        
        startWellSubstring = "id=\"wW\"";
        endWellSubstring = "id=\"end_wW\"";

        startScrapping = "id=\"start_scrapping\"";
        endScrapping = "id=\"finish_scrapping\"";
        softwareVersion= "id=\"software_version=";

        /*
         * The "strong", "weak", etc. strings stablish to wich map the final data will go.
         * When one of this strings is detected, the next processes will end in the storage
         * in a specific map.
         */
        strongDataSubstring = "class='strong-block'>";
        weakDataSubstring = "class='weak-block'>";
        evenDataSubstring = "class='even-block'>";
        wellDataSubstring = "class='good-block'>";

        //After entering in a block and selected a block, the program gather the champion name
        nameDataSubstring = "class='name'>";
        nameEndDataSubstring = "</div></a>";

        // General tips is easy to process, in a similar way to champion names
        tipsDataSubstring = "Tips</p>";

        active = false;
        activeElement = "";

        //Manual fill
        specialSubstrings.add("Dr-Mundo");
        specialSubstrings.add("Lee-Sin");
        specialSubstrings.add("Master-Yi");
        specialSubstrings.add("Miss-Fortune");
        specialSubstrings.add("Twisted-Fate");
        specialSubstrings.add("Xin-Zhao");
        specialSubstrings.add("Jarvan-IV");
        specialSubstrings.add("Tahm-kench");

    }
    // GET-SET METHODS

    public String create() {
        String aux = activeElement;
        active = false;
        activeElement = "";
        return aux;
    }

    public String getStartListSubstring() {
        return this.startListSubstring;
    }

    public String getEndListSubstring() {
        return this.endListSubstring;
    }
    
    public String getStartStrongSubstring() {
        return this.startStrongSubstring;
    }
    
    public String getEndStrongSubstring() {
        return this.endStrongSubstring;
    }
    
    public String getStartWeakSubstring() {
        return this.startWeakSubstring;
    }
    
    public String getEndWeakSubstring() {
        return this.endWeakSubstring;
    }
    
    public String getStartEvenSubstring() {
        return this.startEvenSubstring;
    }
    
    public String getEndEvenSubstring() {
        return this.endEvenSubstring;
    }
    
    public String getStartWellSubstring() {
        return this.startWellSubstring;
    }
    
    public String getEndWellSubstring() {
        return this.endWellSubstring;
    }

    public String getListSubstring () {
        return this.listSubstring;
    }

    public void setListSubstring (String listSubstring) {
        this.listSubstring = listSubstring;
    }

    public String getStartDataSubstrings () {
        return this.startDataSubstring;
    }

    public void setStartDataSubstrings (String listSubstring) {
        this.startDataSubstring = listSubstring;
    }

    public String getEndDataSubstrings () {
        return this.endDataSubstring;
    }

    public void setEndDataSubstrings (String listSubstring) {
        this.endDataSubstring = listSubstring;
    }
    
    public String getStrongDataSubstring () {
        return this.strongDataSubstring;
    }

    public void setStrongDataSubstring (String listSubstring) {
        this.strongDataSubstring = listSubstring;
    }

    public String getWeakDataSubstring () {
        return this.weakDataSubstring;
    }

    public void setWeakDataSubstring (String listSubstring) {
        this.weakDataSubstring = listSubstring;
    }

    public String getEvenDataSubstring () {
        return this.evenDataSubstring;
    }

    public void setEvenDataSubstring (String listSubstring) {
        this.evenDataSubstring = listSubstring;
    }

    public String getWellDataSubstring () {
        return this.wellDataSubstring;
    }

    public void setWellDataSubstring (String listSubstring) {
        this.wellDataSubstring = listSubstring;
    }

    public String getNameDataSubstring () {
        return this.nameDataSubstring;
    }

    public void setNameDataSubstring (String listSubstring) {
        this.nameDataSubstring = listSubstring;
    }

    public String getNameEndDataSubstring () {
        return this.nameEndDataSubstring;
    }

    public String getStartScrapping () {
        return this.startScrapping;
    }

    public String getEndScrapping () {
        return this.endScrapping;
    }

    public String getSoftwareVersion () {
        return this.softwareVersion;
    }

    public void setNameEndDataSubstring (String listSubstring) {
        this.nameEndDataSubstring = listSubstring;
    }

    public boolean special(String fileLine) {
        for (String s: specialSubstrings) {
            if (s.contains(fileLine)) {
                active |= true;
                activeElement = s;
            }
        }
        return active;
    }

}
