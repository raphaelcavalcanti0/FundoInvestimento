package com.rcfin.models;


import java.util.ArrayList;
import java.util.List;

public class Investidor {

    String nome;
    String cpf;
    List<BoletaCota> comprasList = new ArrayList<>();

    public Investidor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void comprarCotas(Fundo fundo, int quantidade, double precoCota, Investidor inv) {
        BoletaCota boletaCota = new BoletaCota(System.currentTimeMillis(), "C", quantidade, precoCota, inv);
        comprasList.add(boletaCota);
        fundo.adicionarCotas(boletaCota);
    }

    public void venderCotas(Fundo fundo, int quantidade, double precoCota, Investidor inv) {
        BoletaCota boletaCota = new BoletaCota(System.currentTimeMillis(), "V", quantidade, precoCota, inv);
        comprasList.add(boletaCota);
        fundo.adicionarCotas(boletaCota);
    }

    public void getDepositos() {
        System.out.println("-------------------------------------");
        System.out.println("----------Lista de Depósitos---------");
        System.out.println("-------------------------------------");
        for (BoletaCota dep : comprasList) {
            System.out.println("Investidor: " + dep.getInvestidor().getNome() +
                    "\t" + "Depósito: " + dep.getPrecoCota() * dep.getQuantidade());
        }

        System.out.println("-------------------------------------");
    }

}
