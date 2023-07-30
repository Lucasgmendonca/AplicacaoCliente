package com.example.velmurugan.getcurrentlatitudeandlongitudeandroidcopia;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

/**
 * Classe que representa um veículo.
 */
public class Veiculo {
    private static final double LATITUDE_INICIAL = -20.473983333333333;
    private static final double LONGITUDE_INICIAL = -45.82669166666666;
    private static final double LATITUDE_FINAL = -20.451495;
    private static final double LONGITUDE_FINAL = -45.826725;
    private static final long TEMPO_PARA_DESTINO_FINAL = 100;
    private static final double DESLOCAMENTO_PARCIAL = 0.5052034858527461;

    private boolean verificaTrocaLocalizacao;
    private double velocidadeMediaParcial;
    private double distanciaPercorrida;
    private double consumoCombustivelTotal;
    private double velocidadeMediaTotal;
    private double velocidadeRecomendada;
    private double velocidadeRecomendadaServico;
    private long tempoDeslocamento;
    private long tempoParaDestinoFinal;
    private int intervaloTempoLocalizacoes;
    private int respectivoIntervalo;
    private int constIntervaloTempoLocalizacoes;
    private double velocidadeMediaParcialLida;
    private double distanciaPercorridaLida;
    private int respectivoIntervaloLido;
    private int intervaloTempoLocalizacoesLido;
    private String numeroIdentificacaoLido;
    private String dataHoraInicioLida;
    private String dataHoraFimLida;
    private String descricaoCargaLida;
    private String nomeMotoristaLido;
    private double somaDiferencasDeIntervalos;

    private final GpsTracker gpsTracker;

    /**
     * Construtor da classe Veiculo.
     *
     * @param gpsTracker O objeto GpsTracker responsável por rastrear a localização do veículo.
     */
    public Veiculo( GpsTracker gpsTracker) {
        this.gpsTracker = gpsTracker;
        this.verificaTrocaLocalizacao = false;
        this.velocidadeMediaParcial = 0;
        this.distanciaPercorrida = 0;
        this.consumoCombustivelTotal = 0;
        this.velocidadeMediaTotal = 0;
        this.velocidadeRecomendada = (DESLOCAMENTO_PARCIAL * 1000 / 20 ) * 3.6;;
        this.velocidadeRecomendadaServico = 0;
        this.tempoDeslocamento = 0;
        this.tempoParaDestinoFinal = TEMPO_PARA_DESTINO_FINAL;
        this.intervaloTempoLocalizacoes = 1;
        this.respectivoIntervalo = 0;
        this.velocidadeMediaParcialLida = 0;
        this.distanciaPercorridaLida = 0;
        this.respectivoIntervaloLido = 0;
        this.intervaloTempoLocalizacoesLido = 0;
        this.numeroIdentificacaoLido = null;
        this.dataHoraInicioLida = null;
        this.dataHoraFimLida = null;
        this.descricaoCargaLida = null;
        this.nomeMotoristaLido = null;
    }

    /**
     * Obtém o valor da variável que indica se houve troca de localização.
     *
     * @return true se houve troca de localização, false caso contrário.
     */
    public boolean getVerificaTrocaLocalizacao() {
        return verificaTrocaLocalizacao;
    }

    /**
     * Obtém a velocidade média parcial do veículo.
     *
     * @return a velocidade média parcial.
     */
    public double getVelocidadeMediaParcial() {
        return velocidadeMediaParcial;
    }

    /**
     * Obtém a distância percorrida pelo veículo.
     *
     * @return a distância percorrida.
     */
    public double getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    /**
     * Obtém o consumo total de combustível do veículo.
     *
     * @return o consumo total de combustível.
     */
    public double getConsumoCombustivelTotal() {
        return consumoCombustivelTotal;
    }

    /**
     * Obtém a velocidade média total do veículo.
     *
     * @return a velocidade média total.
     */
    public double getVelocidadeMediaTotal() {
        return velocidadeMediaTotal;
    }

    /**
     * Obtém a velocidade recomendada para o veículo.
     *
     * @return a velocidade recomendada.
     */
    public double getVelocidadeRecomendada() {
        return velocidadeRecomendada;
    }

    /**
     * Obtém o tempo de deslocamento do veículo.
     *
     * @return o tempo de deslocamento.
     */
    public long getTempoDeslocamento() {
        return tempoDeslocamento;
    }

    /**
     * Obtém o tempo restante para o destino final do veículo.
     *
     * @return o tempo restante para o destino final.
     */
    public long getTempoParaDestinoFinal() {
        return tempoParaDestinoFinal;
    }

    /**
     * Obtém o intervalo de tempo entre as 2 últimas localizações do veículo.
     *
     * @return o intervalo de tempo entre as 2 últimas localizações do veículo.
     */
    public int getIntervaloTempoLocalizacoes() {
        return intervaloTempoLocalizacoes;
    }

