/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Path.java
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

/**
 *
 * @author Alberto
 */
public class Path {

    // Paths
    String mainPath = null; // The main directory
    String downloadPath = null; // Directory with the html downloaded from every champion
    
    // WARNING: "downloadSite" should be in another place?
    String downloadList = null; // Web URL with championList.txt file
    String downloadSite = null;; // Web URL where to find the champion data
    String listPath = null; // Directory with the initial list file, and the final list (in map type)
    String informationPath = null; // Directory with the final information files: strong, even... (in map type)
    String personalPath  = null; // Directory with the personal data files: hated ones, loved ones, combos...
    String metaPath= null; // Directory with meta information (data of the last update, use in the check operation)

    //Files
    String dataFile = null;
    String inputFile = null;
    String championListFile = null;
    String weakAgainstFile = null;
    String strongAgainstFile = null;
    String evenAgainstFile = null;
    String worksWellFile = null;
    String generalTipsFile = null;
    String metaFile = null;

    // Persona files
    String hotOnesFile = null;
    String lovedOnesFile = null;
    String hatedOnesFile = null;
    String personalCombosFile = null;

    String pWeakAgainstFile = null;
    String pStrongAgainstFile = null;
    String pEvenAgainstFile = null;
    String pWorksWellFile = null;

    // CONSTRUCTOR
    public Path () {
        // ALL MANUAL FILLS
        this.mainPath = System.getProperty("user.dir").replace("\\", "/") + ("/");


        this.downloadPath = "downloads/"; // Manual fill
        this.listPath = "championList/";
        this.informationPath = "information/";
        this.personalPath = "personal/";
        this.metaPath = "meta/";

        this.downloadList = "http://glimpse-23.blogspot.com.es/p/data.html";
        this.downloadSite = "http://glimpse-23.blogspot.com.es/p/data.html";


        // Name: lolNames.txt
        this.dataFile = "data_html.txt";
        this.championListFile = "championList.txt";
        this.strongAgainstFile = "strongAgainst.txt";
        this.weakAgainstFile = "weakAgainst.txt";
        this.evenAgainstFile = "evenAgainst.txt";
        this.worksWellFile = "worksWell.txt";
        this.metaFile = "metadata.txt";

        this.hotOnesFile = "hotOnes.txt";
        this.lovedOnesFile = "lovedOnes.txt";
        this.hatedOnesFile = "hatedOnes.txt";
        this.personalCombosFile = "combos.txt";

        this.pWeakAgainstFile = "personalWeakAgainst.txt";
        this.pStrongAgainstFile = "personalStrongAgainst.txt";
        this.pEvenAgainstFile = "personalEvenAgainst.txt";
        this.pWorksWellFile = "personalWorksWell.txt";
    }

    // GET METHODS ////////////////////////////////////////////////////////////
    public String getMainPath () {
        return this.mainPath;
    }
    
    public String getDownloadPath () {
        return this.downloadPath;
    }

    public String getDownloadList() {
        return this.downloadList;
    }

    public String getDownloadSite () {
        return this.downloadSite;
    }

    public String getListPath () {
        return this.listPath;
    }

    public String getInformationPath () {
        return this.informationPath;
    }

    public String getMetaPath () {
        return this.metaPath;
    }

    public String getPersonalPath () {
        return this.personalPath;
    }

    public String getDataFile() {
        return this.dataFile;
    }
    public String getChampionListFile () {
        return this.championListFile;
    }

    public String getInputFile () {
        return this.inputFile;
    }

    public String getStrongAgainstFile () {
        return this.strongAgainstFile;
    }

    public String getWeakAgainstFile() {
        return this.weakAgainstFile;
    }

    public String getEvenAgainstFile () {
        return this.evenAgainstFile;
    }

    public String getWorksWellFile () {
        return this.worksWellFile;
    }

    public String getHotOnesFile () {
        return this.hotOnesFile;
    }

    public String getLovedOnesFile () {
        return this.lovedOnesFile;
    }

    public String getHatedOnesFile () {
        return this.hatedOnesFile;
    }

    public String getPersonalCombosFile () {
        return this.personalCombosFile;
    }

    public String getMetaFile() {
        return this.metaFile;
    }

    public String getPersonalStrongAgainst() {
        return this.pStrongAgainstFile;
    }

    public String getPersonalWeakAgainst() {
        return this.pWeakAgainstFile;
    }

    public String getPersonalEvenAgainst() {
        return this.pEvenAgainstFile;
    }

    public String getPersonalWorksWell() {
        return this.pWorksWellFile;
    }

    // SET METHODS ////////////////////////////////////////////////////////////
    public void setInputFile (String inputFile) {
        this.inputFile = inputFile;
    }
    
    public void setChampionListFile (String championList) {
        this.championListFile = championList;
    }

    public void setMainPath (String path) {
        this.mainPath = path;
    }

    public void setDownloadPath (String path) {
        this.downloadPath = path;
    }

    public void setDownloadList(String site) {
        this.downloadList = site;
    }

    public void setDownloadSite (String site) {
        this.downloadSite = site;
    }

    public void setListPath (String path) {
        this.listPath = path;
    }

    public void setInformationPath (String path) {
        this.informationPath = path;
    }

    public void setMetaPath (String path) {
        this.metaPath = path;
    }

    public void setPersonalPath (String path) {
        this.personalPath = path;
    }

    public void setStrongAgainstFile (String path) {
        this.strongAgainstFile = path;
    }

    public void setWeakAgainstFile (String path) {
        this.weakAgainstFile = path;
    }

    public void setEvenAgainstFile (String path) {
        this.evenAgainstFile = path;
    }

    public void setWorksWellFile (String path) {
        this.worksWellFile = path;
    }

    public void setHotOnesFile (String path) {
        this.hatedOnesFile = path;
    }

    public void setLovedOnesFile (String path) {
        this.lovedOnesFile = path;
    }

    public void setHatedOnesFile (String path) {
        this.hatedOnesFile = path;
    }

    public void setPersonalCombosFile (String path) {
        this.personalCombosFile = path;
    }

}
