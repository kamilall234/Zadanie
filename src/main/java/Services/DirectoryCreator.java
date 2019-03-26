package Services;

import Manager.filesMoverCounter;
import Services.DirectoryFolderEnum.DirectoryFolderEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryCreator {

    private File folderCreator;
    private List<String> folderNames;


    public DirectoryCreator(){
        this.createDirectory();
    }

    private void createDirectory(){
        /*Load name */
        folderNames= Stream.of(DirectoryFolderEnum.values()).map(Enum::name).collect(Collectors.toList());
        /*Create a directory*/
        for (String folderName : folderNames
        ) {
            folderName.toLowerCase();
            try {
                Files.createDirectory(Paths.get(folderName));
            }catch (FileAlreadyExistsException e){
                System.out.println("FOLDER EXIST");
            }catch (IOException e){
                System.out.println("Some problem occured during the create "+folderName);
            }
        }


    }


}
