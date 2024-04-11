
    //Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

    public class MyFirstGame implements Runnable, KeyListener {

        //Variable Declaration Section
        //Declare the variables used in the program
        //You can set their initial values here if you want

        //Sets the width and height of the program window
        final int WIDTH = 1000;
        final int HEIGHT = 700;

        //Declare the variables needed for the graphics
        public JFrame frame;
        public Canvas canvas;
        public JPanel panel;

        public BufferStrategy bufferStrategy;

        //Declare the objects used in the program below
        /**  Step 1: Declare your object and give it a name**/
        public Hero astro;
        public Hero astro2;
        public Hero ball;

        /** Step 2: make room for your image**/
        public Image astroPic;
        public Image astro2Pic;
        public Image ballPic;
        public boolean astrointersectingball;
        public boolean astro2intersectingball;
        /*public Image FighterJetPic;
        public Image SpaceBackground;
        public Image EvilShipPic;
        public Image EvilShipPic2;
        public Image SpaceShipPic;
        public boolean FighterJetIntersectingEvilShip;
        public boolean EvilShipIntersectingEvilShip2;
        public boolean EvilShip2IntersectingFighterJet;
        public boolean SpaceShipIntersectingEvilShip2;
*/
        public Image Background;

        //movement booleans
        /*public boolean rightPressed;
        public boolean leftPressed;
        public boolean upPressed;
        public boolean downPressed;
*/
        private int KeyCode;




        // Main method definition: PSVM
        // This is the code that runs first and automatically
        public static void main(String[] args) {
            MyFirstGame ex = new MyFirstGame();   //creates a new instance of the game and tells GameLand() method to run
            new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
        }

        // Constructor Method
        // This has no return type and has the same name as the class
        // This section is the setup portion of the program
        // Initialize your variables and construct your program objects here.
        public MyFirstGame() {
            setUpGraphics(); //this calls the setUpGraphics() method

            //create (construct) the objects needed for the game below
            //for each object that has a picture, load in images as well

            /**  Step 3: Construct a specific Hero**/
astro = new Hero(800,300,-10,13, 70, 100);
astro2 = new Hero(50,300,-10,13,70,100);
ball = new Hero(200,200,8,8,35,35);


/** Step 4: load in the image for your object**/
    astroPic = Toolkit.getDefaultToolkit().getImage("messi.png");
    astro2Pic = Toolkit.getDefaultToolkit().getImage("mbappe.png");
    Background = Toolkit.getDefaultToolkit().getImage("soccerField.png");
    ballPic = Toolkit.getDefaultToolkit().getImage("soccerBall.png");





astro.printInfo();
astro2.printInfo();
ball.printInfo();



        }// GameLand()

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

        // main thread
        // this is the code that plays the game after you set things up
        public void run() {
            //for the moment we will loop things forever using a while loop
            while (true) {
                moveThings();  //move all the game objects
                collisions();//check for rectangle intersections
                render();  // paint the graphics
                pause(20); // sleep for 20 ms

            }
        }

        //paints things on the screen using bufferStrategy
        private void render() {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);

            //draw the image of your objects below:
            /**Step 5 draw the image of your object to the screen**/
            g.drawImage(Background,0,0,WIDTH,HEIGHT,null);
            g.drawImage(astroPic, astro.xpos,astro.ypos,70,100,null);
            g.drawImage(ballPic,ball.xpos,ball.ypos,10,10,null);
            g.drawImage(astro2Pic,astro2.xpos,astro2.ypos,70,100,null);



            //dispose the images each time(this allows for the illusion of movement).
            g.dispose();

            bufferStrategy.show();
        }

        public void moveThings() {
            //call the move() method code from your object class
            //astro.bouncingMove();
            astro.move();
            astro2.move();
            ball.bouncingMove();

        }

        public void collisions(){
            if(astro.rec.intersects(ball.rec)&&astrointersectingball==false){
                astrointersectingball = true;
                System.out.println("ouch");
                ball.dx = -ball.dx;
                ball.dx=ball.dx+1;
                ball.dy=ball.dy+1;
            }
            astrointersectingball = false;

            if(astro2.rec.intersects(ball.rec)&&astro2intersectingball==false){
                astro2intersectingball = true;
                System.out.println("ow");
                ball.dx = -ball.dx;
                ball.dx=ball.dx+1;
                ball.dy=ball.dy+1;
            }
            astro2intersectingball = false;
        }


        //Pauses or sleeps the computer for the amount specified in milliseconds
        public void pause(int time) {
            //sleep
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {

            }
        }

        //Graphics setup method
        private void setUpGraphics() {
            frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

            panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
            panel.setLayout(null);   //set the layout

            // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
            // and trap input events (Mouse and Keyboard events)
            canvas = new Canvas();
            canvas.setBounds(0, 0, WIDTH, HEIGHT);
            canvas.setIgnoreRepaint(true);
            canvas.addKeyListener(this);

            panel.add(canvas);  // adds the canvas to the panel.

            // frame operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
            frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
            frame.setResizable(false);   //makes it so the frame cannot be resized
            frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

            // sets up things so the screen displays images nicely.
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            canvas.requestFocus();
            System.out.println("DONE graphic setup");
        }

        @Override
        public void keyTyped(KeyEvent e) {
            //probably will stay empty
        }

        @Override
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar();
            int keyCode = e.getKeyCode();
            System.out.println("Key: " + key + "KeyCode: " + keyCode);

            if (keyCode == 83) {
                astro2.upPressed = true;
            }


            if (keyCode == 87) {
                astro2.downPressed = true;
            }


            if (keyCode == 40){
                astro.upPressed = true;
            }
            if(keyCode == 38){
                astro.downPressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            char key = e.getKeyChar();
            int keyCode = e.getKeyCode();

                if(keyCode==83){
                    astro2.upPressed = false;
                }
                if(keyCode==87){
                    astro2.downPressed = false;
                }


            if (keyCode == 40){
                astro.upPressed = false;
            }
            if(keyCode == 38){
                astro.downPressed = false;
            }

        }
    }


