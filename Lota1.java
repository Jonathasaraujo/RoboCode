package robo;
import robocode.*;
import java.awt.*;
 /*
     Robo Equipe Lota - 1º Campeonato de Robocode.
	 versão modificada do Robo Sample Tracker.	
     */
public class Lota1 extends AdvancedRobot {

		int direcaoDoMovimento=1000;
   
    public void run() {
        setAdjustRadarForRobotTurn(true);
        setBodyColor(new Color(225, 28, 174));
        setGunColor(new Color(225, 28, 174));
        setRadarColor(new Color(225, 28, 174));
        setScanColor(Color.white);
        setBulletColor(Color.pink);
        turnRadarRightRadians(Double.POSITIVE_INFINITY); //gira o radar infinitamente para a direita	
    }
    public void onScannedRobot(ScannedRobotEvent e) {
       
		double absBearing=e.getBearingRadians()+getHeadingRadians();
        double velocidade=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing); //pega avelocidade do robo scaneado.
        double gunTurnAmt;
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians()); /*getTurnRemainingRadians() Retorna o ângulo restante no turno do robô, em radianos.
																	Esta chamada retorna valores positivos e negativos. Valores positivos significa que
																 o robô está girando para a direita. Valores negativos significa que o robô está girando para a esquerda. */
       
            setMaxVelocity(30000);
        
        if (e.getDistance() > 150) { // se estiver o robo adversário estiver longe. 
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+velocidade/30); // gira em torno do adversário e ajusta o canhao para o desparo.
            setTurnGunRightRadians(gunTurnAmt); //Define a arma do robô para virar à direita.
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+velocidade/getVelocity()));
            setAhead((e.getDistance() - 140)*direcaoDoMovimento); //by sample tracker
          	fire(4);
		}
        else{ //quando o robo adversário está perto. 
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+velocidade/15); // ajusta a mira para disparar e gira em torno do adversário atirando
            setTurnGunRightRadians(gunTurnAmt);
            setTurnLeft(-90-e.getBearing()); 
            setAhead((e.getDistance() - 140)*direcaoDoMovimento); //by sample tracker
        	
			if (getGunHeat() == 0) {
      		fire(Rules.MAX_BULLET_POWER); // atira com o maximo de força
   			}	
        }
    }
	public void tiroFatal( double energia){ // função tiro fatal: pega a energia do adversário e produz um tiro na potência que falta para matar.
		double tiro = (energia/4) + .1;
		fire(tiro);		
	}
		
    public void onHitWall(HitWallEvent e){
        direcaoDoMovimento=-direcaoDoMovimento;//Mudar o valor da direção quando bater na parede
    }
    /**
     * onWin:  Do a victory dance
     */
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }
}