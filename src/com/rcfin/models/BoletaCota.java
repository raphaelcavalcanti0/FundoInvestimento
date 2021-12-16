package com.rcfin.models;

public class BoletaCota {
    long date;
    String tipo;
    int quantidade;
    double precoCota;
    Investidor investidor;

    public BoletaCota(long date, String tipo, int quantidade, double precoCota, Investidor investidor) {
        this.date = date;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.precoCota = precoCota;
        this.investidor = investidor;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getPrecoCota() {
        return precoCota;
    }

    public void setValorDeposito(int precoCota) {
        this.precoCota = precoCota;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public void setInvestidor(Investidor investidor) {
        this.investidor = investidor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
