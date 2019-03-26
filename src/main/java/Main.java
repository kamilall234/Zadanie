import Manager.MonitoringManager;
import Services.DirectoryCreator;

public class Main {

        public static void main(String[] args){
            System.out.println("Start");
            DirectoryCreator directoryCreator = new DirectoryCreator();
            MonitoringManager monitoringManager = new MonitoringManager();
        }
}
