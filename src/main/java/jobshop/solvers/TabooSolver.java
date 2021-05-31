package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.Encoding;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import jobshop.solvers.neighborhood.Neighbor;
import jobshop.solvers.neighborhood.Neighborhood;
import jobshop.solvers.neighborhood.Nowicki;

import java.sql.ResultSet;
import java.util.*;

public class TabooSolver implements Solver {

    final Neighborhood<ResourceOrder> neighborhood;
    final Solver baseSolver;
    final int max;
    final int dureeTaboo;

    public TabooSolver(Neighborhood<ResourceOrder> neighborhood, Solver baseSolver,int max,int dureeTaboo){
        this.neighborhood = neighborhood;
        this.baseSolver= baseSolver;
        this.max=max;
        this.dureeTaboo=dureeTaboo;
    }

    public Result solve(Instance instance, long deadline) {

        ResourceOrder base = new ResourceOrder(this.baseSolver.solve(instance, deadline).schedule.get()); /*meilleur solution*/
        ResourceOrder s = base;

        int k=0;

        int[][] sTaboo= new int[instance.numJobs*instance.numTasks][instance.numJobs*instance.numTasks];
        for (int i=0;i<instance.numJobs*instance.numTasks;i++){
            for(int j=0;j<instance.numJobs*instance.numTasks;j++){
                sTaboo[i][j]=0;
            }
        }
        int bestIndex=-1;

        while (k<max && System.currentTimeMillis() < deadline) {
            k = k+1;
            List<Neighbor<ResourceOrder>> list = neighborhood.generateNeighbors(base);


            ResourceOrder n1 = base.copy();
            ResourceOrder n2 = base.copy();
            list.get(0).applyOn(n1);
            List<Nowicki.Swap> list_swap=Nowicki.allSwaps(n2);

            bestIndex=-1;
            for (int i = 0; i < list.size(); i++) {
                list.get(i).applyOn(n1);
                int j=0;

                Task taskv1_1 = n1.getTaskOfMachine(list_swap.get(i).machine, list_swap.get(i).t1);
                Task taskv1_2 = n1.getTaskOfMachine(list_swap.get(i).machine, list_swap.get(i).t2);

                if (n2.toSchedule().isEmpty() != true) {

                    if (sTaboo[taskv1_1.job * instance.numTasks +
                            taskv1_1.task][taskv1_2.job * instance.numTasks + taskv1_2.task]<=k ||
                            sTaboo[taskv1_2.job * instance.numTasks +
                                    taskv1_2.task][taskv1_1.job * instance.numTasks + taskv1_1.task]<=k)
                    {
                        j=1;
                    }

                    if (j==1){

                        int value_voisin1 = n2.toSchedule().get().makespan();

                        if (n1.toSchedule().isEmpty()==true){
                            n1 = new ResourceOrder(n2.toSchedule().get());
                            bestIndex=i;
                        }

                        else

                        if (value_voisin1 <= n1.toSchedule().get().makespan()) {
                            bestIndex=i;
                            n1 = new ResourceOrder(n2.toSchedule().get());

                        }
                    }
                    if (n2.toSchedule().get().makespan()<s.toSchedule().get().makespan()){
                        bestIndex=i;
                        n1 = new ResourceOrder(n2.toSchedule().get());
                    }
                }
                list.get(i).undoApplyOn(n2);
            }
            ///////////

            if (bestIndex!=-1 ) {
                Task task_1 = n2.getTaskOfMachine(list_swap.get(bestIndex).machine,list_swap.get(bestIndex).t1);
                Task task_2 = n2.getTaskOfMachine(list_swap.get(bestIndex).machine, list_swap.get(bestIndex).t2);
                sTaboo[task_2.job * instance.numTasks + task_2.task][task_1.job * instance.numTasks + task_1.task] = k + dureeTaboo;
                sTaboo[task_1.job * instance.numTasks + task_1.task][task_2.job * instance.numTasks + task_2.task] = k + dureeTaboo;

            }

            if (n1.toSchedule().isEmpty() != true && n1.toSchedule().get().makespan() < s.toSchedule().get().makespan()) {
                s=n1.copy();
            }

            base=n1.copy();

        }
        return new Result(s.instance,s.toSchedule(),Result.ExitCause.Blocked);
    }}