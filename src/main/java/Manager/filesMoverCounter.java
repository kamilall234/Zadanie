package Manager;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class filesMoverCounter {
    private String name = "count";
    private String pathToFile = "home/count.txt";
    public filesMoverCounter(){

    }

    public boolean filesMoverCounterCreating(){
        try {
            Files.createFile(Paths.get(pathToFile));
            return true;
        }catch (FileAlreadyExistsException e){
            System.out.println("File Exist");
            return false;
        }
        catch (IOException e){
            System.out.println("An Error occured during the file counter creation");
            return false;
        }
    }


}
