
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

        //declare sreen/level booleans
        public boolean startScreen = true;
        public boolean isPlaying;
        public boolean gameOver;


        //Declare the objects used in the program below
        /**
         * Step 1: Declare your object and give it a name
         **/
        public Hero astro;
        public Hero astro11;
        public Hero astro12;
        public Hero astro13;
        public Hero astro14;
        public Hero astro2;
        public Hero astro21;
        public Hero astro22;
        public Hero astro23;
        public Hero astro24;
        public Hero ball;

        public Hero goal;
        public Hero goal2;

        /**
         * Step 2: make room for your image
         **/
        public Image astroPic;
        public Image astro2Pic;
        public Image ballPic;
        public Image goalPic;
        public Image goalPic2;
        public boolean astrointersectingball;
        public boolean astro11intersectingball;
        public boolean astro12intersectingball;
        public boolean astro13intersectingball;
        public boolean astro14intersectingball;
        public boolean astro2intersectingball;
        public boolean astro21intersectingball;
        public boolean astro22intersectingball;
        public boolean astro23intersectingball;
        public boolean astro24intersectingball;
        public boolean ballintersectinggoal;
        public boolean ballintersectinggoal2;
        public boolean farLeftTopBoundary=false;
        public boolean farLeftBottomBoundary;
        public int leftGoalCount = 0;
        public int rightGoalCount = 0;
        public boolean leftWon = false;
        public boolean rightWon = false;

        public Image Background;
        public Image startScreenPic;
        public Image rightWinsPic;
        public Image leftWinsPic;
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
            astro = new Hero(800, 150, -10, 13, 35, 50);
            astro11 = new Hero(800, 400, -10, 13, 35, 50);
            astro12 = new Hero(600, 100, -10, 13, 35, 50);
            astro13 = new Hero(600, 300, -10, 13, 35, 50);
            astro14 = new Hero(600, 500, -10, 13, 35, 50);
            astro2 = new Hero(50, 150, -10, 13, 35, 50);
            astro21 = new Hero(50, 400, -10, 13, 35, 50);
            astro22 = new Hero(250, 100, -10, 13, 35, 50);
            astro23 = new Hero(250, 300, -10, 13, 35, 50);
            astro24 = new Hero(250, 500, -10, 13, 35, 50);
            ball = new Hero(200, 200, 8, 8, 20, 20);
            goal = new Hero(0, 350, 0, 0, 1, 80);
            goal2 = new Hero(1000, 350, 0, 0, 1, 80);


