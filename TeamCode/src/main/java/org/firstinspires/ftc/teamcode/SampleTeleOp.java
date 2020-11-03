package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "tele op", group = "yeah")
public class SampleTeleOp extends LinearOpMode {
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;

    DcMotor llaunch;
    DcMotor rlaunch;
    DcMotor pull;

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

        // For the Launcher

        llaunch = hardwareMap.dcMotor.get("llaunch");
        rlaunch = hardwareMap.dcMotor.get("rlaunch");
        pull = hardwareMap.dcMotor.get("pull");

        // TODO: when builders figure out what direction motors are on.
        llaunch.setDirection(DcMotor.Direction.FORWARD);
        rlaunch.setDirection(DcMotor.Direction.FORWARD);
        pull.setDirection(DcMotor.Direction.FORWARD);

        llaunch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rlaunch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
/*
        This is unused for now, just for later just in case
        extendo = hardwareMap.dcMotor.get("ext");
        grabber = hardwareMap.servo.get("grab");
        */

        while (opModeIsActive()) {


            lf.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x);
            rf.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x);
            lb.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
            rb.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x);


            llaunch.setPower(gamepad1.right_trigger);
            rlaunch.setPower(gamepad1.right_trigger);
            pull.setPower(gamepad1.left_trigger);



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
        rb.setPower(power);

    }

    void lefturn(float power) {

        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);

    }


}

