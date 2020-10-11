package cfd;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Test {
	
	private double dataSet [][][];
	private BufferedImage tImage;
//	private BufferedImage outputImage=null;
	
	public static void main(String [] args) throws Exception {
		
		   System.out.print("Enter the partition number : ");
		   int partitionNumber;
		   Scanner input =new Scanner(System.in);
		   partitionNumber=input.nextInt();
		   
		   
		   System.out.print("Enter the Round number : ");
		   int roundNumber;
		   roundNumber=input.nextInt();
		   
		   System.out.print("/**********Input received Successfully***************/"+"\n");
		   
		   File imageFolder = new File("D:\\\\Eclipse\\\\skinDetector\\\\ibtd");
		   File[] listOfImageFiles = imageFolder.listFiles();
		   
		   int testFileFirstRange=(listOfImageFiles.length/partitionNumber)*roundNumber;
		   int testFileLastRange=(listOfImageFiles.length/partitionNumber)*roundNumber+(listOfImageFiles.length/partitionNumber);
		   
		   for (int i=0 ; i<listOfImageFiles.length ; i++) {
			   
			   String outputFilePath;
			   
			   if(i>= testFileFirstRange && i<testFileLastRange) {
				   
				   if(i>=0 && i<10) {
					   outputFilePath="D:\\Eclipse\\skinDetector\\outputFolder3\\000"+String.valueOf(i)+".jpg";
				   }
				   
				   else if(i>=10 && i<100) {
					   outputFilePath="D:\\Eclipse\\skinDetector\\outputFolder3\\00"+String.valueOf(i)+".jpg";
				   }
				   
				   else {
					  outputFilePath="D:\\Eclipse\\skinDetector\\outputFolder3\\0"+String.valueOf(i)+".jpg";
				   }
				    
					Test t1= new Test();
					System.out.println(listOfImageFiles[i].getName());
					t1.outputCalculation(listOfImageFiles[i].getAbsolutePath().toString() , 0.8 ,outputFilePath);
				   
			   }
			   
		   }
		   
		   
		   
		
		System.out.println("/*****END*****/");
	
	}
	
	public void outputCalculation(String testFile , double threshHold , String outputFile) throws Exception {

		ObjectInputStream object_in_for_deserializable =
                new ObjectInputStream(new FileInputStream("probabilityRatio.txt"));
	            
	    dataSet =  (double[][][]) object_in_for_deserializable.readObject();
	            
		File testImage = new File(testFile);
		tImage=ImageIO.read(testImage);
		
		BufferedImage outputImage=null;
		outputImage=ImageIO.read(new File(testFile));
	//	ImageIO.write(tImage, "jpg", new File (outputFile));
		
		 int width = tImage.getWidth();
	     int height = tImage.getHeight();
	     
	     for(int i=0 ; i<height ; i++) {
	    	 for(int j=0 ; j<width ; j++) {
	    		 
	    		 Color c = new Color(tImage.getRGB(j, i));
	    		 Color color1 =new Color(220 , 220 ,220 );
	    		 Color color2 =new Color(128 , 128 ,128 );
	    		 if( dataSet[c.getRed()] [c.getGreen()] [c.getBlue()] >= threshHold ) {
	    			// System.out.println("Skin");
	    			// outputImage.setRGB(j,i, color2.getRGB());
	    		 }
	    		 
	    		 else {
	    			// System.out.println("non-Skin");
	    			 outputImage.setRGB(j,i, color1.getRGB());
	    		 }
	    	 }
	     }
	     
	 	ImageIO.write( outputImage, "jpg", new File (outputFile));
	     
	/*}
		catch(Exception e) {
			
			System.out.println();
		}*/
   }
	
	
}
