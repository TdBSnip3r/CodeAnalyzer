public class JavaStructure {
    private String nameFile;
    private String pathFile;

    public JavaStructure(String nameFile, String pathFile)
    {
        this.nameFile = nameFile;
        this.pathFile = pathFile;
    }

    public JavaStructure()
    {

    }


    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public void getInfo()
    {
        System.out.println("NameFile: "+this.nameFile);
        System.out.println("PathFile: "+this.pathFile);
    }
}
