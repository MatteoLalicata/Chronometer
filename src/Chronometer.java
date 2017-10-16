import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class Chronometer contains the methods and the instance variables for getting the Chronometer  UI working.
 * The Chronometer is a simple timer with start, stop, resume, and reset buttons.
 *
 * @author La Licata Matteo
 * @version 1.0
 */

public class Chronometer extends JFrame{
    private JPanel main;
    private JButton startButton;
    private JButton stopButton;
    private JButton riprendiButton;
    private JButton giroButton1;
    private JButton azzeraButton;
    private JPanel Text;
    private JLabel Testo;
    private JLabel Testo2;
    private long start, end;
    private int min = 0, hour = 0;
    private Thread thread;

    private int width = 800, height = width / 4 * 3;

    /**
     * This constructor add for each button a new ActionListener
     * startButton: call the method that will start the count of the time
     * stopButton: call the method that will stop the count of the time
     * riprendiButton: call the method that will resume the count if the time
     * giroButton: call the method that will sign the lap
     * azzeraButton: call the method that will reset all
     *
     */
    public Chronometer(){
        Testo.setText(String.format("%02d:%02d:%02d:%03d",hour,min,getSec(),getMillisec()));
        Testo2.setText("Lap: " + Testo.getText());
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 count();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopThread();
            }
        });
        giroButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lap();
            }
        });
        azzeraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        riprendiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.resume();
            }
        });
    }

    /**
     * Method that start the count of time
     */
    public void count(){
        start = System.currentTimeMillis();
        this.thread = new Thread(new Runnable() {
            @Override
            public void run(){
                while(!thread.isInterrupted()){
                    end = System.currentTimeMillis();
                    Testo.setText(String.format("%02d:%02d:%02d:%03d",hour,min,getSec(),getMillisec()));
                    if(getSec() == 60 && getMillisec() == 0){
                        delay(5);
                       min++;
                       if(min == 60) {hour++; min = 0;}
                       start = System.currentTimeMillis();
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * Method that return the seconds
     */
    public long getSec(){
        return (this.end - this.start) / 1000;
    }

    /**
     * Method taht return the milliseconds
     */
    public long getMillisec(){
        return (this.end - this.start) % 1000;
    }

    /**
     * Method that stop the count of the time
     */
    public void stopThread(){
        this.thread.suspend();
    }

    /**
     * Method that wait a millis milliseconds
     * @param millis
     */
    public void delay(int millis){
        Thread delay = new Thread();
        try{
            delay.sleep(millis);
        }catch(InterruptedException e){
            System.err.println(e);
        }
    }

    /**
     * Method that sign the lap
     */
    public void lap(){
        Testo2.setText("Lap: " + Testo.getText());
    }

    /**
     * Method that reset all
     */
    public void reset(){
        stopThread();
        start = 0;
        end = 0;
        min = 0;
        hour = 0;
        Testo.setText(String.format("%02d:%02d:%02d:%03d",hour,min,getSec(),getMillisec()));
        Testo2.setText("Lap: " + Testo.getText());
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Chronometer");
        frame.setContentPane(new Chronometer().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(250,250, 0, 0);
        frame.pack();
        frame.setVisible(true);
    }

}
