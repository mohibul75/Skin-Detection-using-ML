package cfd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Trainning {
	
	private int skin[][][];
	private int nonSkin[][][];
	private int totalSkinPixel=0;
	private int totalNonSkinPixel=0;
	private static double probabilityRatio[][][] ;
	
	   public  void controller() throws Exception {
		   
		   TrainningDataCreation obj =new TrainningDataCreation();
		   obj.controller() ;
		   
		   skin=obj.getSkin();
		   nonSkin=obj.getNonSkin();
		   
		   
		   calculation();
		   trainnerFunction();
		   
	   }
	   
	   public void calculation() {
		  
		   for(int i=0 ; i<256 ; i++) {
			   for(int j=0 ; j<256 ; j++) {
				   for(int h=0 ; h<256 ; h++) {
					   
					  // skin[i][j][h]++;
					   totalSkinPixel+=skin[i][j][h];
					   
					   
					   
					  // nonSkin[i][j][h]++;
					   totalNonSkinPixel+=nonSkin[i][j][h];
					   
				   }
			   }
		   }
	   }
	   
	   public void trainnerFunction() {
		   
		   double probabilitySkin[][][]    =new double [256][256][256];
		   double probabilityNonSkin[][][] =new double [256][256][256];
		   probabilityRatio =new double [256][256][256];
		   
		//   precition = (double)totalSkinPixel/(totalSkinPixel+totalNonSkinPixel);
		   
		   for(int i=0 ; i<256 ; i++) {
			   for(int j=0 ; j<256 ; j++) {
				   for(int h=0 ; h<256 ; h++) {
					   
					//   System.out.println("skin"+skin[i][j][h]);
					//   System.out.println("skin"+nonSkin[i][j][h]);
					   
					   probabilitySkin[i][j][h]=((double)skin[i][j][h])/(totalSkinPixel);
					   probabilityNonSkin[i][j][h]=((double)nonSkin[i][j][h])/(totalNonSkinPixel);
					   
					 //  System.out.println( "PS"+probabilitySkin[i][j][h]);
					  // System.out.println( "PNS"+probabilityNonSkin[i][j][h]);
					   
					   if( probabilityNonSkin[i][j][h] <= 1e-30) {
						   
						   probabilityRatio[i][j][h]=probabilitySkin[i][j][h];
					   }
					   else {
						   
						   probabilityRatio[i][j][h]=probabilitySkin[i][j][h]/probabilityNonSkin[i][j][h];
					   }
					   
					 //  System.out.println("pR"+probabilityRatio[i][j][h]);
					   
				   }
			   }
		   }
	   }
	   
	   
	   
	   public double[][][] getProbabilityRatio() {
		 
		/*   for(int i=0 ; i<25 ; i++) {
			   for(int j=0 ; j<25 ; j++) {
				   for(int h=0 ; h<25 ; h++) {
					   System.out.println(probabilityRatio[i][j][h]);
				   }
			   }
		   }*/
			   
		return probabilityRatio;
	}

	public void print(TrainningDataCreation obj) {
		   
		   
		  System.out.println(totalSkinPixel+totalNonSkinPixel);
		   
		/*   for(int i=0 ; i<25 ; i++) {
			   for(int j=0 ; j<25 ; j++) {
				   for(int h=0 ; h<25 ; h++) {
					   System.out.println(skin[i][j][h]);
				   }
			   }
		   }*/
		   
	   }
	
	public static void main(String []args) throws Exception {
		
		Trainning trainner = new Trainning();
		trainner.controller();
		
		String fileName ="probabilityRatio.txt";
		FileOutputStream fout = null;
	    ObjectOutputStream txtSerialization = null;
	     try {
	    	 
	            fout = new FileOutputStream(fileName);
	            txtSerialization = new ObjectOutputStream(fout);
	            
	            txtSerialization.writeObject(probabilityRatio);
	            txtSerialization.flush();
	                    
	        } catch (FileNotFoundException ex) {
	            System.out.println("FileOutputStream in " + ex.toString());
	        } catch (IOException ex) {
	            System.out.println("ObjectOutputStream in " + ex.toString());
	        } 
		
	        System.out.println("/*********************END**************/");
		
	}

}
