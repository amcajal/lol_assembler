/*******************************************************************************
*   Project: LoL Assembler
*
*   File: FileWorker.java
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

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Alberto Martin Cajal
 * 2015
 */
public class FileWorker {

    //VARIABLES
    LinkedHashMap<String, String> gatheredData;

    // To avoid read the HTML files 5 times each (1 by each tipe of map), 
    // the class has a specific map for each one. Then, reading 1 time the HTML will
    // generate all the needed data (i.e: read the aatrox.html will generate all the aatrox info: weak, strong...)
    // NOTE: Each new file to use requieres a new map here.
    LinkedHashMap<String, String> strongAgainst;
    LinkedHashMap<String, String> weakAgainst;
    LinkedHashMap<String, String> evenAgainst;
    LinkedHashMap<String, String> worksWell;
    LinkedHashMap<String, String> generalTips;

    String path = "";

    Flags fileType;

    //CONSTRUCTORS
    public FileWorker (String path) {

        /*
        * DESCRIPTION:
        * Stablish the path to the data files and initialize the maps
        */

        this.path = path;

        // DEPRECATED
        this.gatheredData = new LinkedHashMap<String, String>();
        this.strongAgainst = new LinkedHashMap<String, String>();
        this.weakAgainst = new LinkedHashMap<String, String>();
        this.evenAgainst = new LinkedHashMap<String, String>();
        this.worksWell = new LinkedHashMap<String, String>();
        this.generalTips = new LinkedHashMap<String, String>();

    }

    //METHODS
    public void clear() {
        /*
        * DESCRIPTION:
        * Clears the maps for the next gathering operation
        */

        this.gatheredData.clear();
        this.strongAgainst.clear();
        this.weakAgainst.clear();
        this.evenAgainst.clear();
        this.worksWell.clear();
        this.generalTips.clear();
    }

    /*
    public LinkedHashMap getMap() {
        return this.gatheredData;
    }
    */

    public HashMap load (Flags type) {
        /*
        * DESCRIPTION:
        * Returns the gathered data map. This method is intended
        * to load the maps in the Information class
        */

        //LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> result = this.gatheredData;

        // THIS IS UNNECESARY: WITH "load" operation, the method only returns the gatherdData hashmap,
        // but is the Information.load() method who decides where this data will be stored (strong, weak...)
        /*
        switch (type) {

            case LIST:
                result = this.gatheredData;
                break;

            case STRONG:
                result = this.strongAgainst;
                break;

            case WEAK:
                result = this.weakAgainst;
                break;

            case EVEN:
                result = this.evenAgainst;
                break;

            case WELL:
                result = this.worksWell;
                break;

            case TIPS:
                result = this.generalTips;
                break;

            default:
                System.out.println ("Unknow file type to load (fatal error)");
                System.out.println ("No changes has been made");
                break;
        }
         *
         */

        return result;
    }

