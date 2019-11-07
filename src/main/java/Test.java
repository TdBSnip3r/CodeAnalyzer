public class Test {
    public static void main(String args[]){
        String repoUrl = "https://github.com/TdBSnip3r/TEST.git";
        String clonepathDir = "C:\\Users\\vince\\Desktop\\Repo";
        RepositoryJava rep = new RepositoryJava();
        InitController.getInstance().setRepository(rep);
        InitController.getInstance().setRepoUrl(repoUrl);
        InitController.getInstance().setClonepathDir(clonepathDir);
        boolean done = InitController.getInstance().jgitCloneRepository();
        //boolean delete = InitController.getInstance().jgitDeleteRepository();
        System.out.println("Contenuto del repository dopo scansione da parte di InitController: ");
        rep.printAllElement();
    }



}
