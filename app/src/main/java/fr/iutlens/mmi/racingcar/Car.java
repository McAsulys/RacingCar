package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;
import android.util.Log;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 *
 *
 * Created by dubois on 27/12/2017.
 */

public class Car {

    private SpriteSheet sprite;

    float x,y,direction,a,vMax, vMin, vflow, vTarget, score;
    float vy,dd;

    public Car(int sprite_id, float x, float y){
        this.x = x;
        this.y = y;
        this.direction = 0;
        this.a = 0;
        this.sprite = SpriteSheet.get(sprite_id);
        this.vy = (float) 0.01;
        this.dd = 0;
        this.vMax = (float) 1;
        this.vMin = 0;
        this.vflow = (float) 0.1;
        this.score = 0;
    }

    public void paint(Canvas canvas, int unit_x, int unit_y){
        canvas.save();
        canvas.translate(x*unit_x,y*unit_y);
        canvas.rotate(direction);
        int model = 0;
        if (dd < -5) model = 1;
        if (dd > 5) model = 2;
        sprite.paint(canvas,model,-sprite.w/2 , -sprite.h/2);
        canvas.restore();
    }


    public void update(Track track) {
        //direction += dd*vy*sprite.h;
        double vx = this.dd * 0.003;


        if(!track.IsValid((float) (x-vx),y)){
            vx = 0;
        }

        a = 0.001f;
        float dv = vTarget- vy;
        if (dv>0 && dv>a){ //acceleration
            dv = a;
        } else if (dv <0 && dv < -a){ //deceleration
            dv =-10*a;
        }
        vy = vy +dv;


        if(!track.IsValid(x,y-vy)){
            vy = 0;
        }

        if(track.IsValid((float) (x-vx),y-vy)){
            vy = vy+dv;
        }

        if (vx != 0 || vy !=0) direction = (float) Math.toDegrees(Math.atan2(vy,vx/5))-90;

        x -= vx;

        y = y- vy;

        score +=vy;
        Log.d("scorer ", String.valueOf(score));

    }

    public double bound(double value, double max){
        if (value >= max) value = max;
        if (value <= -max) value = -max;
        return value;
    }

    public double rescale(double value, double max, double bound){
        value = bound(value, bound);
        value = value * max/ bound;
        return value;
    }

    public void setCommand(double pitch, double roll) {
        pitch -= 45;
        pitch = rescale(pitch,90,15);
        roll = rescale(roll,90,15);

        this.a = (float) (pitch*0.00005);
        this.dd = 0;
        this.dd = (float) (roll/2);
        if (pitch < 0){
            //droite entre 0 et vflow sur -90 a 0
            this.vTarget = (float) (-pitch*0.01+vflow);
        }else {
            //droite entre vflow et vMax sur 0 90
            this.vTarget = (float) (-pitch*0.001+vflow);
        }
        //this.vTarget = (float) (pitch*0.001)+vflow;

    }
}
