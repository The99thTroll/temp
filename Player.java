import javax.swing.*;

public class Player extends Entity{
    protected int heals = 3;

    public Player(){
        super(10, 0, 3);
    }

    public Object[] getAllStats(){
        return new Object[] {this.cHP, this.str, this.def, this.heals};
    }

    public boolean heal(){
        if(heals > 0){
            this.cHP += (int) (this.mHP * 0.5);
            if (cHP > mHP)
                cHP = mHP;
            return true;
        }
        return false;
    }

    public void levelUp(JFrame frame) {
        String[] options = {"+1 Strength",
                "+1 Defense", "+1 Max HP", "+1 Heal"};

        int buff = JOptionPane.showOptionDialog(frame,
                "Battle Clear! Select a Buff/Item.",
                "Rewards",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch(buff){
            case 0:
                str += 1;
                break;
            case 1:
                def += 1;
                break;
            case 2:
                mHP += 1;
                cHP += 1;
                break;
            case 3:
                heals += 1;
                break;
            default:
                break;
        }
    }
}
