package cfdTest;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class DataSetCreation {
	
	private static int tp;
	private static int tn;
	private static int fp;
	private static int fn;
	private double threshHold=0.8;
	
	 private BufferedImage image;
	 private BufferedImage mask;
	 
	 int width;
	 int height;
	
	public static void main(String [] args) {
		
		DataSetCreation obj=new DataSetCreation();
		obj.calculation();
		
		int[] arr=  new int [4];
		
		arr[0]=tp;
		arr[1]=tn;
		arr[2]=fp;
		arr[3]=fn;
		
		String fileName ="measurement.txt";
		FileOutputStream fout = null;
	    ObjectOutputStream txtSerialization = null;
	     try {
	    	 
	            fout = new FileOutputStream(fileName);
	            txtSerialization = new ObjectOutputStream(fout);
	            
	            txtSerialization.writeObject(arr);
	            txtSerialization.flush();
	                    
	        } catch (FileNotFoundException ex) {
	            System.out.println("FileOutputStream in " + ex.toString());
	        } catch (IOException ex) {
	            System.out.println("ObjectOutputStream in " + ex.toString());
	        } 
		
	        System.out.println("/*********************END**************/");
		
		
	}
	
	public void calculation() {
		
		   File imageFolder = new File("D:\\\\Eclipse\\\\skinDetector\\\\outputFolder3");
		   File[] listOfTestImageFiles = imageFolder.listFiles();
		   
		   File maskFolder = new File("D:\\\\Eclipse\\\\skinDetector\\\\ibtd\\\\mask");
		   File[] listOfMaskFiles = maskFolder.listFiles();
		   
		 /*  for(File file:listOfTestImageFiles) {
			   
			   System.out.println(file.getName());
		   }*/
		   
		   
		   for (int i=0 ; i<listOfMaskFiles.length ; i++) {
			   
			   System.out.println(listOfTestImageFiles[i].getName()+"		"+listOfMaskFiles[i].getName());
			   rgbCalculation(listOfTestImageFiles[i].getAbsolutePath().toString() , listOfMaskFiles[i].getAbsolutePath().toString());
		   }
		   
		
	}
	
	 public void rgbCalculation(String imageFile , String maskFile) {
	      try {
	    	  ObjectInputStream object_in_for_deserializable =
	                  new ObjectInputStream(new FileInputStream("probabilityRatioStatic.txt"));
	  	            
	  	    double [][][]dataSet =  (double[][][]) object_in_for_deserializable.readObject();
	    	// System.out.println(imageFile);
	         File imageInput = new File(imageFile);
	         File maskInput  = new  File(maskFile);
	         
	         image = ImageIO.read(imageInput);
	         mask  =  ImageIO.read(maskInput);
	         
	         width = image.getWidth();
	         height = image.getHeight();
	         
	       //  int count = 0;
	         
	         for(int i=0; i<height; i++) {
	         
	            for(int j=0; j<width; j++) {
	            	
	            	Color c = new Color(mask.getRGB(j, i));	
	            	Color cImage= new Color(image.getRGB(j, i));	
	            	
	            	if(skinOrNonSkin(c)) {
	            		
	            		if(dataSet[cImage.getRed()] [cImage.getGreen()] [cImage.getBlue()] >= threshHold ) {
	            			tp++;
	            		}
	            		
	            		else {
	            		
	            			fn++;
	            		}
	            	}
	            	
	            	else {
	            		
	            		if(dataSet[cImage.getRed()] [cImage.getGreen()] [cImage.getBlue()] >= threshHold) {
	            			tn++;
	            		}
	            		
	            		else {
	            		
	            			fp++;
	            		}
	            	}
	            }
	         }

	      } catch (Exception e) {
	    	  
	    	  System.out.print(e.toString());
	      }
	   }
	
   public boolean skinOrNonSkin(Color cl) {
		   
		   if(cl.getBlue() >=250 && cl.getGreen()>=250 && cl.getRed()>=250) {
			   return false;
		   }
		   
		   else {
			   return true;
		   }
	   }
}
