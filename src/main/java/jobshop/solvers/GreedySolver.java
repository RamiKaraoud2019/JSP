package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;

import java.lang.reflect.Array;
import java.util.*;

/** An empty shell to implement a greedy solver. */
public class GreedySolver implements Solver {

    /** All possible priorities for the greedy solver. */
    public enum Priority {
        SPT, LPT, SRPT, LRPT, EST_SPT, EST_LPT, EST_SRPT, EST_LRPT
    }

    /** Priority that the solver should use. */
    final Priority priority;

    /** Creates a new greedy solver that will use the given priority. */
    public GreedySolver(Priority p) {
        this.priority = p;
    }

    public Task SPT(List<Task> list,Instance instance){
        Task task=list.get(0);
        for (Task t : list){
            if (instance.duration(t)<instance.duration(task)){
                task=t;
            }
        }
        return task;
    }

    public Task LRPT(List<Task> list, Instance instance){
        Task task= list.get(0);
        int bestduration=0;
        for (Task t : list) {
            int duration=0;
            for (int i = t.task; i < instance.numTasks; i++) {
                duration += instance.duration(t.job, i);
            }
            if (duration>bestduration){
                bestduration=duration;
                task=t;
            }
        }
        return task;
    }

    public List<Task> EST(List<Task> tasks, Instance instance,int[] machine,int[] jobjob){
        int temps = 0;
        int min=Integer.MAX_VALUE;
        for (Task t: tasks){
            temps= Math.max(machine[instance.machine(t)], jobjob[t.job]);
            if (temps<min){
                min=temps;
            }
        }
        List<Task> result=new ArrayList<>();
        for (Task t: tasks){
            if (Math.max(machine[instance.machine(t)], jobjob[t.job])==min){
                result.add(t);
            }
        }
        return result;
    }








    @Override
    public Result solve(Instance instance, long deadline) {
        long time=System.currentTimeMillis();
        ResourceOrder greedyOrder = new ResourceOrder(instance);
        int numJobs= instance.numJobs;
        int numMachines=instance.numMachines;

        //EST
        int[] machine = new int[numMachines];
        int[] jobjob = new int[numJobs];
        for (int i = 0; i < numMachines; i++) {
            machine[i] = 0;
        }
        for (int i = 0; i < numJobs; i++) {
            jobjob[i] = 0;
        }

        //tasks réalisables
        List<Task> tasks=new ArrayList<>();

        for (int i=0;i<numJobs;i++) {
            tasks.add(new Task(i,0));
        }
        while (!tasks.isEmpty()) {
            if (System.currentTimeMillis()-time>deadline){
                Result result= new Result(instance,greedyOrder.toSchedule(),Result.ExitCause.Timeout);
                return result;
            }

            Task t=null;
            switch (priority) {
                case SPT:
                    t = SPT(tasks, instance);
                    break;
                case LRPT:
                    t = LRPT(tasks, instance);
                break;
                case EST_SPT:
                    t = SPT(EST(tasks, instance, machine, jobjob), instance);
                break;
                case EST_LRPT:
                    t = LRPT(EST(tasks, instance, machine, jobjob), instance);
                break;
                default :
                    t = null;
                    break;
            }

            /////////////
            int temps=Math.max(machine[instance.machine(t)]+instance.duration(t),jobjob[t.job]+instance.duration(t));
            machine[instance.machine(t)]=temps;
            jobjob[t.job]=temps;

            /////////////
            greedyOrder.addTaskToMachine(instance.machine(t),t);

            ///////////////
            tasks.remove(t);
            if (numMachines>t.task+1) {
                tasks.add(new Task(t.job, t.task + 1));
            }
        }
        
        Result result= new Result(instance,greedyOrder.toSchedule(),Result.ExitCause.Blocked);

        return result;
    }



}
