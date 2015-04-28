package hr.fer.solffeginator.views;

/**
 * Created by Valerio on 4/28/2015.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.R.string;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import hr.fer.solffeginator.R;
import hr.fer.solffeginator.TappingActivity;
import hr.fer.solffeginator.musical.Nota;
import hr.fer.solffeginator.musical.Skladba;
import hr.fer.solffeginator.musical.Takt;

public class CustomView extends View {

    private int maxWidth;
    private Skladba skladba;
    private Map<String, Drawable> slikeNota;


    public CustomView(TappingActivity tappingActivity, int height, int width, Skladba skladba) {
        super((Context) tappingActivity);
        maxWidth=width;
        this.skladba = skladba;
        slikeNota = Collections.synchronizedMap(new HashMap<String, Drawable>());
        slikeNota.put("polovinka", getResources().getDrawable(R.drawable.polovinka));
        slikeNota.put("cetvrtinka", getResources().getDrawable(R.drawable.cetvrtinka));
        slikeNota.put("osminka", getResources().getDrawable(R.drawable.osminka));
        slikeNota.put("sesnaestinka", getResources().getDrawable(R.drawable.sestnaestinka));
        slikeNota.put("cijela nota", getResources().getDrawable(R.drawable.cijelanota));
        //isprobajSkladbu();
    }

    /*
    private void isprobajSkladbu() {
        Iterator<Takt> takti = skladba.iterator();
        while (takti.hasNext()) {
            Takt n = takti.next();
            Iterator<Nota> note = n.iterator();
            while (note.hasNext()) {
                Nota nota = note.next();
                Log.d("Test", nota.getNota().getName());
            }
        }
    }
    */

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);;

        // boja crte
        paint.setColor(Color.BLACK);
        // debljina crte
        paint.setStrokeWidth(4);


        // velicina ruba
        int xRub=100;
        int maxWidthLine=maxWidth-xRub; // maximalna y- pocetna x koordinata crte = maksimalna sirina ekrana-50px
        int yHorizontalnaCrta=200; // pocetna y koordinata crte
        int yHorizontalnaCrta2=90;
        canvas.drawLine(xRub, yHorizontalnaCrta, maxWidthLine, yHorizontalnaCrta, paint); // crtanje prve horizontalne crte
        canvas.drawLine(xRub, yHorizontalnaCrta+yHorizontalnaCrta2, maxWidthLine, yHorizontalnaCrta+yHorizontalnaCrta2, paint); // crtanje druge hor crte

        int brojTaktova=skladba.getBrojTaktova();
        int brojTaktovaPoLiniji=2;
        int taktVelicina = (maxWidth-2*xRub)/(brojTaktovaPoLiniji); // maksimalna velicina ekrana - 2*rub / 4/2 (broj taktova/brTaktPoLiniji)

        // crtanje vertikalnih crta
        int yVertikalnaCrtaStart=180;
        int yVertikalnaCrtaEnd=220;
        int brojVertikalnihCrtaPoLiniji=3;

        for(int i=0;i<brojVertikalnihCrtaPoLiniji;i++)
            canvas.drawLine(taktVelicina*i+xRub, yVertikalnaCrtaStart, taktVelicina*i+xRub, yVertikalnaCrtaEnd, paint);
        for(int i=0;i<brojVertikalnihCrtaPoLiniji;i++)
            canvas.drawLine(taktVelicina*i+xRub, yVertikalnaCrtaStart+yHorizontalnaCrta2, taktVelicina*i+xRub, yVertikalnaCrtaEnd+yHorizontalnaCrta2, paint);

        // crtanje kljuca i mjere -> poboljsati
        Drawable kljuc = getResources().getDrawable(R.drawable.kljuc1);
        Drawable mjera = getResources().getDrawable(R.drawable.mjera2);

        int yStart=140; 	// gornji kraj note
        int yEnd=240;		// donji kraj note

        kljuc.setBounds(xRub,yStart,(xRub*3)/2,yEnd); // crtanje kljuca
        kljuc.draw(canvas);

        xRub+=xRub/4;							   // podesavanje udaljenosti mjere
        mjera.setBounds(xRub,yStart,(xRub*3)/2,yEnd); // crtanje mjere
        mjera.draw(canvas);

        yStart=230; 	// gornji kraj
        yEnd=330;		// donji kraj
        xRub=100;		// resetiranje ruba za sljedecu crtu

        kljuc.setBounds(xRub,yStart,(xRub*3)/2,yEnd); // isti k za drugu crtu
        kljuc.draw(canvas);
        xRub+=xRub/4;
        int xRubPom=xRub; // pamtim vrijednost xRuba za sljedece crte
        mjera.setBounds(xRub,yStart,(xRub*3)/2,yEnd);
        mjera.draw(canvas);

        int linija1=1;
        int linija2=2;

        Iterator<Takt> taktovi = skladba.iterator();
        int i = 0;
        while(taktovi.hasNext()) {
            Takt tmpTakt = taktovi.next();
            if(i%2==1){
                xRub=100; // u 2. taktu nema ni kljuca ni mjere
            }
            else xRub=xRubPom;
            if (i<brojTaktova/2) {
                CrtajNote(taktVelicina, tmpTakt.getNote(), i, xRub, canvas, linija1);
            }
            else {
                CrtajNote(taktVelicina, tmpTakt.getNote(), i, xRub, canvas, linija2);
            }
            i++;
        }
    }

    private void CrtajNote(float taktVelicina, ArrayList<Nota> takt, int index, int xStart, Canvas canvas, int linija) {



        int yStart=150; 	// gornji kraj note
        int yEnd=230;		// donji kraj note

        if(linija==2){
            yStart=240; 	// gornji kraj note
            yEnd=320;		// donji kraj note
        }

        int xEnd;			// desni kraj note
        index%=2;
        int taktStart = (int) (taktVelicina*(index-1)+xStart);
        int taktEnd = (int) (taktVelicina*index+xStart);

        xStart = taktStart;
        xEnd=taktEnd;

        int velicinaSektor = taktEnd-taktStart;
        int brNotaTakt = takt.size();

        // pojedini takt se dijeli na maxMjesto dijelova
        int maxMjesto=11;
        int razmakNota=velicinaSektor/maxMjesto;
        xEnd+=(velicinaSektor/2)-((razmakNota*brNotaTakt)/2);


        Iterator<Nota> note = takt.iterator();
        while(note.hasNext()){
            Nota tmpNota = note.next();

            Log.d("Nota", tmpNota.getNota().getName());
            xStart=xEnd;
            xEnd+=razmakNota;

            Drawable crtanaNota = slikeNota.get(tmpNota.getNota().getName());
            crtanaNota.setBounds(xStart,yStart,xEnd,yEnd);
            crtanaNota.draw(canvas);

        }
    }

    /*
    private String[] podjelaTaktova(String ulaz, int brojTaktova) {

        int j=0;
        String[] sveNote = new String[brojTaktova];
        String skupNota = "";
        float taktRez=0;
        int brojac = 0;

        while((brojac<=brojTaktova-1)){
            if(ulaz.charAt(j)=='o')
                taktRez+=1/8f;
            else if(ulaz.charAt(j)=='c')
                taktRez+=1/4f;
            else if(ulaz.charAt(j)=='p')
                taktRez+=1/2f;
            else if(ulaz.charAt(j)=='C')
                taktRez+=1;

            skupNota+=(ulaz.charAt(j));

            if(taktRez==1){
                sveNote[brojac] = skupNota;
                brojac++;
                taktRez=0;
                skupNota="";
            }
            j++;
        }
        return sveNote;
    }
    */
}