/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Modify_Third_UI.java
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
import SourceCode.Information;
import SourceCode.Path;
import java.awt.Color;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Alberto
 */
public class Modify_Third_UI extends javax.swing.JFrame {

    Flags fileType;
    Flags objective;
    Information information;
    Path path;
    Iterator itUpdate;
    String fullOutputPath;
    String combo;
    String positions;
    LinkedHashMap<String, String> temporal;
    
    Color originalB, originalF, changeC, bgc;

    /** Creates new form Modify_Third_UI */
    public Modify_Third_UI(Information info, Flags fT, Flags obj, Path p) {
        initComponents();

        Image i;
        try {
            i = ImageIO.read(getClass().getResource("/Images/lol_assembler_icon.png"));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(LoL_assembly_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bgc = new Color(5,23,37);
        getContentPane().setBackground(bgc);
        originalB = modifyThirdUpdateButton.getBackground();
        originalF = modifyThirdUpdateButton.getForeground();

        temporal = new LinkedHashMap<String, String>();
        objective = obj;   
        Iterator it;
        information = info;
        positions = "";

        jLabel3.setText("a champion to correctly apply the changes.");
        
        if (objective.equals(Flags.COMBOS) || (fT.equals(Flags.DELETE))) {
            topBox.setEnabled(false);
            midBox.setEnabled(false);
            jungleBox.setEnabled(false);
            adcBox.setEnabled(false);
            supportBox.setEnabled(false);
        }
          
        if (fT.equals(fT.ADD)) {
            
            if (objective.equals(objective.HOT)) {
                this.setTitle ("Add Hot champion - LoL Assembler");
            }
            else if (objective.equals(objective.LOVED)){
                this.setTitle ("Add Loved champion - LoL Assembler");
            }
            else if (objective.equals(objective.HATED)){
                this.setTitle ("Add Hated champion - LoL Assembler");
            }
            else { // objective equals to COMBO
                this.setTitle ("Add combo - LoL Assembler");
                jLabel3.setText("a combo to correctly apply the changes.");
            }

             addOrDeleteButton.setText("Add");
             fileType = fT.ADD;
             
            it = information.getChampionList().entrySet().iterator();
            jComboBox1.addItem("");
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                jComboBox1.addItem(pair.getKey());
            }
        }
        else { // fileType is equal to DELETE
            
            if (objective.equals(objective.HOT)) {
                this.setTitle ("Delete Hot champion - LoL Assembler");
                
                it = information.getHotOnes().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (!it.hasNext()) {
                    jLabel2.setText("Hot Ones File is empty");
                    jLabel3.setText("No data to delete");
                    addOrDeleteButton.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                }
                else {
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        jComboBox1.addItem(pair.getKey());
                    }
                }
            }
            else if (objective.equals(objective.LOVED)){
                this.setTitle ("Delete Loved champion - LoL Assembler");
                
                it = information.getLovedOnes().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (!it.hasNext()) {
                    jLabel2.setText("Loved Ones File is empty");
                    jLabel3.setText("No data to delete");
                    addOrDeleteButton.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                }
                else {
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        jComboBox1.addItem(pair.getKey());
                    }
                }
            }
            else if (objective.equals(objective.HATED)){
                this.setTitle ("Delete Hated champion - LoL Assembler");
                
                it = information.getHatedOnes().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (!it.hasNext()) {
                    jLabel2.setText("Hated Ones File is empty");
                    jLabel3.setText("No data to delete");
                    addOrDeleteButton.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                }
                else {
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        jComboBox1.addItem(pair.getKey());
                    }
                }
            }
            else { // objective equals to COMBO
                this.setTitle ("Delete combo - LoL Assembler");
                jLabel3.setText("a combo to correctly apply the changes.");
                
                it = information.getPersonalCombos().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (!it.hasNext()) {
                    jLabel2.setText("Combos File is empty");
                    jLabel3.setText("No data to delete");
                    addOrDeleteButton.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                }
                else {
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        jComboBox1.addItem(pair.getKey());
                    }
                }
            }
            
            addOrDeleteButton.setText("Delete");
            fileType = fT.DELETE;
        }

        jComboBox1.setEditable(true);
        AutoCompletion.enable(jComboBox1);

        combo = "";

        path = p;
        itUpdate = null;
        fullOutputPath = null;

        jLabel1.setText("");
 
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
        addOrDeleteButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        modifyThirdUpdateButton = new javax.swing.JButton();
        topBox = new javax.swing.JCheckBox();
        midBox = new javax.swing.JCheckBox();
        jungleBox = new javax.swing.JCheckBox();
        adcBox = new javax.swing.JCheckBox();
        supportBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 23, 37));

        addOrDeleteButton.setBackground(new java.awt.Color(54, 109, 90));
        addOrDeleteButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addOrDeleteButton.setForeground(new java.awt.Color(255, 255, 255));
        addOrDeleteButton.setText("jButton1");
        addOrDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrDeleteButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("NOTE: press \"Update\" after adding or deleting ");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));

        modifyThirdUpdateButton.setBackground(new java.awt.Color(184, 8, 12));
        modifyThirdUpdateButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        modifyThirdUpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        modifyThirdUpdateButton.setText("Update");
        modifyThirdUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyThirdUpdateButtonActionPerformed(evt);
            }
        });

        topBox.setBackground(new java.awt.Color(5, 23, 37));
        topBox.setForeground(new java.awt.Color(255, 255, 255));
        topBox.setText("Top");

        midBox.setBackground(new java.awt.Color(5, 23, 37));
        midBox.setForeground(new java.awt.Color(255, 255, 255));
        midBox.setText("Mid");

        jungleBox.setBackground(new java.awt.Color(5, 23, 37));
        jungleBox.setForeground(new java.awt.Color(255, 255, 255));
        jungleBox.setText("Jungle");

        adcBox.setBackground(new java.awt.Color(5, 23, 37));
        adcBox.setForeground(new java.awt.Color(255, 255, 255));
        adcBox.setText("ADC");

        supportBox.setBackground(new java.awt.Color(5, 23, 37));
        supportBox.setForeground(new java.awt.Color(255, 255, 255));
        supportBox.setText("Support");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addOrDeleteButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(modifyThirdUpdateButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(topBox)
                                .addGap(10, 10, 10)
                                .addComponent(midBox)
                                .addGap(10, 10, 10)
                                .addComponent(jungleBox)
                                .addGap(10, 10, 10)
                                .addComponent(adcBox)
                                .addGap(10, 10, 10)
                                .addComponent(supportBox)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addOrDeleteButton)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topBox)
                    .addComponent(midBox)
                    .addComponent(jungleBox)
                    .addComponent(adcBox)
                    .addComponent(supportBox))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(modifyThirdUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void addOrDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrDeleteButtonActionPerformed
        // TODO add your handling code here:
        // Work with information
        Boolean posActive = false;
        String champion = jComboBox1.getSelectedItem().toString().replace(" ", "");
        //String fullOutputPath = null;

        if (fileType.equals(fileType.ADD)) {
            
            positions="";
            // BUILD POSITIONS STRING
            if (topBox.isSelected()) {
                positions= positions + "Top, ";
                posActive = true;
            }
            if (midBox.isSelected()) {
                positions= positions + "Mid, ";
                posActive = true;
            }
            if (jungleBox.isSelected()) {
                positions= positions + "Jungle, ";
                posActive = true;
            }
            if (adcBox.isSelected()) {
                positions= positions + "ADC, ";
                posActive = true;
            }
            if (supportBox.isSelected()) {
                positions= positions + "Support, ";
                posActive = true;
            }
            
            if (posActive != true) {
                positions="-"; // To avoid problems loading data
            }

            switch (this.objective) {

                case HOT:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getHotOnesFile();
                    this.information.getHotOnes().put(champion, positions);

                    //itUpdate = this.information.getHotOnes().entrySet().iterator();
                    this.temporal.putAll(this.information.getHotOnes());
                    itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Champion added: " + champion);
                    break;

                case LOVED:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getLovedOnesFile();
                    this.information.getLovedOnes().put(champion, positions);

                    //itUpdate = this.information.getLovedOnes().entrySet().iterator();
                    this.temporal.putAll(this.information.getLovedOnes());
                    itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Champion added: " + champion);
                    break;

                case HATED:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getHatedOnesFile();
                    this.information.getHatedOnes().put(champion, positions);

                    //itUpdate = this.information.getHatedOnes().entrySet().iterator();
                    this.temporal.putAll(this.information.getHatedOnes());
                    itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Champion added: " + champion);
                    break;

                case COMBOS:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getPersonalCombosFile();
                    this.combo = this.combo + champion + "-";

                    //itUpdate = this.information.getPersonalCombos().entrySet().iterator();
                    //this.temporal.putAll(this.information.getPersonalCombos());
                    //itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Combo to add: " + this.combo);
                    break;

                default:
                    System.out.println ("Unknow file type to load (fatal error)");
                    System.out.println ("No changes has been made");
                    break;
            }

            
        }
        else { // fileType equals to DELETE

            switch (this.objective) {

                case HOT:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getHotOnesFile();
                    this.information.getHotOnes().remove(champion);

                    //itUpdate = this.information.getHotOnes().entrySet().iterator();
                    this.temporal.putAll(this.information.getHotOnes());
                    itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Champion deleted: " + champion);
                    break;

                case LOVED:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getLovedOnesFile();
                    this.information.getLovedOnes().remove(champion);

                    //itUpdate = this.information.getLovedOnes().entrySet().iterator();
                    this.temporal.putAll(this.information.getLovedOnes());
                    itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Champion deleted: " + champion);
                    break;

                case HATED:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getHatedOnesFile();
                    this.information.getHatedOnes().remove(champion);

                    //itUpdate = this.information.getHatedOnes().entrySet().iterator();
                    this.temporal.putAll(this.information.getHatedOnes());
                    itUpdate = this.temporal.entrySet().iterator();

                    jLabel1.setText("Champion deleted: " + champion);
                    break;

                case COMBOS:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() + path.getPersonalCombosFile();
                    this.information.getPersonalCombos().remove(champion);

                    //itUpdate = this.information.getPersonalCombos().entrySet().iterator();
                    this.temporal.putAll(this.information.getPersonalCombos());
                    itUpdate = this.temporal.entrySet().iterator();
                    
                    jLabel1.setText("Combo deleted: Combo number " + champion);
                    break;

                default:
                    //System.out.println ("Unknow file type to load (fatal error)");
                    //System.out.println ("No changes has been made");
                    break;
            }

        }
        
        modifyThirdUpdateButton.setText("Update!!!");
        modifyThirdUpdateButton.setBackground(Color.orange);
        modifyThirdUpdateButton.setForeground(Color.black);
    }//GEN-LAST:event_addOrDeleteButtonActionPerformed

    private void modifyThirdUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyThirdUpdateButtonActionPerformed
        // TODO add your handling code here:
        String fileLine = null;
        int comboId = 1;

        try {
            // IN THEORY, THIS SHOULD BE DONE USING "FILEWORKER" CLASS, BUT CONSIDERING THE SIZE
            // OF THE CODE, THE OPERATIONS ARE PERFORMED MANUALLY HERE (TO AVOID PASS SO MUCH PARAMETERS)

            if (this.objective.equals(objective.COMBOS) && (fileType.equals(fileType.ADD))) {

                if (this.information.getPersonalCombos().isEmpty()) {
                    this.information.getPersonalCombos().put("1", this.combo);
                    this.combo = "";
                }

                else {
                    while (this.information.getPersonalCombos().containsKey(Integer.toString(comboId))) {
                        comboId ++;
                    }
                    this.information.getPersonalCombos().put(Integer.toString(comboId), this.combo);
                    this.combo = "";
                }

                this.temporal.putAll(this.information.getPersonalCombos());
                itUpdate = this.temporal.entrySet().iterator();
            }

            PrintWriter writer = new PrintWriter(this.fullOutputPath);

            while (itUpdate.hasNext()) {
                Map.Entry pair = (Map.Entry)itUpdate.next();
                fileLine = pair.getKey() + ":" + pair.getValue();
                writer.println(fileLine.replace(" ", ""));
                itUpdate.remove(); // avoids a ConcurrentModificationException
            }

            writer.close();

            itUpdate = null;
            this.temporal.clear();

            jLabel1.setText("Data uptaded");

            // Return elements to neutral state
            jComboBox1.setSelectedItem("");
            topBox.setSelected(false);
            midBox.setSelected(false);
            adcBox.setSelected(false);
            supportBox.setSelected(false);
            jungleBox.setSelected(false);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Modify_Third_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modifyThirdUpdateButton.setText("Update");
        modifyThirdUpdateButton.setBackground(originalB);
        modifyThirdUpdateButton.setForeground(originalF);

    }//GEN-LAST:event_modifyThirdUpdateButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Modify_Third_UI().setVisible(true);
                this.run();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox adcBox;
    private javax.swing.JButton addOrDeleteButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox jungleBox;
    private javax.swing.JCheckBox midBox;
    private javax.swing.JButton modifyThirdUpdateButton;
    private javax.swing.JCheckBox supportBox;
    private javax.swing.JCheckBox topBox;
    // End of variables declaration//GEN-END:variables

}