/** Step 4: load in the image for your object**/
            astroPic = Toolkit.getDefaultToolkit().getImage("messi.png");
            astro2Pic = Toolkit.getDefaultToolkit().getImage("mbappe.png");
            Background = Toolkit.getDefaultToolkit().getImage("soccerField.png");
            startScreenPic = Toolkit.getDefaultToolkit().getImage("startScreenPic.png");
            ballPic = Toolkit.getDefaultToolkit().getImage("soccerBall.png");
            goalPic = Toolkit.getDefaultToolkit().getImage("paddle.png");
            goalPic2 = Toolkit.getDefaultToolkit().getImage("paddle.png");
            leftWinsPic = Toolkit.getDefaultToolkit().getImage("LeftTeamWins.png");
            rightWinsPic = Toolkit.getDefaultToolkit().getImage("RightTeamWins.png");


            astro.printInfo();
            astro11.printInfo();
            astro2.printInfo();
            astro21.printInfo();
            ball.printInfo();
            goal.printInfo();
            goal2.printInfo();


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
                boundary();
                render();  // paint the graphics
                pause(20); // sleep for 20 ms


            }
        }

        //paints things on the screen using bufferStrategy
        private void render() {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);
            if (startScreen == true) {
                g.drawImage(startScreenPic,0,0,1000,705,null);
                g.drawString("press space bar to start", 420, 385);

                //g.drawImage();
            }

            if (isPlaying == true) {

                //draw the image of your objects below:
                /**Step 5 draw the image of your object to the screen**/
                g.drawImage(Background, 0, 0, WIDTH, HEIGHT, null);

                g.drawImage(astroPic, astro.xpos, astro.ypos, 35, 50, null);
                g.drawImage(astroPic, astro11.xpos, astro11.ypos, 35, 50, null);
                g.drawImage(astroPic, astro12.xpos, astro12.ypos, 35, 50, null);
                g.drawImage(astroPic, astro13.xpos, astro13.ypos, 35, 50, null);
                g.drawImage(astroPic, astro14.xpos, astro14.ypos, 35, 50, null);
                g.drawImage(ballPic, ball.xpos, ball.ypos, 35, 35, null);
                g.drawImage(astro2Pic, astro2.xpos, astro2.ypos, 35, 50, null);
                g.drawImage(astro2Pic, astro21.xpos, astro21.ypos, 35, 50, null);
                g.drawImage(astro2Pic, astro22.xpos, astro22.ypos, 35, 50, null);
                g.drawImage(astro2Pic, astro23.xpos, astro23.ypos, 35, 50, null);
                g.drawImage(astro2Pic, astro24.xpos, astro24.ypos, 35, 50, null);

                g.drawImage(goalPic, goal.xpos, goal.ypos, 1, 80, null);
                g.drawImage(goalPic2, goal2.xpos, goal2.ypos, 1, 80, null);
                g.drawString("Score: "+rightGoalCount,200,30);
                g.drawString("Score: "+leftGoalCount,700,30);


            }

            if(rightGoalCount==5){
                gameOver=true;
                rightWon = true;
            }
            if(leftGoalCount==5){
                gameOver = true;
                leftWon = true;
            }
            if (gameOver == true&&leftWon==true) {
                //paint game over image to the screen
                isPlaying=false;
                g.drawImage(leftWinsPic,0,0,1000,705,null);

            }
            if (gameOver == true&&rightWon==true) {
                //paint game over image to the screen
                isPlaying=false;
                g.drawImage(rightWinsPic,0,0,1000,705,null);
            }

            //dispose the images each time(this allows for the illusion of movement).
            g.dispose();

            bufferStrategy.show();
        }

        public void moveThings() {
            //call the move() method code from your object class
            astro.move();
            astro11.move();
            astro12.move();
            astro13.move();
            astro14.move();

            astro2.move();
            astro21.move();
            astro22.move();
            astro23.move();
            astro24.move();

            ball.bouncingMove();


        }
        public void boundary(){
            if(astro21.ypos>650){
                farLeftTopBoundary=true;
            }else{
                farLeftTopBoundary=false;
            }
        }

        public void collisions() {
            if (astro.rec.intersects(ball.rec) && astrointersectingball == false) {
                astrointersectingball = true;
                System.out.println("ouch");
                ball.dx = -ball.dx;
            }
            if(!astro.rec.intersects(ball.rec)) {
                astrointersectingball = false;
            }
            if (astro2.rec.intersects(ball.rec) && astro2intersectingball == false) {
                astro2intersectingball = true;
                System.out.println("ow");
                ball.dx = -ball.dx;
            }
            if(!astro2.rec.intersects(ball.rec)) {
                astro2intersectingball = false;
            }
            if (astro11.rec.intersects(ball.rec) && astro11intersectingball == false) {
                astro11intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro11.rec.intersects(ball.rec)) {
                astro11intersectingball = false;
            }

            if (astro12.rec.intersects(ball.rec) && astro12intersectingball == false) {
                astro12intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro12.rec.intersects(ball.rec)) {
                astro12intersectingball = false;
            }

            if (astro13.rec.intersects(ball.rec) && astro13intersectingball == false) {
                astro13intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro13.rec.intersects(ball.rec)) {
                astro13intersectingball = false;
            }

            if (astro14.rec.intersects(ball.rec) && astro14intersectingball == false) {
                astro14intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro14.rec.intersects(ball.rec)) {
                astro14intersectingball = false;
            }

            if (astro21.rec.intersects(ball.rec) && astro21intersectingball == false) {
                astro21intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro21.rec.intersects(ball.rec)) {
                astro21intersectingball = false;
            }

            if (astro22.rec.intersects(ball.rec) && astro22intersectingball == false) {
                astro22intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro22.rec.intersects(ball.rec)) {
                astro22intersectingball = false;
            }

            if (astro23.rec.intersects(ball.rec) && astro23intersectingball == false) {
                astro23intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro23.rec.intersects(ball.rec)) {
                astro23intersectingball = false;
            }

            if (astro24.rec.intersects(ball.rec) && astro24intersectingball == false) {
                astro24intersectingball = true;
                System.out.println("oof");
                ball.dx = -ball.dx;
            }
            if(!astro24.rec.intersects(ball.rec)) {
                astro24intersectingball = false;
            }

            if (ball.rec.intersects(goal.rec) && ballintersectinggoal == false) {
                ballintersectinggoal = true;
                System.out.println("owy");
                leftGoalCount = leftGoalCount + 1;
                ball.dx=ball.dx+2;
                ball.dy=ball.dy+2;

            }
            ballintersectinggoal = false;

            if (ball.rec.intersects(goal2.rec) && ballintersectinggoal2 == false) {
                ballintersectinggoal2 = true;
                System.out.println("owwy");
                rightGoalCount = rightGoalCount + 1;
                ball.dx=ball.dx+2;
                ball.dy=ball.dy+2;

            }
            ballintersectinggoal2 = false;
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

            if (keyCode == 65) {
              if(farLeftTopBoundary==false) {
                    System.out.println("top boundary: " +farLeftTopBoundary);
                    astro2.upPressed = true;
                    astro21.upPressed = true;
              }
            }
            if (keyCode == 81) {
                astro2.downPressed = true;
                astro21.downPressed = true;
            }
            if (keyCode == 76) {
                astro.upPressed = true;
                astro11.upPressed = true;
            }
            if (keyCode == 80) {
                astro.downPressed = true;
                astro11.downPressed = true;
            }
            if (keyCode == 87) {
                astro22.downPressed = true;
                astro23.downPressed = true;
                astro24.downPressed = true;
            }
            if (keyCode == 83) {
                astro22.upPressed = true;
                astro23.upPressed = true;
                astro24.upPressed = true;
            }
            if (keyCode == 79) {
                astro12.downPressed = true;
                astro13.downPressed = true;
                astro14.downPressed = true;
            }
            if (keyCode == 75) {
                astro12.upPressed = true;
                astro13.upPressed = true;
                astro14.upPressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            char key = e.getKeyChar();
            int keyCode = e.getKeyCode();

            if (keyCode == 32) {
                startScreen = false;
                isPlaying = true;
            }
            if (keyCode == 65) {
                astro2.upPressed = false;
                astro21.upPressed = false;
            }
            if (keyCode == 81) {
                astro2.downPressed = false;
                astro21.downPressed = false;
            }

            if (keyCode == 76) {
                astro.upPressed = false;
                astro11.upPressed = false;
            }
            if (keyCode == 80) {
                astro.downPressed = false;
                astro11.downPressed = false;
            }
            if (keyCode == 87) {
                astro22.downPressed = false;
                astro23.downPressed = false;
                astro24.downPressed = false;
            }
            if (keyCode == 83) {
                astro22.upPressed = false;
                astro23.upPressed = false;
                astro24.upPressed = false;
            }
            if (keyCode == 79) {
                astro12.downPressed = false;
                astro13.downPressed = false;
                astro14.downPressed = false;
            }
            if (keyCode == 75) {
                astro12.upPressed = false;
                astro13.upPressed = false;
                astro14.upPressed = false;
            }

        }
    }




