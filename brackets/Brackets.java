package brackets;

import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
/**
 *
 * @author g
 */
public class Brackets {
    
    private static final char QUOTE_CHAR = 0x22;
    private static final char APOST_CHAR = 0x27;
    private static final String QUOTE = "" + QUOTE_CHAR;
    private static final String APOST = "" + APOST_CHAR;
    
    public static void main(String[] args){
        Brackets bracket = new Brackets();
        File file = new File("/home/g/cst246-bags.java");
        bracket.processInput(file);
    }
    public void processInput(File file){
         try {
             Scanner stringParser = new Scanner(file);
             int lineNumber = 0;
            while (stringParser.hasNextLine()) {
                lineNumber++;
                String line = stringParser.nextLine();
                Stack<Character> open = new Stack<>();
                open.clear();
                System.out.println(removeCharacterLiterals(removeStringLiterals(
                            removeEscapedCharacters(removeLineComments(
                                    removeLineAlpha(line))))));
                String filtered=
                    removeCharacterLiterals(removeStringLiterals(
                            removeEscapedCharacters(removeLineComments(
                                    removeLineAlpha(line)))));
                for(int i = 0; i < filtered.length(); i++)
                {
                    char cha = filtered.charAt(i);
                    switch(cha)
                    {
                        case'{':
                        case'[':
                        case'(':
                            open.push(cha);
                    //        System.out.println("this is the line input: "+line);
                     //       System.out.println("push! character="+cha+" stack="+open);
                            break;
                            
                        case'}':
                        case']':
                        case')':
                            //in theory this should work....
                            //check if the top of the stack is the same as the char at i in the string
                            //pop if it is the partner
                            if( !open.isEmpty() )
                            {
                            char check = open.peek();
                             if((check=='{' && cha=='}')||(check=='[' && cha==']')||(check=='(' && cha==')')) 
                             {
                                open.pop();
                      //          System.out.println("pop! this character ="+cha);
                                break;
                                }
                            }
                         //  else{
                          //     System.out.println("There is a problem on line " +lineNumber); 
                          //  }
                        }
                }
            }
            stringParser.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
    private String removeStringLiterals(String line) throws IndexOutOfBoundsException{
        Stack<Integer> literals = new Stack<>();
        for(int i = 0; i<=line.length();i++){
            if(line.contains(QUOTE)){
            literals.push(i);
                if(line.contains(QUOTE)){
                    int index = i;
                    String sub = line.substring(i,index);
                    line.replace(sub, "");
                    literals.pop();
                }
            }
            else{
                break;
            }
        }
        return line;
    }
    private String removeCharacterLiterals(String line) throws IndexOutOfBoundsException{
        Stack<Integer> literals = new Stack<>();
        for(int i = 0; i<=line.length();i++){
            if(line.contains(APOST)){
            literals.push(i);
                if(line.contains(APOST)){
                    int index = i;
                    String sub = line.substring(i,index);
                    line.replace(sub, "");
                    literals.pop();
                }
            }
            else{
                break;
            }
        }
        return line;
    }
    private String removeEscapedCharacters(String line){
        //this will clean out all the escaped characters like \' or \"
        // only because they don't really matter when looking at lines 
        // this can then be used in @class removeCharacterLiterals or 
        // @class removeStringLiterals
        final String EAPOST = "\\\"";
        final String EQUOTE = "\\'";
        String clean = line;
        if(line.contains(EAPOST)){
            clean = line.replace(EAPOST,"");
        }
        else if(line.contains(EQUOTE)){
            clean = line.replace(EQUOTE, "");
        }
        return clean; 
    }
    private String removeLineComments(String line){
        //to be safe, we made clean equal to line
        //because it might actually be clean! 
        String clean = line;
        if(line.indexOf("////")>=0){
            clean = line.replaceAll("////", "");
        }
        return clean;
    }
    private String removeLineAlpha(String line){
        String clean = line;
        if(line.contains("[a-zA-Z]")){
        clean = line.replaceAll("[a-zA-Z]", "");
        }
        return clean;
    }
    
}
