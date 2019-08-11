/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Downloader.java
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
//import java.util.LinkedHashMap;
//import javax.swing.JTextArea;

/**
 *
 * @author Alberto
 */
public class Downloader {
    
    // Variables
    String urlList;
    String urlData;
    URL fullUrl;
    InputStream is = null;
    BufferedReader br;
    PrintWriter writer;

    //Constructor
    public Downloader (String list, String data) throws MalformedURLException {
        this.urlList = list;
        this.urlData = data;
    }

    public void downloadData (FileStatus fileStatus, String downloadPath) throws MalformedURLException, IOException {
        //VARIABLES
        String line = "";
        Substrings st = new Substrings();
        boolean active = false;

        //CODE
        this.fullUrl = new URL(this.urlList);
        this.is = this.fullUrl.openStream();
        this.br = new BufferedReader(new InputStreamReader(is));
        this.writer = new PrintWriter(downloadPath + "data_html.txt");

        while ((line = br.readLine()) != null) {
            if (line.contains(st.endScrapping)) {
                break;
            }
            if (line.contains(st.getStartScrapping())) {
                active = true;
            }
            if (active==true) {
                writer.println(line);
            }
        }

        is.close();
        writer.close();
        br.close();

        // Avoiding problems declaratin variables again.
        this.fullUrl = null;
        this.is = null;
        this.br = null;
        this.writer = null;
    }

    // DEPRECATED: THIS WAS THE OLD DOWNLOAD METHOD
    /*
    public void oldDownload (FileStatus fileStatus, String downloadPath, LinkedHashMap<String, String> championList, JTextArea mainTextArea) throws MalformedURLException, IOException {

        //VARIABLES
        String line = "";
        int counter, index = 0;

        //CODE
        if (fileStatus.getChampionList() == true) {

            counter = championList.size();
            for (String champion : championList.keySet()) {

                index ++;
                this.fullUrl = new URL(this.urlData + champion);
                this.is = this.fullUrl.openStream();
                this.br = new BufferedReader(new InputStreamReader(is));
                this.writer = new PrintWriter(downloadPath + champion + "_info.html");

                System.out.println ("Downloading data of champion: " + champion
                        + "; (" + index + " out of " + counter + ")");
                mainTextArea.setText(mainTextArea.getText() + "Downloading data of champion: " + champion
                        + "; (" + index + " out of " + counter + ")\n");
                mainTextArea.update(mainTextArea.getGraphics());

                mainTextArea.setText("DOWNLOAD IN PROGRESS. PLEASE WAIT...\n\n"
                        + "Downloading data of champion: " + champion
                        + "; (" + index + " out of " + counter + ")\n");
                mainTextArea.update(mainTextArea.getGraphics());

                while ((line = br.readLine()) != null) {
                    writer.println(line);
                }

                is.close();
                writer.close();
                br.close();

                // Avoiding problems declaratin variables again.
                this.fullUrl = null;
                this.is = null;
                this.br = null;
                this.writer = null;

            }

            fileStatus.setChampionData(true);
            
        }
        else {
            System.out.println("Champion list file is not ready yet.");
            System.out.println("Unable to obtain the champion data.");
        }
    }
    */
    
}
