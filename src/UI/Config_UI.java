/*******************************************************************************
*   Project: LoL Assembler
*
*   File: Config_UI.java
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

import SourceCode.Flags;
import SourceCode.Information;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Alberto
 */
public class Config_UI extends javax.swing.JFrame {

    Information information;

    Color originalC, bgc, originalF;
    Font font;
    Flags startFirst;
    Flags playerNumber;
    int selected1, selected2;

    /** Creates new form Config_UI */
    public Config_UI(Information info) {
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

        this.setTitle ("Ranked Config - LoL Assembler");

        originalC = configAllyButton.getBackground();
        originalF = configAllyButton.getForeground();
        font = configAllyButton.getFont();
        
        selected1 = 0;
        selected2 = 0;
        information = info;

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
        jLabel1 = new javax.swing.JLabel();
        configStartButton = new javax.swing.JButton();
        configAllyButton = new javax.swing.JButton();
        configEnemyButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        config5playersButton = new javax.swing.JButton();
        config3playersButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 23, 37));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Who start first?");

        configStartButton.setBackground(new java.awt.Color(184, 8, 12));
        configStartButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        configStartButton.setForeground(new java.awt.Color(255, 255, 255));
        configStartButton.setText("Go");
        configStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configStartButtonActionPerformed(evt);
            }
        });

        configAllyButton.setBackground(new java.awt.Color(30, 68, 116));
        configAllyButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        configAllyButton.setForeground(new java.awt.Color(255, 255, 255));
        configAllyButton.setText("ALLIES");
        configAllyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configAllyButtonActionPerformed(evt);
            }
        });

        configEnemyButton.setBackground(new java.awt.Color(30, 68, 116));
        configEnemyButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        configEnemyButton.setForeground(new java.awt.Color(255, 255, 255));
        configEnemyButton.setText("ENEMIES");
        configEnemyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configEnemyButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Select number of players");

        config5playersButton.setBackground(new java.awt.Color(30, 68, 116));
        config5playersButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        config5playersButton.setForeground(new java.awt.Color(255, 255, 255));
        config5playersButton.setText("5vs5");
        config5playersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                config5playersButtonActionPerformed(evt);
            }
        });

        config3playersButton.setBackground(new java.awt.Color(30, 68, 116));
        config3playersButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        config3playersButton.setForeground(new java.awt.Color(255, 255, 255));
        config3playersButton.setText("3vs3");
        config3playersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                config3playersButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(configStartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(config5playersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(config3playersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(configAllyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(configEnemyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(config5playersButton)
                    .addComponent(config3playersButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(configAllyButton)
                    .addComponent(configEnemyButton))
                .addGap(45, 45, 45)
                .addComponent(configStartButton))
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

    private void configStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configStartButtonActionPerformed
        // TODO add your handling code here:
        if ((selected1 == 1) && (selected2 == 1)) {
            Ranked_UI rankedUI = new Ranked_UI(this.information, Flags.RANKED, startFirst, playerNumber);
            rankedUI.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_configStartButtonActionPerformed

    private void configAllyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configAllyButtonActionPerformed
        // TODO add your handling code here:
        configEnemyButton.setBackground(originalC);
        configEnemyButton.setForeground(originalF);
        configEnemyButton.setFont(font);
       
        configAllyButton.setBackground(Color.orange);
        configAllyButton.setForeground(Color.BLACK); 
        
        startFirst = Flags.ALLYSTART;
        selected1 = 1;
    }//GEN-LAST:event_configAllyButtonActionPerformed

    private void configEnemyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configEnemyButtonActionPerformed
        // TODO add your handling code here:
        configAllyButton.setBackground(originalC);
        configAllyButton.setForeground(originalF);
        configAllyButton.setFont(font);
        
        configEnemyButton.setBackground(Color.orange);
        configEnemyButton.setForeground(Color.BLACK);      
        
        startFirst = Flags.ENEMYSTART;
        selected1 = 1;
    }//GEN-LAST:event_configEnemyButtonActionPerformed

    private void config5playersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_config5playersButtonActionPerformed
        // TODO add your handling code here:
        config3playersButton.setBackground(originalC);
        config3playersButton.setForeground(originalF);
        config3playersButton.setFont(font);        
        
        config5playersButton.setBackground(Color.orange);
        config5playersButton.setForeground(Color.BLACK);
        
        
        playerNumber = Flags.VS5;
        selected2 = 1;     
    }//GEN-LAST:event_config5playersButtonActionPerformed

    private void config3playersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_config3playersButtonActionPerformed
        // TODO add your handling code here:
        config5playersButton.setBackground(originalC);
        config5playersButton.setForeground(originalF);
        config5playersButton.setFont(font);        
        
        config3playersButton.setBackground(Color.orange);
        config3playersButton.setForeground(Color.BLACK);        
        
        playerNumber = Flags.VS3;
        selected2 = 1;  
    }//GEN-LAST:event_config3playersButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Config_UI().setVisible(true);
                this.run();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton config3playersButton;
    private javax.swing.JButton config5playersButton;
    private javax.swing.JButton configAllyButton;
    private javax.swing.JButton configEnemyButton;
    private javax.swing.JButton configStartButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
