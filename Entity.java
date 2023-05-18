import java.util.Random;

public class Entity {
    protected int mHP; // Max Health Points
    protected int cHP; // Current Health Points
    protected int def; // Defense
    protected int str; // Strength

    public Entity(int hp, int def, int str){
        this.mHP = hp;
        this.cHP = hp;
        this.def = def;
        this.str = str;
    };

    // Precondition: Values must be able to be printed easily
    public Object[] getAllStats(){
        return new Object[] {this.cHP, this.str, this.def};
    }

    public int getHp(){ return cHP; }
    public int getDef(){ return def; }
    public int getStr(){ return str; }

    public int damageCalc(Entity attacker){
        // Damage = 5 x Strength/(Defense + 1) x Crit
        Random random = new Random();
        int crit = 1;

        // 10% chance to deal x2 damage
        if(random.nextInt(10) == 1){
            crit = 2;
        }

        return attacker.getStr() / (getDef() + 1) * crit;
    }

    // Returns if the target is dead or not
    public boolean removeHP(int num){
        cHP -= num;
        if(cHP <= 0)
            return true;
        return false;
    }

    public void setDef (int num){
        def = num;
    }
}
