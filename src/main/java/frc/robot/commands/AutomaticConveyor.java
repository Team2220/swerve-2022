package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class AutomaticConveyor extends CommandBase {
    ConveyorSubsystem conveyorSubsystem;
    SystemState systemState = SystemState.IDLE;
    DoubleSupplier manualPower;
    BooleanSupplier manualOveride;
    BooleanSupplier switchToIdle;

    public AutomaticConveyor(ConveyorSubsystem conveyorSubsystem, DoubleSupplier manualPower, BooleanSupplier manualOveride, BooleanSupplier switchToIdle) {
        this.conveyorSubsystem = conveyorSubsystem;
        this.manualPower = manualPower;
        this.manualOveride = manualOveride;
        this.switchToIdle = switchToIdle;
    }

    public void initialize() {

    }

    public void execute() {
        switch (systemState) {
            case IDLE:
                conveyorSubsystem.setPower(0);
                if (conveyorSubsystem.isBallPresentAtInput()) {
                    transitionSystemState(SystemState.LOADING);
                }
                if (manualOveride.getAsBoolean()) {
                    transitionSystemState(SystemState.MANUAL);
                }
                break;
            case LOADING:
                conveyorSubsystem.setPower(0.5);
                if (!conveyorSubsystem.isBallPresentAtInput()) {
                    transitionSystemState(SystemState.IDLE);
                }
                if (manualOveride.getAsBoolean()) {
                    transitionSystemState(SystemState.MANUAL);
                }
                break;
            case MANUAL:
                conveyorSubsystem.setPower(manualPower.getAsDouble());
                if (switchToIdle.getAsBoolean()) {
                    transitionSystemState(SystemState.IDLE);
                }
                break;
        }
    }

    public enum SystemState {
        // What the robot is currently doing
        IDLE, LOADING, MANUAL;
    }

    public void transitionSystemState(SystemState state) {
        if (state == systemState) {
            return;
        }
        switch (state) {
            case IDLE:
                break;
            case LOADING:
                break;
            case MANUAL:
                break;
        }
        systemState = state;
    }

    public void end() {

    }

}