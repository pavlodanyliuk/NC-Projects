package companystructure.projstucture;

import companystructure.Quality;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    private Estimate estimateTimeH;
    private String name;
    private String description;
    private Date startDate = null;

    private Quality requirementsQuality = null;

    private ArrayList<Task> dependingTask;
    private ArrayList<Task> subTask;

    private boolean finished = false;
    private boolean started = false;

    public void Task(String name, String description, Estimate estimateTimeH){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        dependingTask = new ArrayList<Task>(0);
        subTask = new ArrayList<Task>(0);
    }

    public void Task(String name, String description, Estimate estimateTimeH, ArrayList<Task> dependingTask){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.dependingTask = dependingTask;
        subTask = new ArrayList<Task>(0);
    }

    public void Task(String name, String description, ArrayList<Task> subTask, Estimate estimateTimeH ){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.subTask = subTask;
        dependingTask = new ArrayList<Task>(0);
    }

    public void Task(String name, String description, Estimate estimateTimeH, ArrayList<Task> dependingTask, ArrayList<Task> subTask){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.dependingTask = dependingTask;
        this.subTask = subTask;
    }

    public boolean isFinished(){
        return finished;
    }

    public void completeTask(){
        if(!isStarted()){
            System.out.println("You need to start task, and after finish it!");
        }
        for(Task subT : subTask){
            if(!subT.isFinished()){
                System.out.println("You can`t to complete the task. You must finished the task " + subT.getName());
                return;
            }
        }

        finished = true;
    }

    public void startTask(){
        if(isStarted() == true){
            System.out.println("Task already started");
            return;
        }
        for(Task dependedTask : dependingTask){
            if(!dependedTask.isFinished()){
                System.out.println("You can`t to start task. Because dependencies tasks are not finished");
                return;
            }
        }
        started = true;
        startDate = new Date();
    }

    public ArrayList<Task> getDependingTask() {
        return dependingTask;
    }

    public void addDependedTask(Task task){
        if(isStarted()){
            System.out.println("Can`t add task, because this task already started");
            return;
        }
        dependingTask.add(task);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Estimate getEstimateTimeH() {
        return estimateTimeH;
    }

    public void setEstimateTimeH(Estimate estimateTimeH) {
        this.estimateTimeH = estimateTimeH;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStarted() {
        return started;
    }

    public void setRequirementsQuality(Quality requirementsQuality) {
        this.requirementsQuality = requirementsQuality;
    }

    public Quality getRequirementsQuality() {
        return requirementsQuality;
    }
}
