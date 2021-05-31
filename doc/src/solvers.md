# Solvers

## The `Solver` interface

`jobshop.solvers.Solver` provides a common interface for all solvers.

Implementing the `Solver` interface requires implementing a method `solve(Instance instance, long deadline)` where:

 - `instance` is the jobshop instance that should be solved.
 - `deadline` is the absolute time by which the solver should have exited. This deadline is in milliseconds and can be compared with the result of `System.currentTimeMillis()`.

 The `solve()` method should return a `Result` object, that provides the found solution as a `Schedule` and the cause for exiting.

## `BasicSolver`

A very simple solver that tries to schedule all first tasks, then all second tasks, then all third tasks, ...
It does so using the `JobNumbers` encoding.

## `RandomSolver`

Another very simple solver based on the `JobNumbers` encoding.
At each iteration, the solver generates a new random solution keeps it if it the best one found so far.

It repeats this process until the deadline to produce a result is met and finally returns the best solution found.


## `GreedySolver`

The greedy solver is not implemented yet. 
Its constructor accepts a parameter that specifies the priority that should be used to produce solutions.

## `DescentSolver`

Not implemented yet. It should use the *Nowicki and Smutnicki* neighborhood for which some initial code is provided in the `jobshop.solver.neighborhood` package.