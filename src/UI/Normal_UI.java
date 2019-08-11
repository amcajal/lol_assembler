/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Normal_UI.java
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
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Alberto
 */
public class Normal_UI extends javax.swing.JFrame {

    Information information;
    Games games;
    Flags ft;

    int list; // Number of champions added. If 4 or less, the scores are calculated
    int playerNumber; // Number of players
    List<String> addeds; // Champions in the jComboBoxes
    
    /** Creates new form Normal_UI */
    public Normal_UI(Information info, Flags fType) {
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
        
        normalTextArea.setMargin(new Insets(15,25,25,25)); // Apply margins
        normalTextArea.setEditable(false);
        DefaultCaret caret1 = (DefaultCaret)normalTextArea.getCaret(); // To avoid the automatic scrolling
        caret1.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        
        comboTextArea.setMargin(new Insets(15,15,25,25)); // Apply margins
        comboTextArea.setEditable(false);
        DefaultCaret caret2 = (DefaultCaret)comboTextArea.getCaret(); // To avoid the automatic scrolling
        caret2.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        this.setTitle ("Normal Game - LoL Assembler");

        this.information = info;
        this.games = new Games (this.information, ft.NORMAL, ft.VS5);

        list = 0;
        addeds = new ArrayList<String>();

        Iterator it = information.getChampionList().entrySet().iterator();

        // Index 0 element. This must be processed in the ADD buttons
        jComboBox1.addItem("");
        jComboBox2.addItem("");
        jComboBox3.addItem("");
        jComboBox4.addItem("");
        jComboBox5.addItem("");


        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            jComboBox1.addItem(pair.getKey());
            jComboBox2.addItem(pair.getKey());
            jComboBox3.addItem(pair.getKey());
            jComboBox4.addItem(pair.getKey());
            jComboBox5.addItem(pair.getKey());
        }

        // Autocompletion
        jComboBox1.setEditable(true);
        AutoCompletion.enable(jComboBox1);

        jComboBox2.setEditable(true);
        AutoCompletion.enable(jComboBox2);

        jComboBox3.setEditable(true);
        AutoCompletion.enable(jComboBox3);

        jComboBox4.setEditable(true);
        AutoCompletion.enable(jComboBox4);

        jComboBox5.setEditable(true);
        AutoCompletion.enable(jComboBox5);
        
