import analyzer.Analyzer;
import exel.ExelWriter;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        File file = new File("/home/roland/exel/stats.xlsx");
        new ExelWriter(Analyzer.analyze(), Analyzer.getLenOfArrays()).writeStats(file);

    }
}
