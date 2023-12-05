import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Swoop extends Rectangle {
    int speed;
    int x_acc, y_acc; // maybe
    int gear; // as in what gear for a manual transmission pretty much

    static final double FIRST_GEAR_MAX = 1;
    static final double SECOND_GEAR_MAX = 2.5;

    Swoop (int x, int y, int width, int height) {
        super(x, y, width, height);
        gear = 0;
        speed = 0;
        y_acc = 1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x,y,width,height);
    }

}
