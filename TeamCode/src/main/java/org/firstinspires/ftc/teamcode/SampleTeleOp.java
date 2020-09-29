package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "tele op", group = "yeah")
public class SampleTeleOp extends LinearOpMode {
    DcMotor lf;
    DcMotor rf;
    DcMotor lb;
    DcMotor rb;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        telemetry.addData("", "yello, world");
        telemetry.update();

        /* TODO: fix these wrong lines */
        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("lb");
        lb = hardwareMap.dcMotor.get("rf");
        rb = hardwareMap.dcMotor.get("rb");

        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rb.setDirection(DcMotor.Direction.FORWARD);

        {
            /* lf.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x);
            rf.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
            lb.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x);
            rb.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x);
            */

            forwards(1);
            sleep(1000);
            forwards(0);
            sleep(1000);
            forwards(-1);
            sleep(1000);
            forwards(0);

            telemetry.update();
        }
    }

    void forwards(float power) {
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);
    }
}
