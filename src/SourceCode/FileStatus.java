/*******************************************************************************
*   Project: LoL Assembler
*
*   File: FileStatus.java
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Alberto
 */
public class FileStatus {

    /* FileStatus keeps information about the gathered information.
     * I.e: if class Information has already the "championList" data,
     * the value of championList boolean will be true, so specific methods
     * can access that information without problems. If the program did not
     * gathered the champion list yet, the boolean will be false, avoiding
     * specific methods to work (for example, download the data bases for
     * every champion, because if the program does not have all the champion names,
     * it is not able to create the correct URLs)
     */

    //VARIABLES
    boolean championList;
    boolean championData;
    boolean strongAgainst;
    boolean weakAgainst;
    boolean evenAgainst;
    boolean worksWell;
    boolean generalTips; // ????????????????????????????????????????
    boolean hotOnes;
    boolean lovedOnes;
    boolean hatedOnes;
    boolean personalCombos;

    boolean personalStrong;
    boolean personalWeak;
    boolean personalEven;
    boolean personalWell;


    boolean ready; // This variable, when its true, lets the program start the "ban-picks" phase
    boolean readyPersonalFiles; // This variable can be false, but in that case, the program will lack of certain data
    boolean updateStatus;

    String message;

    MetaInf meta;

    //CONSTRUCTOR
    public FileStatus() {
        championList = false;
        championData = false;
        strongAgainst = false;
        weakAgainst = false;
        evenAgainst = false;
        worksWell = false;
        generalTips = false; // ??????????????????????????????????????
        hotOnes = false;
        lovedOnes = false;
        hatedOnes = false;
        personalCombos = false;

        personalStrong = false;
        personalWeak = false;
        personalEven = false;
        personalWell = false;

        ready = false;
        readyPersonalFiles = false;
        updateStatus = false;

        message = "";

        meta = new MetaInf();
    }