    /**
     * Obtém um valor fixo do intervalo de tempo entre as 2 últimas localizações do veículo.
     *
     * @return um valor fixo do intervalo de tempo entre as 2 últimas localizações do veículo.
     */
    public int getConstIntervaloTempoLocalizacoes() {return constIntervaloTempoLocalizacoes; }

    /**
     * Obtém o respectivo intervalo de localizações do veículo.
     *
     * @return o respectivo intervalo de localizações do veículo.
     */
    public int getRespectivoIntervalo() {
        return respectivoIntervalo;
    }

    /**
     * Obtém a velocidade média parcial do veículo do serviço de transporte.
     *
     * @return a velocidade média parcial do veículo do serviço de transporte.
     */
    public double getVelocidadeMediaParcialLida() { return velocidadeMediaParcialLida; }

    /**
     * Obtém a distância percorrida do veículo do serviço de transporte.
     *
     * @return a distância percorrid do veículo do serviço de transporte.
     */
    public double getDistanciaPercorridaLida() {
        return distanciaPercorridaLida;
    }

    /**
     * Obtém o número de identificação do serviço de transporte.
     *
     * @return o número de identificação do serviço de transporte.
     */
    public String getNumeroIdentificacaoLido() {
        return numeroIdentificacaoLido;
    }

    /**
     * Obtém o respectivo intervalo de localizações do veículo do serviço de transporte.
     *
     * @return o respectivo intervalo de localizações do veículo do serviço de transporte.
     */
    public int getRespectivoIntervaloLido() { return respectivoIntervaloLido; }

    /**
     * Obtém o intervalo de tempo entre as 2 últimas localizações do veículo do serviço de transporte.
     *
     * @return o intervalo de tempo entre as 2 últimas localizações do veículo do serviço de transporte.
     */
    public int getIntervaloTempoLocalizacoesLido() { return intervaloTempoLocalizacoesLido; }

    /**
     * Obtém a velocidade recomendada para o veículo atual chegue no próximo ponto no mesmo tempo que o veículo do serviço de trasnporte.
     *
     * @return a velocidade recomendada para o veículo atual chegue no próximo ponto no mesmo tempo que o veículo do serviço de trasnporte.
     */
    public double getVelocidadeRecomendadaServico() {
        return velocidadeRecomendadaServico;
    }

    /**
     * Obtém a data e hora de início do percurso estipulada pelo veículo do serviço de transporte.
     *
     * @return a data e hora de início do percurso  estipulada pelo veículo do serviço de transporte.
     */
    public String getDataHoraInicioLida() {
        return dataHoraInicioLida;
    }

    /**
     * Obtém a data e hora esperada de finalização do percurso estipulada pelo veículo do serviço de transporte.
     *
     * @return a data e hora esperada de finalização do percurso estipulada pelo veículo do serviço de transporte.
     */
    public String getDataHoraFimLida() {
        return dataHoraFimLida;
    }

    /**
     * Obtém a descrição da carga estipulada pelo veículo do serviço de transporte.
     *
     * @return a descrição da carga estipulada pelo veículo do serviço de transporte.
     */
    public String getDescricaoCargaLida() {
        return descricaoCargaLida;
    }

    /**
     * Obtém o nome do motorista estipulado pelo veículo do serviço de transporte.
     *
     * @return o nome do motorista estipulado pelo veículo do serviço de transporte.
     */
    public String getNomeMotoristaLido() {
        return nomeMotoristaLido;
    }

    public void setVelocidadeMediaParcialLida(double velocidadeMediaParcialLida) {
        this.velocidadeMediaParcialLida = velocidadeMediaParcialLida;
    }

    public void setDistanciaPercorridaLida(double distanciaPercorridaLida) {
        this.distanciaPercorridaLida = distanciaPercorridaLida;
    }

    public void setRespectivoIntervaloLido(int respectivoIntervaloLido) {
        this.respectivoIntervaloLido = respectivoIntervaloLido;
    }

    public void setIntervaloTempoLocalizacoesLido(int intervaloTempoLocalizacoesLido) {
        this.intervaloTempoLocalizacoesLido = intervaloTempoLocalizacoesLido;
    }

    public void setNumeroIdentificacaoLido(String numeroIdentificacaoLido) {
        this.numeroIdentificacaoLido = numeroIdentificacaoLido;
    }

    public void setDataHoraInicioLida(String dataHoraInicioLida) {
        this.dataHoraInicioLida = dataHoraInicioLida;
    }

    public void setDataHoraFimLida(String dataHoraFimLida) {
        this.dataHoraFimLida = dataHoraFimLida;
    }

