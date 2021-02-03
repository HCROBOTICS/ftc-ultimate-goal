/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Autonomous", group = "yep")
public class Auto extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    private static final int MILLISECONDS_PER_SQUARE = 1080;

    enum TfodView {
        NOTHING("Nothing"), SINGLE("Single"), QUAD("Quad");

        String label;
        TfodView(String name) {
            this.label = name;
        }
    }

    /* This key belongs to FTC teams 6889 and 11357. Get your own. */
    static final String VUFORIA_KEY = "AfypGhD/////AAABmfthsllptEbKpJLWTp1613szVUTl5xQJQBKWoaUbDy" +
            "LjOgOEF38/3fUHjGFD6pAlPmSTrW/ipYTOHpA48kfCl8o6PTWjR8X3220E5rDaANVOtluML1xOfvSl5fwbXr" +
            "Atj4kv8fpf2oFyu/ZYNOE5UCFaNzldW4BkJJ9w9YG5kNz4K0So/SrzZhqxPW+XbT0eTTjyx3Uox7VqRwM/DF" +
            "FAbh5kGzx8gGE+jQOAh9fVzy3rDLgQ/HQNszX7Iqwnnh/w836FuXrBbajfDun3qUQkCQKEJuaFyUEwEyZPZ+" +
            "cRDym2WJigiXsw724H0pv050q0N67W+No/keaLi2mZVMuySZijkNjnsnhKrBCerryW9MJQ";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;

    DcMotor llaunch;
    DcMotor rlaunch;
    DcMotor convey;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /* lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lb = hardwareMap.dcMotor.get("lb");
        rb = hardwareMap.dcMotor.get("rb");
        llaunch = hardwareMap.dcMotor.get("llaunch");
        rlaunch = hardwareMap.dcMotor.get("rlaunch");
        convey = hardwareMap.dcMotor.get("convey");

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        llaunch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rlaunch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE); */

        if (tfod != null) {
            tfod.activate();
        }

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        TfodView view = TfodView.NOTHING;

        /* Get in position to drop the wobble goal */
        while (opModeIsActive()) { // I made this a loop just so I can break out of it at any time.
            // get in front of the rings.
            // TODO: turn right
            goForwards(.5);
            // TODO: turn left

            sleep(500);

            // look at the Starter Stack
            view = tfodLook();

            /* en route to Zone A */
            // TODO: turn left
            goForwards(1);
            // TODO: turn right
            goForwards(2);
            if (view == TfodView.NOTHING)
                break;

            /* en route to Zone B */
            // TODO: turn right
            goForwards(1);
            // TODO: turn left
            goForwards(1);
            if (view == TfodView.SINGLE)
                break;

            /* en route to Zone C */
            goForwards(1);
            // TODO: turn left
            goForwards(1);
            // TODO: turn right
        }

        // TODO: drop the wobble goal.

        if (view == TfodView.NOTHING) {
            //TODO: turn right
            goForwards(1);
            //TODO: turn left
        }
        else if (view == TfodView.SINGLE) {
            goBackwards(1);
        }
        else {
            goBackwards(2);
            //TODO: turn Right
            goForwards(1);
            //TODO: turn Left
        }


        /*
        // drive up to the line
        forwards(.5);
        sleep(3250);
        stopMotors();

        // spin up the launcher
        powerLaunch(1);
        sleep(3000);
        // spin up the conveyor thing
        powerConvey(1);
        sleep(5000);

        // stop everything
        powerLaunch(0);
        powerConvey(0);
        */

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    public TfodView tfodLook() {
        Recognition recognition;

        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                if (updatedRecognitions.size() > 0) {
                    recognition = updatedRecognitions.get(0);

                    for (Recognition r : updatedRecognitions) {
                        // find the most likely recognition.
                        if (r.getConfidence() > recognition.getConfidence())
                            recognition = r;
                    }

                    if (recognition.getLabel().equalsIgnoreCase("Single"))
                        return TfodView.SINGLE;
                    else if (recognition.getLabel().equalsIgnoreCase("Quad"))
                        return TfodView.QUAD;
                }
            }
        }

        return TfodView.NOTHING;
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        // using an external USB webcam
        //parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        // using the cell phone's rear-facing camera
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    void forwards(double power) {
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
    }

    void goForwards(double squares) {
        forwards(.5);
        sleep((int)(squares * MILLISECONDS_PER_SQUARE));
        stopMotors();
    }

    void goBackwards(double squares) {
        forwards(-.5);
        sleep((int)(squares * MILLISECONDS_PER_SQUARE));
        stopMotors();
    }

    void turnRight(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(power);
        rb.setPower(-power);
    }

    void turnLeft(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }

    void stopMotors() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }

    void powerLaunch(double power) {
        llaunch.setPower(power);
        rlaunch.setPower(power);
    }

    void powerConvey(double power) {
        convey.setPower(power);
    }
}
