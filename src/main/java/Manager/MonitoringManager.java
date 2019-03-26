package Manager;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;

public class MonitoringManager {
    private boolean counterFileCreated;
    private File counterFile;
    private final File directory;
    private final String path = "HOME"+File.separator+"count.txt";
    private final String content = new String("JarEven;JarOdd;XML");
    /*In this LIST we save the value of .jarOdd{0} .jarEven{1} and .xml{2} moved to specify folder*/
    private List<Integer> counterMovedFile;
    private final String[] extensionFile = new String[]{"jar","xml"};
    private List<File> currentFileList;


    public MonitoringManager() {
        counterFileCreated = createTxtCounterFile();
        counterMovedFile = new ArrayList<Integer>();
        for(int i=0;i<3;i++){
            counterMovedFile.add(i,0);
        }
        directory = new File("HOME");
        this.run();
    }

    private void run(){
        while(true){
            currentFileList=(List<File>)FileUtils.listFiles(directory,extensionFile,true);
            System.out.println(currentFileList);
            if(!currentFileList.isEmpty()){
                for (File file:currentFileList
                ) {
                    int counter=0;
                    /*If file is xml, or else if is .jar*/
                    if(FilenameUtils.isExtension(file.getPath(),extensionFile[1])){
                        System.out.println(file.getPath());
                        try{
                            FileUtils.moveFileToDirectory(file, new File("DEV"),false);
                            savingCounterValueToTxtFile(2);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }else if (FilenameUtils.isExtension(file.getPath(),extensionFile[0])){
                        /*Getting hour from date creating file*/
                        DateTime timeFileCreation = new DateTime(file.lastModified());
                        System.out.println(timeFileCreation);
                        int hour = timeFileCreation.getHourOfDay();
                        if(hour%2==1){
                            System.out.println(file.getPath());
                            try{
                                FileUtils.moveFileToDirectory(file, new File("DEV"),false);
                                savingCounterValueToTxtFile(0);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }else {
                            System.out.println(file.getPath());
                            try{
                                FileUtils.moveFileToDirectory(file, new File("TEST"),false);
                                savingCounterValueToTxtFile(1);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }


    private boolean createTxtCounterFile(){
        counterFile = new File(path);
        if(counterFile.exists()){
            try {
                counterFile.createNewFile();
                this.creatingContentCounterFile();
                return true;
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }else {
            System.out.println("Counter File exist in Directory");
            return true;
        }
    }

    private void creatingContentCounterFile(){
        if(counterFile.length()==0){
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
                bufferedWriter.write(content);
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("Can't write to file counter");
            }
        }
    }

    private void savingCounterValueToTxtFile(int typeOfDocument){
        int counter=0;
        if ((counterMovedFile.size()>typeOfDocument)){
            counter = counterMovedFile.get(typeOfDocument);
        }
        counter++;
        counterMovedFile.add(typeOfDocument,counter);
    }

    private void savingToFile(){

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            bufferedWriter.write(content);
            String valueContent = new String(counterMovedFile.toString());
            bufferedWriter.write(valueContent);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Can't write to file counter");
        }
    }

    private void readingFromTxtCounterOnStartup(){

    }
}