# Entry Points


## `jobshop.MainTest`

This is a java main class that you can use to run some small tests. It comes with some initial code to get you started when exploring the encodings.

## `jobshop.Main`

This is the principal entry point that aims at helping you running many solvers on many instances and comparing the results.
It expects several command line arguments to specify which solvers to run and which instance to solve.


```
Usage: jsp-solver [-h] [-t TIMEOUT] --solver SOLVER [SOLVER ...]
                  --instance INSTANCE [INSTANCE ...]

Solves jobshop problems.

named arguments:
  -h, --help             show this help message and exit
  -t TIMEOUT, --timeout TIMEOUT
                         Solver  timeout  in  seconds  for  each  instance. 
                         (default: 1)
  --solver SOLVER [SOLVER ...]
                         Solver(s) to use  (space  separated  if  more than
                         one)
  --instance INSTANCE [INSTANCE ...]
                         Instance(s) to  solve  (space  separated  if  more
                         than one). All instances  starting  with the given
                         String will be  selected.  (e.g.  "ft" will select
                         the instances ft06, ft10 and ft20.
```


### Example usage

```shell
# Running the Main program with gradle
❯ ./gradlew run --args="--solver basic --instance ft06"
```

The command line above indicates that we want to solve the instance named`ft06` with the `basic` solver. It should give an output like the following :
```
                         basic
instance size  best      runtime makespan ecart
ft06     6x6     55            1       60   9.1
AVG      -        -          1.0        -   9.1
```

Fields in the result view are the following :
- `instance`: name of the instance
- `size`: size of the instance `{num-jobs}x{num-tasks}`
- `best`: best known result for this instance
- `runtime`: time taken by the solver in milliseconds (rounded)
- `makespan`: makespan of the solution
- `ecart`: normalized distance to the best result: `100 * (makespan - best) / best` 

One can also specify multiple solvers (below `basic` and `random`) and instances (below `ft06`, `ft10` and `ft20`) for simultaneous testing:

```shell
❯ ./gradlew run --args="--solver basic random --instance ft06 ft10 ft20"

                         basic                         random
instance size  best      runtime makespan ecart        runtime makespan ecart
ft06     6x6     55            1       60   9.1            999       55   0.0
ft10     10x10  930            0     1319  41.8            999     1209  30.0
ft20     20x5  1165            0     1672  43.5            999     1529  31.2
AVG      -        -          0.3        -  31.5          999.0        -  20.4
```
Here the last line give the average `runtime` and `ecart` for each solver.


**Tip:** When selecting instances to solve, you can only provide a prefix to instance name. All instances that start with this prefix will be selected.
For instance running the program with the option `--instance la` will select all Lawrences instance (`la01` to `la40`).