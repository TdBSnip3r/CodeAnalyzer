import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Test {
    public static void main(String args[]){
        String repoUrl = "https://github.com/TdBSnip3r/TEST.git";
        String clonepathDir = "C:\\Users\\vince\\Desktop\\Repo";
        InitController.getInstance().setRepoUrl(repoUrl);
        InitController.getInstance().setClonepathDir(clonepathDir);
        boolean done = InitController.getInstance().jgitCloneRepository();
        //boolean delete = InitController.getInstance().jgitDeleteRepository();
    }



}
