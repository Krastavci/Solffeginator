package hr.fer.solffeginator.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.R.string;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import hr.fer.solffeginator.PogodiMelodiju;

/**
 * Created by Helios on 13.5.2015..
 */
public class PogodiMelodijuCustomView extends View {
    static int maxWidth;
    static int maxHeight;
    public PogodiMelodijuCustomView(PogodiMelodiju mainActivity, int height, int width) {
        super((Context) mainActivity);
        maxWidth=width;
        maxHeight = height;
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);;
        int xRub=100;
        int maxWidthLine=maxWidth-xRub; // maximalna y- pocetna x koordinata crte = maksimalna sirina ekrana-50px

        // boja crte
        paint.setColor(Color.BLACK);
        // debljina crte
        paint.setStrokeWidth(10);

        canvas.drawLine(10, 100, 100, 100, paint); // crtanje prve horizontalne crte




//        Drawable vjezba1 = getResources().getDrawable(R.drawable.vj1);
//
//        int yStart=140; 	// gornji kraj note
//        int yEnd=240;		// donji kraj note
//
//        vjezba1.setBounds(xRub,yStart,xRub*3/2,yEnd);
//            vjezba1.draw(canvas);
        /*ImageButton[] note = new ImageButton[4];

        for(int i = 0 ; i < 4 ; i++)
        {

            note[i] = new ImageButton(this);
            note[i].setImageResource(R.drawable.vj1);
            //btnGreen[i].setLayoutParams(lp);
            //btnGreen[i].setOnClickListener(mGreenBallOnClickListener);
            //btnGreen[i].setBackgroundColor(Color.TRANSPARENT);

            //btnGreen[i].setTag(i);
            //btnGreen[i].setId(i);

            //mainlayout.addView(customview);


        }
*/
        // velicina ruba
        //int xRub=100;
        //int maxWidthLine=maxWidth-xRub; // maximalna y- pocetna x koordinata crte = maksimalna sirina ekrana-50px


    }
}
