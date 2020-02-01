package imagePackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Segmenter
{
    BufferedImage image = null;
    BufferedImage image1 = null;

    String filepath = "sample.jpeg";
    String outputFilepath = "out.jpeg";

    File file = new File(filepath);
    File file1 = new File(outputFilepath);

    Color [] centroids;
    double [] distances;
    ArrayList<Color> [] clusters;

    int [][] pixels;


    public void initialize()
    {
        try
        {
            image = ImageIO.read(file);
            image1 = image;

            pixels = new int[image.getWidth()][image.getHeight()];

        }catch(IOException e)
        {

        }


    }

    public int min(double [] ar)
    {
        double min = ar[0];
        int index = 0;

        for(int i=0;i<ar.length;i++)
        {
            if(ar[i]<min)
            {
                min = ar[i];
                index = i;
            }
        }

        return index;
    }

    public void segment(int k)
    {
        centroids = new Color[k];
        distances = new double[k];
        clusters = new ArrayList[k];

        for(int i=0;i<k;i++)
        {
            clusters[i] = new ArrayList<>();
        }

        Random rand = new Random();

        for(int i=0;i<k;i++)
        {
            centroids[i] = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        }

        for(int i=0;i<image.getWidth();i++)
        {
            for(int j=0;j<image.getHeight();j++)
            {
                Color currentColor = new Color(image.getRGB(i,j));

                for(int z=0;z<k;z++)
                {
                    distances[z] = Math.sqrt(Math.pow(currentColor.getRed()-centroids[z].getRed(),2)+
                            Math.pow(currentColor.getGreen()-centroids[z].getGreen(),2)+
                            Math.pow(currentColor.getBlue()-centroids[z].getBlue(),2));
                }

                int index = min(distances);

                clusters[index].add(currentColor);

                pixels[i][j] = index;
            }
        }


        for(int i=0;i<k;i++)
        {

            double rSum = 0;
            double gSum = 0;
            double bSum = 0;

            for(Color c:clusters[i])
            {
                rSum+=c.getRed();
                gSum+=c.getGreen();
                bSum+=c.getBlue();
            }

            centroids[i] = new Color((int)rSum/clusters[i].size(),(int)gSum/clusters[i].size(),(int)bSum/clusters[i].size());
        }

        for(int i=0;i<image.getWidth();i++)
        {
            for(int j=0;j<image.getHeight();j++)
            {
                image1.setRGB(i,j,clusters[pixels[i][j]].get(0).getRGB());
            }
        }

        try
        {
            ImageIO.write(image1,"jpeg",file1);
        }catch (IOException e)
        {

        }
    }
}
