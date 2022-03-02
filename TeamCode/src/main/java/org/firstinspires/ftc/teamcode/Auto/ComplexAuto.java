package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Projects.ProjectOdometryTest;
import org.firstinspires.ftc.teamcode.Vision.ShippingElementDetector;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Autonomous")
public class ComplexAuto extends LinearOpMode {
    //public ProjectOdometryTest robot = new ProjectOdometryTest();
    OpenCvWebcam webcam;
    ShippingElementDetector detector = new ShippingElementDetector(telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        //drivetrain.init(hardwareMap);
        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

        webcam.setPipeline(detector);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Usually this is where you'll want to start streaming from the camera (see section 4)
                webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        Path p = Path.Red;

        while (!isStarted()) {
            if (gamepad1.b) {
                p = Path.Red;
            }
            if (gamepad1.x) {
                p = Path.Blue;
            }
            telemetry.addData("Path: ", p);
            telemetry.update();
        }
        //Vector2d represents a coordinate (x,y)

        //      Trajectory goForward = drivetrain.trajectoryBuilder(new Pose2d(0, 0, 0))
//              .forward(10)
//              .build();
//      drivetrain.followTrajectory(goForward);


        Trajectory goBackwards = drivetrain.trajectoryBuilder(new Pose2d(11.6, 58.7, Math.toRadians(90)))
                .back(10)
                //.forward(19.7)
                //.waitSeconds(3)
                //.back(24.4)
                //.strafeRight(67)
                .build();
        TrajectorySequence peepeepoopoo = drivetrain.trajectorySequenceBuilder(goBackwards.end())
                .splineTo(new Vector2d(-12, 40), Math.toRadians(270))
                .addDisplacementMarker(() -> {
                    //drivetrain.storageunit.
                })
                .build();


        Trajectory strafeLeft = drivetrain.trajectoryBuilder(new Pose2d(11.6, 58.7, Math.toRadians(180)))
                .strafeLeft(15)
                .build();
        Trajectory poop = drivetrain.trajectoryBuilder(strafeLeft.end(), true)
                .splineTo(new Vector2d(-12, 40), Math.toRadians(90))
                .build();
        /*Trajectory ComplexAuto4 = drivetrain.trajectoryBuilder(new Pose2d(11.6, -58.7, Math.toRadians(90)))
                .strafeRight(50)
                .build();
        */
        waitForStart();

        //starting pose for blue side near warehouse
        Pose2d startPose = new Pose2d(11.6, 58.7, Math.toRadians(180));
        drivetrain.setPoseEstimate(startPose);

        drivetrain.followTrajectory(strafeLeft);
        drivetrain.followTrajectory(poop);

        //if(isStopRequested()) return;
        //drivetrain.followTrajectorySequence(peepeepoopoo);
        //drivetrain.followTrajectory(ComplexAuto);
        //drivetrain.followTrajectory(ComplexAuto2);

        //drivetrain.followTrajectory(ComplexAuto3);
        //drivetrain.followTrajectory(ComplexAuto4);



        //get the position of the shipping element
        /*ShippingElementDetector.ShippingElementLocation elementLocation = detector.getShippingElementLocation();
        telemetry.addData("element location:", elementLocation);
        telemetry.update();
        */

    }
}