    public void setDescricaoCargaLida(String descricaoCargaLida) {
        this.descricaoCargaLida = descricaoCargaLida;
    }

    public void setNomeMotoristaLido(String nomeMotoristaLido) {
        this.nomeMotoristaLido = nomeMotoristaLido;
    }

    public void setVelocidadeRecomendada2(double velocidadeRecomendadaServico) {
        this.velocidadeRecomendadaServico = velocidadeRecomendadaServico;
    }


    /**
     * Atualiza os dados do veículo com a nova localização e inicie uma thread para buscar os dados do JSON criptografado e exibi-los na interface de usuário
     *
     * @param latitude  a nova latitude.
     * @param longitude a nova longitude.
     * @param timestamp o timestamp da nova localização.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void atualizarDados(double latitude, double longitude, long timestamp) {
        List<LocationData> locationDataList = gpsTracker.getSortedLocationData();
        int size = locationDataList.size();

        if (size >= 1) {
            LocationData lastLocationData = locationDataList.get(size - 1);

            // Verifica se houve troca de localização
            if (lastLocationData.getLatitude() != latitude || lastLocationData.getLongitude() != longitude) {
                lastLocationData.setLatitude(latitude);
                lastLocationData.setLongitude(longitude);
                lastLocationData.setTimestamp(timestamp);
                verificaTrocaLocalizacao = true;
            }

            if (size >= 2) {

                // Calcula a distância percorrida desde o ponto inicial
                distanciaPercorrida = calculoDistancia(
                        LATITUDE_INICIAL, LONGITUDE_INICIAL,
                        latitude, longitude
                );

                LocationData secondLastLocationData = locationDataList.get(size - 2);

                if (latitude < LATITUDE_FINAL && longitude > LONGITUDE_FINAL) {
                    tempoDeslocamento = (System.currentTimeMillis() - secondLastLocationData.getTimestamp()) / 1000;
                    tempoParaDestinoFinal--;

                }

                if (getVerificaTrocaLocalizacao()) {
                    respectivoIntervalo++;
                    // Calcula a velocidade média parcial
                    velocidadeMediaParcial = (DESLOCAMENTO_PARCIAL * 1000 / getIntervaloTempoLocalizacoes()) * 3.6;
                    // Calcula o consumo de combustível total
                    consumoCombustivelTotal += calculoConsumoCombustivel();
                    // Calcula a velocidade média total
                    velocidadeMediaTotal = (DESLOCAMENTO_PARCIAL * getRespectivoIntervalo() * 1000 / getTempoDeslocamento()) * 3.6;
                    // Calcula a velocidade recomendada
                    velocidadeRecomendada = calculoVelocidadeReconciliacao();

                    // Inicia a thread para buscar os dados do JSON criptografado e exibi-los na interface de usuário
                    ServicoTransporte servicoTransporteThread = new ServicoTransporte(this, null,null,null);
                    servicoTransporteThread.start();

                    constIntervaloTempoLocalizacoes = getIntervaloTempoLocalizacoes();
                    intervaloTempoLocalizacoes = 1;
                }

                intervaloTempoLocalizacoes++;
                verificaTrocaLocalizacao = false;
            }
        }
    }

    /**
     * Calcula a distância entre duas coordenadas geográficas usando a fórmula de Haversine.
     *
     * @param lat1 a latitude da primeira coordenada.
     * @param lon1 a longitude da primeira coordenada.
     * @param lat2 a latitude da segunda coordenada.
     * @param lon2 a longitude da segunda coordenada.
     * @return a distância entre as coordenadas em quilômetros.
     */
    private double calculoDistancia(double lat1, double lon1, double lat2, double lon2) {
        double raioTerra = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return raioTerra * c;
    }

    // Variáveis de Reconciliação para cálculo da velocidade recomendada com base no Tempo para chegar ao Destino Final
    private double P1 = 20.0;
    private double P2 = 20.0;
    private double P3 = 20.0;
    private double P4 = 20.0;
    private double P5 = 20.0;
    private double variacaoTempoParaDestinoFinal = 0.0;
    private double V1 = 1.0;
    private double V2 = 1.0;
    private double V3 = 1.0;
    private double V4 = 1.0;
    private double V5 = 1.0;

