package skinDetector;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

public class Test {
	
	private double dataSet [][][];
	private BufferedImage tImage;
//	private BufferedImage outputImage=null;
	
	public static void main(String [] args) throws Exception {
		String testFile="C:\\Users\\Dell\\Downloads\\t3.jpg";
		Test t1= new Test();
		t1.outputCalculation(testFile, 1,"C:\\Users\\Dell\\Downloads\\parineeta_output.jpg");
		System.out.println("*****END*****");
	
	}
	
	public void outputCalculation(String testFile , double threshHold , String outputFile) throws Exception {

		Trainning trainning =new Trainning();
		trainning.controller();
	            
	    dataSet =  trainning.getProbabilityRatio();
	            
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