    public void initialCheck(Path path, int totalFiles, int totalPersonalFiles) throws IOException {

//        File theDir = new File(directoryName);
//
// if the directory does not exist, create it
//        if (!theDir.exists())
//        {
//            System.out.println("creating directory: " + directoryName);
//            theDir.mkdir();
//        }

        //VARIABLES
        File downloadDir = new File(path.getMainPath() + path.getDownloadPath());
        File listDir = new File(path.getMainPath() + path.getListPath());
        File listFile = new File(path.getMainPath() + path.getListPath() + path.getChampionListFile());
        File dataFile = new File(path.getMainPath() + path.getDownloadPath() + path.getDataFile());
        File informationDir = new File(path.getMainPath() + path.getInformationPath());
        File metaDir = new File(path.getMainPath() + path.getMetaPath());
        File metaFile = new File (path.getMainPath() + path.getMetaPath() + path.getMetaFile());

        // Personal files with personal information (hot ones, hated ones, loved ones and personal combos)
        File personalDir = new File(path.getMainPath() + path.getPersonalPath());

        int nChampions = 0;
        boolean update = false;
        boolean personalUpdate = false;

        String fileLine = "";

        // CODE
        this.cleanMessage();
        this.putHeaderMessage();

        //// CHECK NEW VERSION OF THE SOFTWARE AND LAST UPDATE OPERATION ////////////////////
        if (metaDir.exists()) {
            this.checkSoftwareVersion(path);

            if (metaFile.exists()) {
                this.checkLastUpdate(path);
            }
            
        }
        /////////////////////////////////////////////////////////////////////////////////////////
        this.message = this.message + (" CHECKING DIRECTORIES AND FILE STATUS...\n\n");
        
        // Only error messages will be printed
        if (listDir.exists()) {
            //System.out.println("List directory found : SUCCESSUL");
            //this.message = this.message + ("List directory found : SUCCESSUL") + "\n";
            if (listFile.isFile()) { // This is enought?
                // I mean: I'm cheking if this directory has a file, but i need to check if the file
                // has the proper name (and if im able to, if the file has the correct data in the correct format)
                //System.out.println("Champion list file found : SUCCESSUL");
                //this.message = this.message + ("Champion list file found : SUCCESSUL") + "\n";
                
                // Count number of lines to know how many champions are
                // DIRTY SOLUTION: FIND ANOTHER
                String fullPath = path.getMainPath() + path.getListPath() + path.getChampionListFile();
                BufferedReader br = new BufferedReader(new FileReader(fullPath));

                while((fileLine = br.readLine())!=null) {
                    StringTokenizer st = new StringTokenizer(fileLine, "\n");
                    while (st.hasMoreTokens()) {
                       nChampions++;
                       st.nextToken();
                    }
                }

                this.setChampionList(true);
                br.close();
            }
             else {
                //System.out.println("Champion list file not found : FATAL ERROR");
                this.message = this.message + ("        Champion list file not found : ERROR") + "\n";
                update = update | true;
             }
        }
        else {
            //System.out.println("List directory not found : FATAL ERROR");
            this.message = this.message + ("        List directory not found : ERROR") + "\n";
            update = update | true;
        }

        if (downloadDir.exists()) {
            //System.out.println("Download directory found : SUCCESSUL");
            //this.message = this.message + ("Download directory found : SUCCESSUL") + "\n";
            // DEPRECATED
//            if ((downloadDir.list().length) == nChampions) {
//                //System.out.println("Total download files found : SUCCESSUL");
//                this.message = this.message + ("Total download files found : SUCCESSUL") + "\n";
//                this.setChampionData(true);
//            }
             if (dataFile.isFile()) {
                //this.message = this.message + ("Download files found : SUCCESSUL") + "\n";
                this.setChampionData(true);

             }
             else {
                //System.out.println("Missing downloaded files : FATAL ERROR");
                this.message = this.message + ("        Missing downloaded files : ERROR") + "\n";
                update = update | true;
             }
        }
        else {
            //System.out.println("Download directory not found : FATAL ERROR");
            this.message = this.message + ("        Download directory not found : ERROR") + "\n";
            update = update | true;
        }


        // THIS SECTION CAN BE MORE ACCURATE: CHEKING EVERY FILE IN A INDIVIDUAL WAY
        // I.e: check if strongAgains exist, check if WeakAgainst exist...
        if (informationDir.exists()) {
            //System.out.println("Information directory found : SUCCESSUL");
            //this.message = this.message + ("Information directory found : SUCCESSUL") + "\n";
            if ((informationDir.list().length) == totalFiles) { // MODIFY THIS VALUE MANUALLY
                //System.out.println("Total information files found : SUCCESSUL");
                //this.message = this.message + ("Total information files found : SUCCESSUL") + "\n";
                this.setStrongAgainst(true);
                this.setWeakAgainst(true);
                this.setEvenAgainst(true);
                this.setWorksWell(true);
                this.setGeneralTips(true);
            }
             else {
                //System.out.println("Missing information files : FATAL ERROR");
                this.message = this.message + ("        Missing information files : ERROR") + "\n";
                update = update | true;
             }
        }
        else {
            //System.out.println("Information directory not found : FATAL ERROR");
            this.message = this.message + ("        Information directory not found : ERROR") + "\n";
            update = update | true;
        }

        // THIS SECTION CAN BE MORE ACCURATE: CHEKING EVERY FILE IN A INDIVIDUAL WAY
        // I.e: check if hotOnes exist, personalCombos exist, etc...
        if (personalDir.exists()) {
            //this.message = this.message + ("Personal directory found : SUCCESSUL") + "\n";
            if ((personalDir.list().length) == totalPersonalFiles) { // MODIFY THIS VALUE MANUALLY
                //System.out.println("Total information files found : SUCCESSUL");
                //this.message = this.message + ("Personal  files found : SUCCESSUL") + "\n";

                this.setHotOnes(true);
                this.setLovedOnes(true);
                this.setHatedOnes(true);
                this.setPersonalCombos(true);

                this.setPersonalStrong(true);
                this.setPersonalWeak(true);
                this.setPersonalEven(true);
                this.setPersonalWell(true);
            }
             else {
                //System.out.println("Missing information files : FATAL ERROR");
                this.message = this.message + ("        Missing personal files : DELETED OR EMPTY") + "\n";
                personalUpdate = personalUpdate | true;
             }
        }
        else {
            //System.out.println("Information directory not found : FATAL ERROR");
            this.message = this.message + ("        Personal directory not found : ERROR") + "\n";
            update = update | true;
        }


        if (metaDir.exists()) {
            //this.message = this.message + ("Metadata directory found : SUCCESSUL") + "\n";
            if (metaFile.isFile()) { // MODIFY THIS VALUE MANUALLY
                //System.out.println("Total information files found : SUCCESSUL");
                //this.message = this.message + ("Metadata  file found : SUCCESSUL") + "\n";
            }
            else {
                //System.out.println("Missing information files : FATAL ERROR");
                this.message = this.message + ("        Missing metadata file : DELETED OR EMPTY") + "\n";
                personalUpdate = personalUpdate | true;
            }
        }
        else {
            this.message = this.message + ("        Metadata directory not found : ERROR") + "\n";
            update = update | true;
        }

        this.message = this.message + "\n >>>>> LoL Status >>>>>\n";

        if (update == true){
            this.message = this.message + ("        ERRORS ENCOUNTERED. Press UPDATE button to solve them.\n\n");
        }
        else {
            if ( personalUpdate == true) {
                this.message = this.message + ("        There are some empty files, but still OK. READY TO PLAY.\n\n");
            }
            else {
                this.message = this.message + ("        All is OK. READY TO PLAY.\n\n");
            }
        }
        this.message = this.message + (" -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");
        
        updateStatus = update;
        // If updateStatus = true, the program need to be updated
        // If updateStatus = false, the program is ok

        // This is a dirty solution, but it works
        // The personal files can be empty or lost, does not matter. ??????????????
        if ((this.championList == true) && (this.championData == true) &&
                (this.strongAgainst == true) && (this.weakAgainst == true)
                && (this.evenAgainst == true) && (this.worksWell == true)) {
                this.ready = true;
        }

        //File downloadDir = new File(path.getMainPath() + path.getDownloadPath());
        //File listDir = new File(path.getMainPath() + path.getListPath());
        //File listFile = new File(path.getMainPath() + path.getListPath() + path.getChampionListFile());
        //File informationDir = new File(path.getMainPath() + path.getInformationPath());

        downloadDir = null;
        listDir = null;
        listFile = null;
        informationDir = null;
        personalDir = null;

    }

