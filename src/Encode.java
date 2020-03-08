import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Encode {
	BufferedImage image;
	double PSNR=0.0;
	int[][]temp;
    int width;
    int height;
    int BlockSize=8;
    RGB CTool;
    dctAlgorithm dctTool;
    public Encode(BufferedImage p, int quality) {
    	image = p;
        width = image.getWidth();
        height = image.getHeight();
        CTool = new RGB(image);
        double[][] Rin = CTool.getRedArray();
        double[][] Gin = CTool.getGreenArray();
        double[][] Bin = CTool.getBlueArray();
        dctTool = new dctAlgorithm(quality);
        double[][] Redresult = process(Rin, BlockSize);
        double[][] Greenresult = process(Gin, BlockSize);
        double[][] Blueresult = process(Bin, BlockSize);
        suppressMatrix(Redresult);
        suppressMatrix(Greenresult);
        suppressMatrix(Blueresult);
        
        
        int[][] RGBResult = new int[width][height];
        for(int q = 0; q < RGBResult.length; q++){
            for(int w = 0; w < RGBResult[0].length; w++){
                
                RGBResult[q][w] = new Color((int)Math.round(Redresult[q][w]), (int)Math.round(Greenresult[q][w]),(int)Math.round(Blueresult[q][w])).getRGB();
            }
        }
        
       for (int q = 0; q < width; q++){
            for (int w = 0; w < height; w++){
                image.setRGB(q, w, RGBResult[q][w]);
            }
        }
       temp=RGBResult;
       double psnr=0.0;
   	try {
			psnr=10*Math.log10(255*255/((Mse(Rin,Redresult)+Mse(Gin,Greenresult)+Mse(Bin,Blueresult))/3));
			//JOptionPane.showMessageDialog(null, psnr, "PSNR值为 ", JOptionPane.INFORMATION_MESSAGE);
			//System.out.println(psnr);
			PSNR=psnr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//计算PSNR
    }
    public double[][] process(double[][] in, int BlockSize)
    {
        int i;
        int j;
        int a;
        int b;
        int xpos;
        int ypos;
        double dctArray1[][] = new double[BlockSize][BlockSize];
        double dctArray2[][]  = new double[BlockSize][BlockSize];
        double dctArray3[][]  = new double[BlockSize][BlockSize];
        double dctArray4[][]  = new double[BlockSize][BlockSize];
        double reconstImage[][] = new double[width][height];
        for (i=0; i< width / BlockSize; i++)
        {
            for (j=0; j< height / BlockSize; j++)
            {
                xpos = i * BlockSize;
                ypos = j * BlockSize;
                for (a=0; a<BlockSize; a++)
                {
                    for (b=0; b<BlockSize; b++)
                    {
                        dctArray1[a][b] = in[xpos+a][ypos+b];
                    }
                }
                dctArray2 = dctTool.forwardDCT(dctArray1);

                dctArray3 = dctTool.quantitizeImage(dctArray2);

                for ( a=0; a<BlockSize; a++)
                {
                    for (b=0; b<BlockSize; b++)
                    {
                        reconstImage[xpos+a][ypos+b] = dctArray3[a][b];
                    }
                }

            }
        }


        for (i=0; i< width / BlockSize; i++)
        {
            for (j=0; j< height / BlockSize; j++)
            {
                xpos = i * BlockSize;
                ypos = j * BlockSize;

                for (a=0; a<BlockSize; a++)
                {
                    for (b=0; b<BlockSize; b++)
                    {
                        dctArray2[a][b] = reconstImage[xpos+a][ypos+b];
                    }
                }

                dctArray3 = dctTool.dequantitizeImage(dctArray2);
                dctArray4 = dctTool.inverseDCT(dctArray3);

                for (a=0; a<BlockSize; a++)
                {
                    for (b=0; b<BlockSize; b++)
                    {
                        reconstImage[xpos+a][ypos+b] = dctArray4[a][b];
                    }
                }

            }
        }

        return reconstImage;
    }

    //Converts double[][] to int[][]
    public int[][] MatDoubletoInt(double[][] in){
        int[][] result = new int[in.length][in[0].length];
        for(int i = 0; i < in.length; i++){
            for(int j = 0; j < in[0].length; j++){
                result[i][j] = (int)(in[i][j]);
                if(result[i][j] < 0)
                    result[i][j] = 0;
                else if(result[i][j] > 255)
                    result[i][j] = 255;
            }
        }    
        return result;
    }
        
    //Prints matrix of type double[][]
    public static void printMatrix(double[][] in){
        for(int i = 0; i < in.length; i++){
            for(int j = 0; j < in[0].length; j++){
                System.out.print(in[i][j] + " ");
            }        
            System.out.println();
        }
    }
    
    //Prints matrix of type int[][]
    public static void printMatrix(int[][] in){
        for(int i = 0; i < in.length; i++){
            for(int j = 0; j < in[0].length; j++){
                System.out.print(in[i][j] + " ");
            }        
            System.out.println();
        }
    }         
    
    public static void suppressMatrix(double[][] in){
        for(int i = 0; i < in.length; i++){
            for(int j = 0; j < in[0].length; j++){
                if(in[i][j] < 0)
                    in[i][j] = 0;
                else if(in[i][j] > 255)
                    in[i][j] = 255;
                    
            }        
          
        }
    }
    public double Mse(double[][] rin,double[][] redresult) throws IOException{
    
    	double mse=0;
    	System.out.println(rin[0][0]);
    	System.out.println(redresult[0][0]);
    	System.out.println(Math.pow(rin[0][0]-redresult[0][0],2));
    	for(int i=0;i<width;i++) {
    		for(int j=0;j<height;j++) {
    			mse+= Math.pow((rin[i][j]-redresult[i][j]),2);
    		}
    	}
        return mse/(width*height);
    }
   
    
}
