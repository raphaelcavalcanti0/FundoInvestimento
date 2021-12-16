package com.rcfin.models;

public class Ordem {

    long date;
    String tipo;
    String ativo;
    int quantidade;
    double preco;

    public Ordem(long date, String tipo, String ativo, int quantidade, double preco) {
        this.date = date;
        this.tipo = tipo;
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
