package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "tele op", group = "yeah")
public class SampleTeleOp extends LinearOpMode {
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;

    int encodersFront,
        encodersBack,
        encodersLeft,
        encodersRight;

    DcMotor llaunch;
    DcMotor rlaunch;
    DcMotor convey;
    //DcMotor dylanRotate;
    //Servo gripper;


    // Unused for now
    //DcMotor extendo;
    //Servo grabber;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        telemetry.addData("", " The Code does not work");
        telemetry.update();

        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lb = hardwareMap.dcMotor.get("lb");
        rb = hardwareMap.dcMotor.get("rb");
        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.FORWARD);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // For the Launcher

        llaunch = hardwareMap.dcMotor.get("llaunch");
        rlaunch = hardwareMap.dcMotor.get("rlaunch");
        convey = hardwareMap.dcMotor.get("convey");
        //gripper = hardwareMap.servo.get("gripper");
        //dylanRotate = hardwareMap.dcMotor.get("leftlift");

        // TODO: when builders figure out what direction motors are on.
        llaunch.setDirection(DcMotor.Direction.FORWARD);
        rlaunch.setDirection(DcMotor.Direction.FORWARD);
        convey.setDirection(DcMotor.Direction.REVERSE);
        //dylanRotate.setDirection(DcMotor.Direction.FORWARD);

        llaunch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rlaunch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
/*
        This is unused for now, just for later just in case
        extendo = hardwareMap.dcMotor.get("ext");
        grabber = hardwareMap.servo.get("grab");
        */

        while (opModeIsActive()) {
            encodersFront = (lf.getCurrentPosition() + rf.getCurrentPosition()) / 2;
            encodersBack  = (lb.getCurrentPosition() + rb.getCurrentPosition()) / 2;
            encodersLeft  = (lf.getCurrentPosition() + lb.getCurrentPosition()) / 2;
            encodersRight = (rf.getCurrentPosition() + rb.getCurrentPosition()) / 2;
            if (gamepad1.a) {
                resetEncoders();
                telemetry.addData("encoders", "reset");
            } else {
                telemetry.addData("front", encodersFront);
                telemetry.addData("back", encodersBack);
                telemetry.addData("left", encodersLeft);
                telemetry.addData("right", encodersRight);
            }

            lf.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x);
            rf.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x);
            lb.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
            rb.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x);
            //gripper.setPosition(gamepad1.left_trigger != 0? 1 : 0);

            double launch = gamepad2.right_trigger;
            llaunch.setPower(launch);
            rlaunch.setPower(launch);
            convey.setPower(gamepad2.left_trigger);

        }
    }

    void forwards(float power) {
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
    }

    void righturn(float power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(power);
        rb.setPower(-power);
    }

    void lefturn(float power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }

    void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

