package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
* Created by Jorge on 28/11/2016.
*/

@TeleOp (name ="TeleOp2016_2",group = "TeleOp")
public class TeleOp2016_2 extends LinearOpMode
{
   private DcMotor motorLeftF;
   private DcMotor motorRightF;
   private DcMotor aspas;
   private DcMotor banda;
   private DcMotor motorLeftB;
   private DcMotor motorRightB;
   private DcMotor scoop;
   private Servo servoR;
   private Servo servoL;

   @Override
   public void runOpMode() throws InterruptedException
   {
       motorLeftF = hardwareMap.dcMotor.get("motorLeftF");
       motorLeftB = hardwareMap.dcMotor.get("motorLeftB");
       motorRightF = hardwareMap.dcMotor.get("motorRightF");
       motorRightB = hardwareMap.dcMotor.get("motorRightB");
       aspas = hardwareMap.dcMotor.get("aspas");
       banda = hardwareMap.dcMotor.get("banda");
       scoop = hardwareMap.dcMotor.get ("scoop");
       servoR = hardwareMap.servo.get("servoR");
       servoL = hardwareMap.servo.get("servoL");
       motorLeftF.setDirection(DcMotor.Direction.REVERSE);
       motorLeftB.setDirection(DcMotor.Direction.REVERSE);

waitForStart();

       while (opModeIsActive())
       {
           motorLeftB.setPower(gamepad1.left_stick_y);
           motorLeftF.setPower(gamepad1.left_stick_y);
           motorRightB.setPower(gamepad1.right_stick_y);
           motorRightF.setPower(gamepad1.right_stick_y);
           aspas.setPower(gamepad2.left_stick_y);
           

           if (gamepad1.dpad_up && gamepad1.y) 
           {
               motorRightF.setDirection(DcMotor.Direction.FORWARD);
               motorRightB.setDirection(DcMotor.Direction.FORWARD);
               motorLeftF.setDirection(DcMotor.Direction.REVERSE);
               motorLeftF.setDirection(DcMotor.Direction.REVERSE);
           }

           if (gamepad1.dpad_left && gamepad1.x)
           {
               motorRightF.setDirection(DcMotor.Direction.REVERSE);
               motorRightB.setDirection(DcMotor.Direction.REVERSE);
               motorLeftF.setDirection(DcMotor.Direction.FORWARD);
               motorLeftF.setDirection(DcMotor.Direction.FORWARD)
           }

           if (gamepad2.x)
           {
               banda.setPower(1);
           }
           else if  (gamepad2.y)
           {
               banda.setPower(-1);
           }
           else
           {
               banda.setPower(0);
           }

           if(gamepad2.right_bumper)
           {
               scoop.setPower(0.15);
           }
           else if(gamepad2.left_bumper)
           {
               scoop.setPower(-1);
           }
           else
           {
               scoop.setPower(0);
           }

           if (gamepad1.right_bumper)
           {
               servoR.setPosition(1);
           }
           else
           {
               servoR.setPosition(0);
           }

           if (gamepad1.left_bumper)
           {
               servoL.setPosition(0);
           }
           else
           {
               servoL.setPosition(1);
           }
           
           idle();
       }
   }
}


