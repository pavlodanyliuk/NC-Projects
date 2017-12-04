package companystructure.peoplestructure.workers;

import companystructure.projstucture.tasks.Estimate;
import companystructure.projstucture.tasks.Task;

import java.util.ArrayList;

public class Manager extends Worker {

    public Manager(String name, String company) {
        super(name, company);
    }

    public Task createTask(String name, String description, Estimate estimateTimeH){
        return new Task(name, description, estimateTimeH, this);
    }

    public Task createTask(String name, String description, Estimate estimateTimeH, ArrayList<Task> dependingTask){
        return new Task(name, description, estimateTimeH, dependingTask, this);
    }

    public Task createTask(String name, String description, ArrayList<Task> subTask, Estimate estimateTimeH){
        return new Task(name, description, subTask, estimateTimeH, this);
    }

    public Task createTask(String name, String description, Estimate estimateTimeH, ArrayList<Task> dependingTask, ArrayList<Task> subTask){
        return new Task(name, description, estimateTimeH, dependingTask, subTask,this);
    }

    public void appointTaskToEmployee(Task task, ArrayList<Employee> employees){
        for(Employee employee : employees) {
            employee.addTask(task);
        }
        if(!task.isStarted()){
            task.startTask();
        }
    }

    public void askForMoreTime(Task task){
        Estimate estimate = task.getEstimateTimeH();
        estimate.changeEstimateTime(estimate.getEstimateTime() + 2);
    }
}
