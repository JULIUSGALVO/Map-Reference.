import java.util.Scanner;
import java.util.Random;

public class main {
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;
    private static final int DONE = 0;

    private static int[] coordinate;
    private static int[] coordinateToGo;
    private static int[][] placesDistances;
    private static double fuelStamina;
    private static double speed;
    private static Random random = new Random();

    private static boolean checkInput (int input, int validate) { return input >= 1 && input <= validate; }
    
    public static void printUserPromptToGo() {
        System.out.println("Where you want to go?") ;
        System.out.println("1. house") ;
        System.out.println("2. mall") ;
        System.out.println("3. public market") ;
        System.out.println("4. gas station") ;
        System.out.println("5. church") ;
        System.out.println("6. hospital") ;
        System.out.println("7. school") ;
        System.out.print("Enter your answer (1-7):") ;    
    }

    public static void printUserPrompt() {
        System.out.println("What is your preferred mode of transportation") ;
        System.out.println("1. Bike") ;
        System.out.println("2. Car") ;
        System.out.print("Enter you preferred vehicle (1-2):") ;
    }

    public static void printMap(int[] place, int[][] distances) {
        System.out.print((place[0] == 0 && place[1] == 0) ? "    X   " : "  house(" + distances[0][0] + "m)");                                                                    
        System.out.print(" -------------- ");                                                            
        System.out.print((place[0] == 0 && place[1] == 2) ? "       X       " : " public market(" + distances[0][2] + "m)");
        System.out.print(" ------------------ ");
        System.out.println((place[0] == 0 && place[1] == 4) ? "   X   " : " school(" + distances[0][4] + "m)");
        
        System.out.print("   |   ");        
        System.out.print("                            |       ");
        System.out.println("                            |       ");
        System.out.print("   |   ");
        System.out.print("                            |       ");
        System.out.println("                            |       ");
        System.out.print("   |   ");
        System.out.print("                            |       ");
        System.out.println("                            |       ");
        System.out.print("   |   "); 
        System.out.print("      -------------- ");
        System.out.print((place[0] == 1 && place[1] == 2) ? "       X       " : "  gas station(" + distances[1][2] + "m)");                                                                       
        System.out.print(" ------------------ ");                                                              
        System.out.println("     |   ");        
        System.out.print("   |   ");
        System.out.print("                            |       ");
        System.out.println("                            |       ");
        System.out.print("   |   ");
        System.out.print("                            |       ");
        System.out.println("                            |       ");
        System.out.print("   |   "); 
        System.out.print("                            |       ");
        System.out.println("                            |       ");
        
        System.out.print((place[0] == 2 && place[1] == 0) ? "   X   " : "  mall(" + distances[2][0] + "m)");
        System.out.print((place[0] == 2 && place[1] == 1) ? "      X      " : "  -------------- ");                                                                                                                                                            
        System.out.print((place[0] == 2 && place[1] == 2) ? "       X       " : "    hospital(" + distances[2][2] + "m)");                                                                               
        System.out.print((place[0] == 2 && place[1] == 3) ? "     X      " : " ------------------ ");
        System.out.println((place[0] == 2 && place[1] == 4) ? "     X     " : " church(" + distances[2][4] + "m)");
                                                                                      
    }

