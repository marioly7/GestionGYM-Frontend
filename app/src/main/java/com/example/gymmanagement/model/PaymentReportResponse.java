package com.example.gymmanagement.model;

public class PaymentReportResponse {
    private float ingresos;
    private float porcentajePagado;
    private float porcentajeNoPagado;

    public float getIngresos() {
        return ingresos;
    }

    public void setIngresos(float ingresos) {
        this.ingresos = ingresos;
    }

    public float getPorcentajePagado() {
        return porcentajePagado;
    }

    public void setPorcentajePagado(float porcentajePagado) {
        this.porcentajePagado = porcentajePagado;
    }

    public float getPorcentajeNoPagado() {
        return porcentajeNoPagado;
    }

    public void setPorcentajeNoPagado(float porcentajeNoPagado) {
        this.porcentajeNoPagado = porcentajeNoPagado;
    }


    @Override
    public String toString() {
        return "PaymentReportResponse{" +
                "ingresos=" + ingresos +
                ", porcentajePagado=" + porcentajePagado +
                ", porcentajeNoPagado=" + porcentajeNoPagado +
                '}';
    }
}
