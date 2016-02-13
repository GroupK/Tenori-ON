/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simori.on;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import java.util.concurrent.*;
import java.util.*;


public class Performance {
    private Layer[] layer = new Layer[16];
    private final Synthesizer synthersizer = getSynthesizer();
    Timer timer = new Timer();
    ScheduledExecutorService tracks = Executors.newScheduledThreadPool(16);
    MidiChannel[] midiChannels = synthersizer.getChannels();
    int array[] = {50, 45, 47, 42, 43, 38, 43, 45};
    int index = 0;
    
    private class Layer{
        int channel;
        int instructment;
        int note;
        boolean position[] = new boolean[16];
        MidiChannel midiChannel;
        Instrument[] instruments;
        
        Layer(int channel, int instructment, int note){
            for(boolean item: this.position){
                item = false;
            }
            setInstructment(channel, instructment, note);
        }
        
        public void changeState(int index){
            position[index] = position[index] != true;
        }
        
        public void request(int position){
            if(this.position[position]){
                play();
            }
        }
        
        public void setInstructment(int channel, int instructment, int note){
            this.channel = channel;
            this.instructment = instructment;
            this.note = note;
            midiChannel = midiChannels[channel];
            instruments = synthersizer.getDefaultSoundbank().getInstruments();
            synthersizer.loadInstrument( instruments[instructment] );
            midiChannel.programChange(instructment);
        }
        
        public void play(){
            midiChannel.noteOn (this.note, 20);
            delay( 1000 );
            midiChannel.noteOff(this.note, 20);
        }
    }
    
    private class Play implements Runnable{
        int position = 0;
        int index;
        Play(int index){
            this.index = index;
        }
        public void run(){
            layer[index].request(position);
            position ++;
            if(position == 16){
                position = 0;
            }
        }
    }
    
    /**
     *Constructors for Performance Class
     */
    public Performance(){
        for(int i=0;i<16;i++){
            layer[i] = new Layer(0, 0, 40+i);
            tracks.scheduleAtFixedRate(new Play(i), 0, 500, TimeUnit.MILLISECONDS);
        }
    }
    
    public Synthesizer getSynthesizer() {
        Synthesizer synthesizer = null;
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch ( Exception ex ) {
                System.out.println( ex );
        }
        return synthesizer;
    }
    
    public void delay( int ms ) {
        try {
            Thread.sleep( ms );
        } catch( Exception ex ) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void changeState(int whichLayer, int position){
        layer[whichLayer].changeState(position);
    }
}
