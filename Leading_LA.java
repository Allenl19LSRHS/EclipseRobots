import robocode.Robot;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Leading_LA extends Robot {
	double enemyEnergy = 100;
	double targetLocation = 0;
	double aimpoint = 0;
	boolean scannedLastLoop = false;
	double bulletSpeed = Rules.getBulletSpeed(2);
	boolean canFire = true;
	
	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		turnRadarRight(180);
		turnRadarRight(180);
		
		while (true) {
			if (scannedLastLoop) {
				scannedLastLoop = false;
				turnGunRight(Utils.normalRelativeAngleDegrees(-getGunHeading() + aimpoint));
				turnRadarRight(Utils.normalRelativeAngleDegrees(-getRadarHeading() + targetLocation));
				if (canFire) {
					fire(2);
				}
				//System.out.println(getGunHeading());
				turnRadarRight(7);
				turnRadarLeft(14);
				turnRadarRight(7);
			} else {
				turnRadarRight(90);
			}
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		// code to lead the target
		targetLocation = getHeading() + e.getBearing();
		double targetSpeed = e.getVelocity();
		double targetHeading = e.getHeading();
		double targetDistance = e.getDistance();
		double flightTime = targetDistance/bulletSpeed;
		double distanceToTravel = targetSpeed * flightTime;
		
		double[] targetPosition = {targetDistance * Math.sin(Math.toRadians(targetLocation)), targetDistance * Math.cos(Math.toRadians(targetLocation))};
		double[] targetNewPos = {distanceToTravel * Math.sin(Math.toRadians(targetHeading)), distanceToTravel * Math.cos(Math.toRadians(targetHeading))};
		double[] aimpointPos = {targetPosition[0] + targetNewPos[0], targetPosition[1] + targetNewPos[1]};
		aimpoint = Math.toDegrees(Utils.normalAbsoluteAngleDegrees(Math.atan2(aimpointPos[0], aimpointPos[1])));
		if (e.getBearing() > 180) {
			aimpoint += 180;
		}
		if (targetLocation < 0) targetLocation += 360;
		//System.out.println("TargetPos: " + targetPosition[0] + ", " + targetPosition[1]);
		//System.out.println("Target Movement Vector: " + targetNewPos[0] + ", " + targetNewPos[1]);
		//System.out.println("Aimpoint Pos Vector: " + aimpointPos[0] + ", " + aimpointPos[1]);
		System.out.println("Aimpoint angle: " + aimpoint);

		scannedLastLoop = true;
	}
}
