package org.firstinspires.ftc.teamcode.Phone;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
/*
1cm = 1022  6cm = 60    11cm = 21
2cm = 440   7cm = 45    12cm = 17
3cm = 225   8cm = 35    13cm = 15
4cm = 130   9cm = 30    14cm = 13
5cm = 85    10cm =24    15cm = 12
 */

/**
 * Created by Jorge on 08/12/2016.
 */
@Autonomous(name = "AutoPrueba_4")
public class AutoPrueba_4 extends LinearOpMode {
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
    String sColor = "";
    String Multi; //UTILIZO LA VARIABLE MULTI PARA PASAR DE ETAPAS: START DESPUES DEL METODO
                  //"TODO# PARA PASR A "AVANZARHASTADISTANCIAX" Y DE ESTA ULTIMA A LA NUEVA ORDEN.

    static double reading2cm = 800;
    static double reading10cm = 20;
    static double odsReadngRaw;
    static double odsReadingLinear;
    static int odsEstimatedDistance;
    static double m = 56;
    static double b = -0.356;
    double DistanciaInicial = odsEstimatedDistance;
    double DistanciaFinal;


    @Override
    public void runOpMode() throws InterruptedException {
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
        LineSensor.enableLed(false);
        BeaconSensor.enableLed(false);

        waitForStart();

        Todo(); //METODO DECLARADO ABAJO
        AvanzarHastaDistanciaX(); //EL PROBLEMA ESTA PASAR DE ESTA ORDEN A LA SIGUIENTE
        // PASAR DE "AVANZARHASTADISTACIAX" A ESTA.
        PicarBeacon();
    }

          /*  GirarDerecha(1);
            sleep(2000);
            //Detectar Colores de Beacon
            /*RevisarColorBeacon();
            PicarBeacon();

            Reversa(.5);
            sleep(1000);*/



    //HERRAMIENTAS
    private void Launchers(double power) {
        LauncherL.setPower(power);
        LauncherR.setPower(power);
    }

    private void SubirPelotas(double power) {
        banda.setPower(-power);
        aspas.setPower(-power);
    }

    //VUELTAS
    public void GirarDerecha(double power) {
        motorRightB.setPower(power);
        motorRightF.setPower(power);
        motorLeftB.setPower(-power);
        motorLeftF.setPower(-power);
    }

    public void GirarIzquierda(double power) {
        motorRightB.setPower(-power);
        motorRightF.setPower(-power);
        motorLeftB.setPower(power);
        motorLeftF.setPower(power);
    }

    //MANEJO
    public void Avanzar(double power) {
        motorRightB.setPower(-power);
        motorRightF.setPower(-power);
        motorLeftB.setPower(-power);
        motorLeftF.setPower(-power);
    }

    public void Reversa(double power) {
        motorRightB.setPower(power);
        motorRightF.setPower(power);
        motorLeftB.setPower(power);
        motorLeftF.setPower(power);
    }

    //CANCELACIONES
    public void NoDisparar(double power) {
        Launchers(0);
        SubirPelotas(0);
    }

    public void Frenar() {
        Avanzar(0);
        Reversa(0);
    }

    //REVISAR COLORES
    public void RevisarColorBeacon() {
        float hsvValues[] = {0, 0, 0};

        Color.RGBToHSV(BeaconSensor.red() * 8, BeaconSensor.green() * 8, BeaconSensor.blue() * 8, hsvValues);
        if (BeaconSensor.blue() > BeaconSensor.red() && BeaconSensor.blue() > BeaconSensor.green()) {
            telemetry.addData("Color", blue);
            telemetry.update();
            sColor = blue;
        } else {
            telemetry.addData("Color", red);
            telemetry.update();
            sColor = red;
        }
    }

