import java.time.LocalTime;
import java.time.Duration;

public class Veiculo {
    //Atributos
    private final String placa, tipo;
    private final LocalTime horaEntrada;
    private LocalTime horaSaida;
    private long permanencia;

    //Metodo contrutor
    public Veiculo(String placa, String tipo, LocalTime horaEntrada, LocalTime horaSaida, long permanencia){
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

    public LocalTime retornaHoraEntrada(){
        return this.horaEntrada;
    }
    public void receberHoraSaida(LocalTime horaSaida){
        this.horaSaida = horaSaida;
    }

    public LocalTime retornaHoraSaida(){
        return this.horaSaida;
    }

    //Metodo pra calcular o tempo de permanecia do veiculo
    public long retornaPermanencia(){
        Duration duracao = Duration.between(this.horaEntrada, this.horaSaida);
        this.permanencia = duracao.toMinutes();
        return this.permanencia;
    }
}
