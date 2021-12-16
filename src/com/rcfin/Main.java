package com.rcfin;

import com.rcfin.models.Fundo;
import com.rcfin.models.Investidor;
import com.rcfin.models.Ordem;

public class Main {

    public static void main(String[] args) {

        // Criação do Fundo:
        Fundo fundo = new Fundo("FI NOVO FUNDO", "01.000.000/0001-01");

        // Investidores:
        Investidor investidor1 = new Investidor("Investidor 1", "000.000.000-01");
        Investidor investidor2 = new Investidor("Investidor 2", "000.000.000-02");

        // Inclusão dos Investidores no Fundo:
        fundo.adicionarInvestidor(investidor1);
        fundo.adicionarInvestidor(investidor2);
        fundo.getInvestidores();

        // Depósitos Iniciais:
        investidor1.comprarCotas(fundo, 10000, 1, investidor1);
        investidor2.comprarCotas(fundo, 10000, 1, investidor2);

        // Situação do Fundo:
        fundo.getExtrato();
        fundo.getCotas();
        fundo.getCaixa();
        fundo.getPatrimonio();
        
        // Lançamento de Ordens de Operações do Fundo:
        Ordem ordem1 = new Ordem(System.currentTimeMillis(), "C", "PETR3", 100, 10.00);
        Ordem ordem2 = new Ordem(System.currentTimeMillis(), "C", "PETR4", 500, 10.00);
        Ordem ordem3 = new Ordem(System.currentTimeMillis(), "C", "PETR4", 100, 12.00);
        Ordem ordem4 = new Ordem(System.currentTimeMillis(), "V", "PETR4", 100, 11.00);
        Ordem ordem5 = new Ordem(System.currentTimeMillis(), "V", "PETR4", 100, 12.00);
        Ordem ordem6 = new Ordem(System.currentTimeMillis(), "V", "PETR3", 100, 12.00);
        Ordem ordem7 = new Ordem(System.currentTimeMillis(), "V", "PETR3", 100, 12.00);

        fundo.adicionarOrdem(ordem1);
        fundo.adicionarOrdem(ordem2);
        fundo.adicionarOrdem(ordem3);
        fundo.adicionarOrdem(ordem4);
        fundo.adicionarOrdem(ordem5);
        fundo.adicionarOrdem(ordem6);
        fundo.adicionarOrdem(ordem7);

        // Atualização dos preços de mercado:
        PrecosTask.precosAtuais.put("PETR3", 15.0);
        PrecosTask.precosAtuais.put("PETR4", 15.5);

        // Posição detalhada do Fundo após operações:
        fundo.getPosicaoAtivos();
        fundo.getCaixa();

        Investidor investidor3 = new Investidor("Investidor 3", "000.000.000-03");
        fundo.adicionarInvestidor(investidor3);
        investidor3.comprarCotas(fundo, 10000, 1.11, investidor3);

        // Posição detalhada do Fundo após operações:
        fundo.getPosicaoAtivos();
        fundo.getCaixa();
        fundo.getExtrato();

        // Vender cotas:
        investidor3.venderCotas(fundo, 5000, 1.11, investidor3);

        // Posição detalhada do Fundo após operações:
        fundo.getPosicaoAtivos();
        fundo.getCaixa();
        fundo.getExtrato();

    }

}