        if (fType.equals(ft.VS3)) { // 3vs3 match
            jComboBox4.setEnabled(false);
            normalClearFourButton.setEnabled(false);
            
            jComboBox5.setEnabled(false);
            normalClearFiveButton.setEnabled(false);
            
            playerNumber = 3;
        }
        else {
            playerNumber = 5;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        normalTextArea = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        normalClearOneButton = new javax.swing.JButton();
        normalClearTwoButton = new javax.swing.JButton();
        normalClearThreeButton = new javax.swing.JButton();
        normalClearFourButton = new javax.swing.JButton();
        normalClearFiveButton = new javax.swing.JButton();
        normalClearAllButton = new javax.swing.JButton();
        normalExitButton = new javax.swing.JButton();
        assembleButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        comboTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 23, 37));

        normalTextArea.setBackground(new java.awt.Color(39, 40, 34));
        normalTextArea.setColumns(20);
        normalTextArea.setForeground(new java.awt.Color(255, 255, 0));
        normalTextArea.setRows(5);
        jScrollPane1.setViewportView(normalTextArea);

        normalClearOneButton.setBackground(new java.awt.Color(54, 109, 90));
        normalClearOneButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalClearOneButton.setForeground(new java.awt.Color(255, 255, 255));
        normalClearOneButton.setText("Clear");
        normalClearOneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalClearOneButtonActionPerformed(evt);
            }
        });

        normalClearTwoButton.setBackground(new java.awt.Color(54, 109, 90));
        normalClearTwoButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalClearTwoButton.setForeground(new java.awt.Color(255, 255, 255));
        normalClearTwoButton.setText("Clear");
        normalClearTwoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalClearTwoButtonActionPerformed(evt);
            }
        });

        normalClearThreeButton.setBackground(new java.awt.Color(54, 109, 90));
        normalClearThreeButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalClearThreeButton.setForeground(new java.awt.Color(255, 255, 255));
        normalClearThreeButton.setText("Clear");
        normalClearThreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalClearThreeButtonActionPerformed(evt);
            }
        });

        normalClearFourButton.setBackground(new java.awt.Color(54, 109, 90));
        normalClearFourButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalClearFourButton.setForeground(new java.awt.Color(255, 255, 255));
        normalClearFourButton.setText("Clear");
        normalClearFourButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalClearFourButtonActionPerformed(evt);
            }
        });

        normalClearFiveButton.setBackground(new java.awt.Color(54, 109, 90));
        normalClearFiveButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalClearFiveButton.setForeground(new java.awt.Color(255, 255, 255));
        normalClearFiveButton.setText("Clear");
        normalClearFiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalClearFiveButtonActionPerformed(evt);
            }
        });

        normalClearAllButton.setBackground(new java.awt.Color(54, 109, 90));
        normalClearAllButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalClearAllButton.setForeground(new java.awt.Color(255, 255, 255));
        normalClearAllButton.setText("Clear All");
        normalClearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalClearAllButtonActionPerformed(evt);
            }
        });

        normalExitButton.setBackground(new java.awt.Color(30, 68, 116));
        normalExitButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        normalExitButton.setForeground(new java.awt.Color(255, 255, 255));
        normalExitButton.setText("Exit");
        normalExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalExitButtonActionPerformed(evt);
            }
        });

        assembleButton.setBackground(new java.awt.Color(184, 8, 12));
        assembleButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        assembleButton.setForeground(new java.awt.Color(255, 255, 255));
        assembleButton.setText("Assemble");
        assembleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assembleButtonActionPerformed(evt);
            }
        });

        comboTextArea.setBackground(new java.awt.Color(39, 40, 34));
        comboTextArea.setColumns(20);
        comboTextArea.setForeground(new java.awt.Color(255, 255, 0));
        comboTextArea.setRows(5);
        jScrollPane2.setViewportView(comboTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, 119, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(normalClearOneButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(normalClearThreeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(normalClearFourButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(normalClearFiveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(normalClearAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(normalClearTwoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(assembleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(normalExitButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(normalClearOneButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(normalClearTwoButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(normalClearThreeButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(normalClearFourButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(normalClearFiveButton))
                        .addGap(18, 18, 18)
                        .addComponent(normalClearAllButton)
                        .addGap(41, 41, 41)
                        .addComponent(assembleButton))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(normalExitButton)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
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


    private void normalClearOneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalClearOneButtonActionPerformed
        // TODO add your handling code here:
        jComboBox1.setSelectedIndex(0);
    }//GEN-LAST:event_normalClearOneButtonActionPerformed

    private void normalClearTwoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalClearTwoButtonActionPerformed
        // TODO add your handling code here:
        jComboBox2.setSelectedIndex(0);
    }//GEN-LAST:event_normalClearTwoButtonActionPerformed

    private void normalClearThreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalClearThreeButtonActionPerformed
        // TODO add your handling code here:
        jComboBox3.setSelectedIndex(0);
    }//GEN-LAST:event_normalClearThreeButtonActionPerformed

    private void normalClearFourButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalClearFourButtonActionPerformed
        // TODO add your handling code here:
        jComboBox4.setSelectedIndex(0);
    }//GEN-LAST:event_normalClearFourButtonActionPerformed

    private void normalClearFiveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalClearFiveButtonActionPerformed
        // TODO add your handling code here:
        jComboBox5.setSelectedIndex(0);
    }//GEN-LAST:event_normalClearFiveButtonActionPerformed

    private void normalClearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalClearAllButtonActionPerformed
        // TODO add your handling code here:
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
    }//GEN-LAST:event_normalClearAllButtonActionPerformed

    private void normalExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalExitButtonActionPerformed
        // TODO add your handling code here:
        int selectedOption = JOptionPane.showConfirmDialog(null,
                                  "Close Normal Game Window?", // Message of the window
                                  "Exiting Normal Game Window", // Message of the header
                                  JOptionPane.YES_NO_OPTION);

        if (selectedOption == JOptionPane.YES_OPTION) {
            //System.gc(); // Release all the resources
            this.dispose();
        }
    }//GEN-LAST:event_normalExitButtonActionPerformed

    private void assembleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assembleButtonActionPerformed
        // TODO add your handling code here:
        normalTextArea.setText("");
        comboTextArea.setText("");
        Map<String, Double> dataToPrint = null;
        Map<String, Double> dataToPrintPersonal = null;
        Map<String, Double> combosDataToPrint = null;
        Map<String, Double> combosDataToPrintPersonal = null;
        DecimalFormat dF = new DecimalFormat("0.00");

        list = 0;
        addeds.clear();

        if (!jComboBox1.getSelectedItem().equals("")) {
            addeds.add(jComboBox1.getSelectedItem().toString());
            list ++;
        }

        if (!jComboBox2.getSelectedItem().equals("")) {
            addeds.add(jComboBox2.getSelectedItem().toString());
            list ++;
        }

        if (!jComboBox3.getSelectedItem().equals("")) {
            addeds.add(jComboBox3.getSelectedItem().toString());
            list ++;
        }

        if (!jComboBox4.getSelectedItem().equals("")) {
            addeds.add(jComboBox4.getSelectedItem().toString());
            list ++;
        }

        if (!jComboBox5.getSelectedItem().equals("")) {
            addeds.add(jComboBox5.getSelectedItem().toString());
            list ++;
        }

        if (list != this.playerNumber) {

            normalTextArea.setText(this.normalTextArea.getText() +
                " -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "                                           RECOMMENDED PICKS\n\n" +
                "               CHAMPION --- SCORE --- TAGS --- COMBOS --- POS\n" +
                "--------------------------------------------------------" +
                "-----------------------------------------------------\n");

            comboTextArea.setText(this.comboTextArea.getText() +
                " -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "                                                       COMBOS\n" +
                "--------------------------------------------------------" +
                "-----------------------------------------------------\n");


            if (this.information.getPersonal() == true) {
                if ((combosDataToPrintPersonal != null) && (!combosDataToPrintPersonal.isEmpty())) {
                    combosDataToPrintPersonal.clear();
                }
                
                dataToPrintPersonal = this.games.normalCalculateScorePersonal(this.addeds);
                combosDataToPrintPersonal = this.games.normalCalculateCombosPersonal(this.addeds);

                normalTextArea.setText(this.normalTextArea.getText() + "***********************PERSONAL"
                    + "***********************\n\n");

                comboTextArea.setText(this.comboTextArea.getText() +  "***********************PERSONAL"
                    + "***********************\n\n");

                if (dataToPrintPersonal != null) {

                    Iterator it2 = dataToPrintPersonal.entrySet().iterator();

                    while (it2.hasNext()) {
                        Map.Entry pair = (Map.Entry)it2.next();
                        //System.out.println(pair.getKey() + ":" + pair.getValue());
                        normalTextArea.setText(this.normalTextArea.getText() +
                        pair.getKey() + " --- " + dF.format(pair.getValue()) + " --- [" +
                        this.games.getTagsPersonal(pair.getKey().toString()) + "] --- [" +
                        this.games.getCombos(pair.getKey().toString()) + "] --- [" +
                        this.games.getPositions(pair.getKey().toString(), 0) + "] \n>>>\n");
                    }

                    dataToPrintPersonal.clear();
                }
                else {
                    normalTextArea.setText(this.normalTextArea.getText() +
                            ">>> No picks information avaiable \n");
                }

                if ((combosDataToPrintPersonal != null) && (dataToPrintPersonal != null)
                        && (!this.information.getPersonalCombos().isEmpty())) {

                    double value;
                    String comboStatus = "--> [Already started]";
                    String comboStatus2 = "--> [Complete!]";
                    Iterator it3 = combosDataToPrintPersonal.entrySet().iterator();

                    while (it3.hasNext()) {
                        Map.Entry pair = (Map.Entry)it3.next();
                        value = Double.parseDouble(pair.getValue().toString());

                        if (value >= 1000.0) {
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + comboStatus2 + " \n");
                        }
                        else if ((value >= 200.0) && (value < 1000.0 )) { // Manual value established in Game.java file (to ensure already started combos to appear first)
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + comboStatus + " \n");
                        }
                        else {
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + " \n");
                        }
                    }

                    //combosDataToPrintPersonal.clear();
                }
                else {
                    comboTextArea.setText(this.comboTextArea.getText() +
                    ">>> No Combos information avaiable \n");
                }

            }

            if (this.information.getCommunity() == true) {
                if ((combosDataToPrint != null) && (!combosDataToPrint.isEmpty())) {
                    combosDataToPrint.clear();
                }
                
                dataToPrint = this.games.normalCalculateScore(this.addeds);
                combosDataToPrint = this.games.normalCalculateCombos(this.addeds);

                normalTextArea.setText(this.normalTextArea.getText() + "***********************COMMUNITY"
                        + "***********************\n\n");

                comboTextArea.setText(this.comboTextArea.getText() +  "***********************COMMUNITY"
                    + "***********************\n\n");
                
                Iterator it = dataToPrint.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //System.out.println(pair.getKey() + ":" + pair.getValue());
                    normalTextArea.setText(this.normalTextArea.getText() +
                    pair.getKey() + " --- " + dF.format(pair.getValue()) + " --- [" +
                    this.games.getTags(pair.getKey().toString()) + "] --- [" +
                    this.games.getCombos(pair.getKey().toString()) + "] --- [" +
                    this.games.getPositions(pair.getKey().toString(), 0) + "] \n>>>\n");
                }

                dataToPrint.clear();

                if ((combosDataToPrint != null) && (!this.information.getPersonalCombos().isEmpty())) {
                    double value;
                    String comboStatus = "--> [Already started]";
                    String comboStatus2 = "--> [Complete!]";
                    Iterator it1 = combosDataToPrint.entrySet().iterator();

                    while (it1.hasNext()) {
                        Map.Entry pair = (Map.Entry)it1.next();
                        value = Double.parseDouble(pair.getValue().toString());

                        if (value >= 1000.0) {
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + comboStatus2 + " \n");
                        }
                        else if ((value >= 200.0)&&(value < 1000.0)) { // Manual value established in Game.java file (to ensure already started combos to appear first)
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + comboStatus + " \n");
                        }
                        else {
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + " \n");
                        }
                    }

                    //combosDataToPrint.clear();
                }
                else {
                    comboTextArea.setText(this.comboTextArea.getText() +
                    ">>> No Combos information avaiable \n");
                }

            }
        }
        else {
            // PRINT THE FINAL TEAM
            this.listFinalTeam();
            
            // And now, print the completed combos
            comboTextArea.setText(this.comboTextArea.getText() +
                " -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "                                             FULLFILLED COMBOS\n" +
                "--------------------------------------------------------" +
                "-----------------------------------------------------\n");
            
            if (this.information.getPersonal() == true) {
                comboTextArea.setText(this.comboTextArea.getText() +  "***********************PERSONAL"
                    + "***********************\n\n");
                
                combosDataToPrintPersonal = this.games.normalCalculateCombos(this.addeds);
                
                if ((combosDataToPrintPersonal != null) && (!combosDataToPrintPersonal.isEmpty())) {
                    Iterator itCPersonal = combosDataToPrintPersonal.entrySet().iterator();
                    double value;
                    
                    while (itCPersonal.hasNext()) {
                        Map.Entry pair = (Map.Entry)itCPersonal.next();
                        value = Double.parseDouble(pair.getValue().toString());

                        if (value >= 1000.0) {
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + "--> [Complete!]\n");
                        }
                    } 
                }
                else {
                    comboTextArea.setText(this.comboTextArea.getText() +
                    ">>> No Combos fulfiled \n");
                }               
            }
            
            if (this.information.getCommunity() == true) {
                comboTextArea.setText(this.comboTextArea.getText() +  "***********************COMMUNITY"
                    + "***********************\n\n");
                
                combosDataToPrint = this.games.normalCalculateCombos(this.addeds);
                
                if ((combosDataToPrint != null) && (!combosDataToPrint.isEmpty())) {
                    Iterator itCommunity = combosDataToPrint.entrySet().iterator();
                    double value;
                    
                    while (itCommunity.hasNext()) {
                        Map.Entry pair = (Map.Entry)itCommunity.next();
                        value = Double.parseDouble(pair.getValue().toString());

                        if (value >= 1000.0) {
                            comboTextArea.setText(this.comboTextArea.getText() +
                            pair.getKey() + "--> [Complete!]\n");
                        }
                    } 
                }
                else {
                    comboTextArea.setText(this.comboTextArea.getText() +
                    ">>> No Combos fulfiled \n");
                }               
            }  
        }
    }//GEN-LAST:event_assembleButtonActionPerformed

    private void listFinalTeam() {
        normalTextArea.setText(this.normalTextArea.getText() +
                "\n ******************************************************************************************** \n" +
                "                       YOUR FINAL TEAM IS: \n"+
                " ********************************************************************************************\n\n" );

        for (String champion: addeds) {
            normalTextArea.setText(this.normalTextArea.getText() + "            " +
                    champion + " --> [" + this.games.getPositions(champion, 0) + "]\n");
        }

        normalTextArea.setText(this.normalTextArea.getText() +
                "\n ******************************************************************************************** \n" +
                "                       GOOD LUCK AND HAVE FUN \n"+
                " ******************************************************************************************** \n" );

    }


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Normal_UI().setVisible(true);
                this.run();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton assembleButton;
    private javax.swing.JTextArea comboTextArea;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton normalClearAllButton;
    private javax.swing.JButton normalClearFiveButton;
    private javax.swing.JButton normalClearFourButton;
    private javax.swing.JButton normalClearOneButton;
    private javax.swing.JButton normalClearThreeButton;
    private javax.swing.JButton normalClearTwoButton;
    private javax.swing.JButton normalExitButton;
    private javax.swing.JTextArea normalTextArea;
    // End of variables declaration//GEN-END:variables

}
