import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner; 
import java.util.ArrayList;

public class fileChar {
	public static void main(String[] args) {
		try {
			File file = new File("Aleeza.txt");
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String content = scan.nextLine();
				int length = content.length();
				int i = 0;
				ArrayList<String> list = new ArrayList<String>();
				String temp = "";
				int j;
				while(i<length) {
					if(Character.isAlphabetic(content.charAt(i))) {
						temp = content.charAt(i)+"";
						j=i+1;
						while(j<length && Character.isAlphabetic(content.charAt(j))) {
							temp = temp+content.charAt(j);
							j++; i++;
						}
						list.add(temp+"\tword");
						
					} else if(Character.isDigit(content.charAt(i))) {
						temp = content.charAt(i)+"";
						j=i+1;
						while(j<length && Character.isDigit(content.charAt(j))) {
							temp = temp+content.charAt(j);
							j++; i++;
						}
						list.add(temp+"\tnumber");
					} else if(!Character.isLetterOrDigit(content.charAt(i))){
						temp = content.charAt(i)+"";
						j=i+1; 
						while(j<length && Character.isLetterOrDigit(content.charAt(j)) == false) {
							temp = temp+content.charAt(j);
							j++; i++;
						}
						list.add(temp+"\tspecial character");
					}
				i++;
				}
				for (int k = 0; k < list.size(); k++) {
				      System.out.println(list.get(k));
				    }
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}
}