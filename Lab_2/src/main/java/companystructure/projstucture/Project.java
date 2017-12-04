package companystructure.projstucture;

import companystructure.peoplestructure.Customer;

import java.util.Date;
import java.util.Queue;

/**
 * проект складається з фаз. Наступна фаза може починатись тільки після закінчення попередньої;
 */

public class Project {
    private Date startDate;
    private Date deadlineDate;

    private String name;
    private Customer customer;
    private Queue<Sprint> sprints;

    private boolean started = false;
    private boolean finished = false;



    public Project(String name, Date deadlineDate, Customer customer,Queue<Sprint> sprints){
        this.name = name;
        this.startDate = startDate;
        this.deadlineDate = deadlineDate;
        this.customer = customer;
        this.sprints = sprints;
    }

    public Sprint getPresentsSprint(){
        return sprints.peek();
    }

    public void completeSprint(){
        if(getPresentsSprint().isFinished()) {
            System.out.println("You can complete spirit only finishing all tasks!");
            return;
        }
        sprints.poll();
    }

    /**
     * getters and setters
     */
    public void start(){
        started = true;
        startDate = new Date();
    }

    public boolean isStarted(){
        return started;
    }

    public void complete(){
        if(!isStarted()){
            System.out.println("You need to start project, and after finish it!");
            return;
        }
        finished = true;
    }

    public boolean isFinished(){
        return finished;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}