    /**
     * Calcula a velocidade de reconciliação com base no tempo para o chegar ao Destino Final.
     *
     * @return a velocidade de reconciliação em km/h.
     */
    private double calculoVelocidadeReconciliacao() {
        int respectivoIntervalo = getRespectivoIntervalo();

        switch (respectivoIntervalo) {
            case 1:
                P1 = getIntervaloTempoLocalizacoes();
                V1 = 0.0;
                break;
            case 2:
                P2 = getIntervaloTempoLocalizacoes();
                V2 = 0.0;
                break;
            case 3:
                P3 = getIntervaloTempoLocalizacoes();
                V3 = 0.0;
                break;
            case 4:
                P4 = getIntervaloTempoLocalizacoes();
                V4 = 0.0;
                break;
            case 5:
                P5 = getIntervaloTempoLocalizacoes();
                V5 = 0.0;
                variacaoTempoParaDestinoFinal = 1.0;
                break;
        }

        double[] y = new double[]{TEMPO_PARA_DESTINO_FINAL, P1, P2, P3, P4, P5};
        double[] v = new double[]{variacaoTempoParaDestinoFinal, V1, V2, V3, V4, V5};
        double[][] A = new double[][]{{1.0, -1.0, -1.0, -1.0, -1.0, -1.0}};
        Reconciliation rec = new Reconciliation(y, v, A);

        // System.out.println("Y_hat:");
        // rec.printMatrix(rec.getReconciledFlow());

        double[] reconciledFlow = rec.getReconciledFlow();

        if (respectivoIntervalo >= 5) {
            return 0;
        }

        return (DESLOCAMENTO_PARCIAL * 1000 / reconciledFlow[respectivoIntervalo + 1]) * 3.6;
    }

    // Variáveis de Reconciliação para cálculo da velocidade recomendada com base no Tempo em que o veículo do serviço de transporte irá chegar no Destino Final
    private double tempoParaDestinoCrossDocking = 60;
    private double P1Cross = 20.0;
    private double P2Cross = 20.0;
    private double P3Cross = 20.0;
    private double variacaoTempoParaDestinoCrossDocking = 0.0;
    private double V1Cross = 1.0;
    private double V2Cross = 1.0;
    private double V3Cross = 1.0;

    /**
     * Calcula a velocidade de reconciliação com base no tempo em que o veículo do serviço de transporte irá chegar no Destino Final
     *
     * @return a velocidade de reconciliação em km/h.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public double calculoVelocidadeReconciliacaoServico() {
        double tempoEstimadoParaConclusao = ((3 - getRespectivoIntervaloLido()) * DESLOCAMENTO_PARCIAL * 1000) / (getVelocidadeMediaParcialLida() / 3.6);

        switch (getRespectivoIntervaloLido()) {
            case 1:
                P1Cross = getIntervaloTempoLocalizacoesLido();
                V1Cross = 0.0;
                tempoParaDestinoCrossDocking = tempoEstimadoParaConclusao + P1Cross;
                break;
            case 2:
                P2Cross = getIntervaloTempoLocalizacoesLido();
                V2Cross = 0.0;
                tempoParaDestinoCrossDocking = tempoEstimadoParaConclusao + P1Cross + P2Cross;
                break;
            case 3:
                P3Cross = getIntervaloTempoLocalizacoesLido();
                V3Cross = 0.0;
                tempoParaDestinoCrossDocking = tempoEstimadoParaConclusao + P1Cross + P2Cross + P3Cross;
                variacaoTempoParaDestinoCrossDocking = 1.0;
                break;
        }

        double[] y = new double[]{tempoParaDestinoCrossDocking, P1Cross, P2Cross, P3Cross};
        double[] v = new double[]{variacaoTempoParaDestinoCrossDocking, V1Cross, V2Cross, V3Cross};
        double[][] A = new double[][]{{1.0, -1.0, -1.0, -1.0}};
        Reconciliation rec = new Reconciliation(y, v, A);

        // System.out.println("Y_hat:");
        // rec.printMatrix(rec.getReconciledFlow());

        double[] reconciledFlow = rec.getReconciledFlow();

        if (getRespectivoIntervaloLido() >= 3) {
            return 0;
        }

        double diferencaDeIntervalos = getConstIntervaloTempoLocalizacoes() - getIntervaloTempoLocalizacoesLido();
        somaDiferencasDeIntervalos = somaDiferencasDeIntervalos + diferencaDeIntervalos;

        return  (DESLOCAMENTO_PARCIAL * 1000 * 3.6 / ((reconciledFlow[getRespectivoIntervaloLido() + 1]) - somaDiferencasDeIntervalos));
    }

    /**
     * Calcula o consumo de combustível com base na velocidade média parcial.
     *
     * @return o consumo de combustível.
     */
    private double calculoConsumoCombustivel() {
        double consumoCombustivelPorKm;

        if (getVelocidadeMediaParcial() >= 0 && getVelocidadeMediaParcial() <= 80) {
            consumoCombustivelPorKm = 0.047;
        } else if (getVelocidadeMediaParcial() > 80 && getVelocidadeMediaParcial() <= 120) {
            consumoCombustivelPorKm = 0.0641;
        } else {
            consumoCombustivelPorKm = 0.0962;
        }

        return DESLOCAMENTO_PARCIAL * consumoCombustivelPorKm;
    }

}