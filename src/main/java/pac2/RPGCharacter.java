package pac2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RPGCharacter {

    private int id;
    private static int nextId = 0;
    private String name = "Name";
    private final static String forbiddenChars = "[~!@#$%^&*()]";
    private int level = 1;
    private int life = 100;
    private boolean isAlive = true;
    private LocalDate lastDeath = null;
    private char alignment = 'N';

    public RPGCharacter() {
        setId();
    }

    public RPGCharacter(String name, char alignment) {
        this();
        setName(name);
        setAlignment(alignment);
    }

    public RPGCharacter(String name, char alignment, int life) {
        this(name, alignment);
        this.life = life;
    }

    public int getId() {
        return id;
    }

    private void setId() {
        this.id = this.nextId;
        incNextId();
    }

    public static int getNextId() {
        return nextId;
    }

    private void incNextId() {
        this.nextId = nextId + 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        setName(name, this.forbiddenChars);
    }

    public void setName(String name, String forbiddenChars) {
        String newName = new String();
        if (null != name && (!name.isEmpty() || !name.isBlank())) {
            newName = Arrays.stream(name.trim().replaceAll(" +", " ").split(" "))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                    .collect(Collectors.joining());
        }
        if (newName.matches(".*[" + forbiddenChars + "].*")) {
            System.out.println("[ERROR] Name cannot contain any of the forbidden chars");
        } else {
            this.name = newName;
        }
    }

    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public void incLevel() {
        this.level = level + 1;
    }

    public int getLife() {
        return life;
    }

    private void setLife(int life) {
        if (life <= 0) {
            System.out.println("[ERROR] Life must be a positive number");
        } else {
            this.life = life;
        }
    }

    public void updateLife(int life) {
        if (this.life == 0) {
            System.out.println("[ERROR] The character is dead");
        } else if (this.life > 0 && life > (this.life * 0.5)) {
            System.out.println("[ERROR] A character cannot increase its life more than 50% in a single healing");
        } else {
            this.life += life;
            if (this.life <= 0) {
                this.life = 0;
                setAlive(false);
            }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    private void setAlive(boolean alive) {
        if (!alive) setLastDeath(LocalDate.now());
        isAlive = alive;
    }

    public boolean resurrect() {
        if (this.isAlive) return false;
        this.isAlive = true;
        setLife(1);
        return true;
    }

    public LocalDate getLastDeath() {
        return lastDeath;
    }

    private void setLastDeath(LocalDate lastDeath) {
        this.lastDeath = lastDeath;
    }

    public char getAlignment() {
        return alignment;
    }

    public void setAlignment(char alignment) {
        final Character[] ALLOWED_ALIGNMENTS = {'A', 'H', 'N'};
        if (!Arrays.stream(ALLOWED_ALIGNMENTS).anyMatch(a -> a == alignment)) {
            System.out.println("[ERROR] Alignment must be a valid value ('H', 'A' or 'N')");
        } else {
            this.alignment = alignment;
        }
    }
}