    // DEPRECATED: THIS WAS THE OLD METHOD TO READ DATA
    /*
    public void oldRead (String fullInputPath, FileType type)
            throws FileNotFoundException, IOException {

         * DESCRIPTION:
         * Reads the input file and store the data in a map


        // VARIABLES
        int counter = 0;
                
        String fileLine = "";
        String tokenizerLine = "";

        Substrings subStrings = new Substrings();

         * subString class contains the "string objetives" needed to gather
         * the necessary information from the input file


        //CODE
        //this.clear(); WE CANNOT CLEAR HERE, CAUSE WE NEED TO MAINTAIN THE INFORMATION OF THE MAPS THROUGHT MANY CALLS
        this.gatheredData.clear();

        FileReader fr = new FileReader(fullInputPath);
        BufferedReader br = new BufferedReader(fr);

        if (type.equals(fileType.MAP)){
            // Exctact the map

            while((fileLine = br.readLine())!=null) {
                StringTokenizer st = new StringTokenizer(fileLine, "\n");
                while (st.hasMoreTokens()) {
                    tokenizerLine = st.nextToken();
                    StringTokenizer st2 = new StringTokenizer(tokenizerLine, ":");
                    while (st2.hasMoreTokens()) {
                        this.gatheredData.put(st2.nextToken(), st2.nextToken());
                    }
                }
            }

        }

        else if (type.equals(fileType.LIST)) {
            // Exctact the list

            while((fileLine = br.readLine())!=null) {
                StringTokenizer st = new StringTokenizer(fileLine, " ");
                while (st.hasMoreTokens()) {
                    tokenizerLine = st.nextToken();
                    if (tokenizerLine.contains(subStrings.getListSubstring())) {
                        fileLine = tokenizerLine.replace(subStrings.getListSubstring(), "");
                        fileLine = fileLine.replace("\"", "");
                        fileLine = fileLine.replace("\'", "");                       
                        // Process special champion strings
                        if (subStrings.special(fileLine)){
                            fileLine = subStrings.create();
                        }
                        // DIRTY solution. Try to find another
                        if (fileLine.contains("Dr.")){
                            fileLine = "Dr-Mundo";
                        }

                        counter++;
                        this.gatheredData.put(fileLine, Integer.toString(counter));
                    }
                }
            }

        }

        else {
            // Open file
            // Iterate the file
            // If a special substring is detected, activate the propper mode
            // If a mode is activate, process it (extract data and store in specific map)

            //Only for HTML processing (strong, weak, even, well data)
            String championObjective = "";
            File f = new File(fullInputPath);
            championObjective = f.getName().replace("_info.html", "");

            String aux="";

            boolean inside = false; // This stablish if loop is inside the data zone of the html

            // This stablish what kind of data is being gathered
            boolean strong = false;
            boolean weak = false;
            boolean even = false;
            boolean well = false;

            while((fileLine = br.readLine())!=null) {

                if (fileLine.contains(subStrings.getEndDataSubstrings())) {
                    break; // When the program reach the end line, exits the loop
                }


                if (!fileLine.contains(subStrings.getStartDataSubstrings()) || inside == false) {
                    continue; // If the line it not the start of the data, ignore it
                }

                inside = true;
                 *


                StringTokenizer st = new StringTokenizer(fileLine, " ");
                while (st.hasMoreTokens()) {
                    tokenizerLine = st.nextToken();

                    // Stablish what kind of map will receive the information
                    if (tokenizerLine.contains(subStrings.getWeakDataSubstring())) {
                        weak = true;
                        strong = false;
                        even = false;
                        well = false;
                    }
                    else if(tokenizerLine.contains(subStrings.getStrongDataSubstring())) {
                        weak = false;
                        strong = true;
                        even = false;
                        well = false;
                    }
                    else if(tokenizerLine.contains(subStrings.getEvenDataSubstring())) {
                        weak = false;
                        strong = false;
                        even = true;
                        well = false;
                    }
                    else if(tokenizerLine.contains(subStrings.getWellDataSubstring())) {
                        weak = false;
                        strong = false;
                        even = false;
                        well = true;
                    }

                    if (tokenizerLine.contains(subStrings.getNameDataSubstring())) {
                        fileLine = tokenizerLine.replace(subStrings.getNameDataSubstring(), "");
                        fileLine = fileLine.replace(subStrings.getNameEndDataSubstring(), "");
                        fileLine = fileLine.replace("\"", "");
                        fileLine = fileLine.replace("\'", "");
                        // Process special champion strings
                        if (subStrings.special(fileLine)){
                            fileLine = subStrings.create();
                        }
                        // DIRTY solution. Try to find another
                        if (fileLine.contains("Dr.")){
                            fileLine = "Dr-Mundo";
                        }

                        if (weak == true) {
                            if (this.weakAgainst.containsKey(championObjective)) {
                                aux = this.weakAgainst.get(championObjective);
                                aux = aux + "&&" + fileLine;
                                this.weakAgainst.put(championObjective, aux);
                                aux = "";
                            }
                            else{
                                this.weakAgainst.put(championObjective, fileLine);
                            }
                        }
                        else if(strong == true) {
                            if (this.strongAgainst.containsKey(championObjective)) {
                                aux = this.strongAgainst.get(championObjective);
                                aux = aux + "&&" + fileLine;
                                this.strongAgainst.put(championObjective, aux);
                                aux = "";
                            }
                            else{
                                this.strongAgainst.put(championObjective, fileLine);
                            }
                        }
                        else if(even == true) {
                            if (this.evenAgainst.containsKey(championObjective)) {
                                aux = this.evenAgainst.get(championObjective);
                                aux = aux + "&&" + fileLine;
                                this.evenAgainst.put(championObjective, aux);
                                aux = "";
                            }
                            else{
                                this.evenAgainst.put(championObjective, fileLine);
                            }
                        }
                        else if(well == true) {
                            if (this.worksWell.containsKey(championObjective)) {
                                aux = this.worksWell.get(championObjective);
                                aux = aux + "&&" + fileLine;
                                this.worksWell.put(championObjective, aux);
                                aux = "";
                            }
                            else{
                                this.worksWell.put(championObjective, fileLine);
                            }
                        }
                    }

                }
            }

            f = null;
        }

        fr.close();
        br.close();
    }
    */

