/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package demog;

import Characters.ClassType;
import Characters.Enemy;
import Game.EnemyAI;
import Game.EnemyAI.EnemyAction;
import Game.Items;
import Game.PartyClass;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author hp
 */

public class Gameframe extends javax.swing.JFrame {
    // Store original HUD buttons so we can restore them
    
    private ArrayList<ClassType> party;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Gameframe.class.getName());

    /**
     * Creates new form Gameframe
     */
    private javax.swing.JPanel buttonPanel;
    private ClassType ActiveChar;
    private PartyClass partySystem;
    private Items inventory;
    private boolean isTaunted = false;
    private EnemyAI ai = new EnemyAI();
    private Enemy tauntingEnemy;

   public Gameframe(ArrayList<ClassType> party) {
       
       
    initComponents();
    
    this.party = party;     
    this.partySystem = new PartyClass(party);
    this.inventory = new Items();
    
    
    ArrayList<EnemyAI.EnemyAction> actions = ai.enemyTurn();

    for (EnemyAI.EnemyAction action : actions) {
    handleAction(action);
    }
    
    
    jButton3.addActionListener(e -> showSkillsHud());
    jButton4.addActionListener(e -> showItemsHud());
    jButton1.addActionListener(e -> handleAttack());
    jButton2.addActionListener(e -> handleDefend());
    setResizable(false);
    
    
    setEnemyImage(jLabel3, "/images/Enemy/Brute.png", 100, 100);
    setEnemyImage(jLabel4, "/images/Enemy/BUGGER.png", 100, 100);
    setEnemyImage(jLabel5, "/images/Enemy/GOOMBAFU.jpg", 120, 120);
    setEnemyImage(jLabel6, "/images/Enemy/Fire Spirit.jpg", 90, 90);
    setEnemyImage(jLabel7, "/images/Enemy/Enemy1.jpg", 90, 90);

    // Create a dedicated button panel with GridLayout (5 equal columns)
    buttonPanel = new javax.swing.JPanel(new java.awt.GridLayout(1, 5, 4, 0));
    buttonPanel.setPreferredSize(new java.awt.Dimension(jPanel1.getPreferredSize().width, 127));

    // Move the original buttons INTO buttonPanel
    buttonPanel.add(jButton1);
    buttonPanel.add(jButton2);
    buttonPanel.add(jButton3);
    buttonPanel.add(jButton5);
    buttonPanel.add(jButton4);

    // Add buttonPanel to jPanel1 at the SOUTH position
    jPanel1.setLayout(new java.awt.BorderLayout());
    jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
    jPanel1.add(buttonPanel, java.awt.BorderLayout.SOUTH);

    
     if (!party.isEmpty()) {
        ActiveChar = party.get(0); // FIRST PICK = FIRST TURN
    }
     
     updatePartyUI();
}
    
private void setLabelImage(javax.swing.JLabel label, String path) {

    java.net.URL location = getClass().getResource(path);

    if (location == null) {
        System.out.println("Image not found: " + path);
        return;
    }

    label.setIcon(new javax.swing.ImageIcon(location));
}
   
private void setEnemyImage(javax.swing.JLabel label, String path, int w, int h) {

    ImageIcon icon = new ImageIcon(getClass().getResource(path));

    Image img = icon.getImage();
    Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

    label.setIcon(new ImageIcon(scaled));
    label.setText("");
}
private void updatePartyUI() {

    ArrayList<ClassType> p = partySystem.getParty();

    
    if (p.size() < 3) return;
    
    
    // Class NAMEs
    jLabel1.setText(p.get(0).getName());
    jLabel2.setText(p.get(1).getName());
    jLabel8.setText(p.get(2).getName());
    
    
    //Find what character User picked
    setLabelImage(jLabel1, p.get(0).getImagePath());
    setLabelImage(jLabel2, p.get(1).getImagePath());
    setLabelImage(jLabel8, p.get(2).getImagePath());

    jLabel1.setForeground(
        p.get(0) == ActiveChar ? java.awt.Color.GREEN : java.awt.Color.BLACK
    );

    jLabel2.setForeground(
        p.get(1) == ActiveChar ? java.awt.Color.GREEN : java.awt.Color.BLACK
    );

    jLabel8.setForeground(
        p.get(2) == ActiveChar ? java.awt.Color.GREEN : java.awt.Color.BLACK
    );
}

 private void showSkillsHud() {
    buttonPanel.removeAll();
    
    // Set columns to skills count + 1 (for back button)
    buttonPanel.setLayout(new java.awt.GridLayout(1, ActiveChar.getSkill().size() + 1, 4, 0));
    
    //Back Button
    java.awt.Button backBtn = new java.awt.Button("Back");

    java.awt.Font hudFont = new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 24);
    
        for (String skillName : ActiveChar.getSkill()) {
        java.awt.Button btn = new java.awt.Button(skillName);
        btn.setFont(hudFont);
        btn.setBackground(new java.awt.Color(204, 255, 153));
        btn.addActionListener(e -> useSkill(skillName)); // hook per button
        buttonPanel.add(btn);
    }

    
    backBtn.setFont(hudFont);
    backBtn.setBackground(new java.awt.Color(200, 200, 200));
    backBtn.addActionListener(e -> restoreOriginalHud());
    buttonPanel.add(backBtn);
        




    buttonPanel.revalidate();
    buttonPanel.repaint();
}

