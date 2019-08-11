/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Modify_Third_P_UI.java
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
//import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Alberto
 */
public class Modify_Third_P_UI extends javax.swing.JFrame {

    Flags fileType;
    Flags objective;
    Information information;
    Path path;
    Iterator itUpdate;
    String fullOutputPath;
    String combo;
    String championOb;
    LinkedHashMap<String, String> temporal;
    Color originalB, originalF;

    /** Creates new form Modify_Third_UI */
    public Modify_Third_P_UI(Information info, Flags fT, Flags obj, Path p, String chOb) {
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
        originalB = modifyThirdUpdateButton.getBackground();
        originalF = modifyThirdUpdateButton.getForeground();

        temporal = new LinkedHashMap<String, String>();
        objective = obj;
        championOb = chOb;
        information = info;
        
        Iterator it;
        
        if (fT.equals(fT.ADD)) {

            if (objective.equals(objective.PSTRONG)) {
               this.setTitle ("Add Personal Strong - LoL Assembler");
            }
            else if (objective.equals(objective.PWEAK)){
               this.setTitle ("Add Personal Weak - LoL Assembler");
            }
            else if (objective.equals(objective.PEVEN)){
               this.setTitle ("Add Personal Even - LoL Assembler");
            }
            else { // objective equal to PWELL
               this.setTitle ("Add Personal W. Well - LoL Assembler");
            }

            AddOrDelete.setText("Add One");
            DeleteAllButton.setVisible(false); // This button is not visible in ADD mode

            fileType = fT.ADD;
             
            it = information.getChampionList().entrySet().iterator();
            jComboBox1.addItem("");
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                jComboBox1.addItem(pair.getKey());
                //System.out.println(pair.getKey() + ":" + pair.getValue());
            }
        }
        else { // fileType is equal to DELETE
            String values = "";
            
            if (objective.equals(objective.PSTRONG)) {
                this.setTitle ("Delete Personal Strong - LoL Assembler");
                
                //it = information.getPersonalStrong().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (information.getPersonalStrong().isEmpty()) {
                    jLabel2.setText("Personal Strong File is empty");
                    jLabel3.setText("No data to delete");
                    AddOrDelete.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                    DeleteAllButton.setEnabled(false);
                }
                else {
                    values = information.getPersonalStrong().get(championOb);
                    for (String s: values.split("&&")) {
                        jComboBox1.addItem(s);
                    }
                }              
             }
             else if (objective.equals(objective.PWEAK)){
                this.setTitle ("Delete Personal Weak - LoL Assembler");
                
                //it = information.getPersonalWeak().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (information.getPersonalWeak().isEmpty()) {
                    jLabel2.setText("Personal Weak File is empty");
                    jLabel3.setText("No data to delete");
                    AddOrDelete.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                    DeleteAllButton.setEnabled(false);
                }
                else {
                    values = information.getPersonalWeak().get(championOb);
                    for (String s: values.split("&&")) {
                        jComboBox1.addItem(s);
                    }
                }              
             }
             else if (objective.equals(objective.PEVEN)){
                this.setTitle ("Delete Personal Even - LoL Assembler");
                
                //it = information.getPersonalEven().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (information.getPersonalEven().isEmpty()) {
                    jLabel2.setText("Personal Even File is empty");
                    jLabel3.setText("No data to delete");
                    AddOrDelete.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                    DeleteAllButton.setEnabled(false);
                }
                else {
                    values = information.getPersonalEven().get(championOb);
                    for (String s: values.split("&&")) {
                        jComboBox1.addItem(s);
                    }
                }    
             }
             else { // objective equal to PWELL
                this.setTitle ("Delete Personal W. Well - LoL Assembler");
                
                //it = information.getPersonalWell().entrySet().iterator();
                jComboBox1.addItem("");
                
                if (information.getPersonalWell().isEmpty()) {
                    jLabel2.setText("Personal Well File is empty");
                    jLabel3.setText("No data to delete");
                    AddOrDelete.setEnabled(false);
                    modifyThirdUpdateButton.setEnabled(false);
                    DeleteAllButton.setEnabled(false);
                }
                else {
                    values = information.getPersonalWell().get(championOb);
                    for (String s: values.split("&&")) {
                        jComboBox1.addItem(s);
                    }
                }
             }
            
            AddOrDelete.setText("Delete One");
            DeleteAllButton.setVisible(true);

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
        AddOrDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        modifyThirdUpdateButton = new javax.swing.JButton();
        DeleteAllButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 23, 37));

        AddOrDelete.setBackground(new java.awt.Color(54, 109, 90));
        AddOrDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        AddOrDelete.setForeground(new java.awt.Color(255, 255, 255));
        AddOrDelete.setText("AddDelt");
        AddOrDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddOrDeleteActionPerformed(evt);
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
        jLabel3.setText("all the champions to correctly apply the changes.");

        modifyThirdUpdateButton.setBackground(new java.awt.Color(184, 8, 12));
        modifyThirdUpdateButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        modifyThirdUpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        modifyThirdUpdateButton.setText("Update");
        modifyThirdUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyThirdUpdateButtonActionPerformed(evt);
            }
        });

        DeleteAllButton.setBackground(new java.awt.Color(54, 109, 90));
        DeleteAllButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        DeleteAllButton.setForeground(new java.awt.Color(255, 255, 255));
        DeleteAllButton.setText("Delete Entry");
        DeleteAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddOrDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(DeleteAllButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(modifyThirdUpdateButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddOrDelete)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteAllButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

    private void AddOrDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddOrDeleteActionPerformed
        // TODO add your handling code here:
        // Work with information
        
        String champion = jComboBox1.getSelectedItem().toString();
        //String fullOutputPath = null;

        if (fileType.equals(fileType.ADD)) {

            switch (this.objective) {

                case PSTRONG:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath()
                            + path.getPersonalStrongAgainst();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText(championOb.toUpperCase() + " is strong against: " + this.combo);
                    break;

                case PWEAK:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() 
                            + path.getPersonalWeakAgainst();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText(championOb.toUpperCase() + " is weak against: " + this.combo);
                    break;

                case PEVEN:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() 
                            + path.getPersonalEvenAgainst();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText(championOb.toUpperCase() + " is even against: " + this.combo);
                    break;

                case PWELL:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath() 
                            + path.getPersonalWorksWell();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText(championOb.toUpperCase() + " works well with: " + this.combo);
                    break;

                default:
                    System.out.println ("Unknow file type to load (fatal error)");
                    System.out.println ("No changes has been made");
                    break;
            }

            
        }
        else { // fileType equals to DELETE

            switch (this.objective) {

                case PSTRONG:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath()
                            + path.getPersonalStrongAgainst();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText("Champs to delete: " + this.combo);
                    break;

                case PWEAK:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath()
                            + path.getPersonalWeakAgainst();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText("Champs to delete: " + this.combo);
                    break;

                case PEVEN:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath()
                            + path.getPersonalEvenAgainst();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText("Champs to delete: " + this.combo);
                    break;

                case PWELL:
                    this.fullOutputPath = path.getMainPath() + path.getPersonalPath()
                            + path.getPersonalWorksWell();
                    this.combo = this.combo + champion + "-";

                    jLabel1.setText("Champs to delete: " + this.combo);
                    break;

                default:
                    System.out.println ("Unknow file type to load (fatal error)");
                    System.out.println ("No changes has been made");
                    break;
            }
 
        }
        
        modifyThirdUpdateButton.setText("Update!!!");
        modifyThirdUpdateButton.setBackground(Color.orange);
        modifyThirdUpdateButton.setForeground(Color.black);
    }//GEN-LAST:event_AddOrDeleteActionPerformed

    private void modifyThirdUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyThirdUpdateButtonActionPerformed
        // TODO add your handling code here:
        String fileLine = null;
        String newData = "";
        String [] list = null;
        boolean update = false;

        try {

            if (fileType.equals(fileType.ADD)) {
                switch (this.objective) {

                    case PSTRONG:
                        list = this.combo.split("-");

                        if ((this.information.getPersonalStrong().isEmpty())
                                || (!this.information.getPersonalStrong().containsKey(this.championOb))) {
                            for (String champ : list) {
                                newData = newData + champ + "&&";
                            }
                            this.information.getPersonalStrong().put(championOb, newData);
                        }
                        else {
                            newData = this.information.getPersonalStrong().get(this.championOb);
                            for (String champ : list) {
                                if (!this.information.getPersonalStrong().get(this.championOb).contains(champ)) {
                                    newData = newData + champ + "&&";
                                }
                            }

                            this.information.getPersonalStrong().put(championOb, newData);
                        }

                        this.temporal.putAll(this.information.getPersonalStrong());
                        
                        break;

                    case PWEAK:
                        list = this.combo.split("-");

                        if ((this.information.getPersonalWeak().isEmpty())
                                || (!this.information.getPersonalWeak().containsKey(this.championOb))) {
                            for (String champ : list) {
                                newData = newData + champ + "&&";
                            }
                            this.information.getPersonalWeak().put(championOb, newData);
                        }
                        else {
                            newData = this.information.getPersonalWeak().get(this.championOb);
                            for (String champ : list) {
                                if (!this.information.getPersonalWeak().get(this.championOb).contains(champ)) {
                                    newData = newData + champ + "&&";
                                }
                            }

                            this.information.getPersonalWeak().put(championOb, newData);
                        }

                        this.temporal.putAll(this.information.getPersonalWeak());
                        break;

                    case PEVEN:
                        list = this.combo.split("-");

                        if ((this.information.getPersonalEven().isEmpty())
                                || (!this.information.getPersonalEven().containsKey(this.championOb))) {
                            for (String champ : list) {
                                newData = newData + champ + "&&";
                            }
                            this.information.getPersonalEven().put(championOb, newData);
                        }
                        else {
                            newData = this.information.getPersonalEven().get(this.championOb);
                            for (String champ : list) {
                                if (!this.information.getPersonalEven().get(this.championOb).contains(champ)) {
                                    newData = newData + champ + "&&";
                                }
                            }

                            this.information.getPersonalEven().put(championOb, newData);
                        }

                        this.temporal.putAll(this.information.getPersonalEven());
                        break;

                    case PWELL:
                        list = this.combo.split("-");

                        if ((this.information.getPersonalWell().isEmpty())
                                || (!this.information.getPersonalWell().containsKey(this.championOb))) {
                            for (String champ : list) {
                                newData = newData + champ + "&&";
                            }
                            this.information.getPersonalWell().put(championOb, newData);
                        }
                        else {
                            newData = this.information.getPersonalWell().get(this.championOb);
                            for (String champ : list) {
                                if (!this.information.getPersonalWell().get(this.championOb).contains(champ)) {
                                    newData = newData + champ + "&&";
                                }
                            }

                            this.information.getPersonalWell().put(championOb, newData);
                        }

                        this.temporal.putAll(this.information.getPersonalWell());
                        break;

                    default:
                        System.out.println ("Unknow file type to load (fatal error)");
                        System.out.println ("No changes has been made");
                        break;
                }
            }
            else { // DELETE OPERATION
                switch (this.objective) {

                    case PSTRONG:
                        if ((!this.information.getPersonalStrong().isEmpty())
                                && (this.information.getPersonalStrong().containsKey(this.championOb))) {

                            newData = this.information.getPersonalStrong().get(this.championOb);
                            list = this.combo.split("-");
                            for (String champ : list) {
                                if (this.information.getPersonalStrong().get(this.championOb).contains(champ)) {
                                    newData = newData.replace(champ+"&&", "");
                                }
                            }

                            this.information.getPersonalStrong().put(championOb, newData);
                            this.temporal.putAll(this.information.getPersonalStrong());
                            update = true;
                        }
                        
                        break;

                    case PWEAK:
                        if ((!this.information.getPersonalWeak().isEmpty())
                                && (this.information.getPersonalWeak().containsKey(this.championOb))) {
                            newData = this.information.getPersonalWeak().get(this.championOb);
                            list = this.combo.split("-");
                            for (String champ : list) {
                                if (this.information.getPersonalWeak().get(this.championOb).contains(champ)) {
                                    newData = newData.replace(champ+"&&", "");
                                }
                            }

                            this.information.getPersonalWeak().put(championOb, newData);
                            this.temporal.putAll(this.information.getPersonalWeak());
                            update = true;
                        }

                        break;

                    case PEVEN:
                        if ((!this.information.getPersonalEven().isEmpty())
                                && (this.information.getPersonalEven().containsKey(this.championOb))) {
                            newData = this.information.getPersonalEven().get(this.championOb);
                            list = this.combo.split("-");
                            for (String champ : list) {
                                if (this.information.getPersonalEven().get(this.championOb).contains(champ)) {
                                    newData = newData.replace(champ+"&&", "");
                                }
                            }

                            this.information.getPersonalEven().put(championOb, newData);
                            this.temporal.putAll(this.information.getPersonalEven());
                            update = true;
                        }

                        break;

                    case PWELL:
                        if ((!this.information.getPersonalWell().isEmpty())
                                && (this.information.getPersonalWell().containsKey(this.championOb))) {
                            newData = this.information.getPersonalWell().get(this.championOb);
                            list = this.combo.split("-");
                            for (String champ : list) {
                                if (this.information.getPersonalWell().get(this.championOb).contains(champ)) {
                                    newData = newData.replace(champ+"&&", "");
                                }
                            }

                            this.information.getPersonalWell().put(championOb, newData);
                            this.temporal.putAll(this.information.getPersonalWell());
                            update = true;
                        }

                        break;

                    default:
                        System.out.println ("Unknow file type to load (fatal error)");
                        System.out.println ("No changes has been made");
                        break;
                }
            }

            if ((fileType.equals(fileType.ADD)) || (update==true)) {
                itUpdate = this.temporal.entrySet().iterator();
                PrintWriter writer = new PrintWriter(this.fullOutputPath);

                while (itUpdate.hasNext()) {
                    Map.Entry pair = (Map.Entry)itUpdate.next();
                    fileLine = pair.getKey() + ":" + pair.getValue();
                    writer.println(fileLine);
                    itUpdate.remove(); // avoids a ConcurrentModificationException
                }

                writer.close();

                itUpdate = null;
                this.temporal.clear();
            }            

            jLabel1.setText("Data uptaded");
            jComboBox1.setSelectedItem("");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Modify_Third_P_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modifyThirdUpdateButton.setText("Update");
        modifyThirdUpdateButton.setBackground(originalB);
        modifyThirdUpdateButton.setForeground(originalF);

    }//GEN-LAST:event_modifyThirdUpdateButtonActionPerformed

    private void DeleteAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAllButtonActionPerformed
        // TODO add your handling code here:
        String fileLine = null;
        boolean update = false;
        
        int selectedOption = JOptionPane.showConfirmDialog(null,
                                  "Do you want to delete the full entry of "
                                  + championOb + "?", // Message of the window
                                  "Deleting champion entry", // Message of the header
                                  JOptionPane.YES_NO_OPTION);

        if (selectedOption == JOptionPane.YES_OPTION) {

            switch (this.objective) {

                case PSTRONG:
                    if ((!this.information.getPersonalStrong().isEmpty())
                            && this.information.getPersonalStrong().containsKey(championOb)) {
                        this.information.getPersonalStrong().remove(championOb);
                        update = true;
                    }
                    break;

                case PWEAK:
                    if ((!this.information.getPersonalWeak().isEmpty())
                            && this.information.getPersonalWeak().containsKey(championOb)) {
                        this.information.getPersonalWeak().remove(championOb);
                        update = true;
                    }
                    break;

                case PEVEN:
                    if ((!this.information.getPersonalEven().isEmpty())
                            && this.information.getPersonalEven().containsKey(championOb)) {
                        this.information.getPersonalEven().remove(championOb);
                        update = true;
                    }
                    break;

                case PWELL:
                    if ((!this.information.getPersonalWell().isEmpty())
                            && this.information.getPersonalWell().containsKey(championOb)) {
                        this.information.getPersonalWell().remove(championOb);
                        update = true;
                    }
                    break;

                default:
                    System.out.println ("Unknow file type to load (fatal error)");
                    System.out.println ("No changes has been made");
                    break;
            }

            jLabel1.setText("Data uptaded (Dont need to press Update)");
            jComboBox1.setSelectedItem("");

            if (update == true) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(this.fullOutputPath);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Modify_Third_P_UI.class.getName()).log(Level.SEVERE, null, ex);
                }

                while (itUpdate.hasNext()) {
                    Map.Entry pair = (Map.Entry)itUpdate.next();
                    fileLine = pair.getKey() + ":" + pair.getValue();
                    writer.println(fileLine.replace(" ", ""));
                    itUpdate.remove(); // avoids a ConcurrentModificationException
                }

                writer.close();

                itUpdate = null;
                this.temporal.clear();
            }
        }

    }//GEN-LAST:event_DeleteAllButtonActionPerformed

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
    private javax.swing.JButton AddOrDelete;
    private javax.swing.JButton DeleteAllButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton modifyThirdUpdateButton;
    // End of variables declaration//GEN-END:variables

}
