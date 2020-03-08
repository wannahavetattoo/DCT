import java.awt.image.*;
public class RGB {

    public BufferedImage image;
    public int Height;
    public int Width;

    public RGB(BufferedImage image)
    {
        this.image = image;
        Width = image.getWidth();
        Height = image.getHeight();
    }

    public double[][] getRedArray()
    {
        double result[][] = new double[Width][Height];

    	for (int x = 0; x < Width; ++x)
    	{
    	    for (int y = 0; y < Height; ++y)
    	    {
        	   int rgb = image.getRGB(x, y);
                   int rx = (rgb >> 16) & 0xFF;
                   result[x][y] = rx;
            }
    	}
        
    	return result;
    }

    public double[][] getGreenArray()
    {
        double result[][] = new double[Width][Height];

    	for (int x = 0; x < Width; ++x)
    	{
    	    for (int y = 0; y < Height; ++y)
    	    {
        	   int rgb = image.getRGB(x, y);
                   int gx = (rgb >> 8) & 0xFF;
                   result[x][y] = gx;
            }
    	}
    	return result;
    }

    public double[][] getBlueArray()
    {
        double result[][] = new double[Width][Height];

    	for (int x = 0; x < Width; ++x)
    	{
    	    for (int y = 0; y < Height; ++y)
    	    {
        	   int rgb = image.getRGB(x, y);
                   int bx = rgb & 0xFF;
                   result[x][y] = bx;
            }
        }
    	return result;
    }

   
}
