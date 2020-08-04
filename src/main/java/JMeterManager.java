import org.apache.jmeter.JMeter;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class JMeterManager implements Runnable {
    final static org.apache.log4j.Logger log4j = Logger.getLogger(JMeterManager.class);
    private FileManager fileManager;
    private final String jmeterHome = "c:\\Program Files (x86)\\apache-jmeter-5.3\\";
    private final String jmeterProperties = ".\\files\\jmeter.properties";
    private final String jmeterCsvFile = ".\\files\\RealTimeReportsBase.csv";

    private StandardJMeterEngine jmeter;
    private HashTree testPlanTree;
    private String jmxFileName;

    public JMeterManager(String jmxFile) throws Exception {
        this.jmxFileName = jmxFile;
        this.jmeter = new StandardJMeterEngine();
        JMeterUtils.loadJMeterProperties(jmeterProperties);
        this.fileManager = new FileManager(jmeterCsvFile);
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();
        SaveService.loadProperties();
        File file = new File(jmxFileName);
        testPlanTree = loadJMX(file);
    }

    private HashTree loadJMX(File jmxFile) throws Exception {
        HashTree tree = SaveService.loadTree(jmxFile);
        JMeter.convertSubTree(tree, true);
        JMeterEngine engine = new StandardJMeterEngine();
        engine.configure(tree);
        return tree;
    }

    public void createNowCsvFiles() throws IOException {
        long now = System.currentTimeMillis();
        fileManager.createCsvFromTemplate("RealTimeReportsNowLimit10.csv", now, now, 10);
        fileManager.createCsvFromTemplate("RealTimeReportsNowLimit20.csv", now, now, 20);
        fileManager.createCsvFromTemplate("RealTimeReportsNowLimit30.csv", now, now, 30);
        fileManager.createCsvFromTemplate("RealTimeReportsNowLimit50.csv", now, now, 50);
        fileManager.createCsvFromTemplate("RealTimeReportsNowLimit70.csv", now, now, 70);
        fileManager.createCsvFromTemplate("RealTimeReportsNowLimit100.csv", now, now, 100);
    }

    public void createMonthCsvFiles() throws IOException {
        Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis();
        cal.add(Calendar.MONTH, -1);
        long monthBack = cal.getTimeInMillis();
        fileManager.createCsvFromTemplate("RealTimeReportsMonthLimit10.csv", monthBack, now, 10);
        fileManager.createCsvFromTemplate("RealTimeReportsMonthLimit20.csv", monthBack, now, 20);
        fileManager.createCsvFromTemplate("RealTimeReportsMonthLimit30.csv", monthBack, now, 30);
        fileManager.createCsvFromTemplate("RealTimeReportsMonthLimit50.csv", monthBack, now, 50);
        fileManager.createCsvFromTemplate("RealTimeReportsMonthLimit70.csv", monthBack, now, 70);
        fileManager.createCsvFromTemplate("RealTimeReportsMonthLimit100.csv", monthBack, now, 100);
    }

    public void createWeekCsvFiles() throws IOException {
        Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_WEEK, -1);
        long weekBack = cal.getTimeInMillis();
        fileManager.createCsvFromTemplate("RealTimeReportsWeekLimit10.csv", weekBack, now, 10);
        fileManager.createCsvFromTemplate("RealTimeReportsWeekLimit20.csv", weekBack, now, 20);
        fileManager.createCsvFromTemplate("RealTimeReportsWeekLimit30.csv", weekBack, now, 30);
        fileManager.createCsvFromTemplate("RealTimeReportsWeekLimit50.csv", weekBack, now, 50);
        fileManager.createCsvFromTemplate("RealTimeReportsWeekLimit70.csv", weekBack, now, 70);
        fileManager.createCsvFromTemplate("RealTimeReportsWeekLimit100.csv", weekBack, now, 100);
    }

    public void run() {
        try {
            log4j.info("Jmeter Start...");
            jmeter.configure(testPlanTree);
            jmeter.run();
            fileManager.deleteAllCsvTests();
            log4j.info("Jmeter finish...");
        } catch (IOException e) {
           log4j.debug(e);
       }
    }
}