private void showItemsHud() {
    buttonPanel.removeAll();
    
    // Set columns to skills count + 1 (for back button)
    buttonPanel.setLayout(new java.awt.GridLayout(1, inventory.getItems().size() + 1, 4, 0));
    
    //Same for Items
    java.awt.Button backBtn = new java.awt.Button("Back");

    java.awt.Font hudFont = new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 24);

    for (String itemName : inventory.getItems()) {
        java.awt.Button btn = new java.awt.Button(itemName);

        btn.setFont(hudFont);
        btn.setBackground(new java.awt.Color(255, 255, 120));

        String selectedItem = itemName;

        btn.addActionListener(e -> useItem(selectedItem));

        buttonPanel.add(btn);
    }
    
    backBtn.setFont(hudFont);
    backBtn.setBackground(new java.awt.Color(200, 200, 200));
    backBtn.addActionListener(e -> restoreOriginalHud());
    buttonPanel.add(backBtn);
    
    buttonPanel.revalidate();
    buttonPanel.repaint();
}

private void useItem(String itemName) {

    System.out.println(ActiveChar.getName() + " used " + itemName + "!");

    switch (itemName) {

        case "Health Potion":
            System.out.println("Healed HP!");
            break;

        case "Mana Elixir":
            System.out.println("Restored Mana!");
            break;

        case "Revive Scroll":
            System.out.println("Revived ally!");
            break;

        case "Fire Stone":
            System.out.println("Buffed fire damage!");
            break;
    }

    nextTurn();
    restoreOriginalHud();
}
private void restoreOriginalHud() {
    buttonPanel.removeAll();
    buttonPanel.add(jButton1);
    buttonPanel.add(jButton2);
    buttonPanel.add(jButton3);
    buttonPanel.add(jButton5);
    buttonPanel.add(jButton4);

    buttonPanel.revalidate();
    buttonPanel.repaint();
}

    private void handleAttack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void handleDefend() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

private void useSkill(String skillName) {

    System.out.println(ActiveChar.getName() + " used " + skillName + "!");

    // TODO: apply damage / effects here

    nextTurn();        // switch to next character
    restoreOriginalHud();
}

private void nextTurn() {
    partySystem.nextTurn();

    ActiveChar = partySystem.getActiveChar();

    System.out.println("Now playing: " + ActiveChar.getName());
    
    updatePartyUI(); 
}

private void handleAction(EnemyAI.EnemyAction action) {

    switch (action.type) {

        case ATTACK:
            System.out.println(action.actor.name + " attacks!");
            break;

        case DEFEND:
            System.out.println(action.actor.name + " defends!");
            action.actor.defense += 2;
            break;

        case TAUNT:
            System.out.println(action.actor.name + " taunts!");

            tauntingEnemy = action.actor;
            isTaunted = true;
            break;
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenu3.setText("jMenu3");

        jMenuItem1.setText("jMenuItem1");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        jPanel2.setBackground(new java.awt.Color(102, 204, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 10));
        jPanel2.setForeground(new java.awt.Color(51, 51, 51));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");

        jLabel3.setText("E1");
        jLabel3.setName("Enemy1"); // NOI18N

        jLabel4.setText("E2");
        jLabel4.setName("Enemy2"); // NOI18N

        jLabel5.setText("E3");
        jLabel5.setName("Enemy3"); // NOI18N

        jLabel6.setText("F1");
        jLabel6.setName("Fenemy1"); // NOI18N

        jLabel7.setForeground(new java.awt.Color(102, 102, 255));
        jLabel7.setText("F2");
        jLabel7.setName("Fenemy2"); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jLabel3.getAccessibleContext().setAccessibleName("Enemy1");
        jLabel4.getAccessibleContext().setAccessibleName("Enemy2");
        jLabel5.getAccessibleContext().setAccessibleName("Enemy3");
        jLabel7.getAccessibleContext().setAccessibleName("Fenemy2");

        jButton1.setBackground(new java.awt.Color(255, 51, 102));
        jButton1.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jButton1.setText("Attack");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.black, java.awt.Color.darkGray));
        jButton1.setName("Attack"); // NOI18N

        jButton2.setBackground(new java.awt.Color(102, 102, 255));
        jButton2.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jButton2.setText("Defend");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.black, java.awt.Color.darkGray));
        jButton2.setName("Defend"); // NOI18N

        jButton3.setBackground(new java.awt.Color(255, 51, 255));
        jButton3.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jButton3.setText("Skills");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.black, java.awt.Color.darkGray));
        jButton3.setName("Defend"); // NOI18N

        jButton4.setBackground(new java.awt.Color(153, 255, 51));
        jButton4.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jButton4.setText("Items");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.black, java.awt.Color.darkGray));
        jButton4.setName("Items"); // NOI18N
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setBackground(new java.awt.Color(255, 255, 51));
        jButton5.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jButton5.setText("Flee");
        jButton5.setActionCommand("RUN");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.black, java.awt.Color.darkGray));
        jButton5.setName("RUN"); // NOI18N
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(this::jButton5ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("Foreground");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Map Mframe = new Map();
    
        Mframe.pack();
        Mframe.setLocationRelativeTo(this); // centers
        Mframe.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables



}
