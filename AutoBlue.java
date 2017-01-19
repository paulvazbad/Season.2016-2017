package org.firstinspires.ftc.teamcode.Phone;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jorge on 08/12/2016.
 */
//@Autonomous(name = "AutoBlue")
public class AutoBlue extends LinearOpMode
{
    private DcMotor motorLeftF;
    private DcMotor motorLeftB;
    private DcMotor motorRightF;
    private DcMotor motorRightB;
    private DcMotor LauncherR;
    private DcMotor LauncherL;
    private DcMotor banda;
    private DcMotor aspas;
    private Servo ServoR;
    private Servo ServoL;
    private ColorSensor LineSensor;
    private ColorSensor BeaconSensor;
    private OpticalDistanceSensor WallSensor;

    String white = "white";
    String blue = "blue";
    String red = "red";
    String green = "green";
    String black = "black";
    String sColor="";


    @Override
    public void runOpMode() throws InterruptedException
    {
        motorLeftB = hardwareMap.dcMotor.get("motorLeftB");
        motorLeftF = hardwareMap.dcMotor.get("motorLeftF");
        motorRightB = hardwareMap.dcMotor.get("motorRightB");
        motorRightF = hardwareMap.dcMotor.get("motorRightF");
        LauncherL = hardwareMap.dcMotor.get("LauncherL");
        LauncherR = hardwareMap.dcMotor.get("LauncherR");
        banda = hardwareMap.dcMotor.get("banda");
        aspas = hardwareMap.dcMotor.get("aspas");
        LineSensor = hardwareMap.colorSensor.get("LineSensor");
        BeaconSensor = hardwareMap.colorSensor.get("BeaconSensor");
        WallSensor = hardwareMap.opticalDistanceSensor.get("WallSensor");
        motorLeftB.setDirection(DcMotor.Direction.REVERSE);
        motorLeftF.setDirection(DcMotor.Direction.REVERSE);
        LauncherL.setDirection(DcMotor.Direction.REVERSE);
        LineSensor.enableLed(true);
        BeaconSensor.enableLed(false);


        waitForStart();

        Avanzar(1);
        sleep(500);
        Frenar();

        Launchers(1);
        sleep(2000);
        SubirPelotas(1);
        sleep(6000);
        NoDisparar(0);
        sleep(1000);

        Avanzar(1);
        sleep(1000);
        Frenar();

        sleep(2000);

        Avanzar(1);
        sleep(500);
       // GirarDerecha(1);
        //sleep(800);
        //Frenar();

    }
    //HERRAMIENTAS
    private void Launchers (double power){
        LauncherL.setPower(power);
        LauncherR.setPower(power);
    }
    private void SubirPelotas (double power) {
        banda.setPower(-power);
        aspas.setPower(-power);
    }
    //VUELTAS
    public void GirarDerecha(double power){
        motorRightB.setPower(power);
        motorRightF.setPower(power);
        motorLeftB.setPower(-power);
        motorLeftF.setPower(-power);
    }
    public void GirarIzquierda(double power){
        motorRightB.setPower(-power);
        motorRightF.setPower(-power);
        motorLeftB.setPower(power);
        motorLeftF.setPower(power);
    }
    //MANEJO
    public void Avanzar(double power){
        motorRightB.setPower(-power);
        motorRightF.setPower(-power);
        motorLeftB.setPower(-power);
        motorLeftF.setPower(-power);
    }
    public void Reversa(double power){
        motorRightB.setPower(power);
        motorRightF.setPower(power);
        motorLeftB.setPower(power);
        motorLeftF.setPower(power);
    }
    //CANCELACIONES
    public void NoDisparar(double power){
        Launchers(0);
        SubirPelotas(0);
    }
    public void Frenar(){
        Avanzar(0);
        Reversa(0);
    }
    //REVISAR COLORES
    public void RevisarColor (){
        float hsvValues[] = {0,0,0};

        Color.RGBToHSV(LineSensor.red()*8, LineSensor.green()*8,LineSensor.blue()*8,hsvValues);
        if (LineSensor.blue()>0 && LineSensor.red()> 0 && LineSensor.green() > 0){
            telemetry.addData("Color", white);
            sColor=white;
        }
        else if (LineSensor.blue()>LineSensor.red()&& LineSensor.blue()>LineSensor.green()){
            telemetry.addData("Color", blue);
            sColor=blue;
        }
        else if (LineSensor.red()>LineSensor.blue()&& LineSensor.red()>LineSensor.green()){
            telemetry.addData("Color", red);
            sColor=red;
        }
        else if (LineSensor.green()>LineSensor.blue()&& LineSensor.green()>LineSensor.red()){
            telemetry.addData("Color", green);
            sColor=green;
        }
        else {
            telemetry.addData("Color", black);
            sColor=black;
        }


    }

}
