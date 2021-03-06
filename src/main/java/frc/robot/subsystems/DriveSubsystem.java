package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Map;
import frc.robot.IDriveSubsystem;

public class DriveSubsystem implements IDriveSubsystem {

    SpeedControllerGroup leftMotors, rightMotors;
    
    private Encoder leftEncoder, rightEncoder;

    int ticksPerRev = 360;
    double diameter = 6.0; // inches
    double distancePerRev = diameter * Math.PI;

    public DriveSubsystem() {
        leftMotors = new SpeedControllerGroup(new WPI_VictorSPX(Map.LEFT_DRIVE_1), 
                                            new WPI_VictorSPX(Map.LEFT_DRIVE_2));

        rightMotors = new SpeedControllerGroup(new WPI_VictorSPX(Map.RIGHT_DRIVE_1), 
                                            new WPI_VictorSPX(Map.RIGHT_DRIVE_2));

        leftMotors.setInverted(true);       

        leftEncoder = new Encoder(Map.LEFT_ENCODER_1, Map.LEFT_ENCODER_2, true);
        rightEncoder = new Encoder(Map.RIGHT_ENCODER_1, Map.RIGHT_ENCODER_2);
        leftEncoder.setDistancePerPulse(distancePerRev / ticksPerRev);
        rightEncoder.setDistancePerPulse(distancePerRev / ticksPerRev);
    }

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public void tankDrive(double left, double right) {
        leftMotors.set(left);
        rightMotors.set(right);
    }

    public void arcadeDrive(double y, double x) {

        if(Math.abs(y - x) > 1.0 || Math.abs(y + x) > 1.0) {
            System.out.printf("ERROR: motor inputs for arcade drive out of range. x:%f, y:%f\n", x, y);
        }
        leftMotors.set(y - x);
        rightMotors.set(y + x);
    }
    
    public int getLeftEncoderValue() {
        return leftEncoder.getRaw();
    }
    
    public int getRightEncoderValue() {
        return rightEncoder.getRaw();
    }

    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }
    
    public double getRightDistance() {
        return rightEncoder.getDistance();
    }
}