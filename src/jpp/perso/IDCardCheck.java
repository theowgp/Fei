package jpp.perso;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;







public class IDCardCheck {
	
	
	public static void main (String[] args){
		
		String[] mrz = new String[3];
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
        try {
        	System.out.println("Enter the first row: ");
			mrz[0] = br.readLine();
			
			System.out.println("Enter the second row: ");
			mrz[1] = br.readLine();
			
			System.out.println("Enter the third row, if not applicable just press Enter: ");
			mrz[2] = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("The ausweiss is: ");
        System.out.println(checkIDD(mrz));
        
        
	}
	
	
	
	
	
	public static IDCardCheckResult checkIDD(String[] mrz){
		if(mrz == null) throw new NullPointerException();
//		throw	IllegalArgumentException
		

//		new ausweiss
//		if(mrz.length == 3){
		if(mrz[2].length() != 0){
//			PATTERN
//			checking if the length is correct
			if(!check_length(mrz, 30)){
//			   	System.out.println("INVALID_FORMAT, length is not 30");
			   	return IDCardCheckResult.INVALID_FORMAT;
			}
			
//			checking the first pattern
			String pattern1 = "\\bIDD<<(\\d|\\w){8}\\d<*";
			Pattern r1 = Pattern.compile(pattern1);
			
			Matcher m1 = r1.matcher(mrz[0]);
		    if (!m1.find( )) {
//		       System.out.println("INVALID_FORMAT, the first pattern is broken, new Ausweiss");
		       return IDCardCheckResult.INVALID_FORMAT;
		    } 
		    
//			checking the second pattern
			String pattern2 = "\\b\\d{7}<\\d{7}D<+\\d\\b";
			Pattern r2 = Pattern.compile(pattern2);
			
			Matcher m2 = r2.matcher(mrz[1]);
		    if (!m2.find( )) {
//		       System.out.println("INVALID_FORMAT, the second pattern is broken, new Ausweiss");
		       return IDCardCheckResult.INVALID_FORMAT;
		    }		    
			
//			checking the third pattern
			String pattern3 = "\\b\\w+<<\\w+<*";
			Pattern r3 = Pattern.compile(pattern3);
			
			Matcher m3 = r3.matcher(mrz[2]);
		    if (!m3.find( )) {
//		       System.out.println("INVALID_FORMAT, the third pattern is broken, new Ausweiss");
		       return IDCardCheckResult.INVALID_FORMAT;
		    }
			
			
			
			
		}
//		old ausweiss
		else{
//			PATTERN
//			checking if the length is correct
			if(!check_length(mrz, 36)){
//			   	System.out.println("INVALID_FORMAT, length is not 36");
			   	return IDCardCheckResult.INVALID_FORMAT;
			}
			
//			checking the first pattern
			String pattern1 = "\\bIDD<<\\w+<<\\w+<*";
			Pattern r1 = Pattern.compile(pattern1);
			
			Matcher m1 = r1.matcher(mrz[0]);
		    if (!m1.find( )) {
//		       System.out.println("INVALID_FORMAT, the first pattern is broken, old Ausweiss");
		       return IDCardCheckResult.INVALID_FORMAT;
		    } 
		    
//			checking the second pattern
			String pattern2 = "\\d{10}((\\w<<)|(\\w\\w<)|(\\w\\w\\w))\\d{7}<\\d{7}<+\\d";
			Pattern r2 = Pattern.compile(pattern2);
			
			Matcher m2 = r2.matcher(mrz[1]);
		    if (!m2.find( )) {
//		       System.out.println("INVALID_FORMAT, the second pattern is broken, old Ausweiss");
		       return IDCardCheckResult.INVALID_FORMAT;
		    }
		    
		    
//		    CHECKSUM
//		    part one
		    int[] numbers = mypars(mrz[1].substring(0, 9));
		    int proof = Integer.parseInt(String.valueOf(mrz[1].charAt(9)));
		    if(calcChecksum(numbers) != proof)return IDCardCheckResult.INVALID_CHECKSUM;

//		    part two
		    numbers = mypars(mrz[1].substring(13, 19));
		    proof = Integer.parseInt(String.valueOf(mrz[1].charAt(19)));
		    if(calcChecksum(numbers) != proof)return IDCardCheckResult.INVALID_CHECKSUM;
		    
		    
//		    part three
		    numbers = mypars(mrz[1].substring(21, 27));
		    proof = Integer.parseInt(String.valueOf(mrz[1].charAt(27)));
		    if(calcChecksum(numbers) != proof)return IDCardCheckResult.INVALID_CHECKSUM;
		    
		    
		    
		    
			
		}
		
		return IDCardCheckResult.VALID;
		
	}
	
	private static boolean check_length(String[] mrz, int n){
		for(String s : mrz) {
		    if(s.length() != n && s.length() > 0){
		    	return false;
		    }
		}
		return true;
	}
	public static int[] mypars(String s){
		int[] numbers = new int[s.length()];
		for(int i=0; i<s.length(); i++) {
			numbers[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
		}
		return numbers;
	}
	
	
	
	
	public static int calcChecksum(int[] numbers){
		int[] proofs = {7, 3, 1};
		
		int temp = 0;
		for(int i=0; i<numbers.length; i++){
			temp += proofs[i%3] * numbers[i]; 
		}
		
		return temp%10;
	}
	
	
	
	

}
