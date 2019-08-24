package pegorov.lesson1;

public abstract class Barrier {
    private String name;

    public Barrier(String name) {
        this.name = name;
    }

    protected abstract boolean moving(Human human);

    protected abstract boolean moving(Robot robot);

    protected abstract boolean moving(Cat cat);

    public String getName() {
        return name;
    }
}
