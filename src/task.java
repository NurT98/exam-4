import java.util.Random;

public class task {
    private String name;
    private int age;
    private int health;
    private int mood;
    private int satiety;
    private boolean actionDoneToday = false;

    public task(String name, int age) {
        Random rand = new Random();
        this.name = name;
        this.age = age;
        this.health = rand.nextInt(61) + 20;
        this.mood = rand.nextInt(61) + 20;
        this.satiety = rand.nextInt(61) + 20;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHealth() {
        return health;
    }

    public int getMood() {
        return mood;
    }

    public int getSatiety() {
        return satiety;
    }

    public boolean isActionDoneToday() {
        return actionDoneToday;
    }

    public void setActionDoneToday(boolean state) {
        this.actionDoneToday = state;
    }

    public void updateHealth(int delta) {
        this.health = Math.min(100, Math.max(0, this.health + delta));
    }

    public void updateMood(int delta) {
        this.mood = Math.min(100, Math.max(0, this.mood + delta));
    }

    public void updateSatiety(int delta) {
        this.satiety = Math.min(100, Math.max(0, this.satiety + delta));
    }


}
