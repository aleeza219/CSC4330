import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;  

public class Tokens_List {
	public static HashMap<Integer, String> fileCode = new HashMap<Integer, String>();
	public static ArrayList<String> tokenList = new ArrayList<String>();
	public static ArrayList<String> keywords = new ArrayList<String>();
	public static void main(String[] args) throws FileNotFoundException {
	keywords.add("int"); keywords.add("String"); keywords.add("char"); keywords.add("float"); keywords.add("void");
		
		Scanner input = new Scanner(new File("SampleFile.txt"));
		int lineNo = 1;
		while (input.hasNextLine())
		{
			// Saving all the Code to fileCode, 
			//in my language, anything after ? will be considered comments until end of the line
			//ignoring empty lines and any comments in the file, then saving code in hashmap
			
			String line = input.nextLine();
			int i = line.indexOf("?");
			if (i != -1) {
				line = line.substring(0,i);
			}
			
			//if (!line.trim().isEmpty()) { // ignoring empty lines and replacing double space to 1 space
				fileCode.put(lineNo++, line.replace("  ", " ").trim());
			//}
		}
		
		//System.out.println(fileCode);
		
		for(int i=1; i<=fileCode.size(); i++) {
			if (!fileCode.get(i).isEmpty()) {
				tokenidentifier(fileCode.get(i), i);
			}
		}
		
		System.out.println("\n\n-------------------------------------Printing Token List from file---------------------------------");
		System.out.println(tokenList);	
		
	}
	
	public static void tokenidentifier(String line, int lineNo) {
		ArrayList<String> tempList = new ArrayList<String>( Arrays.asList(line.split(" ")));
		String temp = "";
		
		for (int i= 0; i < tempList.size(); i++) {
			if (keywords.contains(tempList.get(i))) {
				tokenList.add("TYPE_" + tempList.get(i).toUpperCase());
				System.out.println(tempList.get(i) + " Declared in line no " + lineNo);
			}else if (tempList.get(i).equals("+")) {
				tokenList.add("ADD_OP");	
				System.out.println("Mathematical Expression in Line no " + lineNo+ ": "+line.replace(';', '\0') + " = "+ performMathOperations(tempList.get(i), tempList.get(i-1), tempList.get(i+1)));
			}else if (tempList.get(i).equals("=")) {
				tokenList.add("ASSIGN_OP");
				System.out.println("Assignment Operation in Line no "  + lineNo + ": "+line.replace(';', '\0'));
			}else if (tempList.get(i).equals("-") || tempList.get(i).equals("~") || tempList.get(i).equals("`")) { //three symbols represent subtraction - ~ `
				tokenList.add("SUB_OP");
				System.out.println("Mathematical Expression in Line no " + lineNo+ ": "+line.replace(';', '\0') + " = "+ performMathOperations(tempList.get(i), tempList.get(i-1), tempList.get(i+1)));
			}else if (tempList.get(i).equals("/")) {
				tokenList.add("DIV_OP");
				System.out.println("Mathematical Expression in Line no " + lineNo+ ": "+line.replace(';', '\0') + " = "+ performMathOperations(tempList.get(i), tempList.get(i-1), tempList.get(i+1)));
			}else if (tempList.get(i).equals("*") || tempList.get(i).equals("^") || tempList.get(i).equals("#")) { //three symbols represent multiplication * ^ #
				tokenList.add("MUL_OP");
				System.out.println("Mathematical Expression in Line no " + lineNo+ ": "+line.replace(';', '\0') + " = "+ performMathOperations(tempList.get(i), tempList.get(i-1), tempList.get(i+1)));
			}else if (tempList.get(i).equals("\\")) {
				tokenList.add("MOD_OP");
			}else if (tempList.get(i).equals("&")) {
				tokenList.add("AND_OP");
			}else if (tempList.get(i).equals("|")) {
				tokenList.add("OR_OP");
			}else if (tempList.get(i).equals("!")) {
				tokenList.add("NOT_OP");
			}else if (tempList.get(i).equals("{")) {
				tokenList.add("LEFT_CURLY_BRAC");
			}else if (tempList.get(i).equals("}")) {
				tokenList.add("RIGHT_CURLY_BRAC");
			}else if (tempList.get(i).equals("(")) {
				tokenList.add("LEFT_PAREN");
			}else if (tempList.get(i).equals(")")) {
				tokenList.add("RIGHT_PAREN");
			}else if (tempList.get(i).equals("[")) {
				tokenList.add("LEFT_SQUARE_BRAC");
			}else if (tempList.get(i).equals("]")) {
				tokenList.add("RIGHT_SQUARE_BRAC");
			}else if (tempList.get(i).matches("\".*\"")) {
				tokenList.add("STRING_LIT");
			}else if (tempList.get(i).matches("[1-9]+")) {
				tokenList.add("INT_LIT");
			}else if (tempList.get(i).matches("\'.?\'")) {
				tokenList.add("CHAR_LIT");
			}else if (tempList.get(i).matches("[+-]?([0-9]*[.])?[0-9]+")) {
				tokenList.add("FLOAT_LIT");
			}else if (tempList.get(i).matches("[$]_?[a-zA-Z][a-zA-Z_0-9]*") || tempList.get(i).matches("[%]_?[a-zA-Z][a-zA-Z_0-9]*") || tempList.get(i).matches("[@]_?[a-zA-Z][a-zA-Z_0-9]*")) {
				temp = "IDENT";
				if(tempList.get(i).charAt(1) ==  '_') {
				temp = "PRIVATE_"+temp;
				}else {
					temp = "PUBLIC_"+temp;
				}
				if(tempList.get(i).charAt(0) ==  '$') {
				tokenList.add(temp+"_SCALAR_VAR");
				}else if(tempList.get(i).charAt(0) ==  '%') {
					tokenList.add(temp+"_HASHMAP");
				}else if(tempList.get(i).charAt(0) ==  '@') {
					tokenList.add(temp+"_ARRAY");
				}
			}else if(tempList.get(i).equals(";")) {
				tokenList.add("SEMICOLON");
			}else {
				tokenList.add("UNKNOWN");
			}
			
		}
		
		
	}
	
	public static String performMathOperations (String operation, String value1, String value2) {
		String result = "";
		int num1 =0;
		int num2 = 0;
		try {
		   num1 = Integer.parseInt(value1);
		   num2 = Integer.parseInt(value2);
		   
		   
		}
		catch (NumberFormatException e) {
		   return result;
		}
		
		if (operation.equals("+")) {
			result = (num1 + num2) + "";
		}else if (operation.equals("-") || operation.equals("~") || operation.equals("`")) { //three symbols represent subtraction - ~ `
			result = (num1 - num2) + "";
		}else if (operation.equals("/")) {
			result = (num1 / num2) + "";
		}else if (operation.equals("*") || operation.equals("^") || operation.equals("#")) { //three symbols represent multiplication * ^ #
			result = (num1 * num2) + "";
		}
	   
	   return result;
	}

}
