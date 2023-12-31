package com.example.velmurugan.getcurrentlatitudeandlongitudeandroidcopia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.velmurugan.getcurrentlatitudeandlongitudeandroid.R;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Semaphore;

/**
 * Classe que representa a atividade principal da aplicação Android.
 * Esta é a classe de atividade principal que gerencia a interface do aplicativo Android.
 */
public class MainActivity extends AppCompatActivity {
    private boolean percursoIniciado = false;
    private TextView tvLatitude, tvLongitude, tvVelocidadeMediaParcial, tvVelocidadeMediaTotal, tvTempoDeslocamento, tvDistanciaPercorrida, tvConsumoCombustivelTotal, tvTempoParaDestinoFinal, tvVelocidadeRecomendada, tvVelocidadeMediaParcialLida, tvDistanciaPercorridaLida, tvNumeroIdentificacaoLido, tvDataHoraInicioLida, tvDataHoraFimLida, tvDescricaoCargaLida, tvNomeMotoristaLido, tvVelocidadeRecomendada2;
    private Veiculo veiculo;
    private LocationThread locationThread;
    private Semaphore semaphore;

    /**
     * Método chamado quando a Activity é criada. Ele é responsável por configurar a interface do usuário,
     * inicializar componentes, solicitar permissão de localização e iniciar a thread de atualização de localização.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exibirLayoutParaInserirCodigo();

        FirebaseApp.initializeApp(this); // Inicializa o Firebase

        // Inicializa os elementos de interface
        tvLatitude = findViewById(R.id.latitude);
        tvLongitude = findViewById(R.id.longitude);
        tvVelocidadeMediaParcial = findViewById(R.id.velocidadeMediaParcial);
        tvVelocidadeMediaTotal = findViewById(R.id.velocidadeMediaTotal);
        tvTempoDeslocamento = findViewById(R.id.tempoDeslocamento);
        tvDistanciaPercorrida = findViewById(R.id.distanciaPercorrida);
        tvConsumoCombustivelTotal = findViewById(R.id.consumoCombustivelTotal);
        tvTempoParaDestinoFinal = findViewById(R.id.tempoParaDestinoFinal);
        tvVelocidadeRecomendada = findViewById(R.id.velocidadeRecomendada);
        tvVelocidadeMediaParcialLida = findViewById(R.id.velocidadeMediaParcialLida);
        tvDistanciaPercorridaLida = findViewById(R.id.distanciaPercorridaLida);
        tvVelocidadeRecomendada2 = findViewById(R.id.velocidadeRecomendada2);
        tvNumeroIdentificacaoLido = findViewById(R.id.numeroIdentificacaoLido);
        tvDataHoraInicioLida = findViewById(R.id.dataHoraInicioLida);
        tvDataHoraFimLida = findViewById(R.id.dataHoraFimLida);
        tvDescricaoCargaLida = findViewById(R.id.descricaoCargaLida);
        tvNomeMotoristaLido = findViewById(R.id.nomeMotoristaLido);

        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        JSONLeitor jsonLeitor = new JSONLeitor(new JSONLeitor.JSONLeitorCallback() {
            @Override
            public void onResult(JSONObject result) {
                try {
                    // Obtém os dados necessários do resultado JSON e configura a instância e atributos do Veiculo
                    String numeroIdentificacaoLido = result.getString("numeroIdentificacao");
                    String dataHoraInicioLida = result.getString("dataHoraInicio");
                    String dataHoraFimLida = result.getString("dataHoraFim");
                    String descricaoCargaLida = result.getString("descricaoCarga");
                    String nomeMotoristaLido = result.getString("nomeMotorista");

                    // Instancializa a classe do Veiculo com os dados obtidos
                    veiculo = new Veiculo(gpsTracker);
                    veiculo.setNumeroIdentificacaoLido(numeroIdentificacaoLido);
                    veiculo.setDataHoraInicioLida(dataHoraInicioLida);
                    veiculo.setDataHoraFimLida(dataHoraFimLida);
                    veiculo.setDescricaoCargaLida(descricaoCargaLida);
                    veiculo.setNomeMotoristaLido(nomeMotoristaLido);

                    // Define os TextViews com os dados obtidos
                    tvNumeroIdentificacaoLido.setText(numeroIdentificacaoLido);
                    tvDataHoraInicioLida.setText(dataHoraInicioLida);
                    tvDataHoraFimLida.setText(dataHoraFimLida);
                    tvDescricaoCargaLida.setText(descricaoCargaLida);
                    tvNomeMotoristaLido.setText(nomeMotoristaLido);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        // Chama lerDados para ler os dados uma vez durante a inicialização
        jsonLeitor.lerDados();

        // Solicita permissão de localização
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnIniciarPercurso = findViewById(R.id.btnIniciarPercurso);
        btnIniciarPercurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percursoIniciado = true; // Define percursoIniciado como true para iniciar o percurso
                veiculo.setTempoDeslocamento(0);
            }
        });

        Button btnReiniciar = findViewById(R.id.btnReiniciar);
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarAplicativo(); // Reinicia o aplicativo
            }
        });

        semaphore = new Semaphore(1); // Inicializa o semáforo com 1 permissão
        locationThread = new LocationThread(this, semaphore); // Inicializa a classe LocationThread para obter atualizações de localização em uma thread separada
    }

    // Método para exibir o novo layout para a entrada de dados da nova carga
    private void exibirLayoutParaInserirCodigo() {
        // Infla o layout personalizado
        View novoLayoutView = getLayoutInflater().inflate(R.layout.layout_verifica, null);

        EditText etCodigoAcesso= novoLayoutView.findViewById(R.id.etCodigoAcesso);

        // Cria um AlertDialog personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(novoLayoutView);
        AlertDialog dialog = builder.create();

        // Define o comportamento do botão de confirmação
        Button btnConfirmar = novoLayoutView.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Aqui, você pode pegar a entrada do usuário e criar uma nova instância de Carga
                String codigoAcesso = etCodigoAcesso.getText().toString();

                // Verifica se o código de acesso é igual ao número de identificação lido do veículo
                if (codigoAcesso.equals(veiculo.getNumeroIdentificacaoLido())) {
                } else {
                    exibirLayoutParaInserirCodigo();
                }

                // Feche o AlertDialog
                dialog.dismiss();
            }
        });
        // Exiba o AlertDialog
        dialog.show();
    }

    /**
     * Obtém o status do percurso.
     *
     * @return true se o percurso foi iniciado, false caso contrário.
     */
    public boolean getPercursoIniciado() {
        return percursoIniciado;
    }

