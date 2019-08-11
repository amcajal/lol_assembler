/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Ranked_UI.java
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


package UI;

import SourceCode.AutoCompletion;
import SourceCode.Flags;
import SourceCode.Games;
import SourceCode.Information;
import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.text.DefaultCaret;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Alberto
 */
public class Ranked_UI extends javax.swing.JFrame {

    Information information;
    Flags starts;
    Flags next;
    Games rankedGame;

    Color originalC;

    Map<String, Double> communityDataToPrint; // From community data files
    Map<String, Double> personalDataToPrint; // From personal data files
    Map<String, Double> combosDataToPrint; // From personal data files
    Map<String, Double> combosDataToPrintPersonal; // From personal data files

    int rankedTotalPicks; // Total champions already picked (max value = 10)
    int rankedTotalBans; // Total champions already banned (max value = 6)
    int totalPicks; // Number of picks
    
    boolean firstPickRound; // This variable establish if the allies perform the first pick or not.
    
    int roundIndex;
    //Flags [] round;
    String [] round;
    
    int infoMode; // 0 to show hot and loved; 1 to show hot and hated

    /** Creates new form Ranked_UI */
    public Ranked_UI(Information info, Flags type, Flags start, Flags playerNumber) {
        initComponents();

        Image i;
        try {
            i = ImageIO.read(getClass().getResource("/Images/lol_assembler_icon.png"));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(LoL_assembly_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Color bgc = new Color(5,23,37);
        getContentPane().setBackground(bgc);
        
        jTextArea1.setMargin(new Insets(15,25,25,25)); // Apply margins
        jTextArea1.setEditable(false);
        DefaultCaret caret1 = (DefaultCaret)jTextArea1.getCaret(); // To avoid the automatic scrolling
        caret1.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        rankedCombotextArea.setMargin(new Insets(15,15,25,25)); // Apply margins
        rankedCombotextArea.setEditable(false);
        DefaultCaret caret2 = (DefaultCaret)rankedCombotextArea.getCaret(); // To avoid the automatic scrolling
        caret2.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        alliesBans.setMargin(new Insets(15,15,25,25)); // Apply margins
        alliesBans.setEditable(false);
        
        enemiesBans.setMargin(new Insets(15,15,25,25)); // Apply margins
        enemiesBans.setEditable(false);
        
        alliesPicks.setMargin(new Insets(15,15,25,25)); // Apply margins
        alliesPicks.setEditable(false);
        
        enemiesPicks.setMargin(new Insets(15,15,25,25)); // Apply margins
        enemiesPicks.setEditable(false);

        this.setTitle ("Ranked Game - LoL Assembler");

        this.information = info;

        roundIndex = -1;
        round = new String [11]; // A total of 10 turns, 1 more for caution
                
        Iterator it = information.getChampionList().entrySet().iterator();
        //Iterator it = info.getChampionList().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            jComboBox1.addItem(pair.getKey());
        }

        // Autocompletion
        jComboBox1.setEditable(true);
        AutoCompletion.enable(jComboBox1);

        pickBanButton.setText("BAN");
        //dataToPrint = new HashMap<String, Double>();

        rankedGame = new Games(this.information, type, playerNumber);
        
        if (playerNumber.equals(Flags.VS3)) {
            totalPicks = 6;
        }
        else {
            totalPicks = 10;
        }
        
        infoMode = 1; // First, hot and hated are shown.
        
        starts = start;
        this.initializeRounds(start);
        originalC = alliesMessage.getBackground();

        rankedTotalPicks = 0; // Total champions already picked (max value = 10)
        rankedTotalBans = 0; // Total champions already banned (max value = 6)

        alliesPicks.setText("                           ALLY PICKS \n--------------------" +
                "--------------------------------------\n");
        alliesBans.setText("                            ALLY BANS \n--------------------" +
                "--------------------------------------\n");

        enemiesPicks.setText("                          ENEMY PICKS \n--------------------" +
                "--------------------------------------\n");
        enemiesBans.setText("                           ENEMY BANS \n--------------------" +
                "--------------------------------------\n");

        alliesMessage.setOpaque(true); // Enable the background painting
        enemiesMessage.setOpaque(true); // Enable the background painting
        jTextArea1.setCaretPosition(0); // Starts at the top in the jTextArea (so you must scroll down)
        
        // DEPENDS ON THE starts value, show certain information
        if (starts.equals(starts.ALLYSTART)) {
            //alliesMessage.setForeground(Color.orange);
            alliesMessage.setBackground(Color.orange);
            alliesMessage.setText("Allies BAN turn");
            enemiesMessage.setBackground(Color.gray);
            enemiesMessage.setText("");

            //dataToPrint.putAll(this.rankedGame.rankedCalculateBanScore(null));
            if (this.information.getPersonal() == true) {
                personalDataToPrint = this.rankedGame.rankedCalculateBanScorePersonal();
            }

            if (this.information.getCommunity() == true) {
                communityDataToPrint = this.rankedGame.rankedCalculateBanScore();
            }
            
            this.printData(0);
            this.printAllCombos();

            if (this.information.getPersonal() == true) {
                personalDataToPrint.clear();
            }

            if (this.information.getCommunity() == true) {
                communityDataToPrint.clear();
            }

            next = next.ENEMYSTART;
            firstPickRound = true;
        }
        else { // starts.equals to ENEMYSTART
            enemiesMessage.setBackground(Color.orange);
            enemiesMessage.setText("Enemies BAN turn");
            alliesMessage.setBackground(Color.gray);
            alliesMessage.setText("");

            this.printData(1);
            rankedCombotextArea.setText("");

            next = next.ALLYSTART;
            firstPickRound = false;
        }
        
        setDefaultCloseOperation(Info_UI.DISPOSE_ON_CLOSE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox();
        pickBanButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        alliesBans = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        enemiesBans = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        enemiesPicks = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        alliesPicks = new javax.swing.JTextArea();
        alliesMessage = new javax.swing.JLabel();
        enemiesMessage = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        rankedCombotextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 23, 37));

        jTextArea1.setBackground(new java.awt.Color(39, 40, 34));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 0));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        pickBanButton.setBackground(new java.awt.Color(184, 8, 12));
        pickBanButton.setFont(new java.awt.Font("Tahoma", 1, 11));
        pickBanButton.setForeground(new java.awt.Color(255, 255, 255));
        pickBanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickBanButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ALLIES");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ENEMIES");

        alliesBans.setBackground(new java.awt.Color(39, 40, 34));
        alliesBans.setColumns(20);
        alliesBans.setForeground(new java.awt.Color(255, 255, 0));
        alliesBans.setRows(5);
        jScrollPane1.setViewportView(alliesBans);

        enemiesBans.setBackground(new java.awt.Color(39, 40, 34));
        enemiesBans.setColumns(20);
        enemiesBans.setForeground(new java.awt.Color(255, 255, 0));
        enemiesBans.setRows(5);
        jScrollPane3.setViewportView(enemiesBans);

        enemiesPicks.setBackground(new java.awt.Color(39, 40, 34));
        enemiesPicks.setColumns(20);
        enemiesPicks.setForeground(new java.awt.Color(255, 255, 0));
        enemiesPicks.setRows(5);
        jScrollPane4.setViewportView(enemiesPicks);

        alliesPicks.setBackground(new java.awt.Color(39, 40, 34));
        alliesPicks.setColumns(20);
        alliesPicks.setForeground(new java.awt.Color(255, 255, 0));
        alliesPicks.setRows(5);
        jScrollPane5.setViewportView(alliesPicks);

        alliesMessage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        alliesMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        enemiesMessage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        enemiesMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        rankedCombotextArea.setBackground(new java.awt.Color(39, 40, 34));
        rankedCombotextArea.setColumns(20);
        rankedCombotextArea.setForeground(new java.awt.Color(255, 255, 0));
        rankedCombotextArea.setRows(5);
        jScrollPane6.setViewportView(rankedCombotextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alliesMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pickBanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 84, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(enemiesMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pickBanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(enemiesMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(alliesMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pickBanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickBanButtonActionPerformed
        // TODO add your handling code here:

        if (this.rankedTotalPicks < this.totalPicks) { // When total picks reach 10 or 6 (depends on the mode), the teams are complete
        //if (this.rankedTotalBans != 6) {

            String champion = jComboBox1.getSelectedItem().toString().replace(" ", "");

            if (this.rankedTotalBans < 6) { // BANS phase
                
                if (this.next.equals(next.ENEMYSTART)) { // After push the button, is enemy turn
                    alliesBans.setText(alliesBans.getText() + champion + "\n");
                    this.rankedGame.addBan(champion);
                    this.rankedGame.addAllyBan(champion);
                    this.rankedTotalBans++;

                    enemiesMessage.setBackground(Color.orange);
                    enemiesMessage.setText("Enemies BAN turn");
                    alliesMessage.setBackground(Color.gray);
                    alliesMessage.setText("");

                    this.printData(1);
                    rankedCombotextArea.setText("");

                    next = next.ALLYSTART;
                }
                else { // After push the button, is ally turn
                    enemiesBans.setText(enemiesBans.getText() + champion + "\n");
                    this.rankedGame.addBan(champion);
                    this.rankedGame.addEnemyBan(champion);
                    this.rankedTotalBans++;

                    alliesMessage.setBackground(Color.orange);
                    alliesMessage.setText("Allies BAN turn");
                    enemiesMessage.setBackground(Color.gray);
                    enemiesMessage.setText("");

                    if (this.information.getPersonal() == true) {
                        personalDataToPrint = this.rankedGame.rankedCalculateBanScorePersonal();
                    }

                    if (this.information.getCommunity() == true) {
                        communityDataToPrint = this.rankedGame.rankedCalculateBanScore();
                    }

                    this.printData(0);
                    this.printAllCombos();

                    if (this.information.getPersonal() == true) {
                        personalDataToPrint.clear();
                    }

                    if (this.information.getCommunity() == true) {
                        communityDataToPrint.clear();
                    }

                    next = next.ENEMYSTART;

                }
            } 
            else { // PICKS phase
                
                if (this.round[roundIndex].equals("A")) {
                    // ADD TO ALLY
                    alliesPicks.setText(alliesPicks.getText() + champion + "\n\n");
                    this.rankedGame.addPick(champion);
                    this.rankedGame.addAllyPick(champion);
                    this.rankedTotalPicks++;
                    
                    // PREPARE ALLY
                    alliesMessage.setBackground(Color.orange);
                    alliesMessage.setText("Allies PICK turn");
                    enemiesMessage.setBackground(Color.gray);
                    enemiesMessage.setText("");
                    
                    if (this.information.getPersonal() == true) {
                        personalDataToPrint = this.rankedGame.rankedCalculatePickScorePersonal();
                        combosDataToPrintPersonal= this.rankedGame.rankedCalculateCombosPersonal();
                    }

                    if (this.information.getCommunity() == true) {
                        communityDataToPrint = this.rankedGame.rankedCalculatePickScore();
                        combosDataToPrint= this.rankedGame.rankedCalculateCombos();
                    }

                    this.printData(0);
                    this.printCombos();

                    if (this.information.getPersonal() == true) {
                        if ((personalDataToPrint != null) && (!personalDataToPrint.isEmpty())) {
                            personalDataToPrint.clear();
                        }

                        if ((combosDataToPrintPersonal != null) && (!combosDataToPrintPersonal.isEmpty())) {
                            combosDataToPrintPersonal.clear();
                        }
                    }

                    if (this.information.getCommunity() == true) {
                        if ((communityDataToPrint != null) && (!communityDataToPrint.isEmpty())) {
                            communityDataToPrint.clear();
                        }

                        if ((combosDataToPrint != null) && (!combosDataToPrint.isEmpty())) {
                            combosDataToPrint.clear();
                        }

                    }

                }
                else if (this.round[roundIndex].equals("B")) {
                    // ADD TO ALLY
                    alliesPicks.setText(alliesPicks.getText() + champion + "\n\n");
                    this.rankedGame.addPick(champion);
                    this.rankedGame.addAllyPick(champion);
                    this.rankedTotalPicks++;
                    
                    // PREPARE ENEMY
                    enemiesMessage.setBackground(Color.orange);
                    enemiesMessage.setText("Enemies PICK turn");
                    alliesMessage.setBackground(Color.gray);
                    alliesMessage.setText("");

                    this.printData(1);
                    rankedCombotextArea.setText("");
                    
                    
                }
                else if (this.round[roundIndex].equals("C")) {
                    // ADD TO ENEMY
                    enemiesPicks.setText(enemiesPicks.getText() + champion + "\n\n");
                    this.rankedGame.addPick(champion);
                    this.rankedGame.addEnemyPick(champion);
                    this.rankedTotalPicks++;
                    
                    // PREPARE ENEMY
                    enemiesMessage.setBackground(Color.orange);
                    enemiesMessage.setText("Enemies PICK turn");
                    alliesMessage.setBackground(Color.gray);
                    alliesMessage.setText("");

                    this.printData(1);
                    rankedCombotextArea.setText("");
                    
                    
                }
                else if (this.round[roundIndex].equals("D")) {
                    // ADD TO ENEMY
                    enemiesPicks.setText(enemiesPicks.getText() + champion + "\n\n");
                    this.rankedGame.addPick(champion);
                    this.rankedGame.addEnemyPick(champion);
                    this.rankedTotalPicks++;
                    
                    // PREPARE ALLY
                    alliesMessage.setBackground(Color.orange);
                    alliesMessage.setText("Allies PICK turn");
                    enemiesMessage.setBackground(Color.gray);
                    enemiesMessage.setText("");
                    
                    if (this.information.getPersonal() == true) {
                        personalDataToPrint = this.rankedGame.rankedCalculatePickScorePersonal();
                        combosDataToPrintPersonal= this.rankedGame.rankedCalculateCombosPersonal();
                    }

                    if (this.information.getCommunity() == true) {
                        communityDataToPrint = this.rankedGame.rankedCalculatePickScore();
                        combosDataToPrint= this.rankedGame.rankedCalculateCombos();
                    }

                    this.printData(0);
                    this.printCombos();

                    if (this.information.getPersonal() == true) {
                        if ((personalDataToPrint != null) && (!personalDataToPrint.isEmpty())) {
                            personalDataToPrint.clear();
                        }

                        if ((combosDataToPrintPersonal != null) && (!combosDataToPrintPersonal.isEmpty())) {
                            combosDataToPrintPersonal.clear();
                        }
                    }

                    if (this.information.getCommunity() == true) {
                        if ((communityDataToPrint != null) && (!communityDataToPrint.isEmpty())) {
                            communityDataToPrint.clear();
                        }

                        if ((combosDataToPrint != null) && (!combosDataToPrint.isEmpty())) {
                            combosDataToPrint.clear();
                        }

                    }
                    
                }
                else {
                    // Do nothing, this block should not be reached in any circumstances
                }
                
                roundIndex ++;
            }

            if (this.rankedTotalBans == 6) {
                infoMode = 0; // Now, hot and loved are shown.
                rankedTotalBans++; // To avoid enter again in this IF block
                pickBanButton.setText("PICK");
                
                if (this.starts.equals(starts.ALLYSTART)) {
                    alliesMessage.setBackground(Color.orange);
                    alliesMessage.setText("Allies PICK turn");
                    enemiesMessage.setBackground(Color.gray);
                    enemiesMessage.setText("");


                    if (this.information.getPersonal() == true) {
                        personalDataToPrint = this.rankedGame.rankedCalculatePickScorePersonal();
                    }

                    if (this.information.getCommunity() == true) {
                        communityDataToPrint = this.rankedGame.rankedCalculatePickScore();
                    }

                    this.printData(0);
                    this.printAllCombos();

                    if (this.information.getPersonal() == true) {
                        personalDataToPrint.clear();
                    }

                    if (this.information.getCommunity() == true) {
                        communityDataToPrint.clear();
                    }

                    //next = next.ENEMYSTART;
                }
                else {
                    alliesMessage.setBackground(Color.gray);
                    alliesMessage.setText("");
                    enemiesMessage.setBackground(Color.orange);
                    enemiesMessage.setText("Enemies PICK turn");
                    
                    this.printData(1);
                    rankedCombotextArea.setText("");
                }
                
                roundIndex ++;
            }
        }
        
        if (this.rankedTotalPicks == this.totalPicks) {
            //this.pickBanButton.setEnabled(false);
            this.pickBanButton.setText("Save");
            this.printData(2); // Print final teams
            //this.printCombos();
            this.printFinalCombos();
            rankedTotalPicks++; // this is to avoid entering again in the loop
            
            alliesMessage.setBackground(Color.black);
            alliesMessage.setForeground(Color.yellow);
            alliesMessage.setText("GOOD LUCK AND HAVE FUN");
            
            enemiesMessage.setBackground(Color.black);
            enemiesMessage.setForeground(Color.yellow);
            enemiesMessage.setText("GOOD LUCK AND HAVE FUN");
            
        }
        else if (this.rankedTotalPicks == (this.totalPicks + 1)) {
            try {
                this.pickBanButton.setEnabled(false);
                this.saveResults(); // Dirty method. It should be implemented in another class
            } catch (IOException ex) {
                Logger.getLogger(Ranked_UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_pickBanButtonActionPerformed

    private void saveResults() throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("dd_MM_YYYY__HH_mm_ss");
        Date date = new Date();

        // Create new directory if it doesnt exist, called matches
        String mainPath = System.getProperty("user.dir").replace("\\", "/") + ("/");
        String matchPath = "matches/";
        String resultsFile = "match_"+ dateFormat.format(date) + ".txt";
        String fullOutputPath = mainPath + matchPath + resultsFile;

        File resultsDir = new File(mainPath + matchPath);

        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }

        // Create new file, with name with date
        PrintWriter writer = new PrintWriter(fullOutputPath);

        String bans = "BANS:";
        String enemyPicks = "ENEMY:";
        String allyPicks = "ALLY:";

        
        Iterator it1 = this.rankedGame.getBans().entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry)it1.next();
            bans = bans + pair.getKey() + ",";
            it1.remove(); // avoids a ConcurrentModificationException
        }

        for (String element : this.rankedGame.getEnemyPicks()) {
            enemyPicks = enemyPicks + element + ",";
        }

        for (String element : this.rankedGame.getAllyPicks()) {
            allyPicks = allyPicks + element + ",";
        }

        writer.println(bans);
        writer.println(enemyPicks);
        writer.println(allyPicks);
        
        writer.close();
        resultsDir = null;

    }

    private void printData(int mode) {
        if (mode == 0) {
            if (this.rankedTotalBans < 6) {
                //jTextArea1.setText("RECOMMENDED BANS \n");
                jTextArea1.setText(" -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                    "                                           RECOMMENDED BANS\n\n");
            }
            else {
                //jTextArea1.setText("RECOMMENDED PICKS \n");
                jTextArea1.setText(" -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                    "                                           RECOMMENDED PICKS\n\n");
            }

            this.printHeader();
            
            if (this.information.getPersonal() == true) {
                this.printPersonalInformation();
            }
            
            if (this.information.getCommunity() == true) {
                this.printCommunityInformation();
            }
            
        }
        else if (mode==1) {
            if (this.rankedTotalBans < 6) {
                jTextArea1.setText("\n\n        Waiting enemy ban...");
            }
            else {
                jTextArea1.setText("\n\n        Waiting enemy picks...");
            }

        }
        else { // Print final teams
            //jTextArea1.setText("-------- FINAL TEAMS --------: \n");
            jTextArea1.setText(
                "\n ******************************************************************************************** \n" +
                "                       FINAL TEAMS: \n"+
                " ********************************************************************************************\n\n" );

            jTextArea1.setText(jTextArea1.getText() + ">>> ALLY TEAM : \n-----------------------\n");
            jTextArea1.setText(jTextArea1.getText() + this.printList(this.rankedGame.getAllyPicks(), 0) + "\n\n");
            jTextArea1.setText(jTextArea1.getText() + ">>> ENEMY TEAM : \n-----------------------\n");
            jTextArea1.setText(jTextArea1.getText() + this.printList(this.rankedGame.getEnemyPicks(), 1)+ "\n\n");

            jTextArea1.setText(jTextArea1.getText() +
                "\n ******************************************************************************************** \n" +
                "                       GOOD LUCK AND HAVE FUN \n"+
                " ******************************************************************************************** \n" );
        }
    }
    
    public String printList (List<String> list, int infoMode) {
        String team = "";

        /*
        for (String champ : list) {
            team = team + champ + ", ";
        }
         *
         */
        
        for (String champ : list) {
            team = team + champ + "--> [" + this.rankedGame.getPositions(champ, infoMode) + "] \n";
        }

        return team;
    }
    
    public void printHeader() {
        /*
        jTextArea1.setText(this.jTextArea1.getText() +
        "CHAMPION       PERCENTAGE      TAGS        COMBOS\n" +
        "___________________________________________________________\n");
         */
        jTextArea1.setText(this.jTextArea1.getText() +
            "               CHAMPION --- SCORE --- TAGS --- COMBOS --- POS\n" +
            "--------------------------------------------------------" +
            "-----------------------------------------------------\n");
    }
    
    public void printCommunityInformation () {
        
        if ((this.communityDataToPrint != null) && (!this.communityDataToPrint.isEmpty())) {
            
            DecimalFormat dF = new DecimalFormat("0.00");

            jTextArea1.setText(this.jTextArea1.getText() +
            "***********************COMMUNITY"
            + "***********************\n\n");

            Iterator it = communityDataToPrint.entrySet().iterator();

            while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + ":" + pair.getValue());
            jTextArea1.setText(this.jTextArea1.getText() +
                pair.getKey() + " --- " + dF.format(pair.getValue()) + " --- [" +
                this.rankedGame.getTags(pair.getKey().toString()) + "] --- [" +
                this.rankedGame.getCombos(pair.getKey().toString()) + "] --- [" +
                this.rankedGame.getPositions(pair.getKey().toString(), infoMode) + "] \n>>>\n");
            }
        } 
        else {
         jTextArea1.setText(this.jTextArea1.getText() +
           ">> No information avaiable \n");
        }        
    }
    
    public void printPersonalInformation () {

        if ((this.personalDataToPrint != null) && (!this.personalDataToPrint.isEmpty())) {
            DecimalFormat dF = new DecimalFormat("0.00");

            jTextArea1.setText(this.jTextArea1.getText() +
            "***********************PERSONAL"
            + "***********************\n\n");

            Iterator it = personalDataToPrint.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                jTextArea1.setText(this.jTextArea1.getText() +
                    pair.getKey() + " --- " + dF.format(pair.getValue()) + " --- [" +
                    this.rankedGame.getTagsPersonal(pair.getKey().toString()) + "] --- [" +
                    this.rankedGame.getCombos(pair.getKey().toString()) + "] --- [" +
                    this.rankedGame.getPositions(pair.getKey().toString(), infoMode) + "] \n>>>\n");
            }
        }
        else {
          jTextArea1.setText(this.jTextArea1.getText() +
            ">> No information avaiable \n");
        } 
    }
    
    public void initializeRounds (Flags st) {
        /*
        OPERATION A: add to ally, prepare ally
        OPERATION B: add to ally, prepare enemy
        OPERATION C: add to enemy, prepare enemy
        OPERATION D: add to enemy, prepare ally
        
        IF ALLY STARTS, THE SEQUENCE IS: B,C,D,A  B,C,D,A...
        IF ENEMY STARTS, THE SEQUENCE IS: D,A,B,C D,A,B,C...
        */
        
        if (st.equals(this.starts.ALLYSTART)) {
            this.round[0]= "B";
            this.round[1]= "C";
            this.round[2]= "D";
            this.round[3]= "A";
            this.round[4]= "B";
            this.round[5]= "C";
            this.round[6]= "D";
            this.round[7]= "A";
            this.round[8]= "B";
            this.round[9]= "C";
            this.round[10]= "H"; // this is just for caution
            
        }
        else { // ENEMY STARTS
            this.round[0]= "D";
            this.round[1]= "A";
            this.round[2]= "B";
            this.round[3]= "C";
            this.round[4]= "D";
            this.round[5]= "A";
            this.round[6]= "B";
            this.round[7]= "C";
            this.round[8]= "D";
            this.round[9]= "A";
            this.round[10]= "H"; // this is just for caution
            
        }
    }
    
    private void printAllCombos() {
        
        String comboStatus = "--> [Cannot be completed]";
        String elements = "";
        boolean cStatusAlly = false; 
        boolean cStatusEnemy = false;
        
        rankedCombotextArea.setText(
            " -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
            "                                                       COMBOS\n" +
            "--------------------------------------------------------" +
            "-----------------------------------------------------\n");
        
        for (Map.Entry pair: this.information.getPersonalCombos().entrySet()) {
            elements = pair.getValue().toString();
            
            for (String pair2: this.rankedGame.getAllyBans()) {
                if (elements.contains(pair2)) {
                    cStatusAlly = true;
                    break;
                }
            }
            
            for (String pair3: this.rankedGame.getEnemyBans()) {
                if (elements.contains(pair3)) {
                    cStatusEnemy = true;
                    break;
                }
            }
            
            if ((cStatusAlly == true) || (cStatusEnemy == true)) {
                rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + ". " + pair.getValue() + comboStatus + " \n");
            }
            else {
                rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + ". " + pair.getValue() + " \n");
            }
            
            cStatusAlly = false;
            cStatusEnemy = false;
        }
    }
    
    private void printCombos() {
        String comboStatus = "--> [Already started]";
        String comboStatus2 = "--> [Complete!]";
        String comboStatus3 = "--> [Cannot be completed]";
        String comboStatus4 = "";
        double value;
        
        rankedCombotextArea.setText(
            " -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
            "                                                       COMBOS\n" +
            "--------------------------------------------------------" +
            "-----------------------------------------------------\n");

        if (this.information.getPersonal() == true) {
            rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                    "***********************PERSONAL"
                    + "***********************\n\n");
            if ((combosDataToPrintPersonal != null) && (!combosDataToPrintPersonal.isEmpty())) {

                Iterator it0 = combosDataToPrintPersonal.entrySet().iterator();
                
                String aux="";

                while (it0.hasNext()) {
                    
                    Map.Entry pair = (Map.Entry)it0.next();
                    value = Double.parseDouble(pair.getValue().toString());

                    if (value >= 1000.0) {
                        for (Map.Entry banned : this.rankedGame.getBans().entrySet()) {
                            if (pair.getKey().toString().contains(banned.getKey().toString())) {
                                aux = comboStatus2 + comboStatus3;
                                break;
                            }
                        }
                        for (String enemyPicked : this.rankedGame.getEnemyPicks()) {
                            if (pair.getKey().toString().contains(enemyPicked)) {
                                aux = comboStatus2 + comboStatus3;
                                break;
                            }
                        }
                        if (aux.equals("")) {
                            aux = comboStatus2;
                        }
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + aux + " \n");
                        aux = "";
                    }
                    else if ((value >= 200.0)&& (value < 1000.0)) { // Manual value established in Game.java file (to ensure already started combos to appear first)
                        for (Map.Entry banned : this.rankedGame.getBans().entrySet()) {
                            if (pair.getKey().toString().contains(banned.getKey().toString())) {
                                aux = comboStatus + comboStatus3;
                                break;
                            }
                        }
                        for (String enemyPicked : this.rankedGame.getEnemyPicks()) {
                            if (pair.getKey().toString().contains(enemyPicked)) {
                                aux = comboStatus + comboStatus3;
                                break;
                            }
                        }
                        if (aux.equals("")) {
                            aux = comboStatus;
                        }
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + aux + " \n");
                        aux = "";
                    }
                    else {
                        for (Map.Entry banned : this.rankedGame.getBans().entrySet()) {
                            if (pair.getKey().toString().contains(banned.getKey().toString())) {
                                aux = comboStatus4 + comboStatus3;
                                break;
                            }
                        }
                        for (String enemyPicked : this.rankedGame.getEnemyPicks()) {
                            if (pair.getKey().toString().contains(enemyPicked)) {
                                aux = comboStatus4 + comboStatus3;
                                break;
                            }
                        }
                        if (aux.equals("")) {
                            aux = comboStatus4;
                        }
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + aux + " \n");
                        aux = "";
                    }
                    
                    comboStatus = "--> [Already started]";
                    comboStatus2 = "--> [Complete!]";
                    comboStatus3 = "--> [Can not be completed]";
                    comboStatus4 = "";
                }
            }
            else {
                rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                ">> No combos information avaiable \n");
            }
        }

        if (this.information.getCommunity() == true) {
            rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                    "***********************COMMUNITY"
                    + "***********************\n\n");
            if ((combosDataToPrint != null) && (!combosDataToPrint.isEmpty())) {

                Iterator it = combosDataToPrint.entrySet().iterator();

                String aux ="";
                
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //System.out.println(pair.getKey());
                    value = Double.parseDouble(pair.getValue().toString());

                    if (value >= 1000.0) {
                        for (Map.Entry banned : this.rankedGame.getBans().entrySet()) {
                            if (pair.getKey().toString().contains(banned.getKey().toString())) {
                                aux = comboStatus2 + comboStatus3;
                                break;
                            }
                        }
                        for (String enemyPicked : this.rankedGame.getEnemyPicks()) {
                            if (pair.getKey().toString().contains(enemyPicked)) {
                                aux = comboStatus2 + comboStatus3;
                                break;
                            }
                        }
                        if (aux.equals("")) {
                            aux = comboStatus2;
                        }
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + aux + " \n");
                        aux = "";
                    }
                    else if ((value >= 200.0)&& (value < 1000.0)) { // Manual value established in Game.java file (to ensure already started combos to appear first)
                        for (Map.Entry banned : this.rankedGame.getBans().entrySet()) {
                            if (pair.getKey().toString().contains(banned.getKey().toString())) {
                                aux = comboStatus + comboStatus3;
                                break;
                            }
                        }
                        for (String enemyPicked : this.rankedGame.getEnemyPicks()) {
                            if (pair.getKey().toString().contains(enemyPicked)) {
                                aux = comboStatus + comboStatus3;
                                break;
                            }
                        }
                        if (aux.equals("")) {
                            aux = comboStatus;
                        }
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + aux + " \n");
                        aux = "";
                    }
                    else {
                        for (Map.Entry banned : this.rankedGame.getBans().entrySet()) {
                            if (pair.getKey().toString().contains(banned.getKey().toString())) {
                                aux = comboStatus4 + comboStatus3;
                                break;
                            }
                        }
                        for (String enemyPicked : this.rankedGame.getEnemyPicks()) {
                            if (pair.getKey().toString().contains(enemyPicked)) {
                                aux = comboStatus4 + comboStatus3;
                                break;
                            }
                        }
                        if (aux.equals("")) {
                            aux = comboStatus4;
                        }
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + aux + " \n");
                        aux = "";
                    }
                    
                    comboStatus = "--> [Already started]";
                    comboStatus2 = "--> [Complete!]";
                    comboStatus3 = "--> [Can not be completed]";
                    comboStatus4 = "";
                }
            }
            else {
                rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                ">> No combos information avaiable \n");
            }
        }
    }
    
    private void printFinalCombos() {

        // And now, print the completed combos
        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
            " -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
            "                                             FULLFILLED COMBOS\n" +
            "--------------------------------------------------------" +
            "-----------------------------------------------------\n");

        if (this.information.getPersonal() == true) {
            rankedCombotextArea.setText(this.rankedCombotextArea.getText() +  "***********************PERSONAL"
                + "***********************\n\n");

            combosDataToPrintPersonal = this.rankedGame.normalCalculateCombos(this.rankedGame.getAllyPicks());

            if ((combosDataToPrintPersonal != null) && (!combosDataToPrintPersonal.isEmpty())) {
                Iterator itCPersonal = combosDataToPrintPersonal.entrySet().iterator();
                double value;

                while (itCPersonal.hasNext()) {
                    Map.Entry pair = (Map.Entry)itCPersonal.next();
                    value = Double.parseDouble(pair.getValue().toString());

                    if (value >= 1000.0) {
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + "--> [Complete!]\n");
                    }
                } 
            }
            else {
                rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                ">>> No Combos fulfiled \n");
            }               
        }

        if (this.information.getCommunity() == true) {
            rankedCombotextArea.setText(this.rankedCombotextArea.getText() +  "***********************COMMUNITY"
                + "***********************\n\n");

            combosDataToPrint = this.rankedGame.normalCalculateCombos(this.rankedGame.getAllyPicks());

            if ((combosDataToPrint != null) && (!combosDataToPrint.isEmpty())) {
                Iterator itCommunity = combosDataToPrint.entrySet().iterator();
                double value;

                while (itCommunity.hasNext()) {
                    Map.Entry pair = (Map.Entry)itCommunity.next();
                    value = Double.parseDouble(pair.getValue().toString());

                    if (value >= 1000.0) {
                        rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                        pair.getKey() + "--> [Complete!]\n");
                    }
                } 
            }
            else {
                rankedCombotextArea.setText(this.rankedCombotextArea.getText() +
                ">>> No Combos fulfiled \n");
            }               
        }  
    }


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Ranked_UI().setVisible(true);
                this.run();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea alliesBans;
    private javax.swing.JLabel alliesMessage;
    private javax.swing.JTextArea alliesPicks;
    private javax.swing.JTextArea enemiesBans;
    private javax.swing.JLabel enemiesMessage;
    private javax.swing.JTextArea enemiesPicks;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton pickBanButton;
    private javax.swing.JTextArea rankedCombotextArea;
    // End of variables declaration//GEN-END:variables

}