    /*
     * 
    public void correctErrors () {
        //Download missing files, or create unexistant directories
     * // To do this, first we must keep track of the errors
     * // i.e: has ChampionList file generate an error? Then, download it calling Donwlader class...
    }
     */

    public void cleanAndCreate (Path path) throws IOException{
        /* Delete downloads/, championList/ and information/ directories (with all the data);
         * Then, creates again the directories, making them clean and ready to start again
         */

        File championDir = new File(path.getMainPath() + path.getListPath());
        //File championFile = new File(path.getMainPath() + path.getListPath() + path.getChampionListFile());
        File downloadDir = new File(path.getMainPath() + path.getDownloadPath());
        File informationDir = new File(path.getMainPath() + path.getInformationPath());
        File personalDir = new File(path.getMainPath() + path.getPersonalPath());
        File metaDir = new File(path.getMainPath() + path.getMetaPath());
        File metaFile = new File(path.getMainPath() + path.getMetaPath() + path.getMetaFile());

        // Now for personal files
        File hotFile = new File(path.getMainPath() + path.getPersonalPath() + path.getHotOnesFile());
        File hatedFile = new File(path.getMainPath() + path.getPersonalPath() + path.getHatedOnesFile());
        File lovedFile = new File(path.getMainPath() + path.getPersonalPath() + path.getLovedOnesFile());
        File combosFile = new File(path.getMainPath() + path.getPersonalPath() + path.getPersonalCombosFile());

        File personalStrongFile = new File(path.getMainPath() + path.getPersonalPath()
                + path.getPersonalStrongAgainst());
        File personalWeakFile = new File(path.getMainPath() + path.getPersonalPath()
                + path.getPersonalWeakAgainst());
        File personalEvenFile = new File(path.getMainPath() + path.getPersonalPath()
                + path.getPersonalEvenAgainst());
        File personalWellFile = new File(path.getMainPath() + path.getPersonalPath()
                + path.getPersonalWorksWell());

        this.cleanMessage();

        //championFile.delete();
        
        if (championDir.exists()) {
            FileUtils.deleteDirectory(championDir);
        }

        if (downloadDir.exists()) {
            FileUtils.deleteDirectory(downloadDir);
        }

        if (informationDir.exists()) {
            FileUtils.deleteDirectory(informationDir);
        }

        if (metaDir.exists()) {
            FileUtils.deleteDirectory(metaDir);
        }


        // The personal directory is not deleted, because the personal information cant be scrapped from the web
        if (!personalDir.exists()) {
            personalDir.mkdir();

            hotFile.createNewFile();
            hatedFile.createNewFile();
            lovedFile.createNewFile();
            combosFile.createNewFile();

            personalStrongFile.createNewFile();
            personalWeakFile.createNewFile();
            personalEvenFile.createNewFile();
            personalWellFile.createNewFile();
        }
        

        championDir.mkdir();
        downloadDir.mkdir();
        informationDir.mkdir();
        metaDir.mkdir();
        metaFile.createNewFile();

        championDir = null;
        downloadDir = null;
        informationDir = null;
        metaDir = null;
        metaFile = null;

        personalDir = null;

        hotFile = null;
        hatedFile = null;
        lovedFile = null;
        combosFile = null;

        personalStrongFile = null;
        personalWeakFile = null;
        personalEvenFile = null;
        personalWellFile = null;
    }

