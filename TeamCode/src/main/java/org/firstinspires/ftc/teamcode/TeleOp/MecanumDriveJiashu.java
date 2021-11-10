package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;

@TeleOp(name="MecanumDriveJiashu", group="Mecanum")
public class MecanumDriveJiashu extends LinearOpMode {
    private ProjectOdometryTest robot = new ProjectOdometryTest();
    /* Setting variables */

    //Speed multiplier when slow mode is active
    private float slowModeMultiplier = .4f;

    float intake = 0;
    //   float carousel = 0;
    float storageunit = 0;
    //;
    boolean yPressed = false;
    boolean isTrapdoorClosed = false;

    int elevatorHeight = 1;
    boolean storageUnitUp = false;

    @Override
    public void runOpMode() throws InterruptedException {


        robot.init(hardwareMap);
        waitForStart();


        while (opModeIsActive()) {
            //Driving controls

            //driving forward
            double y = -gamepad1.left_stick_y; //back and forth
            double x = -gamepad1.right_stick_x * 1.1; //strafing
            double rx = gamepad1.left_stick_x; //turning

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if(gamepad1.right_trigger > 0){
                robot.frontleft.setPower(1);
                robot.backleft.setPower(1);
                robot.frontright.setPower(1);
                robot.backright.setPower(1);
            }
            if(gamepad1.left_trigger > 0){
                robot.frontleft.setPower(-1);
                robot.backleft.setPower(-1);
                robot.frontright.setPower(-1);
                robot.backright.setPower(-1);
            }


            robot.frontleft.setPower(frontLeftPower * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));
            robot.backleft.setPower(backLeftPower * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));
            robot.frontright.setPower(frontRightPower * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));
            robot.backright.setPower(backRightPower * (gamepad1.left_trigger < .8 ? 1 : slowModeMultiplier));

            telemetry.addData("y", y);
            telemetry.addData("right trigger", gamepad1.right_trigger);
            telemetry.addData("left trigger", gamepad1.left_trigger);
            telemetry.addData("frontLeftPower", frontLeftPower);
            telemetry.addData("frontRightPower", frontRightPower);
            telemetry.addData("backRightPower", backRightPower);
            telemetry.addData("backLeftPower", backLeftPower);
            telemetry.update();










            //controlling intake, duck spinning, elevator lift

            if(gamepad2.x) { //a and right bumper to make the intake spin backward and slower
                robot.intake.setPower(-0.6);
            }
            else if(gamepad2.a == true){ //a to make intake spin backward
                robot.intake.setPower(-1);
            }
            else{
                robot.intake.setPower(0);
            }

            //Servo Toggle
            //0 is open trapdoor
            //1 is closed trapdoor
            if (gamepad2.y){
                if(yPressed == false){
                    yPressed = true;
                    isTrapdoorClosed = !isTrapdoorClosed;
                    if(isTrapdoorClosed == true){
                        robot.trapdoor.setPosition(0);
                    }else{
                        robot.trapdoor.setPosition(1);
                    }
                }
            }
            else {
                yPressed = false;
            }


//            if(gamepad2.x == true){ //x to spin the carousel
//                robot.carousel.setPower(1);
//            }
//            else{
//                robot.carousel.setPower(0);
//            }

            // Elevator Lift

            /*if (gamepad2.right_bumper == true){ //right bumper toggles between the 3 elevator lift heights
                elevatorHeight++;
                if (elevatorHeight>3){
                    elevatorHeight = 1;
                }
            }
            if (gamepad2.left_bumper == true){

                if(elevatorHeight > 1){
                    elevatorHeight--;
                }
                else {
                    elevatorHeight = 3;
                }

            }


            if (gamepad2.y && !storageUnitUp){

                if(elevatorHeight == 1){
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setTargetPosition(1000);
                    robot.storageunit.setPower(0.5);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                }
                if(elevatorHeight == 2){
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setTargetPosition(2000);
                    robot.storageunit.setPower(0.5);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                if(elevatorHeight == 3){
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.storageunit.setTargetPosition(3000);
                    robot.storageunit.setPower(0.5);
                    robot.storageunit.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                storageUnitUp = true;

            }

            if (gamepad2.y && storageUnitUp){
                robot.storageunit.setPower(0.5);
                robot.storageunit.setTargetPosition(0);
                storageUnitUp = false;
            }


            if(gamepad2.dpad_up == true){ //starts the elevator lift
                robot.storageunit.setPower(0.5);
                //robot.storageunit.setTargetPosition(3000);
            }
            else{
                robot.storageunit.setPower(0);
                robot.storageunit.setTargetPosition(0);
            }
            if(robot.storageunit.getCurrentPosition() > 3000 || robot.storageunit.getCurrentPosition() < 0){ //limit for the elevator lift
                robot.storageunit.setPower(0);
            }
            */


            //telemetry.addData("Elevator Height:", elevatorHeight);



            /*if(gamepad2.y == true){
                if(servoposition == 1){
                    servoposition = 0;
                }
                else{
                    servoposition = 1;
                }


            }
            robot.trapdoor.setPosition(servoposition);
            */










        }
    }
}