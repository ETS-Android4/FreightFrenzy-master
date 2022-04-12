package org.firstinspires.ftc.teamcode.Vision;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ACE extends OpenCvPipeline {
    Telemetry telemetry;

    Mat mat = new Mat();

    //created an enum containing the different possible numbers of rings
    public enum NumberOfRings {
        ZERO,
        ONE,
        FOUR,
        UNKNOWN
    }

    private NumberOfRings ringNumber;


    // defined the region of interest
    static final Rect mainROI = new Rect(
            new Point( 122, 100),
            new Point(189, 149)
    );

    public ACE(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        //mat.convertTo(destMat, -1, 50, 100);
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //define HSV range to identify the color yellow
        Scalar lowHSV = new Scalar (3, 80, 80);
        Scalar highHSV = new Scalar(30, 255, 255);

        //applies a threshold (everything that is yellow will be white, everything else will be black)
        //returns a new mat with this threshold
        Core.inRange(mat,lowHSV, highHSV, mat);

        //extract regions of interest from camera frame
        //submat = sub-mat, a portion of the original
        Mat main = mat.submat(mainROI);

        //calculate what percentage of the ROI became white
        //(add all the pixels together, divide by its area, divide by 255)
        double mainPercentage = Core.sumElems(main).val[0] / mainROI.area() / 255;


        //deallocates the Matrix data from the memory
        main.release();

        //create if else statements declaring the number of rings based on the percentage of white
        if(mainPercentage > 0 && mainPercentage < 0.05){
            ringNumber = NumberOfRings.ZERO;
        }
        else if (mainPercentage > 0.05 && mainPercentage < 0.40) {
            ringNumber = NumberOfRings.ONE;
        }
        else if(mainPercentage > 0.40 && mainPercentage < 1){
            ringNumber = NumberOfRings.FOUR;
        }
        else {
            ringNumber = NumberOfRings.UNKNOWN;
        }

        telemetry.addData("main percentage", Math.round(mainPercentage * 100) + "%");


        telemetry.update();

        return main;
    }

    public NumberOfRings getRingNumber() {
        return ringNumber;
    }
}
