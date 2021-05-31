package jobshop;

import jobshop.encodings.Schedule;

import java.util.Optional;

/** Class representing the result of a solver. */
public class Result {

    /** Instance that was solved. */
    public final Instance instance;

    /** A schedule of the solution or Optional.empty() if no solution was found. */
    public final Optional<Schedule> schedule;

    /** Reason why the solver exited with this solution. */
    public final ExitCause cause;

    /** Creates a new Result object with the corresponding fields. */
    public Result(Instance instance, Optional<Schedule> schedule, ExitCause cause) {
        this.instance = instance;
        this.schedule = schedule;
        this.cause = cause;
    }

    /** Documents the reason why a solver returned the solution. */
    public enum ExitCause {
        /** The solver ran out of time and had to exit. */
        Timeout,
        /** The solution has been proved optimal and thus can no longer be improved. */
        ProvedOptimal,
        /** The solver was not able to further improve the solution (e.g. blocked in a local minima. */
        Blocked
    }
}
