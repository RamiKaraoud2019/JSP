package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.JobNumbers;

/**
 * A very naïve solver that first schedules all first tasks, then all second tasks, ...
 **/
public class BasicSolver implements Solver {
    @Override
    public Result solve(Instance instance, long deadline) {

        JobNumbers sol = new JobNumbers(instance);
        for(int t = 0 ; t<instance.numTasks ; t++) {
            for(int j = 0 ; j<instance.numJobs ; j++) {
                sol.addTask(j);
            }
        }

        return new Result(instance, sol.toSchedule(), Result.ExitCause.Blocked);
    }
}
