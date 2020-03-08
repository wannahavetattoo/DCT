public class dctAlgorithm {
	//Block size
    public int N = 8;

    //Quality
    public int QUALITY;

    //Image Width
    public int ROWS;

    //Image Height
    public int COLS;
    
    //Cosine Matrix
    public double c[][] = new double[N][N];

    //Transposed matrix
    public double cT[][] = new double[N][N];

    //Quantization matrix
    public int quantize[][]     = new int[N][N];

    //DCT result matrix
    public int resultDCT[][] = new int[ROWS][COLS];

    /**
     * @param QUALITY The image quality
     */
    public dctAlgorithm(int QUALITY)
    {
        initMatrix(QUALITY);
        
    }

    /**
     *.Sets up the matrices we need in our transformation.
     * @param quality The image quality
     */
    private void initMatrix(int quality)
    {
    	
        int i;
        int j;

        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                quantize[i][j] = (1 + ((1 + i + j) * quality));
            }
        }

        for (j = 0; j < N; j++)
        {
            double nn = (double)(N);
            c[0][j]  = 1.0 / Math.sqrt(nn);
            cT[j][0] = c[0][j];
        }

        for (i = 1; i < 8; i++)
        {
            for (j = 0; j < 8; j++)
            {
                double jj = (double)j;
                double ii = (double)i;
                c[i][j]  = Math.sqrt(2.0/8.0) * Math.cos(((2.0 * jj + 1.0) * ii * Math.PI) / (2.0 * 8.0));
                cT[j][i] = c[i][j];
            }
        }
    }

    public double[][] forwardDCT(double input[][])
    {
        double output[][] = new double[N][N];
        double temp[][] = new double[N][N];
        double temp1;
        int i;
        int j;
        int k;

        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                temp[i][j] = 0.0;
                for (k = 0; k < N; k++)
                {
                    temp[i][j] += (((int)(input[i][k]) - 128) * cT[k][j]);
                }
            }
        }

        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                temp1 = 0.0;

                for (k = 0; k < N; k++)
                {
                    temp1 += (c[i][k] * temp[k][j]);
                }

                output[i][j] = (int)Math.round(temp1);
            }
        }

        return output;
    }

    /**
     * Reverses DCT
     * @param input The input matrix that is the result of some DCT
     * @return output is the matrix before the DCT
     */
    public double[][] inverseDCT(double input[][])
    {
        double output[][] = new double[N][N];
        double temp[][] = new double[N][N];
        double temp1;
        int i;
        int j;
        int k;
        for (i=0; i<N; i++)
        {
            for (j=0; j<N; j++)
            {
                temp[i][j] = 0.0;
                for (k=0; k<N; k++)
                {
                    temp[i][j] += input[i][k] * c[k][j];
                }
            }
        }

        for (i=0; i<N; i++)
        {
            for (j=0; j<N; j++)
            {
                temp1 = 0.0;
                for (k=0; k<N; k++)
                {
                    temp1 += cT[i][k] * temp[k][j];
                }

                temp1 += 128.0;

                if (temp1 < 0)
                {
                    output[i][j] = 0;
                }
                else if (temp1 > 255)
                {
                    output[i][j] = 255;
                }
                else
                {
                     output[i][j] = (int)Math.round(temp1);
                }
            }
        }

        return output;
    }    
    
    
    /**
     * Dequantizes the input matrix
     * @param inputData An 8x8 block quantitized matrix
     * @return outputData A N * N array of dequantitized matrix
     */
    public double[][] dequantitizeImage(double[][] inputData)
    {
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;

        double outputData[][] = new double[N][N];

        double result;

        for (i=0; i<8; i++){
            for (j=0; j<8; j++){
                result = inputData[i][j] * quantize[i][j];
                outputData[i][j] = (int)(Math.round(result));
            }
        }
        return outputData;
    }

    public double[][] quantitizeImage(double inputData[][])
    {
        double outputData[][] = new double[N][N];
        double result;

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                result = inputData[i][j] / quantize[i][j];
                outputData[i][j] = (int)(Math.round(result));
            }
        }
        
        return outputData;
    }
    

   
    
}
