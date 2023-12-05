import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstacle extends Rectangle{
    boolean onPage;
    boolean overlapped;

    Obstacle(int x, int y, int width, int height) {
        super(x,y,width,height);
        overlapped = false;
        if(y > 0)
            onPage = true;
        else
            onPage = false;
    }

    public void updateY(int swoopSpeed) {
        y += swoopSpeed;
        if(y > 0)
            onPage = true;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x,y,width,height);
    }
    
}