    public void RevisarColorLinea(){
        float hsvValues[] = {0,0,0};

        Color.RGBToHSV(LineSensor.red() *8, LineSensor.green() *8, LineSensor.blue() *8, hsvValues);
        if (LineSensor.blue() > 0 && LineSensor.red() > 0 && LineSensor.green() > 0) {
            telemetry.addData("Color", white);
            telemetry.update();
            sColor = white;
        }
        else {
            telemetry.addData("Color", black);
            telemetry.update();
            sColor = black;
        }
    }

    public void RevisarDistancia(){
        telemetry.addData("Distance", odsEstimatedDistance);
        telemetry.update();
    }
    //USO DE SENSORES
    public void AvanzarHastaDistanciaX() {
         odsReadngRaw = WallSensor.getRawLightDetected();
         odsReadingLinear = Math.pow(odsReadngRaw, -0.5);
         odsEstimatedDistance = (int) ((m * odsReadingLinear) + b);
        
        while (Multi.equals("Start") && odsEstimatedDistance > 145){
            odsReadngRaw = WallSensor.getRawLightDetected();
            odsReadingLinear = Math.pow(odsReadngRaw, -0.5);
            odsEstimatedDistance = (int) ((m * odsReadingLinear) + b);
            /*  LO QUE QUIERO QUE HAGA
                Avanzar(-.2);
                while (odsEstimatedDistance < 145) {
                    motorLeftB.setPower(0);
                    motorLeftF.setPower(0);
                    motorRightB.setPower(0);
                    motorRightF.setPower(0);
                }
                GirarIzquierda(.3);
                sleep(1000);
                Frenar();
            }
        }*/
        Avanzar(-.2);
        }
        motorLeftB.setPower(0);
        motorLeftF.setPower(0);
        motorRightB.setPower(0);
        motorRightF.setPower(0);
        Multi = "Start2";
        telemetry.addLine(Multi);
        telemetry.update();

            //DEBERIA PASAR A ESTA NUEVA OREDEN AL TRANSFORMAR LA VARIABLE MULTI A "START2", PERO NO
            //RESULTA ASI, EN VEZ DE ESO SE PARA A LA DISTANCIA ESTIMADA DE 145 COMO SE UESTRA ARRIBA
            // Y MANDA LA TELEMTRY START2 POR LO QUE SE QUE EL CODIGO LLEGA HASTA ESA ORDEN Y EL PROBLEMA
            //ES LA SIGUIENTE.
        if (Multi.equals("Start2")){
            aspas.setPower(1);
            sleep(2000);
            aspas.setPower(0);
            telemetry.addLine("Logrado");
            telemetry.update();
            Multi = "Logrado";
        }
      }



    public void PicarBeacon() {

        BeaconSensor.enableLed(false);
        RevisarColorBeacon();
        while (sColor.equals(red)) {
          /*  Avanzar(.2);
            sleep(200);*/
            ServoR.setPosition(1);
            ServoL.setPosition(.6);
          //  Avanzar(.2);
          //  sleep(350);
            RevisarColorBeacon();
        }

        RevisarColorBeacon();
        while (sColor.equals(blue)) {
           // Avanzar(.2);
           // sleep(200);
            ServoL.setPosition(0);
            ServoR.setPosition(.4);
           // Avanzar(.2);
           // sleep(350);
            RevisarColorBeacon();
        }
        ServoL.setPosition(.6);
        ServoR.setPosition(.4);
    }

    public void Todo(){
        //Avanzar hasta Tape
        RevisarColorLinea();
        LineSensor.enableLed(true);
        while (sColor.equals(black)) {
            Reversa(.4);
            RevisarColorLinea();
        }
        Frenar();

        sleep(1000);

        //Acomodarse
        GirarIzquierda(.5);
        sleep(450);
        Frenar();

        sleep(1000);
        //Lanzar Pelotas
        Launchers(1);
        sleep(1500);
        SubirPelotas(1);
        sleep(3000);
        NoDisparar(0);

        sleep(2000);

        Multi = "Start";
        telemetry.addLine(Multi);
        telemetry.update();


    }
}
