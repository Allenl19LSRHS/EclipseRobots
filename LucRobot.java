import robocode.HitWallEvent;
import robocode.Robot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class LucRobot extends Robot {
	double enemyEnergy = 100;
	double targetLocation = 0;
	double aimpoint = 0;
	boolean scannedLastLoop = false;
	double bulletSpeed = Rules.getBulletSpeed(1);

	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		turnRadarRight(180);
		turnRadarRight(180);
		
		while (true) {
			if (scannedLastLoop) {
				scannedLastLoop = false;
				if (getGunHeading() - targetLocation > 180) {
					turnGunRight(360 - getGunHeading() - targetLocation);
					
				} else if (getGunHeading() - targetLocation < 0) {
					if (getGunHeading() - targetLocation > -180) {
						turnGunLeft(getGunHeading() - targetLocation);
					} else {
						turnGunLeft(360 - Math.abs(getGunHeading() - targetLocation));
					}
					
				} else {
					turnGunLeft(getGunHeading() - targetLocation);	
					
				}
				if (getRadarHeading() - targetLocation > 180) {
					turnRadarRight(360 - getRadarHeading() - targetLocation);
					
				} else if (getRadarHeading() - targetLocation < 0) {
					if (getRadarHeading() - targetLocation > -180) {
						turnRadarLeft(getRadarHeading() - targetLocation);
					} else {
						turnRadarLeft(360 - Math.abs(getRadarHeading() - targetLocation));
					}
					
				} else {
					turnRadarLeft(getRadarHeading() - targetLocation);
				}
				turnRadarRight(7);
				turnRadarLeft(14);
				turnRadarRight(7);
			} else {
				turnRadarRight(90);
			}
			turnRight(getHeading() - targetLocation);
			ahead(10);
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
		
		double targetPosition[] = {targetDistance * Math.cos(Math.toRadians(targetLocation)), targetDistance * Math.sin(Math.toRadians(targetLocation))};
		double targetNewPos[] = {distanceToTravel * Math.cos(Math.toRadians(targetHeading-90)), distanceToTravel * Math.sin(Math.toRadians(targetHeading-90))};
		double aimpointPos[] = {targetPosition[0] + targetNewPos[0], targetPosition[1] + targetNewPos[1]};
		aimpoint = Math.toDegrees(Math.atan(aimpointPos[1]/aimpointPos[0]));
		if (e.getBearing() > 180) {
			aimpoint += 180;
		}
		if (targetLocation < 0) targetLocation += 360;
		//System.out.println("TargetPos: " + targetPosition[0] + ", " + targetPosition[1]);
		//System.out.println("Target Movement Vector: " + targetNewPos[0] + ", " + targetNewPos[1]);
		//System.out.println("Aimpoint Pos Vector: " + aimpointPos[0] + ", " + aimpointPos[1]);
		//System.out.println("Aimpoint angle: " + aimpoint);

		fire(1);
		scannedLastLoop = true;
	}
	
	public void onHitWall(HitWallEvent e) {
		back(20);
		turnRight(180);
	}
}
