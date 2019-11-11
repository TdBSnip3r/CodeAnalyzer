import java.util.ArrayList;

public class RepositoryJava {

    ArrayList<JavaStructure> structure;

    public RepositoryJava()
    {
        this.structure = new ArrayList<JavaStructure>();
    }

    public void addJavaStructure(JavaStructure structure)
    {
        this.structure.add(structure);
    }

    public ArrayList<JavaStructure> getStructure() {
        return structure;
    }

    public void setStructure(ArrayList<JavaStructure> structure) {
        this.structure = structure;
    }

    public void printAllElement()
    {
        for(JavaStructure j : this.structure)
        {
            j.getInfo();

        }
    }
}
