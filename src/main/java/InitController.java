
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class InitController {

    //Variabili di istanza:
    private String clonepathDir;
    private String repoUrl;
    private RepositoryJava repository;

    public static void setInstance(InitController instance) {
        InitController.instance = instance;
    }

    private static InitController instance;
    private InitController(){}

    static{
        try{
            instance = new InitController();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public RepositoryJava getRepository() {
        return repository;
    }

    public void setRepository(RepositoryJava repository) {
        this.repository = repository;
    }

    public static InitController getInstance(){
        return instance;
    }

    public String getClonepathDir() {
        return clonepathDir;
    }

    public void setClonepathDir(String clonepathDir) {
        this.clonepathDir = clonepathDir;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public void cloneRepository(String repoUrl, String clonepathDir) throws IOException {

        //Acquisire il nome del repository
        String nameRepo = getRepositoryNameFromPath(repoUrl);
        System.out.println("Repo Name: "+nameRepo);
        //Verificare che il clonePathDir sia un path valido
        if(Files.exists(Paths.get(clonepathDir)))
        {
            System.out.println("Cloning "+repoUrl+" into "+clonepathDir);
            String cmd1 = new String("git clone "+repoUrl+" "+clonepathDir+"\\"+nameRepo);
            ArrayList<String> commands = new ArrayList<String>(); commands.add(cmd1);
            executeListOfProcess(commands);
        }
        else
        {
            System.out.println("Directory inesistente");
        }

    }

    public boolean jgitCloneRepository() {
        File file = new File(this.clonepathDir);
        //Caso RepoUrl inesistente
        if(!isRepoUrl(this.repoUrl)) return false;
        //Caso Path inesistente
        if(!file.isDirectory()){
            System.out.println("Il path non fa riferimento ad un percorso valido");
            return false;
        }
        try {
                System.out.println("Cloning "+this.repoUrl+" into "+this.clonepathDir);
                Git clone = Git.cloneRepository()
                        .setURI(repoUrl)
                        .setDirectory(Paths.get(this.clonepathDir).toFile())
                        .call();
                System.out.println("Completed Cloning");
                clone.close();
                if(!analizeProjectThreeAndSearchJavasResources(this.clonepathDir))
                {
                    System.out.println("Non ci sono file Java al suo interno");
                    return false;
                }
                return true;
            } catch (GitAPIException e) {
                System.out.println("Exception occurred while cloning repo");
                e.printStackTrace();
                return false;
            }
            catch(JGitInternalException e){
                System.out.println("Non è possibile scaricare: "+getRepositoryNameFromPath(repoUrl)+" in "+this.clonepathDir);
                return false;
            }

    }


    private void executeListOfProcess(ArrayList<String> processes) throws IOException {
        StringBuilder output = new StringBuilder();
        for(String str : processes) {
            String s = null;
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", str);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
        }
    }

    private String getDirectoryNameFromPath(String path)
    {
        StringTokenizer tokenizer = new StringTokenizer(path,"\\");
        String name = null;
        while (tokenizer.hasMoreTokens()) {
            name = tokenizer.nextToken();
        }
        return name;
    }

    private String getRepositoryNameFromPath(String path)
    {
        StringTokenizer tokenizer = new StringTokenizer(path,"/");
        String name = null;
        while (tokenizer.hasMoreTokens()) {
            name = tokenizer.nextToken();
        }
        tokenizer = new StringTokenizer(name,".");
        return tokenizer.nextToken();
    }

    private boolean isUrlExist(String repoUrl){
        try
        {
            URL url = new URL("http://www.example.com");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            int responseCode = huc.getResponseCode();
            System.out.println("ResponseCode: "+responseCode);
            if(responseCode == 404)
            {
                return false;
            }
        }
        catch(IOException e)
        {
            return false;
        }

        return true;
    }

    private boolean isRepoUrl(String repoUrl)
    {
        try
        {
            final LsRemoteCommand lsCmd = new LsRemoteCommand(null);
            final List<String> repos = Arrays.asList(repoUrl);
            for (String gitRepo: repos){
                lsCmd.setRemote(gitRepo);
                System.out.println(lsCmd.call().toString());
            }
        }
        catch(GitAPIException e)
        {
            System.out.println("Il repo è inesistente");
            return false;
        }
        return true;
    }

    public boolean jgitDeleteRepository()
    {
        //Path inesistente
        File file = new File(this.clonepathDir);
        if(!file.isDirectory()){
            System.out.println("Non si può eliminare il Repository. La cartella è inesistente!");
            return false;
        }

        //Repository non presente
        try {
            Git git = Git.open(file);
            git.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Il Repository che si sta tentando di eliminare non è valido");
            return false;
        }

        //Cancellare il repo
        System.out.println("La rimozione del Repository cancellare la cartella superiore");
        deleteRepoInFolder(file);
        return true;
    }

    public static void deleteRepoInFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteRepoInFolder(f);
                } else {
                    f.delete();
                }
            }
            folder.delete();
        }
    }

    //Funzione ricorsiva per analizzare il FileSystem di progetto!
    private boolean analizeProjectThreeAndSearchJavasResources(String path)
    {
        File folder = new File(path);
        File[] files = folder.listFiles();
        if(files!=null)
        {
            for(File f: files) {
                if(f.isDirectory()) {
                    analizeProjectThreeAndSearchJavasResources(f.getPath());
                } else {
                    //Se è un file Java
                    System.out.println("Analize path: "+f.getPath()+" -FileName: "+f.getName());
                    if(isJavaFile(f))
                    {
                        System.out.println("File Java trovato: "+f.getName());
                        createJavaStructureAndAddToRepositoryJava(f);
                    }
                }
            }

        }
        return true;
    }

    private boolean isJavaFile(File filename)
    {
        //Prendo il path relativo al percorso
        String name = filename.getName();
        //Genero una stringa che conterrà l'estenzione "java" se si parla di file Java
        String extension = null;
        //Creo un tokenizer per identificare il deliminatore "."
        StringTokenizer str = new StringTokenizer(name,"."); int i = 0;
        //Se ci troviamo dinnanzi ad un file Java avremo bisogno di 2 token per arrivare all'estensione. Es: main.java token=main token java
        while(i<2 && str.hasMoreElements())
        {
            extension = new String(str.nextToken());
        }
        //Se ci troviamo dinnanzi ad un file java avremo come estensione "java" altrimenti qualsiasi altra cosa
        if(extension.equalsIgnoreCase("java")) return true;
        //qualsiasi altra cosa
        return false;
    }

    private void createJavaStructureAndAddToRepositoryJava(File filejava)
    {
        StringTokenizer str = new StringTokenizer(filejava.getName(),".");
        String nameFile = str.nextToken();
        String path = filejava.getPath();
        JavaStructure javaStr = new JavaStructure(nameFile,path);
        this.repository.addJavaStructure(javaStr);
    }

    private void printRepository()
    {
        for(JavaStructure j : this.repository.getStructure())
        {
            j.getInfo();
        }
    }



}
