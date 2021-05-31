package jobshop.solvers.neighborhood;

import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Task;

import java.util.ArrayList;
import java.util.List;

/** Implementation of the Nowicki and Smutnicki neighborhood.
 *
 * It works on the ResourceOrder encoding by generating two neighbors for each block
 * of the critical path.
 * For each block, two neighbors should be generated that respectively swap the first two and
 * last two tasks of the block.
 */
public class Nowicki extends Neighborhood<ResourceOrder> {

    /** A block represents a subsequence of the critical path such that all tasks in it execute on the same machine.
     * This class identifies a block in a ResourceOrder representation.
     *
     * Consider the solution in ResourceOrder representation
     * machine 0 : (0,1) (1,2) (2,2)
     * machine 1 : (0,2) (2,1) (1,1)
     * machine 2 : ...
     *
     * The block with : machine = 1, firstTask= 0 and lastTask = 1
     * Represent the task sequence : [(0,2) (2,1)]
     *
     * */
    public static class Block {
        /** machine on which the block is identified */
        public final int machine;
        /** index of the first task of the block */
        public final int firstTask;
        /** index of the last task of the block */
        public final int lastTask;

        /** Creates a new block. */
        Block(int machine, int firstTask, int lastTask) {
            this.machine = machine;
            this.firstTask = firstTask;
            this.lastTask = lastTask;
        }
    }

    /**
     * Represents a swap of two tasks on the same machine in a ResourceOrder encoding.
     *
     * Consider the solution in ResourceOrder representation
     * machine 0 : (0,1) (1,2) (2,2)
     * machine 1 : (0,2) (2,1) (1,1)
     * machine 2 : ...
     *
     * The swap with : machine = 1, t1= 0 and t2 = 1
     * Represent inversion of the two tasks : (0,2) and (2,1)
     * Applying this swap on the above resource order should result in the following one :
     * machine 0 : (0,1) (1,2) (2,2)
     * machine 1 : (2,1) (0,2) (1,1)
     * machine 2 : ...
     */
    public static class Swap extends Neighbor<ResourceOrder> {
        /** machine on which to perform the swap */
        public final int machine;

        /** index of one task to be swapped (in the resource order encoding) */
        public final int t1;

        /** index of the other task to be swapped (in the resource order encoding) */
        public final int t2;

        /** Creates a new swap of two tasks. */
        Swap(int machine, int t1, int t2) {
            this.machine = machine;
            this.t1 = t1;
            this.t2 = t2;
        }


        /** Apply this swap on the given ResourceOrder, transforming it into a new solution. */
        @Override
        public void applyOn(ResourceOrder current) {
            current.swapTasks(machine,t1,t2);
        }

        /** Unapply this swap on the neighbor, transforming it back into the original solution. */
        @Override
        public void undoApplyOn(ResourceOrder current) { current.swapTasks(machine,t1,t2); }
    }


    @Override
    public List<Neighbor<ResourceOrder>> generateNeighbors(ResourceOrder current) {
        // this simply converts the list of swaps into a list of neighbors
        return new ArrayList<>(allSwaps(current));
    }

    /** Generates all swaps of the given ResourceOrder.
     * This method can be used if one wants to access the inner fields of a neighbors. */
    public static List<Swap> allSwaps(ResourceOrder current) {
        List<Swap> neighbors = new ArrayList<>();
        // iterate over all blocks of the critical path
        for(var block : blocksOfCriticalPath(current)) {
            // for this block, compute all neighbors and add them to the list of neighbors
            neighbors.addAll(neighbors(block));
        }
        return neighbors;
    }

    /** Returns a list of all the blocks of the critical path. */
    static List<Block> blocksOfCriticalPath(ResourceOrder order) {
        List<Block> list= new ArrayList<>();
        List <Task> critical=order.toSchedule().get().criticalPath();
        int previousMachine=-1,currentMachine=-1,first=0,last=0,i=0,j=0;
        Task previousTask=null;

        //////////
        for (Task task : critical){
            if (i>0){
                previousMachine=currentMachine;
            }
            currentMachine=order.instance.machine(task);
            if (i>0 && previousMachine==currentMachine){
                last=i;
            }else{
                if (first<last){

                    while (!previousTask.equals(order.getTaskOfMachine(previousMachine,j))){
                        j++;
                    }
                    Block block=new Block(previousMachine,j-(last-first),j);
                    list.add(block);
                    j=0;
                }
                first=i;
            }
            previousTask=task;
            i++;
        }
        return list;
    }

    /** For a given block, return the possible swaps for the Nowicki and Smutnicki neighborhood */
    static List<Swap> neighbors(Block block) {
        List<Swap> result=new ArrayList<>();
        Swap currentSwap = new Swap(block.machine, 0,0);
        for (int i=block.firstTask;i<block.lastTask;i++){
            currentSwap=new Swap(block.machine,i,i+1);
            result.add(currentSwap);
        }
     return result;
    }

}
