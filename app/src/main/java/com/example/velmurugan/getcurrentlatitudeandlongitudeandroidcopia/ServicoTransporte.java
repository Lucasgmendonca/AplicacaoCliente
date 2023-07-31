package com.example.velmurugan.getcurrentlatitudeandlongitudeandroidcopia;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Representa um serviço de transporte que possui um número de identificação,
 * data e hora de início e data e hora de fim, bem como listas de cargas e motoristas associados.
 */
public class ServicoTransporte extends Thread {

    private String numeroIdentificacao;
    private String dataHoraInicio;
    private String dataHoraFim;
    private List<Carga> cargas;
    private List<Motorista> motoristas;

    private final Veiculo veiculo;
    private final Semaphore semaphore;

    /**
     * Construtor da classe ServicoTransporte.
     *
     * @param veiculo O veículo utilizado no serviço de transporte.
     * @param numeroIdentificacao O número de identificação do serviço de transporte.
     * @param dataHoraInicio A data e hora de início do serviço de transporte.
     * @param dataHoraFim A data e hora de fim do serviço de transporte.
     */
    public ServicoTransporte(Veiculo veiculo, String numeroIdentificacao, String dataHoraInicio, String dataHoraFim) {
        this.numeroIdentificacao = numeroIdentificacao;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.cargas = new ArrayList<>();
        this.motoristas = new ArrayList<>();
        this.veiculo = veiculo;
        this.semaphore = new Semaphore(1);
    }

    /**
     * Obtém o número de identificação do serviço de transporte.
     *
     * @return O número de identificação do serviço de transporte.
     */
    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    /**
     * Obtém a data e hora de início do serviço de transporte.
     *
     * @return A data e hora de início do serviço de transporte.
     */
    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    /**
     * Obtém a data e hora de fim do serviço de transporte.
     *
     * @return A data e hora de fim do serviço de transporte.
     */
    public String getDataHoraFim() {
        return dataHoraFim;
    }

    /**
     * Obtém a lista de cargas associadas ao serviço de transporte.
     *
     * @return A lista de cargas associadas ao serviço de transporte.
     */
    public List<Carga> getCargas() {
        return cargas;
    }

    /**
     * Obtém a lista de motoristas associados ao serviço de transporte.
     *
     * @return A lista de motoristas associados ao serviço de transporte.
     */
    public List<Motorista> getMotoristas() {
        return motoristas;
    }


    /**
     * Método run() que é executado quando a thread é iniciada.
     * Ele lê os dados do JSON criptografado usando um JSONLeitor e processa esses dados.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        try {
            semaphore.acquire(); // Permissão do semáforo

            // Lê os dados do JSON criptografado usando a classe JSONLeitor
            JSONLeitor leitor = new JSONLeitor(new JSONLeitor.JSONLeitorCallback() {
                @Override
                public void onResult(JSONObject result) {
                    // Tratamento dos dados JSON descriptografados (result)
                    try {
                        // Processa os dados do JSON e atualiza o objeto Veiculo
                        veiculo.setVelocidadeMediaParcialLida(result.getDouble("velocidadeMediaParcial"));
                        veiculo.setDistanciaPercorridaLida(result.getDouble("distanciaPercorrida"));
                        veiculo.setNumeroIdentificacaoLido(result.getString("numeroIdentificacao"));
                        veiculo.setDataHoraInicioLida(result.getString("dataHoraInicio"));
                        veiculo.setDataHoraFimLida(result.getString("dataHoraFim"));
                        veiculo.setDescricaoCargaLida(result.getString("descricaoCarga"));
                        veiculo.setNomeMotoristaLido(result.getString("nomeMotorista"));
                        veiculo.setRespectivoIntervaloLido(result.getInt("respectivoIntervalo"));
                        veiculo.setIntervaloTempoLocalizacoesLido(result.getInt("intervaloTempoLocalizacoes"));
                        double velocidadeRecomendada2 = veiculo.calculoVelocidadeReconciliacaoServico();
                        veiculo.setVelocidadeRecomendada2(velocidadeRecomendada2);

                        // Exibindo as informações para verificação
                        //System.out.println("Velocidade Média Parcial Lida: " + veiculo.getVelocidadeMediaParcialLida());
                        //System.out.println("Distância Percorrida Lida: " + veiculo.getDistanciaPercorridaLida());
                        //System.out.println("Respectivo Intervalo: " + veiculo.getRespectivoIntervaloLido());
                        //System.out.println("Intervalo Localizações: " + veiculo.getIntervaloTempoLocalizacoesLido());
                        //System.out.println("Número de Identificação Lido: " + veiculo.getNumeroIdentificacaoLido());
                        //System.out.println("Data e Hora de Início Lidas: " + veiculo.getDataHoraInicioLida());
                        //System.out.println("Data e Hora de Fim Lidas: " + veiculo.getDataHoraFimLida());
                        //System.out.println("Descrição da Carga Lida: " + veiculo.getDescricaoCargaLida());
                        //System.out.println("Nome do Motorista Lido: " + veiculo.getNomeMotoristaLido());

                        semaphore.release(); // Libera a permissão do semáforo
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });

            leitor.lerDados();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}