    public void cleanMessage () {
        this.message= "";
    }

    public void checkSoftwareVersion(Path path) throws FileNotFoundException, IOException {
        String fileLine, line;
        Substrings st = new Substrings();
        Double version = 0.0;

        FileReader fr = new FileReader(path.getMainPath() + path.getDownloadPath() + path.getDataFile());
        BufferedReader br = new BufferedReader(fr);


        while((fileLine = br.readLine())!=null) {
            if (fileLine.contains(st.getSoftwareVersion())) {
                line = fileLine.replace("<div id=\"software_version=", "");
                line = line.replace("\"></div>", "");
                version = Double.parseDouble(line);
                break; // END LOOP
            }
        }

        br.close();
        fr.close();

        br = null;
        fr = null;

        if (version == this.meta.getSV()) {
            this.message = this.message + (" SOFTWARE IS UP TO DATE:\n      Version " + version + "\n\n");
        }
        else {
            this.message = this.message + (" THERE IS A NEW VERSION OF SOFTWARE:\n      VERSION " + version + "\n");
            this.message = this.message + (" DOWNLOAD IT FROM http://glimpse-23.blogspot.com.es/ \n\n");
        }
    }

    public void checkLastUpdate(Path path) throws FileNotFoundException, IOException {
        // Use metadata.txt to check when the last update operaten took place

        String fileLine;

        FileReader fr = new FileReader(path.getMainPath() + path.getMetaPath() + path.getMetaFile());
        BufferedReader br = new BufferedReader(fr);

        this.message = this.message + (" LAST UPDATE (Day-Month-Year; Hour-Minute-Second)\n");
        while((fileLine = br.readLine())!=null) {
            this.message = this.message + ("        " + fileLine + "\n\n");
        }

        br.close();
        fr.close();

        br = null;
        fr = null;
    }

    public void updateDate (Path path) throws FileNotFoundException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  -  HH:mm:ss");
        Date date = new Date();
        dateFormat.format(date);
        //System.out.println(dateFormat.format(date));

        FileWriter fstream = new FileWriter(path.getMainPath() + path.getMetaPath() + path.getMetaFile(), true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(dateFormat.format(date));

        out.close();
        fstream.close();

        out = null;
        fstream = null;
    }
    
    //GET-METHODS ////////////////////////////////////////////////////////////
    public boolean getChampionList () {
        return this.championList;
    }

    public boolean getChampionData () {
        return this.championData;
    }

    public boolean getStrongAgainst () {
        return this.strongAgainst;
    }

    public boolean getWeakAgainst () {
        return this.weakAgainst;
    }

    public boolean getEvenAgainst () {
        return this.evenAgainst;
    }

    public boolean getWorksWell () {
        return this.worksWell;
    }

    public boolean getGeneralTips () {
        return this.generalTips;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getReady () {
        return this.ready;
    }
    
    public boolean getUpdateStatus () {
        return this.updateStatus;
    }

    //SET METHODS ////////////////////////////////////////////////////////////
    public void setChampionList (boolean signal) {
        this.championList = signal;
    }

    public void setChampionData (boolean signal) {
        this.championData = signal;
    }

    public void setStrongAgainst (boolean signal) {
        this.strongAgainst = signal;
    }

    public void setWeakAgainst (boolean signal) {
        this.weakAgainst = signal;
    }

    public void setEvenAgainst (boolean signal) {
        this.evenAgainst = signal;
    }

    public void setWorksWell (boolean signal) {
        this.worksWell = signal;
    }

    public void setGeneralTips (boolean signal) {
        this.generalTips = signal;
    }

    public void setHotOnes (boolean signal) {
        this.hotOnes = signal;
    }

    public void setLovedOnes (boolean signal) {
        this.lovedOnes = signal;
    }

    public void setHatedOnes (boolean signal) {
        this.hatedOnes = signal;
    }

    public void setPersonalCombos (boolean signal) {
        this.personalCombos = signal;
    }

    public void setPersonalStrong(boolean signal) {
        this.personalStrong = signal;
    }

    public void setPersonalWeak(boolean signal) {
        this.personalWeak = signal;
    }

    public void setPersonalEven (boolean signal) {
        this.personalEven = signal;
    }

    public void setPersonalWell(boolean signal) {
        this.personalWell = signal;
    }

    public void setMessage (String m) {
        this.message = m;
    }

    private void putHeaderMessage() {
        this.message = this.message + (" -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n\n");
    }

}