    public void read (String fullInputPath, Flags type) throws FileNotFoundException, IOException {
        /*
         * DESCRIPTION:
         * Reads the input file and store the data in a map
         */

        // VARIABLES
        int counter = 0;

        String fileLine = "";
        String tokenizerLine = "";

        Substrings subStrings = new Substrings();
         /*
         * subString class contains the "string objetives" needed to gather
         * the necessary information from the input file
         */

        //CODE
        //this.clear(); WE CANNOT CLEAR HERE, CAUSE WE NEED TO MAINTAIN THE INFORMATION OF THE MAPS THROUGHT MANY CALLS
        this.gatheredData.clear();

        FileReader fr = new FileReader(fullInputPath);
        BufferedReader br = new BufferedReader(fr);

        if (type.equals(fileType.MAP)){
            // Exctact the map

            while((fileLine = br.readLine())!=null) {
                StringTokenizer st = new StringTokenizer(fileLine, "\n");
                while (st.hasMoreTokens()) {
                    tokenizerLine = st.nextToken();
                    StringTokenizer st2 = new StringTokenizer(tokenizerLine, ":");
                    while (st2.hasMoreTokens()) {
                        this.gatheredData.put(st2.nextToken(), st2.nextToken());
                    }
                }
            }

        }
        else if (type.equals(fileType.LIST)) {
            // Exctact the list
            boolean activeList = false;

            while((fileLine = br.readLine())!=null) {
                if (fileLine.contains(subStrings.getEndListSubstring())){
                    activeList = false;
                    break;
                }
                if (fileLine.contains(subStrings.getStartListSubstring())){
                    activeList = true;
                    continue;
                }
                if (activeList == true) {
                    counter++;
                    this.gatheredData.put(fileLine.replace("<br>", ""), "0"); // The 0 is just to fill the method
                }
            }
        }
        else if (type.equals(fileType.STRONG)) {
            // Exctact the list
            boolean activeStrong = false;

            while((fileLine = br.readLine())!=null) {
                if (fileLine.contains(subStrings.getEndStrongSubstring())){
                    activeStrong = false;
                    break;
                }
                if (fileLine.contains(subStrings.getStartStrongSubstring())){
                    activeStrong = true;
                    continue;
                }
                if (activeStrong == true) {
                    this.strongAgainst.put(fileLine.replace("<br>", ""), "0"); // The 0 is just to fill the method
                }
            }
        }
        else if (type.equals(fileType.WEAK)) {
            // Exctact the list
            boolean activeWeak = false;

            while((fileLine = br.readLine())!=null) {
                if (fileLine.contains(subStrings.getEndWeakSubstring())){
                    activeWeak = false;
                    break;
                }
                if (fileLine.contains(subStrings.getStartWeakSubstring())){
                    activeWeak = true;
                    continue;
                }
                if (activeWeak == true) {
                    this.weakAgainst.put(fileLine.replace("<br>", ""), "0"); // The 0 is just to fill the method
                }
            }
        }
        else if (type.equals(fileType.EVEN)) {
            // Exctact the list
            boolean activeEven = false;

            while((fileLine = br.readLine())!=null) {
                if (fileLine.contains(subStrings.getEndEvenSubstring())){
                    activeEven = false;
                    break;
                }
                if (fileLine.contains(subStrings.getStartEvenSubstring())){
                    activeEven = true;
                    continue;
                }
                if (activeEven == true) {
                    this.evenAgainst.put(fileLine.replace("<br>", ""), "0"); // The 0 is just to fill the method
                }
            }
        }
        else if (type.equals(fileType.WELL)) {
            // Exctact the list
            boolean activeWell = false;

            while((fileLine = br.readLine())!=null) {
                if (fileLine.contains(subStrings.getEndWellSubstring())){
                    activeWell = false;
                    break;
                }
                if (fileLine.contains(subStrings.getStartWellSubstring())){
                    activeWell = true;
                    continue;
                }
                if (activeWell == true) {
                    this.worksWell.put(fileLine.replace("<br>", ""), "0"); // The 0 is just to fill the method
                }
            }
        }

        fr.close();
        br.close();

    }

