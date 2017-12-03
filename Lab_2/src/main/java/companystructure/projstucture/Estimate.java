package companystructure.projstucture;

public class Estimate {
    private int timeH; //in hour

    public Estimate(){
        timeH = 0;
    }

    public Estimate(int timeH){
        if(timeH < 0) {
            timeH = 0;
            return;
        }
        this.timeH = timeH;
    }

    public int getEstimateTime() {
        return timeH;
    }

    public void changeEstimateTime(int timeH){
        if(timeH < 0) {
            timeH = 0;
            return;
        }
        this.timeH = timeH;
    }
}
