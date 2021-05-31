package jobshop;

import jobshop.encodings.JobNumbers;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import jobshop.solvers.DescentSolver;
import jobshop.solvers.GreedySolver;
import jobshop.solvers.TabooSolver;
import jobshop.solvers.neighborhood.Nowicki;

import java.io.IOException;
import java.nio.file.Paths;

/** A java main classes for testing purposes. */
public class MainTest {

    public static void main(String[] args) {
        try {
//            // load the aaa1 instance
//            Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));
//
//            // builds a solution in the job-numbers encoding [0 0 1 1 0 1]
//            JobNumbers enc = new JobNumbers(instance);
//            enc.addTask(0);
//            enc.addTask(0);
//            enc.addTask(1);
//            enc.addTask(1);
//            enc.addTask(0);
//            enc.addTask(1);
//
//            System.out.println("\nENCODING: " + enc);
//
//            // convert to a schedule and display
//            Schedule schedule = enc.toSchedule().get();
//            System.out.println("VALID: " + schedule.isValid());
//            System.out.println("MAKESPAN: " + schedule.makespan());
//            System.out.println("SCHEDULE: " + schedule.toString());
//            System.out.println("GANTT: " + schedule.asciiGantt());
//
//            Schedule manualSchedule = new Schedule(instance);
//            // TODO: encode the same solution
//            manualSchedule.setStartTime(0,0,0);
//            manualSchedule.setStartTime(0,1,3);
//            manualSchedule.setStartTime(0,2,6);
//            manualSchedule.setStartTime(1,0,6);
//            manualSchedule.setStartTime(1,1,8);
//            manualSchedule.setStartTime(1,2,10);
//            //GANTT
//            System.out.println("GANTT: " + manualSchedule.asciiGantt());
//

//            ResourceOrder manualRO = new ResourceOrder(instance);
//            // TODO: encode the same solution
//            manualRO.addToMachine(0,1);
//            manualRO.addToMachine(0,0);
//            manualRO.addToMachine(0,1);
//            manualRO.addToMachine(1,0);
//            manualRO.addToMachine(2,1);
//            manualRO.addToMachine(2,0);
//

//
//            Schedule optimalSchedule = manualRO.toSchedule().get();
//            System.out.println("VALID: " + optimalSchedule.isValid());
//            System.out.println("MAKESPAN: " + optimalSchedule.makespan());
//            System.out.println("SCHEDULE: " + optimalSchedule);
//            System.out.println("GANTT: " + optimalSchedule.asciiGantt());


            GreedySolver solver=new GreedySolver(GreedySolver.Priority.EST_SPT);
            DescentSolver bestsolver=new DescentSolver(new Nowicki(),solver);
            TabooSolver taboosolver=new TabooSolver(new Nowicki(),solver,10,3);
            Instance essai = Instance.fromFile(Paths.get("instances/aaa3"));
//            int num=10;
//            String base="instances/la";
//            while(num<=40){
//                String result=base + String.valueOf(num) ;
//
//                essai = Instance.fromFile(Paths.get(result));
                Schedule greedyschedule=solver.solve(essai,1000000).schedule.get();
//                System.out.println("Numbers of Neighbors Explored:" +bestsolver.getNeighborsexplored());
                System.out.println("test solver");
                System.out.println("VALID: " + greedyschedule.isValid());
//                System.out.println(num);
                System.out.println("MAKESPAN: " + greedyschedule.makespan());
                System.out.println("SCHEDULE: " + greedyschedule);
                System.out.println("GANTT: " + greedyschedule.asciiGantt());

//                num++;
//            }


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
