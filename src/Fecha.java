package com.company;

/**
 * Esta clase define objetos que contienen la fecha de la cita (hora/dia/mes/anio
 * @author: Pineapple
 * @version: 10/06/2021
 */
public class Fecha {
    //Campos de la clase
    private int dia;
    private int mes;
    private int anio;
    private String hora;

    /**
     * Constructor para la clase fecha
     * @param dia El parámetro dia define el dia de la cita
     * @param mes El parámetro mes define el mes de la cita
     * @param anio El parámetro anio define el anio de la cita
     * @param hora El parámetro hora define el hora de la cita
     */
    public Fecha(int dia, int mes, int anio, String hora) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
    }

    /**
     * Método que devuelve el dia
     * @return el dia
     */
    public int getDia() {
        return this.dia;
    }

    /**
     * Método que devuelve el mes
     * @return el mes
     */
    public int getMes() {
        return this.mes;
    }

    /**
     * Método que devuelve el anio
     * @return el anio
     */
    public int getAnio() {
        return this.anio;
    }

    /**
     * Método que devuelve la hora
     * @return la hora
     */
    public String hora() {
        return this.hora;
    }
}
