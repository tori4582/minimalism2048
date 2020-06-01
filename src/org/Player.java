
package org;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Player extends javax.swing.JFrame {

    JButton[][] buttonGrid;
    
    private Engine2048 playerEngine;
    
    private int x, y;
    
    private boolean playable = true;
    
    private int score = 0;
    private int best = 0;
    
    private File recordFile = new File(getClass().getResource("record.dat").getFile());
    
    public Player() {
        initComponents();
        
        buttonGrid = new JButton[4][4];
        buttonGrid[0][0] = this.Grid_11; 
        buttonGrid[0][1] = this.Grid_12;
        buttonGrid[0][2] = this.Grid_13;
        buttonGrid[0][3] = this.Grid_14;
        
        buttonGrid[1][0] = this.Grid_21; 
        buttonGrid[1][1] = this.Grid_22;
        buttonGrid[1][2] = this.Grid_23;
        buttonGrid[1][3] = this.Grid_24;
        
        buttonGrid[2][0] = this.Grid_31; 
        buttonGrid[2][1] = this.Grid_32;
        buttonGrid[2][2] = this.Grid_33;
        buttonGrid[2][3] = this.Grid_34;
        
        buttonGrid[3][0] = this.Grid_41; 
        buttonGrid[3][1] = this.Grid_42;
        buttonGrid[3][2] = this.Grid_43;
        buttonGrid[3][3] = this.Grid_44;
        
        playerEngine = new Engine2048();
        
        try {
            requestRecord();
        } catch (NoSuchElementException ex) {
            this.best = 0;
        }
        
        this.label_best.setText(String.valueOf(best));
       
    }
    
    private void updateRecord(int score) {
        try {
            recordFile.delete();
            recordFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter writer = new FileWriter(recordFile)) {
             writer.write(String.valueOf(score));
        } catch (IOException ex) {
             ex.printStackTrace();
        }
    }
    
    private void requestRecord() {
        try (Scanner reader = new Scanner(recordFile)) {
            String t = reader.nextLine();
            this.best = t.equals("") ? 0 : Integer.valueOf(t);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void playSound(String filename) {
        if (checkerPlaySound.getState())
        try {
            
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                new File(getClass().getResource(filename).getFile()).getAbsoluteFile()
            );
            
            Clip audioFile = AudioSystem.getClip();
            audioFile.open(audioStream);
            audioFile.start();
                
        } catch (IOException | 
                 LineUnavailableException |
                 UnsupportedAudioFileException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    private void lockBoard() {
        JButton maxButton = 
            buttonGrid[Engine2048.FIRST_LIMIT][Engine2048.FIRST_LIMIT];
        
        for (int i = Engine2048.FIRST_LIMIT; i < Engine2048.BOARD_SIZE; i++) {
             for (int j = Engine2048.FIRST_LIMIT; j < Engine2048.BOARD_SIZE; j++) {
                  buttonGrid[i][j].setEnabled(false);
                  buttonGrid[i][j].setSelected(false);
                  if (Integer.valueOf(buttonGrid[i][j].getText()) > 
                      Integer.valueOf(maxButton.getText()))
                           maxButton = buttonGrid[i][j];
             }
             
         }
        maxButton.setSelected(true);
        
        if (score > best) {
            updateRecord(score);
        }
        
        
    }
    
    public void normalizeBoard() {
        for (int i = Engine2048.FIRST_LIMIT; i < Engine2048.BOARD_SIZE; i++) {
             for (int j = Engine2048.FIRST_LIMIT; j < Engine2048.BOARD_SIZE; j++) {
                  buttonGrid[i][j].setEnabled(true);
                  buttonGrid[i][j].setSelected(false);
             }
             
         }
    }
    
    private void updateBoard() {
        
        this.buttonGrid[x][y].setSelected(false);
        
        x = playerEngine.getAddedX();
        y = playerEngine.getAddedY();
        
        this.buttonGrid[x][y].setSelected(true);
        
        if (!playerEngine.isMoveable()) {
            System.out.println("User lost the game");
            lockBoard();
            
        }
        
        
        if (!playerEngine.getPassedFlag() && (playerEngine.getMax() == 2048)) {
            playerEngine.flipPassedFlag();
            playSound("win.wav");
        }
        
        if (!playerEngine.isMoveable() && playable) {
            playSound("lose.wav");
            playable = !playable;
            String name = JOptionPane.showInputDialog(
                null,
                "Your score : " + playerEngine.getTotalScore() + "\nWhat is your name?",
                "GUI-Executer",
                JOptionPane.INFORMATION_MESSAGE
            );
            
        }

        for (int i = Engine2048.FIRST_LIMIT; i < Engine2048.BOARD_SIZE; i++) {
            for (int j = Engine2048.FIRST_LIMIT; j < Engine2048.BOARD_SIZE; j++) {
                buttonGrid[i][j].setText(
                           (playerEngine.get(i, j) == 0) 
                               ? " " 
                               : Integer.toString(playerEngine.get(i, j))
                );
                
            }
        }
        
        this.score = playerEngine.getTotalScore();
        
        this.label_score.setText(String.valueOf(this.score));
        if (this.best < this.score) {
            this.label_best.setText(String.valueOf(this.score));
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        Grid_11 = new javax.swing.JButton();
        Grid_12 = new javax.swing.JButton();
        Grid_13 = new javax.swing.JButton();
        Grid_14 = new javax.swing.JButton();
        Grid_21 = new javax.swing.JButton();
        Grid_22 = new javax.swing.JButton();
        Grid_23 = new javax.swing.JButton();
        Grid_24 = new javax.swing.JButton();
        Grid_31 = new javax.swing.JButton();
        Grid_32 = new javax.swing.JButton();
        Grid_33 = new javax.swing.JButton();
        Grid_34 = new javax.swing.JButton();
        Grid_41 = new javax.swing.JButton();
        Grid_42 = new javax.swing.JButton();
        Grid_43 = new javax.swing.JButton();
        Grid_44 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        label_score = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        label_best = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        checkerPlaySound = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        jButton3.setText("jButton1");

        jLabel1.setText("jLabel1");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minimalism 2048");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFocusable(false);

        Grid_11.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_11.setFocusPainted(false);
        Grid_11.setFocusable(false);

        Grid_12.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_12.setFocusPainted(false);
        Grid_12.setFocusable(false);

        Grid_13.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_13.setFocusPainted(false);
        Grid_13.setFocusable(false);

        Grid_14.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_14.setFocusPainted(false);
        Grid_14.setFocusable(false);

        Grid_21.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_21.setFocusPainted(false);
        Grid_21.setFocusable(false);

        Grid_22.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_22.setFocusPainted(false);
        Grid_22.setFocusable(false);

        Grid_23.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_23.setFocusPainted(false);
        Grid_23.setFocusable(false);

        Grid_24.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_24.setFocusPainted(false);
        Grid_24.setFocusable(false);

        Grid_31.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_31.setFocusPainted(false);
        Grid_31.setFocusable(false);

        Grid_32.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_32.setFocusPainted(false);
        Grid_32.setFocusable(false);

        Grid_33.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_33.setFocusPainted(false);
        Grid_33.setFocusable(false);

        Grid_34.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_34.setFocusPainted(false);
        Grid_34.setFocusable(false);

        Grid_41.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_41.setFocusPainted(false);
        Grid_41.setFocusable(false);

        Grid_42.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_42.setFocusPainted(false);
        Grid_42.setFocusable(false);

        Grid_43.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_43.setFocusPainted(false);
        Grid_43.setFocusable(false);

        Grid_44.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        Grid_44.setFocusPainted(false);
        Grid_44.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Grid_11, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_14, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Grid_21, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_22, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_23, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_24, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Grid_31, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_32, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_34, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Grid_41, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_42, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_43, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Grid_44, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grid_11, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_14, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grid_21, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_22, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_23, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_24, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grid_31, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_32, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_34, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grid_41, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_42, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_43, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Grid_44, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Score:");

        label_score.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label_score.setText("NOW");

        jLabel4.setText("Your Best:");

        label_best.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label_best.setText("PAST");

        jMenu1.setText("Game");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        checkerPlaySound.setSelected(true);
        checkerPlaySound.setText("Play Sound");
        jMenu1.add(checkerPlaySound);
        jMenu1.add(jSeparator1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        jMenuItem6.setText("About");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_score, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_best, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(label_score))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label_best)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
                
    }//GEN-LAST:event_formKeyTyped

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.playerEngine.printBoard();
        this.updateBoard();
    }//GEN-LAST:event_formWindowOpened

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(playerEngine.isMoveable()){
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_A:
                playerEngine.swipeLeft();
                playSound("popsound.wav");
                System.out.println("User has activated the key: LEFT");
                break;
            case KeyEvent.VK_D:
                playerEngine.swipeRight();
                playSound("popsound.wav");
                System.out.println("User has activated the key: RIGHT");
                break;
            case KeyEvent.VK_W:
                playerEngine.swipeUp();
                playSound("popsound.wav");
                System.out.println("User has activated the key: UP");
                break;
            case KeyEvent.VK_S:
                playerEngine.swipeDown();
                playSound("popsound.wav");
                System.out.println("User has activated the key: DOWN");
                break;
        }
        }
        this.updateBoard();
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.out.println("Menu->Command : Require a new game");
        this.playerEngine = new Engine2048();
        this.normalizeBoard();
        this.updateBoard();
        playable = true;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        System.out.println("Menu->Command : Close the window");
        this.setVisible(false);
        System.out.println("Closed. Thread isolated");
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JOptionPane.showMessageDialog(this,
            "MINIMALISM 2048\n\n" +
            "This game is created by Pham Quoc Hung (Steve Alan).\n"+
            "Version: 1.0\n" +
            "I hope you enjoy it ;)\n\n"
            , "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | 
                 InstantiationException | 
                 IllegalAccessException | 
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Player().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Grid_11;
    private javax.swing.JButton Grid_12;
    private javax.swing.JButton Grid_13;
    private javax.swing.JButton Grid_14;
    private javax.swing.JButton Grid_21;
    private javax.swing.JButton Grid_22;
    private javax.swing.JButton Grid_23;
    private javax.swing.JButton Grid_24;
    private javax.swing.JButton Grid_31;
    private javax.swing.JButton Grid_32;
    private javax.swing.JButton Grid_33;
    private javax.swing.JButton Grid_34;
    private javax.swing.JButton Grid_41;
    private javax.swing.JButton Grid_42;
    private javax.swing.JButton Grid_43;
    private javax.swing.JButton Grid_44;
    private javax.swing.JCheckBoxMenuItem checkerPlaySound;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel label_best;
    private javax.swing.JLabel label_score;
    // End of variables declaration//GEN-END:variables
}
