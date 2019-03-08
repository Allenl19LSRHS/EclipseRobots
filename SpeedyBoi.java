import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class SpeedyBoi extends Robot {
	double reverse = 1;
	double enemyBearing;
	double enemyDistance;
	double timesLooped = 0;
	
	public void run() {
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		turnRadarRight(360);
		while (true) {
			turnRadarRight(Utils.normalRelativeAngleDegrees(getHeading() - getRadarHeading() + enemyBearing));
			turnRight(Utils.normalRelativeAngleDegrees(getHeading() - getGunHeading() + enemyBearing));
			ahead(10);
			
			reverse *= -1;
			timesLooped += 1;
			if (timesLooped >= 5) {
				turnRadarRight(360);
			}
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		enemyBearing = e.getBearing();
		enemyDistance = e.getDistance();
		timesLooped = 0;
		if (enemyDistance < 100) {
			fire(1);
		}
	}
}
