package aco;

public class ACO {
    private final Matrix adjMatrix;
    private final int numOfCities;
    private final int numOfCycles;
    private int curCycle = 0;
    private Matrix pheromones;
    private Ant[] ants;
    private double bestTourLength = Double.MAX_VALUE;
    private String bestTourPath;

    public ACO(Matrix matrix) {
        adjMatrix = matrix;
        numOfCities = adjMatrix.getSize();
        numOfCycles = 2000;
        initPheromones();
    }

    public void run() {
        while (shouldContinue()) {
            initAnts();

            for (int i = 1; i < numOfCities; i++) {
                for (Ant ant : ants) {
                    ant.moveToNext(adjMatrix, pheromones);
                }
            }
            findBestTour();
            evaporate();
            updatePheromones();
        }
        System.out.printf("Best found TSP solution of cost %f visiting %s%n", bestTourLength, bestTourPath);
    }

    private void findBestTour() {
        boolean isBetterFound = false;
        for (Ant ant : ants) {
            double antTourLength = ant.getTourLength(adjMatrix);
            if (bestTourLength > antTourLength) {
                isBetterFound = true;
                bestTourLength = antTourLength;
                bestTourPath = ant.getPath();
            }
        }
        if (isBetterFound) {
            System.out.printf("New best found TSP solution of cost %f visiting %s%n", bestTourLength, bestTourPath);
        }
    }

    private void evaporate() {
        for (int i = 0; i < numOfCities; i++) {
            for (int j = i + 1; j < numOfCities; j++) {
                pheromones.set(i, j, pheromones.get(i, j) * Config.getRateOfEvaporation());
                pheromones.set(j, i, pheromones.get(j, i) * Config.getRateOfEvaporation());
            }
        }
    }

    private void updatePheromones() {
        for (Ant ant : ants) {
            double pheromone = Config.getQ3() / ant.getTourLength(adjMatrix);
            int[] tabu = ant.getTabu();

            // tabu.length - 1 is important because we don't want to go till the last node
            // and then overflow the i + 1 value
            for (int i = 0; i < tabu.length - 1; i++) {
                int u = tabu[i], v = tabu[i + 1];
                // Add pheromone to the edges
                pheromones.set(u, v, pheromones.get(u, v) + pheromone);
                pheromones.set(v, u, pheromones.get(v, u) + pheromone);
            }
            // Add pheromone to the last edge
            int first = tabu[0];
            int last = tabu[tabu.length - 1];
            pheromones.set(first, last, pheromones.get(first, last) + pheromone);
            pheromones.set(last, first, pheromones.get(last, first) + pheromone);
        }
    }

    private void initPheromones() {
        pheromones = new Matrix(numOfCities);
        for (int i = 0; i < numOfCities; i++) {
            for (int j = i + 1; j < numOfCities; j++) {
                pheromones.set(i, j, 1);
                pheromones.set(j, i, 1);
            }
        }
    }

    private void initAnts() {
        ants = new Ant[numOfCities];
        for (int i = 0; i < numOfCities; i++) {
            ants[i] = new Ant(i, numOfCities);
        }
    }

    private boolean shouldContinue() {
        return curCycle++ < numOfCycles;
    }
}

