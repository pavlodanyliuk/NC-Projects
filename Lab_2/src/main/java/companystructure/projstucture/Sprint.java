package companystructure.projstucture;


import companystructure.projstucture.tasks.Task;

import java.util.ArrayList;

/**
 * Фаза проекту складається з завдань
 *
 */
public class Sprint {
    private ArrayList<Task> tasks;

    public Sprint(){
        tasks = new ArrayList<>(0);
    }

    public Sprint(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public boolean isFinished(){
        for(Task task : tasks){
            if (!task.isFinished()){
                return false;
            }
        }
        return true;
    }
}
