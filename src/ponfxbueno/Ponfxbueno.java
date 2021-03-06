/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ponfxbueno;
/*Importamos las distintas clases para su posterior utilizacion en objetos*/
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; /*Layout para colocacion de elementos en ventana*/
import javafx.scene.paint.Color; /*paint.Color para obtener los distintos colores*/
import javafx.scene.shape.Circle; /*Importamos Clase geometrica (Circulo)*/
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author isaac
 */
public class Ponfxbueno extends Application {
    short bolaCenterX=10;
    short bolaCenterY=30;
    byte velocidadBolay=3;
    byte velocidadBolax=3;
    final int SCENE_TAM_X= 600;
    final int SCENE_TAM_Y= 400;
    final int STICK_WIDTH= 7;
    final int STICK_HEIGHT= 50;
    int stickPosy = (SCENE_TAM_Y - STICK_HEIGHT) / 2;
    int stickCurrentSpeed = 0;
    int stickCurrentSpeed2 = 0;
    short TEXT_SIZE= 24;
    int score;
    int highScore;
    Text textScore;
    Pane root;
    
    
    @Override
    public void start(Stage primaryStage) {
        //LAYOUTS PARA MOSTRAR PUNTUACIONES
        //Layout principal
        root= new Pane();
        Scene scene = new Scene (root, SCENE_TAM_X, SCENE_TAM_Y);
        scene.setFill(Color.YELLOW);
        primaryStage.setTitle("PongFX");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Circle bola = new Circle(/*10, 30, 7*/);
        bola.setCenterX(bolaCenterX);
        bola.setCenterY(30);
        bola.setRadius(7);
        AnimationTimer movimiento;
        Rectangle rectStick = new Rectangle (SCENE_TAM_X*0.9, stickPosy, STICK_WIDTH, STICK_HEIGHT);
        Rectangle rectStick2 = new Rectangle (SCENE_TAM_X*0.1, stickPosy, STICK_WIDTH, STICK_HEIGHT);
        rectStick.setFill(Color.GREEN);
        //LAYOUTS PARA MOSTRAR PUNTUACIONES
        //Layout principal
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_TAM_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(100);
        root.getChildren().add(paneScores);
        // Layout para puntuacion actual
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(10);
        paneScores.getChildren().add(paneCurrentScore);
        // Layout para puntuacion maxima
        HBox paneHighScore = new HBox();
        paneHighScore.setSpacing(10);
        paneScores.getChildren().add(paneHighScore);
        //Texto de etiqueta para la puntuacion
        Text textTitleScore = new Text("Score: ");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.GREY);
        //Texto para la puntuacion
        textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.GRAY);
        //Texto de la etiqueta para la puntuacion maxima
        Text textTitleHighScore = new Text("Max.Score: ");
        textTitleHighScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.GRAY);
        //Texto para la puntuacion maxima
        Text textHighScore = new Text("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.GRAY);
        //Para reiniciar el juego
        resetGame();
        //Añadir los textos a los layouts reservados para ellos
        paneCurrentScore.getChildren().add(textTitleScore);
        paneCurrentScore.getChildren().add(textScore);
        paneHighScore.getChildren().add(textTitleHighScore);
        paneHighScore.getChildren().add(textHighScore);
        //Creacion de la red del campo
        drawNet(10, 4, 30);
        // Creacion del movimiento de la pala y bola
        
        movimiento = new AnimationTimer() {
            //Actualizar la posicion de la pala
            //Detectar zona de colision de bola con la pala
            
            @Override
            public void handle(long now) {
                int collisionZone = getStickCollisionZone(bola, rectStick);
                stickPosy += stickCurrentSpeed;
                if(stickPosy < 0) {
                    //No sobrepasar el borde superior de la ventana
                    stickPosy = 0;
                } else {
                    if(stickPosy > SCENE_TAM_Y - STICK_HEIGHT) {
                        stickPosy = SCENE_TAM_Y - STICK_HEIGHT; 
                    }
                  }
            calculateBolaSpeed(collisionZone);
                  
                //Mover rectangulo de la pala derecha a la posicion actual
                rectStick.setY(stickPosy);
                rectStick2.setY(stickPosy);
                bola.setCenterX(bolaCenterX);
                stickPosy += stickCurrentSpeed;
                bolaCenterX+= velocidadBolax;
                if (bolaCenterX >= SCENE_TAM_X){
                    if (score > highScore) {
                        highScore = score;
                        textHighScore.setText(String.valueOf(highScore));
                    }
                    resetGame();
                    //Reiniciar partida
                    score = 0;
                    textScore.setText(String.valueOf(score));
                    bolaCenterX = 10;
                    velocidadBolay = 3;
                }
                if (bolaCenterX <= 0){
                    velocidadBolax = 3;
                }
                bola.setCenterY(bolaCenterY);
                bolaCenterY+= velocidadBolay;
                if (bolaCenterY >= SCENE_TAM_Y){
                    velocidadBolay = -3;
                }
                if (bolaCenterY <= 0){
                    velocidadBolay = 3;
                }
                
              
                scene.setOnKeyPressed((KeyEvent event) -> {
                switch(event.getCode()){
                case UP:
                    //Pulsada tecla arriba
                    stickCurrentSpeed = -6;
                    break;
                case DOWN:
                    //Pulsada tecla abajo
                    stickCurrentSpeed = 6;
                    break;
            }
        });

                //Colision barra verde
                Shape.intersect(bola, rectStick);
               
                
                Shape shapeColision = Shape.intersect(bola, rectStick);
                
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
               
                if(colisionVacia == false && velocidadBolax > 0){
                    //Colision detectada. Mover bola hacia la izquierda
                    velocidadBolax = -3;
                    score++;
                    textScore.setText(String.valueOf(score));
                }
                
                scene.setOnKeyReleased((KeyEvent event) -> {
                    stickCurrentSpeed= 0;
                });
            
        
            
             
            }
        };

    
        
            
        root.getChildren().add(bola);
        root.getChildren().add(rectStick);
       // root.getChildren().add(rectStick2);
        bola.setFill(Color.PURPLE);
        movimiento.start();

        
        /*Codigo anterior simplificado de las disntintas clases y objetos*/
        /*Scene scene = new Scene(root, 600, 400, Color.YELLOW);
        Circle bola = new Circle(10, 30, 7, Color.PURPLE);
        En estas dos lineas hemos declarado obtejos y sus atributos en una sola linea
        */
    }  
    
    private void calculateBolaSpeed (int collisionZone) {
        switch(collisionZone){
            case 0:
                //No hay colision
                break;
            case 1:
                //Hay colision esquina superior
                velocidadBolax = -3;
                velocidadBolay = -6;
                break;
            case 2:
                //Hay colision lado superior
                velocidadBolax= -3;
                velocidadBolay = -3;
                break;
            case 3:
                //Hay colision lado inferior
                velocidadBolax= -3;
                velocidadBolay = 3;
                break;
            case 4:
                //Hay colision esquina inferior
                velocidadBolax= -3;
                velocidadBolay = 6;
                break;
        }
    }
    
    private int getStickCollisionZone(Circle bola, Rectangle stick){
        if (Shape.intersect(bola, stick).getBoundsInLocal().isEmpty()){
            return 0;
        } else {
            double offsetBolaStick = bola.getCenterY()- stick.getY();
            if (offsetBolaStick < stick.getHeight() * 0.1){
                return 1;
            } else if (offsetBolaStick < stick.getHeight() / 2){
                return 2;
            } else if(offsetBolaStick >= stick.getHeight() / 2 &&
                    offsetBolaStick < stick.getHeight() * 0.9 ){
                return 3;
            } else {
                return 4;
            }
        }
    }
    
    private void drawNet(int portionHeight, int portionWidth, int portionSpacing){
        for(int i=0; i<SCENE_TAM_Y; i+=portionSpacing){
            Line line = new Line(SCENE_TAM_X/2, i, SCENE_TAM_X/2, i+portionHeight);
            line.setStroke(Color.BLUE);
            line.setStrokeWidth(portionWidth);
            root.getChildren().add(line);
        }
    }
    
    private void resetGame(){
        score = 0;
        textScore.setText(String.valueOf(score));
        bolaCenterX = 10;
        velocidadBolay = 3;
        //Posicion inicial aleatoria para la bola en el eje y
        Random random = new Random();
        bolaCenterY = (short)random.nextInt(SCENE_TAM_Y);
    }   
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

 
    /**
     * @param args the command line arguments
     */
   