package cfdTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Test {
	
	private int tp;
	private int fp;
	private int tn;
	private int fn;
	
	private double accuracy;
	private double precision;
	private double recall;
	private double fmeasure;
	
	private int []arr=new int[4];
	
	public static void main(String[]args) throws FileNotFoundException, IOException {
		
		Test obj1=new Test();
		obj1.termCalculation();
	}
	
	public void termCalculation() throws FileNotFoundException, IOException {
		
		ObjectInputStream object_in_for_deserializable =
                new ObjectInputStream(new FileInputStream("measurement.txt"));
	            
	    try {
			arr =  (int[]) object_in_for_deserializable.readObject();
			
			tp=arr[0];
			tn=arr[1];
			fp=arr[2];
			fn=arr[3];
			
			accuracy=2*(double)(tp+tn)/(tp+tn+fn+fp)*100;
			precision=(double)(tp)/(tp+fp)*100;
			recall=(double)(tp)/(tp+fn)*100;
			fmeasure=(double)(2*recall*precision)/(recall+precision);
			
			
			System.out.println("Accuracy	:	"+accuracy+"%");
			System.out.println("precision	:	"+precision+"%");
			System.out.println("recall	:	"+   recall+"%");
			System.out.println("fmeasure	:	"+fmeasure);
			
			
			
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
