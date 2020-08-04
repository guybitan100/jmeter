
public class JMeterMain {


    public static void main(String[] args) throws Exception {
        JMeterManager jmeterManager = new JMeterManager(".\\files\\RealTimeRequestsNow.jmx");
        jmeterManager.createNowCsvFiles();
        jmeterManager.run();

        jmeterManager = new JMeterManager(".\\files\\RealTimeRequestsWeek.jmx");
        jmeterManager.createWeekCsvFiles();
        jmeterManager.run();

        jmeterManager = new JMeterManager(".\\files\\RealTimeRequestsMonth.jmx");
        jmeterManager.createMonthCsvFiles();
        jmeterManager.run();

    }
}