    /**
     * Reinicia a aplicação criando uma nova instância da atividade principal.
     */
    private void reiniciarAplicativo() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Método chamado quando a atividade é retomada após estar em pausa ou após a criação inicial.
     * Inicia a execução da thread de localização.
     */
    @Override
    protected void onResume() {
        super.onResume();
        locationThread.start(); // Inicia a execução da thread
    }

    /**
     * Método chamado quando a Activity é pausada e fica em segundo plano ou é interrompida.
     * Interrompe a execução da thread de localização.
     */
    @Override
    protected void onPause() {
        super.onPause();
        locationThread.interrupt(); // Interrompe a execução da thread
    }

    /**
     * Obtém através classe GpsTracker os dados de localização e os atualiza e exibe informações relevantes na interface.
     * Esse método é chamado pela thread de atualização de localização para obter os dados de localização e desempenho
     * do veículo em tempo real. Ele também verifica se o veículo chegou ao destino final e exibe uma mensagem personalizada
     * em um AlertDialog.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void atualizarLocalizacao() {
        // Obtem a localização atual do veículo usando o GpsTracker
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        LocationData locationData = gpsTracker.getLocation();

        // Verifica se a localização foi obtida/alterada com sucesso
        if (locationData != null) {
            double latitude = locationData.getLatitude();
            double longitude = locationData.getLongitude();
            long timestamp = locationData.getTimestamp();

            // Exibe a latitude e longitude na interface do usuário
            tvLatitude.setText(String.valueOf(latitude));
            tvLongitude.setText(String.valueOf(longitude));

            // Atualiza os dados do veículo com base nas coordenadas
            if (veiculo == null) {
                veiculo = new Veiculo(gpsTracker);
            }
            veiculo.atualizarDados(latitude, longitude, timestamp);

            // Obtem os dados atualizados do veículo e do serviço de transporte
            double velocidadeMediaParcial = veiculo.getVelocidadeMediaParcial();
            double velocidadeMediaTotal = veiculo.getVelocidadeMediaTotal();
            long tempoDeslocamento = veiculo.getTempoDeslocamento();
            double distanciaPercorrida = veiculo.getDistanciaPercorrida();
            double consumoCombustivelTotal = veiculo.getConsumoCombustivelTotal();
            long tempoParaDestinoFinal = veiculo.getTempoParaDestinoFinal();
            double velocidadeRecomendada = veiculo.getVelocidadeRecomendada();
            double velocidadeMediaParcialLida = veiculo.getVelocidadeMediaParcialLida();
            double distanciaPercorridaLida = veiculo.getDistanciaPercorridaLida();
            double velocidadeRecomendada2 = veiculo.getVelocidadeRecomendadaServico();
            String numeroIdentificacaoLido = veiculo.getNumeroIdentificacaoLido();
            String dataHoraInicioLida = veiculo.getDataHoraInicioLida();
            String dataHoraFimLida = veiculo.getDataHoraFimLida();
            String descricaoCargaLida = veiculo.getDescricaoCargaLida();
            String nomeMotoristaLido = veiculo.getNomeMotoristaLido();

            ServicoTransporte servicoTransporte = new ServicoTransporte(veiculo, numeroIdentificacaoLido, dataHoraInicioLida, dataHoraFimLida);

            Carga carga1 = new Carga( "Carga 1");
            servicoTransporte.getCargas().add(carga1);

            Carga carga2 = new Carga(descricaoCargaLida);
            servicoTransporte.getCargas().add(carga2);

            Motorista motorista1 = new Motorista( "Lilian");
            servicoTransporte.getMotoristas().add(motorista1);

            Motorista motorista2 = new Motorista(nomeMotoristaLido);
            servicoTransporte.getMotoristas().add(motorista2);

            // Atualiza as TextViews com os valores calculados e estipulados
            tvVelocidadeMediaParcial.setText(String.valueOf(velocidadeMediaParcial));
            tvVelocidadeMediaTotal.setText(String.valueOf(velocidadeMediaTotal));
            tvTempoDeslocamento.setText(String.valueOf(tempoDeslocamento));
            tvDistanciaPercorrida.setText(String.valueOf(distanciaPercorrida));
            tvConsumoCombustivelTotal.setText(String.valueOf(consumoCombustivelTotal));
            tvTempoParaDestinoFinal.setText(String.valueOf(tempoParaDestinoFinal));
            tvVelocidadeRecomendada.setText(String.valueOf(velocidadeRecomendada));
            tvVelocidadeMediaParcialLida.setText(String.valueOf(velocidadeMediaParcialLida));
            tvDistanciaPercorridaLida.setText(String.valueOf(distanciaPercorridaLida));
            tvVelocidadeRecomendada2.setText(String.valueOf(velocidadeRecomendada2));
            tvNumeroIdentificacaoLido.setText(String.valueOf(numeroIdentificacaoLido));
            tvDataHoraInicioLida.setText(String.valueOf(dataHoraInicioLida));
            tvDataHoraFimLida.setText(String.valueOf(dataHoraFimLida));
            tvDescricaoCargaLida.setText(String.valueOf(descricaoCargaLida));
            tvNomeMotoristaLido.setText(String.valueOf(nomeMotoristaLido));

            // Verifica se o veículo chegou ao destino final
            if (latitude > -20.4515 && longitude < -45.8267) {
                if (tempoParaDestinoFinal >= -10 && tempoParaDestinoFinal <= 10) {
                    tvTempoParaDestinoFinal.setText(String.valueOf(tempoParaDestinoFinal));
                    exibirResultado("Você concluiu o percurso no tempo correto!", android.R.color.holo_green_light);
                } else if (tempoParaDestinoFinal < -10) {
                    tvTempoParaDestinoFinal.setText(String.valueOf(tempoParaDestinoFinal));
                    exibirResultado("Você atrasou!", android.R.color.holo_red_light);
                } else if (tempoParaDestinoFinal <= 100) {
                    tvTempoParaDestinoFinal.setText(String.valueOf(tempoParaDestinoFinal));
                    exibirResultado("Você adiantou!", android.R.color.holo_red_light);
                }
            }

        }
    }

    /**
     * Exibe um resultado na tela usando um AlertDialog personalizado (concluído no tempo correto, atrasado ou adiantado).
     *
     * @param mensagem  A mensagem a ser exibida.
     * @param corFundo  A cor de fundo para o AlertDialog.
     */
    private void exibirResultado(String mensagem, int corFundo) {
        // Cria um novo layout
        View resultadoView = getLayoutInflater().inflate(R.layout.layout_result, null);
        TextView tvResultado = resultadoView.findViewById(R.id.tvResultado);
        tvResultado.setText(mensagem);
        tvResultado.setBackgroundResource(corFundo);

        Button btnReiniciar = resultadoView.findViewById(R.id.btnReiniciar);
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarAplicativo();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(resultadoView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}