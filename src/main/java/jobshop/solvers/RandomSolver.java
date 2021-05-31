package jobshop.solvers;

import jobshop.*;
import jobshop.encodings.JobNumbers;
import jobshop.encodings.Schedule;

import java.util.Optional;
import java.util.Random;

/** A solver that generates random solutions until a deadline is met.
 *  Then returns the best solution that was generated.
 */
public class RandomSolver implements Solver {

    @Override
    public Result solve(Instance instance, long deadline) {
        Random generator = new Random(0);

        JobNumbers sol = new JobNumbers(instance);

        // initialize a first solution to the problem.
        for(int j = 0 ; j<instance.numJobs ; j++) {
            for(int t = 0 ; t<instance.numTasks ; t++) {
                sol.addTask(j);
            }
        }
        // best solution is currently the initial one
        Optional<Schedule> best = sol.toSchedule();

        // while we have some time left, generate new solutions by shuffling the current one
        while(deadline - System.currentTimeMillis() > 1) {
            shuffleArray(sol.jobs, generator);
            Optional<Schedule> candidate = sol.toSchedule();
            if(candidate.isPresent()) {
                if (best.isEmpty() || candidate.get().makespan() < best.get().makespan()) {
                    best = candidate;
                }
            }
        }


        return new Result(instance, best, Result.ExitCause.Timeout);
    }

    /** Simple Fisher–Yates array shuffling */
    private static void shuffleArray(int[] array, Random randomNumberGenerator)
    {
        int index;
        for (int i = array.length - 1; i > 0; i--)
        {
            index = randomNumberGenerator.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}