    // DEPRECATED: THIS WAS THE METHOD USED BY THE OLD VERSION TO WRITE THE FILES
    /*
    public void oldWrite (String fullOutputPath, FileType type, FileStatus fileStatus) throws IOException {

         * DESCRIPTION:
         * Write data in the specified file

        // VARIABLES
        String fileLine = "";

        //CODE
        PrintWriter writer = new PrintWriter(fullOutputPath);

        Iterator it = null;

        switch (type) {

            case LIST:
                fileStatus.setChampionList(true);
                it = this.gatheredData.entrySet().iterator();
                break;

            case STRONG:
                fileStatus.setStrongAgainst(true);
                it = this.strongAgainst.entrySet().iterator();
                break;

            case WEAK:
                fileStatus.setWeakAgainst(true);
                it = this.weakAgainst.entrySet().iterator();
                break;

            case EVEN:
                fileStatus.setEvenAgainst(true);
                it = this.evenAgainst.entrySet().iterator();
                break;

            case WELL:
                fileStatus.setWorksWell(true);
                it = this.worksWell.entrySet().iterator();
                break;

            case TIPS:
                fileStatus.setGeneralTips(true);
                it = this.generalTips.entrySet().iterator();
                break;

            default:
                // Control error
                System.out.println("FATAL ERROR: UNKNOW FILE TYPE WRITED");
            break;
        }

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fileLine = pair.getKey() + ":" + pair.getValue();
            writer.println(fileLine);
            it.remove(); // avoids a ConcurrentModificationException
        }

        writer.close();
    }
    */

    public void write (String fullOutputPath, Flags type, FileStatus fileStatus) throws IOException {

        /*
         * DESCRIPTION:
         * Write data in the specified file
         */

        // VARIABLES
        String fileLine = "";

        //CODE
        PrintWriter writer = new PrintWriter(fullOutputPath);

        Iterator it = null;

        switch (type) {

            case LIST:
                fileStatus.setChampionList(true);
                it = this.gatheredData.entrySet().iterator();
                break;

            case STRONG:
                fileStatus.setStrongAgainst(true);
                it = this.strongAgainst.entrySet().iterator();
                break;

            case WEAK:
                fileStatus.setWeakAgainst(true);
                it = this.weakAgainst.entrySet().iterator();
                break;

            case EVEN:
                fileStatus.setEvenAgainst(true);
                it = this.evenAgainst.entrySet().iterator();
                break;

            case WELL:
                fileStatus.setWorksWell(true);
                it = this.worksWell.entrySet().iterator();
                break;

            case TIPS:
                fileStatus.setGeneralTips(true);
                it = this.generalTips.entrySet().iterator();
                break;

            default:
                // Control error
                System.out.println("FATAL ERROR: UNKNOW FILE TYPE WRITED");
            break;
        }

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fileLine = pair.getKey() + "";
            writer.println(fileLine);
            it.remove(); // avoids a ConcurrentModificationException
        }

        writer.close();
    }


}
