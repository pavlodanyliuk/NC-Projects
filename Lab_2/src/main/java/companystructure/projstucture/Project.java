package companystructure.projstucture;

import companystructure.projstucture.Sprint;

import java.util.Date;
import java.util.Queue;

/**
 * проект складається з фаз. Наступна фаза може починатись тільки після закінчення попередньої;
 */

public class Project {
    private Date startDate;
    private Date deadlineDate;

    private Queue<Sprint> sprints;

}