    public static int[][] generateRandomDistances(int numRows, int numCols) {
        int[][] distances = new int[numRows][numCols];
        Random random = new Random();
        int[][] allowedCoordinates = {
                {0, 0}, {0, 2}, {0, 4},
                {1, 2},
                {2, 0}, {2, 2}, {2, 4}
        };

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                boolean isAllowed = false;
                for (int[] coord : allowedCoordinates) {
                    if (coord[0] == i && coord[1] == j) {
                        isAllowed = true;
                        break;
                    }
                }

                if (isAllowed) {
                    distances[i][j] = random.nextInt(100) + 10;
                } else {
                    distances[i][j] = 0; 
                }
            }
        }

        return distances;
    }
    private static int[] generateRandomCoordinate(int numRows, int numCols) {
        int[] result = new int[2];
        int[][] allowedCoordinates = {
                {0, 0}, {0, 2}, {0, 4},
                {1, 2},
                {2, 0}, {2, 2}, {2, 4}
        };

        int index = random.nextInt(allowedCoordinates.length);
        result[0] = allowedCoordinates[index][0];
        result[1] = allowedCoordinates[index][1];

        
        return result;
    }
    public static int[] findPaths(int startX, int startY, int endX, int endY) {
        int[] paths = new int[100];
        findPathsRecursive(startX, startY, endX, endY, paths, 0, false);
        return paths;
    }

    private static void findPathsRecursive(int currentX, int currentY, int endX, int endY, int[] paths, int pathIndex, boolean found) {
        if (found) {
            return; 
        }

        if (currentX == endX && currentY == endY) {
            paths[pathIndex] = DONE;
            found = true;
            return;
        }
       
        Random rand = new Random();
        int[] directions = {UP, DOWN, LEFT, RIGHT};
        shuffleArray(directions, rand); 

        for (int direction : directions) {
            switch (direction) {
                case UP:
                    if (currentY > endY) {
                        paths[pathIndex] = UP;
                        findPathsRecursive(currentX, currentY - 1, endX, endY, paths, pathIndex + 1, found);
                    }
                    break;
                case DOWN:
                    if (currentY < endY) {
                        paths[pathIndex] = DOWN;
                        findPathsRecursive(currentX, currentY + 1, endX, endY, paths, pathIndex + 1, found);
                    }
                    break;
                case LEFT:
                    if (currentX > endX) {
                        paths[pathIndex] = LEFT;
                        findPathsRecursive(currentX - 1, currentY, endX, endY, paths, pathIndex + 1, found);
                    }
                    break;
                case RIGHT:
                    if (currentX < endX) {
                        paths[pathIndex] = RIGHT;
                        findPathsRecursive(currentX + 1, currentY, endX, endY, paths, pathIndex + 1, found);
                    }
                    break;
            }
        }
    }

    private static int calculateTotalDistance(int[] route, int startX, int startY) {
        int totalDistance = 0;

        int currentX = startX;
        int currentY = startY;

        for (int direction : route) {
            switch (direction) {
                case UP:
                    currentY--;
                    break;
                case DOWN:
                    currentY++;
                    break;
                case LEFT:
                    currentX--;
                    break;
                case RIGHT:
                    currentX++;
                    break;
            }
            totalDistance += placesDistances[currentX][currentY];
        }

        return totalDistance;
    }

    private static double calculateTotalTime(int[] route, int startX, int startY) {
        double totalDistance = calculateTotalDistance(route, startX, startY);
        return totalDistance / speed;
    }

    private static double calculateTotalFuelCost(int[] route, int startX, int startY) {
        double totalDistance = calculateTotalDistance(route, startX, startY);
        return totalDistance * fuelStamina / 100;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
     
        int[] bestRoute = null;
        double shortestTime = Double.MAX_VALUE;
        int iterations = 10; 

        placesDistances = generateRandomDistances(3,5);

        coordinate = generateRandomCoordinate(3, 5);

        printMap(coordinate, placesDistances);
        
        String currentLocation = "here";

        if (coordinate[0] == 0 && coordinate[1] == 0) {
            currentLocation = "You're Currently at your house";
        } else if (coordinate[0] == 0 && coordinate[1] == 2) {
            currentLocation = "You're Currently at the public market";
        } else if (coordinate[0] == 0 && coordinate[1] == 4) {
            currentLocation = "You're Currently at school";
        } else if (coordinate[0] == 1 && coordinate[1] == 2) {
            currentLocation = "You're Currently at the gas station";
        } else if (coordinate[0] == 2 && coordinate[1] == 0) {
            currentLocation = "You're Currently at the mall";
        } else if (coordinate[0] == 2 && coordinate[1] == 2) {
            currentLocation = "You're Currently at the hospital";
        } else if (coordinate[0] == 2 && coordinate[1] == 4) {
            currentLocation = "You're Currently at the church";
        }

        System.out.println(currentLocation);

        int vehicle = 0;
        while (true) {
            printUserPrompt();
            int preferredVehicle = scanner.nextInt();

            if (!checkInput(preferredVehicle, 2)) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            } else {
                switch (preferredVehicle) {
                    case 1:
                        speed = 0.1;
                        fuelStamina = random.nextDouble(100) + 50;
                        vehicle = 1;
                        break;
                    case 2:
                        speed = 0.7;
                        fuelStamina = random.nextDouble(100) + 50;
                        vehicle = 2;
                        break;
                }
                break;
            }
        }

        while (true) {
            printUserPromptToGo();
            int userToGo = scanner.nextInt();

            if (!checkInput(userToGo, 7)) {
                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            } else {
                coordinateToGo = new int[2];
                switch (userToGo) {
                    case 1:
                        coordinateToGo[0] = 0;
                        coordinateToGo[1] = 0;
                        break;
                    case 2:
                        coordinateToGo[0] = 2;
                        coordinateToGo[1] = 0;
                        break;
                    case 3:
                        coordinateToGo[0] = 0;
                        coordinateToGo[1] = 2;
                        break;
                    case 4:
                        coordinateToGo[0] = 1;
                        coordinateToGo[1] = 2;
                        break;
                    case 5:
                        coordinateToGo[0] = 2;
                        coordinateToGo[1] = 4;
                        break;
                    case 6:
                        coordinateToGo[0] = 2;
                        coordinateToGo[1] = 2;
                        break;
                    case 7:
                        coordinateToGo[0] = 0;
                        coordinateToGo[1] = 4;
                        break;
                }
            }

            for (int i = 0; i < iterations; i++) {
                int[] paths = findPaths(coordinate[0], coordinate[1], coordinateToGo[0], coordinateToGo[1]);                
                System.out.println("Path: " + (i + 1));
                for (int j = 1; j < paths.length - 1 && paths[j] != DONE; j++) {   
                    double time = calculateTotalTime(paths, coordinate[0], coordinate[1]);
                    if (time < shortestTime) {
                        shortestTime = time;
                        bestRoute = paths; 
                    }
                    switch (paths[j]) {
                        case UP:
                            System.out.println("Go Up");
                            break;
                        case DOWN:
                            System.out.println("Go Down");
                            break;
                        case LEFT:
                            System.out.println("Go Left");
                            break;
                        case RIGHT:
                            System.out.println("Go Right");
                            break;
                    }                                             
                    
                }
                System.out.println("You arrived at your destination!");
            }

            
            System.out.println("Best Route:");
            for (int i = 1; i < bestRoute.length - 1 && bestRoute[i] != DONE; i++) {
                switch (bestRoute[i]) {
                    case UP:
                        System.out.println("Go Up");
                        break;
                    case DOWN:
                        System.out.println("Go Down");
                        break;
                    case LEFT:
                        System.out.println("Go Left");
                        break;
                    case RIGHT:
                        System.out.println("Go Right");
                        break;
                }
            }            
            System.out.println("You arrived at your destination!");

            double totalTime = calculateTotalTime(bestRoute, coordinate[0], coordinate[1]);
            double totalFuelCost = calculateTotalFuelCost(bestRoute, coordinate[0], coordinate[1]);
            double totalDistance = calculateTotalDistance(bestRoute, coordinate[0], coordinate[1]);
            System.out.println("Total Distance: " + totalDistance + " m");
            System.out.println("Total Time: " + totalTime + " hours");
            String fuelCostString = "Fuel Cost: " + totalFuelCost + " ml";
            if (vehicle == 1) {
                fuelCostString = "Total Stamina: " + totalFuelCost;
            }

            System.out.println(fuelCostString);
            break;
        }
        
    }

    private static void shuffleArray(int[] array, Random rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private static int countSteps(int[] path) {
        int count = 0;
        for (int step : path) {
            if (step != DONE) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
