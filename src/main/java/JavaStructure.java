import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JavaStructure {
    private File fileJava;
    private String nameFile;
    private String pathFile;

    //Tutto il codice Java in una lista di stringhe
    ArrayList<String> stringsCode;

    //Variabili per la sezione statica del codice
    private int numbOfPoint = 0;                        //PUNTI
    private int numbOfComma = 0;                        //VIRGOLE
    private int numbOfCircleParenthesisOpen = 0;        //PARENTESI (
    private int numbOfCircleParenthesisClose = 0;       //PARENTESI )
    private int numbOfSquareParenthesisOpen = 0;        //PARENTESI [
    private int numbOfSquareParenthesisClose = 0;       //PARENTESI ]
    private int numbOfGraphParenthesisOpen = 0;         //PARENTESI {
    private int numbOfGraphParenthesisClose = 0;        //PARENTESI }
    private int numbOfSemicolons = 0;                   //PUNTI E VIRGOLA
    private int numbOfSpace = 0;                        //SPAZI
    private int numbOfEmpyLine = 0;                     //LINEE VUOTE
    private int numbOfCodeLine = 0;                     //LINEE CODICE
    private int numbOfTab = 0;                          //TABULAZIONI
    private int numbOfSpecialChar = 0;                  //CARATTERI SPECIALI


    public JavaStructure(String nameFile, String pathFile)
    {
        this.nameFile = nameFile;
        this.pathFile = pathFile;
        this.stringsCode = new ArrayList<String>();
    }

    public JavaStructure()
    {
        this.stringsCode = new ArrayList<String>();
    }

    public JavaStructure(File filejava)
    {
        this.stringsCode = new ArrayList<String>();
        this.pathFile = filejava.getPath();
        String nameWithExtension = filejava.getName();
        StringTokenizer str = new StringTokenizer(nameWithExtension,".");
        this.nameFile = str.nextToken();
        this.fileJava = filejava;
    }

    public boolean generateStructure()
    {
        boolean readDone = readFile();
        boolean staticCode = staticCodeAnalysis();
        //TUTTI IN AND
        return (readDone && staticCode);
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

    //STAMPA LE INFORMAZIONI RELATIVE AL FILE JAVA: NOMEFILE,PERCORSO,CONTENUTO DEL CODICE
    public void getInfo()
    {
        System.out.println("NameFile: "+this.nameFile);
        System.out.println("PathFile: "+this.pathFile);
        System.out.println("#############################");
        System.out.println("ContentOfFile:");
        printFile();
        System.out.println("#############################");
        System.out.println("Static element of code:");
        System.out.println("POINT: "+this.numbOfPoint);
        System.out.println("COMMA (Vigole): "+this.numbOfComma);
        System.out.println("OPEN ROUND PARANTHESIS: "+this.numbOfCircleParenthesisOpen);
        System.out.println("CLOSE ROUND PARANTHESIS: "+this.numbOfCircleParenthesisClose);
        System.out.println("OPEN SQUARE PARANTHESIS: "+this.numbOfSquareParenthesisOpen);
        System.out.println("CLOSE SQUARE PARANTHESIS: "+this.numbOfSquareParenthesisClose);
        System.out.println("OPEN GRAPH PARANTHESIS: "+this.numbOfGraphParenthesisOpen);
        System.out.println("CLOSE GRAPH PARANTHESIS: "+this.numbOfGraphParenthesisClose);
        System.out.println("SEMICOLON (Punti e virgole):  "+this.numbOfSemicolons);
        System.out.println("SPACE (spazi):  "+this.numbOfSpace);
        System.out.println("TAB (tabulazioni): "+this.numbOfTab);
        System.out.println("EMPTY LINE (linee vuote):  "+this.numbOfEmpyLine);
        System.out.println("CODE LINE (linee codice):  "+this.numbOfCodeLine);
    }

    //LA SEGUENTE FUNZIONE LEGGE IL CONTENUTO DEL CODICE JAVA TROVATO LINEA A LINEA IN UNA LISTA DI STRINGHE
    private boolean readFile() {
        try{
            Scanner s = new Scanner(new File(this.pathFile));
            while (s.hasNextLine()){
                this.stringsCode.add(s.nextLine());
            }
            s.close();
            return true;
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Il file non è presente");
            return false;
        }
    }

    //LA SEGUENTE FUNZIONE STAMPA IL CONTENUTO DEL CONTENUTO DEL FILE PROCESSATO DA READFILE
    public void printFile()
    {
        for(String s : this.stringsCode)
        {
            System.out.println(">>: "+s);
        }
    }

    //LA SEGUENTE FUNZIONE DETERMINA TUTTI GLI ELEMENTI STATICI DEL CODICE
    public boolean staticCodeAnalysis()
    {
        for(String s : this.stringsCode)
        {
            int i;
            for(i = 0 ; i < s.length() ;i++)
            {
                switch(s.charAt(i))
                {
                    case '.':
                        this.numbOfPoint++;
                        break;
                    case ',':
                        this.numbOfComma++;
                        break;
                    case ';':
                        this.numbOfSemicolons++;
                        break;
                    case '(':
                        this.numbOfCircleParenthesisOpen++;
                        break;
                    case ')':
                        this.numbOfCircleParenthesisClose++;
                        break;
                    case '[':
                        this.numbOfSquareParenthesisOpen++;
                        break;
                    case ']':
                        this.numbOfSquareParenthesisClose++;
                        break;
                    case '{':
                        this.numbOfGraphParenthesisOpen++;
                        break;
                    case '}':
                        this.numbOfGraphParenthesisClose++;
                        break;
                    case ' ':
                        this.numbOfSpace++;
                        break;
                    case '\t':
                        this.numbOfTab++;
                        break;
                }
            }
            if(s.isEmpty()) this.numbOfEmpyLine++;
            else this.numbOfCodeLine++;
        }
        return true;
    }
}
