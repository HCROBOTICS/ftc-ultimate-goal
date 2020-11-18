package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Dtele op", group = "naw")
public class DylanTeleOp extends LinearOpMode {

    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;

    int encodersFront,
            encodersBack,
            encodersLeft,
            encodersRight;

    DcMotor dylanRotate;
    Servo gripper;


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

        gripper = hardwareMap.servo.get("gripper");
        dylanRotate = hardwareMap.dcMotor.get("leftlift");
        dylanRotate.setDirection(DcMotor.Direction.FORWARD);

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
            gripper.setPosition(gamepad1.left_trigger != 0? 1 : 0);


        }
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
