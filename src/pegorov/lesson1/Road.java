package pegorov.lesson1;

public class Road extends Barrier {
    private int length;

    public Road(String name, int length) {
        super(name);

        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    protected boolean moving(Human human) {
        System.out.println("The road " + super.getName() + " length: " + this.length);

        human.run();

        if (getLength() <= human.getRunDistance()) {
            System.out.println("Human run success");

            return true;
        } else {
            System.out.println("Human run unsuccessfully");

            return false;
        }
    }

    @Override
    protected boolean moving(Robot robot) {
        System.out.println("The road " + super.getName() + "length: " + this.length);

        robot.run();

        if (getLength() <= robot.getRunDistance()) {
            System.out.println("Robot run success");

            return true;
        } else {
            System.out.println("Robot run unsuccessfully");

            return false;
        }
    }

    @Override
    protected boolean moving(Cat cat) {
        System.out.println("The road " + super.getName() + "length: " + this.length);

        cat.run();

        if (getLength() <= cat.getRunDistance()) {
            System.out.println("Cat run success");

            return true;
        } else {
            System.out.println("Cat run unsuccessfully");

            return false;
        }
    }
}
