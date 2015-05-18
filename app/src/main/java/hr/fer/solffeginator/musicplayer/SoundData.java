package hr.fer.solffeginator.musicplayer;

/**
 * Created by Valerio on 4/26/2015.
 */

import android.content.res.AssetManager;
import android.media.AudioFormat;

import java.io.DataInputStream;
import java.io.IOException;

import hr.fer.solffeginator.App;


public class SoundData {
    private String identifier;
    private Object data;
    private int samplingRate;
    private int resolution;
    private int channel;
    private int duration;

    public SoundData(String soundName) {
        identifier = soundName;

        AssetManager am = App.getContext().getResources().getAssets();

       // File sounds = new File("storage/emulated/0/appSounds/" + identifier);

        byte [] header = null;

        try {
           // DataInputStream dis = new DataInputStream(new FileInputStream(sounds));
            DataInputStream dis = new DataInputStream(am.open("music/" + soundName));

            int index = 0;

            header = new byte [45];
            while(dis.available()>0 && index < header.length){
                header[index++] = dis.readByte();
            }

            WavHeader head = new WavHeader(header);
            samplingRate = head.getSamplingRate();
            resolution = head.getResolution();
            channel = head.getChannel();
            duration = head.getDuration();

            if(resolution == 16){
                data = new short[dis.available()/2];
                index = 0;
                while(dis.available()/2 > 0){
                    ((short [])data)[index++] = dis.readShort();
                }
            } else {
                data = new byte[dis.available()];
                index = 0;
                while(dis.available() > 0){
                    ((byte [])data)[index++] = dis.readByte();
                }
            }

            dis.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public Object getData() {
        return data;
    }

    public int getResolution(){
        return resolution;
    }

    public short[] getShortData() throws IOException{
        if(resolution != 16){
            throw new IOException();
        }
        return (short [])data;
    }

    public byte[] getByteData() throws IOException{
        if(resolution != 8){
            throw new IOException();
        }
        return (byte [])data;
    }

    public int getDataLength(){
        if(resolution == 16){
            return ((short[])data).length;
        } else {
            return ((byte[])data).length;
        }
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof SoundData){
            return identifier.equals(((SoundData)o).getIdentifier());
        }
        return false;
    }

    public int getChannel() {
        if(channel==2){
            return AudioFormat.CHANNEL_OUT_STEREO;
        } else {
            return AudioFormat.CHANNEL_OUT_MONO;
        }
    }

    public int getEncoding() {
        if(resolution == 16){
            return AudioFormat.ENCODING_PCM_16BIT;
        } else {
            return AudioFormat.ENCODING_PCM_8BIT;
        }
    }

    public int getDuration() {
        return duration;
    }

    public byte[] getPartialByteData(int offset, int size) throws IOException{
        if(resolution != 8){
            throw new IOException();
        }

        try {
            byte[] retVal = new byte[size];
            byte[] data = getByteData();
            for (int i = 0; i < retVal.length; i++) {
                retVal[i] = data[offset + i];
            }

            return retVal;
        } catch(ArrayIndexOutOfBoundsException ex){
            System.err.println("Invalid arguments for partial entry.");
        }

        return null;
    }

    public short[] getPartialShortData(int offset, int size, int tone) throws IOException{
        if(resolution != 16){
            throw new IOException();
        }

        double coefficient = 1/Math.pow(2, (tone - 9 - 3)/(double)12);

        try {
            short[] retVal = new short[(int)((double)(size+1/2) * coefficient)];
            short[] data = getShortData();

            short start = 0;

            for (int i = 0; i < retVal.length; i++) {
                if(start == 0){
                    start += data[offset + (int)(i/coefficient)];
                }
                retVal[i] = data[offset + (int)(i/coefficient)];
            }

            if(retVal == null){
                throw new IllegalStateException("Well, this is wrong.");
            }

            return retVal;
        } catch(ArrayIndexOutOfBoundsException ex){
            System.err.println("Invalid arguments for partial entry.");
        }

        return null;
    }

    @Override
    public String toString() {
        return "Resolution: " + resolution;
    }
}
