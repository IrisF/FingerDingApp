package ifi.lmu.com.handmeasurementstudy.system;

/**
 * Created by Jonny on 25.05.2016.
 */
public class Coords {
    private float fX;
    private float fY;

    public Coords (float i_fX, float i_fY){
        this.fX = i_fX;
        this.fY = i_fY;
    }

    public float getX () {return fX;}
    public float getY () {return fY;}
}
