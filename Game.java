import javax.swing.*;

public class Game extends JComponent{
    // Data
    private final Enemy[] enemyHandbook = {
        new Enemy(
            "Thief", 600, 0, 3, "None",
            new String[]{
                "You're just minding your own business when a thief tries to rob you!",
                "\n\nThe thief takes a slash at you, dealing ",
                "\n\nWith an sad thump, the thief falls flat, knocked out.",
                "\n\nThe last thing you hear is a crazy cackle before you black out"
            })
    };

    // Player and Enemy
    private final Player player = new Player();
    private final Enemy enemy = enemyHandbook[0].copyOfSelf();

    // Create the window + dynamic UI elements
    private final JFrame frame = new JFrame("Turn Based Combat");
    private final JTextArea output = new JTextArea();
    private final JButton[] actions = {new JButton("Attack"), new JButton("Parry"), new JButton("Heal")};
    private final JLabel[] playerStats = {
        new JLabel("Player HP: "), new JLabel("Strength: "),
        new JLabel("Defense: "), new JLabel("Heals Left: ")
    };
    private final JLabel[] enemyStats = {
        new JLabel("Enemy HP: "), new JLabel("Strength: "),
        new JLabel("Defense: "), new JLabel("Quirk: ")
    };

    public Game(){
        new UI(frame, actions, playerStats, enemyStats, output, enemyHandbook[0].getFlavorText()[0]);
        this.setUI();

        // Setup Action Listeners
        actions[0].addActionListener(e -> {
            int dmg = enemy.damageCalc(player);
            boolean enemyDead = enemy.removeHP(dmg);

            output.setText(output.getText() + "You strike, dealing " + dmg + " DMG");

            if(!enemyDead){
                enemyStrike();
                setUI();
            }else{
                setUI();
                player.levelUp(frame);
            }
            // Attack
        });
        actions[1].addActionListener(e -> {
            player.setDef(player.getDef() + 1);
            output.setText(output.getText() + "\n\nYou ready yourself for the attack.");

            int initHP = player.getHp();
            enemyStrike();
            int changeInHP = initHP - player.getHp();
            int reboundDmg = (int) (changeInHP * 2.25);
            enemy.removeHP(reboundDmg);

            player.setDef(player.getDef() - 1);

            output.setText(output.getText() + "You lash back dealing " + reboundDmg + " DMG!");

            // Parry
            setUI();
        });
        actions[2].addActionListener(e -> {
            // Heal
            setUI();
        });
    }

    // Helper Function for Enemy attacks
    private void enemyStrike(){
        int dmg = player.damageCalc(enemy);
        output.setText(output.getText() + enemy.getFlavorText()[1] + dmg + " DMG");

        if(player.removeHP(dmg)){
            output.setText(output.getText() + enemy.getFlavorText()[3]);
            JOptionPane.showMessageDialog(frame, "You died!");
            for(JButton b : actions){
                b.setEnabled(false);
            }
        }
    }

    // Helper Function for setting UI, overloaded
    private void setUI(){
        updateListOfUI(player.getAllStats(), playerStats);
        updateListOfUI(enemy.getAllStats(), enemyStats);
    }

    private void updateListOfUI(Object[] data, JLabel[] list) {
        for(int i = 0; i < list.length; i++){
            String temp = list[i].getText();

            list[i].setText(
                temp.substring(0, temp.indexOf(":") + 1) +
                " " + data[i]
            );
        }
    }
}
