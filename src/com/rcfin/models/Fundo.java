package com.rcfin.models;

import com.rcfin.PrecosTask;

import java.util.*;

public class Fundo {

    String nome;
    String cnpj;
    int numeroCotas;
    double patrimonio, caixa, resultado, emAberto;
    List<Investidor> investidorList = new ArrayList<>();
    List<BoletaCota> boletaCotas = new ArrayList<>();
    List<Ordem> ordensList = new ArrayList<>();
    HashMap<String, List<Ordem>> ativosList = new HashMap<>();

    public Fundo(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void adicionarInvestidor(Investidor inv) {
        this.investidorList.add(inv);
    }

    public void getInvestidores() {
        System.out.println("-----------------------------------------");
        System.out.println("----------Lista de Investidores----------");
        System.out.println("-----------------------------------------");

        for (Investidor inv : investidorList) {
            System.out.println("Nome: " + inv.getNome() +
                    "\t CPF: " + inv.getCpf());
        }

        System.out.println("-----------------------------------------");
    }

    public void adicionarCotas(BoletaCota dep) {
        boletaCotas.add(dep);
    }

    public void getExtrato() {
        System.out.println("------------------------------------------");
        System.out.println("----------Lista de Movimentações----------");
        System.out.println("------------------------------------------");

        for (BoletaCota boletaCota : boletaCotas) {
            if (boletaCota.getTipo().equals("C")) {
                System.out.println("Nome: " + boletaCota.getInvestidor().getNome() +
                        "\t Compra: \t" + String.format("%,.2f", boletaCota.getPrecoCota() * boletaCota.getQuantidade()));

            } else if (boletaCota.getTipo().equals("V")) {
                System.out.println("Nome: " + boletaCota.getInvestidor().getNome() +
                        "\t Venda: \t" + String.format("%,.2f", boletaCota.getPrecoCota() * boletaCota.getQuantidade()));
            }
        }

        System.out.println("------------------------------------------");
    }

    public void getCotas() {
        this.numeroCotas = 0;
        for (BoletaCota boletaCota : boletaCotas) {
            if (boletaCota.getTipo().equals("C")) {
                numeroCotas += boletaCota.getQuantidade();
            } else if (boletaCota.getTipo().equals("V")) {
                numeroCotas -= boletaCota.getQuantidade();
            }
        }
        System.out.println("Número de Cotas: " + String.format("%1$,d", numeroCotas));
    }

    public void getCaixa() {
        this.caixa = 0;
        for (BoletaCota boletaCota : boletaCotas) {
            if (boletaCota.getTipo().equals("C")) {
                caixa += boletaCota.getQuantidade() * boletaCota.getPrecoCota();
            } else if (boletaCota.getTipo().equals("V")) {
                caixa -= boletaCota.getQuantidade() * boletaCota.getPrecoCota();
            }
        }
        for (Ordem ordem : ordensList) {
            if (ordem.getTipo().equals("C")) {
                caixa -= ordem.getPreco() * ordem.getQuantidade();
            } else if (ordem.getTipo().equals("V")) {
                caixa += ordem.getPreco() * ordem.getQuantidade();
            }
        }
        System.out.println("Caixa Disponível: \t" + String.format("%,.2f", caixa));
    }

    public void getPatrimonio() {
        this.patrimonio = 0;
        for (BoletaCota boletaCota : boletaCotas) {
            if (boletaCota.getTipo().equals("C")) {
                patrimonio += boletaCota.getQuantidade() * boletaCota.getPrecoCota();
            } else if (boletaCota.getTipo().equals("V")) {
                patrimonio -= boletaCota.getQuantidade() * boletaCota.getPrecoCota();
            }
        }
        System.out.println("Patrimônio Total: \t\t" + String.format("%,.2f", patrimonio));
    }

    public void adicionarOrdem(Ordem ordem) {
        ordensList.add(ordem);
    }

    public void getPosicaoAtivos() {
        emAberto = 0;
        resultado = 0;
        ativosList = new HashMap<>();
        patrimonio = 0;
        numeroCotas = 0;

        for (Ordem ordem : ordensList) {
            if (!ativosList.containsKey(ordem.getAtivo())) {
                List<Ordem> ordens = new ArrayList<>();
                ordens.add(ordem);
                ativosList.put(ordem.getAtivo(), ordens);
            } else {
                List<Ordem> ordens = ativosList.get(ordem.getAtivo());
                ordens.add(ordem);
            }
        }

        for (Map.Entry<String, List<Ordem>> set : ativosList.entrySet()) {
            Queue<Ordem> filaOrdensCompra = new LinkedList<>();
            Queue<Ordem> filaOrdensVenda = new LinkedList<>();
            int quantidade = 0;
            double somaPreco = 0.0;

            for (Ordem ord : set.getValue()) {
                Ordem newOrdem = new Ordem(ord.getDate(), ord.getTipo(), ord.getAtivo(), ord.getQuantidade(), ord.getPreco());
                if (ord.getTipo().equals("C")) {
                    filaOrdensCompra.add(newOrdem);
                } else if (ord.getTipo().equals("V")) {
                    filaOrdensVenda.add(newOrdem);
                }
            }

            while (filaOrdensVenda.size() != 0 && filaOrdensCompra.size() != 0) {
                if (filaOrdensCompra.peek().getQuantidade() > filaOrdensVenda.peek().getQuantidade()) {
                    int saldo = filaOrdensCompra.peek().getQuantidade() - filaOrdensVenda.peek().getQuantidade();
                    resultado += (filaOrdensVenda.peek().getPreco() - filaOrdensCompra.peek().getPreco()) * filaOrdensVenda.peek().getQuantidade();
                    filaOrdensCompra.peek().setQuantidade(saldo);
                    filaOrdensVenda.remove();
                } else if (filaOrdensCompra.peek().getQuantidade() == filaOrdensVenda.peek().getQuantidade()) {
                    resultado += (filaOrdensVenda.peek().getPreco() - filaOrdensCompra.peek().getPreco()) * filaOrdensVenda.peek().getQuantidade();
                    filaOrdensCompra.remove();
                    filaOrdensVenda.remove();
                } else if (filaOrdensCompra.peek().getQuantidade() < filaOrdensVenda.peek().getQuantidade()) {
                    int saldo = filaOrdensVenda.peek().getQuantidade() - filaOrdensCompra.peek().getQuantidade();
                    resultado += (filaOrdensVenda.peek().getPreco() - filaOrdensCompra.peek().getPreco()) * filaOrdensVenda.peek().getQuantidade();
                    filaOrdensCompra.remove();
                    filaOrdensVenda.peek().setQuantidade(saldo);
                }
            }

            if (filaOrdensCompra.size() > filaOrdensVenda.size()) {
                while (filaOrdensCompra.size() > 0) {
                    if (filaOrdensCompra.peek() != null) {
                        quantidade += filaOrdensCompra.peek().getQuantidade();
                        somaPreco += filaOrdensCompra.peek().getQuantidade() * filaOrdensCompra.peek().getPreco();
                        filaOrdensCompra.remove();
                    } else {
                        break;
                    }
                }
            } else {
                while (filaOrdensVenda.size() > 0) {
                    if (filaOrdensVenda.peek() != null) {
                        quantidade -= filaOrdensVenda.peek().getQuantidade();
                        somaPreco -= filaOrdensVenda.peek().getQuantidade() * filaOrdensVenda.peek().getPreco();
                        filaOrdensVenda.remove();
                    } else {
                        break;
                    }
                }
            }

            if (quantidade != 0) {
                double precoMedio = somaPreco / quantidade;
                double resultadoAberto = (PrecosTask.precosAtuais.get(set.getKey()) - precoMedio) * quantidade;
                emAberto += resultadoAberto;
                System.out.println("Ativo: " + set.getKey() + "\t N° Ordens: " + set.getValue().size()
                        + "\t Quantidade: " + String.format("%1$,d", quantidade) + "\t Preço Médio: " + String.format("%,.2f", precoMedio)
                        + "\t Preço Atual: " + String.format("%,.2f", PrecosTask.precosAtuais.get(set.getKey()))
                        + "\t Resultado em Aberto: " + String.format("%,.2f", resultadoAberto));

            }

        }
        getCotas();
        getPatrimonio();
        System.out.println("Patrimônio Líquido: \t\t" + String.format("%,.2f", (resultado + emAberto + patrimonio)));

        double valorCota = ((resultado + emAberto + patrimonio) * 1) / numeroCotas;

        System.out.println("Resultado Realizado: \t\t" + String.format("%,.2f", resultado));
        System.out.println("Resultado Em Aberto: \t\t" + String.format("%,.2f", emAberto));
        System.out.println("Valor da Cota: \t\t" + valorCota + "\t Valorização (+)/Depreciação (-): " + String.format("%,.2f" , (valorCota - 1) * 100) + "%");
    }

}
