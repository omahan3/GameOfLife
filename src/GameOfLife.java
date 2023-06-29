import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameOfLife {

    private static Environment world = null;
    private static final int DEFAULT_SPEED = 5;
    private static final int DEFAULT_LENGTH = 1000;
    private static ArrayList<String> history = new ArrayList<>();

    public static void displayWorld(int steps, int speed) throws InterruptedException {
        for (int i = 0; i < steps; i++) {
            System.out.println(world.toString());
            System.out.println();
            TimeUnit.MILLISECONDS.sleep(50L * speed);
            world.updatePopulation();
        }
        System.out.println(world.toString());
    }

    public static void displayWorld(int speed) throws InterruptedException {
        for (String state : history) {
            System.out.println(state);
            System.out.println();
            TimeUnit.MILLISECONDS.sleep(50L * speed);
        }
    }

    public static void displayWorldReverse(int speed) throws InterruptedException {
        for (int i = history.size() - 1; i >= 0 ; i--) {
            System.out.println(history.get(i));
            System.out.println();
            TimeUnit.MILLISECONDS.sleep(50L * speed);
        }
    }

    public static void printUsage() {
        System.out.println("Usage: java GameOfLife [pattern1] [vcoord1] [hcoord1] ... " +
                "[patternN] [vdoordN] [hcoordN] [steps/history] [speed/direction]");
    }

    public static boolean parsePatterns(String patternName, String vcoord, String hcoord) throws IllegalAccessException {

        int vertical = Integer.parseInt(vcoord);
        int horizontal = Integer.parseInt(hcoord);
        Field[] fields = Patterns.class.getFields();

        for (Field field : fields) {
            if (patternName.equalsIgnoreCase(field.getName())) {
                if (Patterns.makePattern(world, vertical, horizontal, (int[][]) field.get(null))) {
                    return true;
                } else {
                    System.out.println("The pattern " + patternName + " couldn't be added at the specified position.");
                    return false;
                }
            }
        }
        System.out.println("Pattern " + patternName + " was not found.");
        return false;
    }

    public static void recordSimulation(int maxStates) {

        boolean broke = false;
        for (int i = 0; i < maxStates; i++) {
            history.add(world.toString());
            world.updatePopulation();
            if (world.toString().equals(history.get(i))) {
                broke = true;
                break;
            }
        }
        if (!broke) {
            history.add(world.toString());
        }
    }

    public static void parseCommands(String first, String second) throws InterruptedException {
        if (first.equalsIgnoreCase("history")) {
            recordSimulation(DEFAULT_LENGTH);
            if (second.equalsIgnoreCase("forward")) {
                displayWorld(DEFAULT_SPEED);
            } else if (second.equalsIgnoreCase("reverse")) {
                displayWorldReverse(DEFAULT_SPEED);
            } else {
                printUsage();
            }
        } else {
            displayWorld(Integer.parseInt(first), Integer.parseInt(second));
        }
    }

    public static void main(String[] args) throws InterruptedException, IllegalAccessException {

        for (String arg : args) {
            System.out.println(arg);

        }

        if (args.length == 0) {
            printUsage();
            System.exit(0);
        }

        world = new Environment(40, 150);

        int numPatterns = (args.length) / 3;
        for (int i = 0; i < numPatterns; i++) {
            if (!parsePatterns(args[3*i], args[3*i+1], args[3*i+2])) {
                System.out.println("Program terminated: unknown pattern.");
                System.exit(0);
            }
        }

        parseCommands(args[args.length-2], args[args.length-1]);
        if (!history.isEmpty()) {
            System.out.println(history.size() + " states were recorded in history.");
        }
    }
}
