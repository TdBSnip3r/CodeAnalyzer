import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JavaStructure {

    private File fileJava;
    private String nameFile;
    private String pathFile;

    public ArrayList<String> getImportlst() {
        return importlst;
    }

    public void setImportlst(ArrayList<String> importlst) {
        this.importlst = importlst;
    }

    private ArrayList<String> importlst;

    public ArrayList<String> getStringsCode() {
        return stringsCode;
    }

    public void setStringsCode(ArrayList<String> stringsCode) {
        this.stringsCode = stringsCode;
    }

    //Tutto il codice Java in una lista di stringhe
    private ArrayList<String> stringsCode;

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

    //Variabili per numero di costruttori,import,function
    private int numbOfImport;

    public JavaStructure(String nameFile, String pathFile)
    {
        this.nameFile = nameFile;
        this.pathFile = pathFile;
        this.stringsCode = new ArrayList<String>();
        this.importlst = new ArrayList<String>();
    }

    public JavaStructure()
    {
        this.stringsCode = new ArrayList<String>();
        this.importlst = new ArrayList<String>();
    }

    public JavaStructure(File filejava)
    {
        this.stringsCode = new ArrayList<String>();
        this.importlst = new ArrayList<String>();
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
        boolean importDone = detectImport();
        //TUTTI IN AND
        return (readDone && staticCode && importDone);
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
        System.out.println("#############################");
        System.out.println("##########IMPORT#############");
        System.out.println("IMPORT LINE (linee import):  "+this.numbOfImport);
        for(String s : this.importlst) System.out.println("import "+s);

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
                    default:
                        break;
                }
            }
            if(s.isEmpty()) this.numbOfEmpyLine++;
            else this.numbOfCodeLine++;
        }
        return true;
    }


    public boolean detectImport()
    {
        String findStr = "import";
        ArrayList<Integer> indexOfimport = new ArrayList<Integer>();
        for(String s : this.stringsCode)
        {
            if(!s.isEmpty())
            {
                //Rimozione di tutti gli spazi nella stringa all'inizio in mezzo e alla fine.
                String fixStr = s.replaceAll(" ","");
                indexOfimport.clear();
                //Determina quanti import multipli per rigo ci sono e estrare gli indici
                int lastIndex = 0;
                int count = 0;
                while(lastIndex != -1){
                    lastIndex = fixStr.indexOf(findStr,lastIndex);
                    if(lastIndex != -1){
                        count ++;
                        indexOfimport.add(lastIndex);
                        lastIndex += findStr.length();
                    }
                }
                if(count>0)
                {
                    int importLenght = findStr.length();
                    System.out.println("COUNT: "+count);
                    for(int index : indexOfimport) System.out.println("Index: "+index);
                    System.out.println("fixStr: "+fixStr);
                    this.numbOfImport += count;
                    //Estrazione di tutti i valori
                    int i = 0;
                    for(i=0; i <= count-1 ;i++)
                    {
                        if(i==count-1)
                        {
                            //Preso alla fine, ultimo import
                            this.importlst.add( fixStr.substring( (indexOfimport.get(i)+importLenght),fixStr.length()-1) );
                        }
                        else if(i==0)
                        {
                            //Preso all'inizio (primo import)
                            this.importlst.add( fixStr.substring(importLenght,indexOfimport.get(i+1)) );
                        }
                        else
                        {
                            //Preso tra 2 import
                            this.importlst.add( fixStr.substring( (indexOfimport.get(i)+importLenght),indexOfimport.get(i+1)) );
                        }

                    }
                }
            }

            //Reset del conteggio
        }
        return true;
    }

    // SOLUZIONE ERRORI PMD
    // REGOLA: BeanMembersShouldSerialize
    // Found non-transient, non-static member. Please mark as transient or provide accessors.
    // SOLUZIONE: creare GETTER e SETTER per ogni variabile d'istanza della classe
    public File getFileJava() {
        return fileJava;
    }

    public void setFileJava(File fileJava) {
        this.fileJava = fileJava;
    }

    public int getNumbOfPoint() {
        return numbOfPoint;
    }

    public void setNumbOfPoint(int numbOfPoint) {
        this.numbOfPoint = numbOfPoint;
    }

    public int getNumbOfComma() {
        return numbOfComma;
    }

    public void setNumbOfComma(int numbOfComma) {
        this.numbOfComma = numbOfComma;
    }

    public int getNumbOfCircleParenthesisOpen() {
        return numbOfCircleParenthesisOpen;
    }

    public void setNumbOfCircleParenthesisOpen(int numbOfCircleParenthesisOpen) {
        this.numbOfCircleParenthesisOpen = numbOfCircleParenthesisOpen;
    }

    public int getNumbOfCircleParenthesisClose() {
        return numbOfCircleParenthesisClose;
    }

    public void setNumbOfCircleParenthesisClose(int numbOfCircleParenthesisClose) {
        this.numbOfCircleParenthesisClose = numbOfCircleParenthesisClose;
    }

    public int getNumbOfSquareParenthesisOpen() {
        return numbOfSquareParenthesisOpen;
    }

    public void setNumbOfSquareParenthesisOpen(int numbOfSquareParenthesisOpen) {
        this.numbOfSquareParenthesisOpen = numbOfSquareParenthesisOpen;
    }

    public int getNumbOfSquareParenthesisClose() {
        return numbOfSquareParenthesisClose;
    }

    public void setNumbOfSquareParenthesisClose(int numbOfSquareParenthesisClose) {
        this.numbOfSquareParenthesisClose = numbOfSquareParenthesisClose;
    }

    public int getNumbOfGraphParenthesisOpen() {
        return numbOfGraphParenthesisOpen;
    }

    public void setNumbOfGraphParenthesisOpen(int numbOfGraphParenthesisOpen) {
        this.numbOfGraphParenthesisOpen = numbOfGraphParenthesisOpen;
    }

    public int getNumbOfGraphParenthesisClose() {
        return numbOfGraphParenthesisClose;
    }

    public void setNumbOfGraphParenthesisClose(int numbOfGraphParenthesisClose) {
        this.numbOfGraphParenthesisClose = numbOfGraphParenthesisClose;
    }

    public int getNumbOfSemicolons() {
        return numbOfSemicolons;
    }

    public void setNumbOfSemicolons(int numbOfSemicolons) {
        this.numbOfSemicolons = numbOfSemicolons;
    }

    public int getNumbOfSpace() {
        return numbOfSpace;
    }

    public void setNumbOfSpace(int numbOfSpace) {
        this.numbOfSpace = numbOfSpace;
    }

    public int getNumbOfEmpyLine() {
        return numbOfEmpyLine;
    }

    public void setNumbOfEmpyLine(int numbOfEmpyLine) {
        this.numbOfEmpyLine = numbOfEmpyLine;
    }

    public int getNumbOfCodeLine() {
        return numbOfCodeLine;
    }

    public void setNumbOfCodeLine(int numbOfCodeLine) {
        this.numbOfCodeLine = numbOfCodeLine;
    }

    public int getNumbOfTab() {
        return numbOfTab;
    }

    public void setNumbOfTab(int numbOfTab) {
        this.numbOfTab = numbOfTab;
    }

    public int getNumbOfSpecialChar() {
        return numbOfSpecialChar;
    }

    public void setNumbOfSpecialChar(int numbOfSpecialChar) {
        this.numbOfSpecialChar = numbOfSpecialChar;
    }

    public int getNumbOfImport() {
        return numbOfImport;
    }

    public void setNumbOfImport(int numbOfImport) {
        this.numbOfImport = numbOfImport;
    }
}
