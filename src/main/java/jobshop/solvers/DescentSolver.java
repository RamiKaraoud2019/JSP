package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.solvers.neighborhood.Neighbor;
import jobshop.solvers.neighborhood.Neighborhood;
import jobshop.solvers.neighborhood.Nowicki;

import java.util.List;

/** An empty shell to implement a descent solver. */
public class DescentSolver implements Solver {

    final Neighborhood<ResourceOrder> neighborhood;
    final Solver baseSolver;
    private static int neighborsexplored=0;
    /** Creates a new descent solver with a given neighborhood and a solver for the initial solution.
     *
     * @param neighborhood Neighborhood object that should be used to generates neighbor solutions to the current candidate.
     * @param baseSolver A solver to provide the initial solution.
     */
    public DescentSolver(Neighborhood<ResourceOrder> neighborhood, Solver baseSolver) {
        this.neighborhood = neighborhood;
        this.baseSolver = baseSolver;
    }
    public int getNeighborsexplored(){
        return neighborsexplored;
    }

    @Override
    public Result solve(Instance instance, long deadline) {
        Schedule base=baseSolver.solve(instance,deadline).schedule.get();
        ResourceOrder order= new ResourceOrder(base);

        int bestspan=Integer.MAX_VALUE;
        int currentspan = 0;
        int initialspan=base.makespan();
        int i=0;
        Neighbor<ResourceOrder> bestneighbor=null;
        Neighbor<ResourceOrder> previousbestneighbor=null;
        List<Neighbor<ResourceOrder>> neighbors=neighborhood.generateNeighbors(order);
        //do ... while pour le faire au moins une fois
        do {
            neighbors=neighborhood.generateNeighbors(order);
            previousbestneighbor=bestneighbor;

            for (Neighbor<ResourceOrder> currentneighbor : neighbors) {
                neighborsexplored++;
                currentneighbor.applyOn(order);

                if (!order.toSchedule().isEmpty()) {
                    currentspan = order.toSchedule().get().makespan();
                    if (currentspan < bestspan) {
                        bestspan = currentspan;
                        bestneighbor = currentneighbor;
                    }
                    currentneighbor.undoApplyOn(order);
                }
            }
            if (bestspan < initialspan) {
                bestneighbor.applyOn(order);
            }
            i++;
        } while(previousbestneighbor!=bestneighbor & i<deadline);

        return new Result(instance,order.toSchedule(),Result.ExitCause.ProvedOptimal);
    }

}
