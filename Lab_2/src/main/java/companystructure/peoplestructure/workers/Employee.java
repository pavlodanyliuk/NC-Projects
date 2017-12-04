package companystructure.peoplestructure.workers;

import companystructure.projstucture.tasks.Task;

import java.util.ArrayList;

public class Employee extends Worker {
    private Qualification qualification;
    private ArrayList<Task> tasks;

    public Employee(String name, String company, Qualification qualification) {
        super(name, company);

        this.qualification = qualification;
        tasks = new ArrayList<>(0);
    }

    public void addTask(Task task){
        if(qualification.ordinal() < task.getRequirementsQualification().ordinal()){
            System.out.println("Sorry, the qualification of an employee is insufficient!");
            return;
        }
        tasks.add(task);
        task.joinTheEmployee(this);
        System.out.println(name + " successfully received the task!");
    }

    public void confirmFinishingTask(Task task){
        if (!tasks.contains(task)){
            System.out.println("The task is not appointed this employee");
            return;
        }
        if(!task.isFinished()){
            System.out.println("Task already finished");
            return;
        }
        task.completeTask();
    }

    public void askForMoreTime(Task task){
        if (!tasks.contains(task)){
            System.out.println("The task is not appointed this employee");
            return;
        }
        task.getManager().askForMoreTime(task);
    }


    /**
     * getters and setters block
     */

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Qualification getQualification() {
        return qualification;
    }
}
