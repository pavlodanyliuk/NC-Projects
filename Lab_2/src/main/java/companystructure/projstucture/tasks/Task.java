package companystructure.projstucture.tasks;

import companystructure.peoplestructure.workers.Employee;
import companystructure.peoplestructure.workers.Manager;
import companystructure.peoplestructure.workers.Qualification;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Task {
    private Estimate estimateTimeH;
    private String name;
    private String description;
    private Date startDate = null;
    private Date finishDate = null;

    private Qualification requirementsQuality = null;

    private ArrayList<Task> dependingTask;
    private ArrayList<Task> subTask;

    private boolean finished = false;
    private boolean started = false;

    private HashSet<Employee> employees = null;

    private Manager manager;

    public Task(String name, String description, Estimate estimateTimeH, Manager manager){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.manager = manager;
        dependingTask = new ArrayList<Task>(0);
        subTask = new ArrayList<Task>(0);

    }

    public Task(String name, String description, Estimate estimateTimeH, ArrayList<Task> dependingTask, Manager manager){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.dependingTask = dependingTask;
        this.manager = manager;
        subTask = new ArrayList<Task>(0);
    }

    public Task(String name, String description, ArrayList<Task> subTask, Estimate estimateTimeH, Manager manager ){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.subTask = subTask;
        this.manager = manager;
        dependingTask = new ArrayList<Task>(0);
    }

    public Task(String name, String description, Estimate estimateTimeH, ArrayList<Task> dependingTask, ArrayList<Task> subTask, Manager manager){
        this.name = name;
        this.description = description;
        this.estimateTimeH = estimateTimeH;
        this.dependingTask = dependingTask;
        this.subTask = subTask;
        this.manager = manager;
    }

    public boolean isFinished(){
        return finished;
    }

    public void completeTask(){
        if(!isStarted()){
            System.out.println("You need to start task, and after finish it!");
            return;
        }
        for(Task subT : subTask){
            if(!subT.isFinished()){
                System.out.println("You can`t to complete the task. You must finished the task " + subT.getName());
                return;
            }
        }

        finishDate = new Date();
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

    public void joinTheEmployee(Employee employee){
        employees.add(employee);
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

    public void setRequirementsQualification(Qualification requirementsQuality) {
        this.requirementsQuality = requirementsQuality;
    }

    public Qualification getRequirementsQualification() {
        return requirementsQuality;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void changeStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void changeManager(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}
