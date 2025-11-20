// import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Duration;

public class Veiculo {
    //Atributos
    private final String placa, tipo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private long permanencia;

    //Metodo contrutor
    public Veiculo(String placa, String tipo, LocalDateTime horaEntrada, LocalDateTime horaSaida, long permanencia){
        this.placa = placa;
        this.tipo = tipo;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.permanencia = permanencia;

    }

    //Metodos da classe Veiculo
    public String retornaPlaca(){
        return this.placa;
    }

    public String retornaTipo(){
        return this.tipo;
    }

    public LocalDateTime retornaHoraEntrada(){
        return this.horaEntrada;
    }
    public void receberHoraSaida(LocalDateTime horaSaida){
        this.horaSaida = horaSaida;
    }

    public void receberHoraEntrada(LocalDateTime horaEntrada){
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime retornaHoraSaida(){
        return this.horaSaida;
    }

    //Metodo pra calcular o tempo de permanecia do veiculo
    public long retornaPermanencia(){
        Duration duracao = Duration.between(this.horaEntrada, this.horaSaida);
        this.permanencia = duracao.toMinutes();
        return this.permanencia;
    }
}
