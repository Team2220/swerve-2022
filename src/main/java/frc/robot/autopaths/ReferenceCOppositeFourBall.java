package frc.robot.autopaths;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.commands.ExtendIntake;
import frc.robot.commands.GoToCommand;
import frc.robot.commands.RetractIntake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunShooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.util.GeomUtil;

public class ReferenceCOppositeFourBall extends SequentialCommandGroup {
    
    public ReferenceCOppositeFourBall(Intake intake, Drivetrain drivetrain, Shooter shooter, Conveyor conveyor) {
        addCommands(
            new InstantCommand(()->drivetrain.setPose(GeomUtil.getRobotCoordinate(FieldConstants.referenceCOppositeRobotCenter))),

            new ExtendIntake(intake),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(GeomUtil.poseToGetCargo(FieldConstants.referenceCOppositeRobotCenter.getTranslation(), FieldConstants.cargoEOpposite.getTranslation()))).raceWith(new RunIntake(intake, false)),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceCOppositeRobotCenter)).raceWith(new RunIntake(intake, false)),

            new RunShooter(shooter, conveyor, true).raceWith(new RunIntake(intake, false)).withTimeout(4),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(GeomUtil.poseToGetCargo(FieldConstants.referenceCOppositeRobotCenter.getTranslation(), FieldConstants.cargoDOpposite.getTranslation()))).raceWith(new RunIntake(intake, false)),

            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(GeomUtil.poseToGetCargo(FieldConstants.cargoDOpposite.getTranslation(), FieldConstants.cargoGOpposite.getTranslation()))).raceWith(new RunIntake(intake, false)),
            
            new GoToCommand(drivetrain, GeomUtil.getRobotCoordinate(FieldConstants.referenceCOppositeRobotCenter)).raceWith(new RunIntake(intake, false)),

            new RetractIntake(intake),

            new RunShooter(shooter, conveyor, true).raceWith(new RunIntake(intake, false)).withTimeout(5)
        );
    }
}