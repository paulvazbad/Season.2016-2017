package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
* Created by Jorge on 28/11/2016.
*/
@Autonomous(name ="AutonomousRed",group = "Autonomous")
public class AutonomousRed extends LinearOpMode
{
   public DcMotor motorLeftF;
   public DcMotor motorLeftB;
   public DcMotor motorRightF;
   public DcMotor motorRightB;
   public DcMotor aspas;
   public DcMotor banda;

   @Override
   public void runOpMode() throws InterruptedException
   {
       motorLeftF = hardwareMap.dcMotor.get("motorLeftF");
       motorLeftB = hardwareMap.dcMotor.get("motorLeftB");
       motorRightF = hardwareMap.dcMotor.get("motorRightF");
       motorRightB = hardwareMap.dcMotor.get("motorRightB");
       aspas = hardwareMap.dcMotor.get("aspas");
       banda = hardwareMap.dcMotor.get("banda");
       motorLeftF.setDirection(DcMotor.Direction.REVERSE);
       motorLeftB.setDirection(DcMotor.Direction.REVERSE);

       waitForStart();
       
       sleep(2000);

       DriveBackward(1);
       sleep(1800);
       StopDriving();

       sleep(2000);

       TurnRight(1);
       sleep(450);
       StopDriving();

       DriveForward(1);
       sleep(1900);
       StopDriving();

       Girar(1);
       Banda(1);
       sleep(6000);
       NoGirar(0);
       NoGirar(0);

   }
   public void DriveBackward(double power)
   {
       motorLeftF.setPower(power);
       motorLeftB.setPower(power);
       motorRightF.setPower(power);
       motorRightB.setPower(power);
   }
   public void DriveForward(double power)
   {
       motorLeftF.setPower(-power);
       motorLeftB.setPower(-power);
       motorRightF.setPower(-power);
       motorRightB.setPower(-power);
   }
   public void StopDriving()
   {
       DriveBackward(0);
       DriveForward(0);
   }
   public void TurnRight(double power)
   {
       motorRightF.setPower(power);
       motorRightB.setPower(power);
       motorLeftF.setPower(-power);
       motorLeftB.setPower(-power);
   }
   public void Girar (double power)
   {
       aspas.setPower(power);
   }
   public void NoGirar (double power)
   {
       Girar(0);
       Banda(0);
   }
   public void Banda (double power)
   {
       banda.setPower(power);
   }
   public void NoBanda (double power)
   {
       Banda(0);
   }
}
