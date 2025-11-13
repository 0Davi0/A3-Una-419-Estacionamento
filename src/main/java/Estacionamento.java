import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Estacionamento {
    //Atributos de utilitarios
    private final Scanner sc = new Scanner(System.in);
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    //Atributos da classe
    private final int vagas = 2; //Criei uma Variavel para caso queiro aumentar o numero de vagas posteriormente
    private int vagasOcupadas = 0;
    private final ArrayList<Veiculo> listaVeiculos = new ArrayList<>();
    private final ArrayList<Veiculo> fila = new ArrayList<>();
    private double totalArrecadado = 0.00;

    //Metodos da classe Estacionamento
    public void registraEntrada(){
        System.out.println();
        //Adquire as informaços necessarrias para o registro caso haja vagas
        if(vagasOcupadas < vagas){
            System.out.println("Informe os dados do veiculo");

            //Adquire a placa
            String placa;

                System.out.print("Placa: ");
                placa = sc.nextLine().toLowerCase();

                //Verifica se a placa digitada ja esta registrado, caso sim, encerra o metodo
                for (int i = 0; i < listaVeiculos.size(); i++){
                    if(placa.equals(listaVeiculos.get(i).retornaPlaca())){
                        System.out.println();
                        System.out.println("Veiculo ja registrado!!");
                        return;
                    }
                }

            //Adquire o tipo de veiculo, limitando a escolha a somente Carro e moto
            String tipo;
            int escolhaTipo;

            do {
                //garante que o usuario ira escolher o tipo certo de veiculo
                System.out.println("Tipo de veiculos aceitos");
                System.out.println("1 - Carro");
                System.out.println("2 - Moto");
                System.out.print("Escolha o tipo de veículo: ");
                escolhaTipo = sc.nextInt();
                sc.nextLine(); //Limpa o espaco deixado pelo scanner acima

                if (escolhaTipo != 1 && escolhaTipo != 2) {
                    System.out.println("Escolha inválida, tente novamente.");
                }
            } while (escolhaTipo != 1 && escolhaTipo != 2);

            tipo = (escolhaTipo == 1) ? "carro" : "moto"; //Utilizei um operador ternário para simplificar

            //Adquire a hora para registrar na entrada
            LocalTime horaRegistrada = null;
            char escolhaHora;

            do {
                System.out.println("Deseja informar a hora de entrada? [S/N]");
                System.out.print("OBS: Caso não informe, iremos registrar a hora atual: ");
                escolhaHora = Character.toLowerCase(sc.next().charAt(0)); //Pega apenas a primeira letra digita e deixa em minusculo
                sc.nextLine(); // consome o Enter deixado pelo next()
                if (escolhaHora != 's' && escolhaHora != 'n') {
                    System.out.println("Opção invalida");
                }
            } while (escolhaHora != 's' && escolhaHora != 'n');

            if (escolhaHora == 's') {
                boolean horaValida = false;
                do {
                    //Valida se o usuario esta digitando a hora no formato correto
                    try {
                        System.out.print("Digite a hora no formato HH:mm: ");
                        horaRegistrada = LocalTime.parse(sc.nextLine(), formatoHora);
                        horaValida = true;
                    } catch (Exception e) {
                        System.out.println("Formato inválido! Utilize HH:mm");
                    }
                } while (!horaValida);
            } else {
                horaRegistrada = LocalTime.now();
            }

            //Cria o objeto "veiculo" e o guarda dentro do arrey listaVeiculos
            Veiculo veiculo = new Veiculo(placa, tipo, horaRegistrada, null, 0);
            listaVeiculos.add(veiculo);
            vagasOcupadas += 1;

            System.out.println("-----------------------------------");
            System.out.println("Veículo registrado com sucesso.");
            System.out.println("Placa: " + veiculo.retornaPlaca());
            System.out.println("Tipo: " + veiculo.retornaTipo());
            System.out.println("Entrada: " + veiculo.retornaHoraEntrada().format(formatoHora));

        }else{
            //Casos todas as vagas estejam ocupadas ao tentar registrar um veiculo
            char resposta;
            do {
                System.out.print("Todas as vagas estao ocupadas, gostaria de esperar na fila? [S/N]: ");
                resposta = Character.toLowerCase(sc.next().charAt(0));
                if (resposta != 's' && resposta != 'n'){
                    System.out.println("Opção invalida");
                }
            }while (resposta != 's' && resposta != 'n');
            if(resposta == 's'){
                controleFila();
            }
        }
    }

    public void registraSaida(){
        System.out.println();
        System.out.print("Informe a placa do Veiculo que esta saindo: ");
        String placaInfo = sc.nextLine();

        //Verificar se o veiculo com a placa digitada esta no estacionamento
        boolean encontrado = false; //Variavel utilizada para caso a placa nao seja encontrada, enviar um alerta informando
        for (int i = 0; i < listaVeiculos.size(); i++){
            // se encontrar a placa, realiza o registro de saida
            if (placaInfo.equals(listaVeiculos.get(i).retornaPlaca())){
                //Valida se o usuario quer digitar a hora de saida ou nao, depois envia a hora para que seja calculado a permanencia
                char escolhaHora;

                do{
                    System.out.println("Deseja informar a hora da saida? [S/N]");
                    System.out.print("OBS: Caso não informe, iremos registrar a hora atual: ");
                    escolhaHora = Character.toLowerCase(sc.next().charAt(0)); //Pega apenas a primeira letra digita e deixa em minusculo
                    sc.nextLine(); //Limpa o espaco deixado pelo scanner acima
                    if (escolhaHora != 's' && escolhaHora != 'n'){
                        System.out.println("Opção invalida");
                    }
                }while(escolhaHora != 's' && escolhaHora != 'n');

                if (escolhaHora == 's'){
                    boolean horaValida = false;
                    do{
                        //Valida se o usuario esta digitando a hora no formato correto
                        try{
                            System.out.print("Digite a hora no formato HH:mm: ");
                            listaVeiculos.get(i).receberHoraSaida(LocalTime.parse(sc.nextLine(), formatoHora));
                            horaValida = true;
                        } catch (Exception e){
                            System.out.println("Formato inválido! Utilize HH:mm");
                        }
                    }while(!horaValida);
                }else{
                    listaVeiculos.get(i).receberHoraSaida(LocalTime.now());//Caso o usuario não queira digitar, pega a hora atual
                }
                double permanencia = listaVeiculos.get(i).retornaPermanencia();

                //Calcula o valor a pagar
                double valorPagar, valorInicial, valorAdicional, calculoValor;

                //Diferencia os valores dependendo do tipo de veiculo
                if ("carro".equals(listaVeiculos.get(i).retornaTipo())){
                    valorInicial = 12.00;
                    valorAdicional = 8.00;
                }else{
                    valorInicial = 8.00;
                    valorAdicional = 5.00;
                }
                calculoValor = valorInicial;

                if (permanencia > 60) {
                    permanencia -= 60;
                    while (permanencia >0){
                        calculoValor += valorAdicional;
                        permanencia -= 60;
                    }
                }

                valorPagar = calculoValor;

                System.out.println();
                System.out.printf("Valor a pagar: R$%.2f%n", valorPagar);
                listaVeiculos.remove(i);
                vagasOcupadas -= 1;
                if(!fila.isEmpty()){
                    fila.get(0).receberHoraEntrada(LocalTime.now());
                    listaVeiculos.add(fila.get(0));
                    System.out.println("-----------------------------------");
                    System.out.println("Veículo da fila entrou no estacionamento.");
                    System.out.println("Placa: " + fila.get(0).retornaPlaca());
                    System.out.println("Tipo: " + fila.get(0).retornaTipo());
                    System.out.println("Entrada: " + fila.get(0).retornaHoraEntrada().format(formatoHora));
                    fila.remove(0);
                    vagasOcupadas += 1;

                }
                totalArrecadado += valorPagar;
                encontrado = true;
                break;

            }
        }
        //Se nao encontrar a placa, apenas informa que nao encontou o veiculo
        if (!encontrado){
            System.out.println("Veiculo não encontrado");
        }
    }

    public void controleFila(){
        //Reutilizei uma parte do codigo do metodo registraEntrada
        System.out.println("Informe os dados do veiculo");

        //Adquire a placa
        String placa;

        System.out.print("Placa: ");
        sc.nextLine(); // consome o Enter deixado pelo next()
        placa = sc.nextLine().toLowerCase();

        //Verifica se a placa digitada ja esta estacionado, caso sim, encerra o metodo
        for (int i = 0; i < listaVeiculos.size(); i++){
            if(placa.equals(listaVeiculos.get(i).retornaPlaca())){
                System.out.println();
                System.out.println("Veiculo ja esta no estacionamento!!");
                return;
            }
        }

        //Verifica se a placa digitada ja esta na fila, caso sim, encerra o metodo
        for (int i = 0; i < fila.size(); i++){
            if(placa.equals(fila.get(i).retornaPlaca())){
                System.out.println();
                System.out.println("Veiculo ja esta na fila!!");
                return;
            }
        }

        //Adquire o tipo de veiculo, limitando a escolha a somente Carro e moto
        String tipo;
        int escolhaTipo;

        do {
            //garante que o usuario ira escolher o tipo certo de veiculo
            System.out.println("Tipo de veiculos aceitos");
            System.out.println("1 - Carro");
            System.out.println("2 - Moto");
            System.out.print("Escolha o tipo de veículo: ");
            escolhaTipo = sc.nextInt();
            sc.nextLine(); //Limpa o espaco deixado pelo scanner acima

            if (escolhaTipo != 1 && escolhaTipo != 2) {
                System.out.println("Escolha inválida, tente novamente.");
            }
        } while (escolhaTipo != 1 && escolhaTipo != 2);

        tipo = (escolhaTipo == 1) ? "carro" : "moto";

        //Cria o objeto "veiculo" e o guarda dentro do arrey fila
        Veiculo veiculo = new Veiculo(placa, tipo, null, null, 0);
        fila.add(veiculo);

        System.out.println("-----------------------------------");
        System.out.println("Veículo ingressado na fila.");
        System.out.println("Placa: " + veiculo.retornaPlaca());
        System.out.println("Tipo: " + veiculo.retornaTipo());

    }

    public void pesquisaVeiculo(){
        boolean encontrado = false;

        System.out.println();
        System.out.print("Digite a placa do veiculo: ");
        String veiculoProcurado = sc.nextLine().toLowerCase();

        for (int i = 0; i < listaVeiculos.size(); i++){
            if (veiculoProcurado.equals(listaVeiculos.get(i).retornaPlaca())){
                encontrado = true;
                System.out.println("-----------------------------------");
                System.out.println("Veiculo encontrado:");
                System.out.println("Placa: " + listaVeiculos.get(i).retornaPlaca());
                System.out.println("Tipo: " + listaVeiculos.get(i).retornaTipo());
                System.out.println("Entrada: " + listaVeiculos.get(i).retornaHoraEntrada().format(formatoHora));
                System.out.println("-----------------------------------");
            }
        }

        //Se o veiculo não for encontrado
        if (!encontrado){
            System.out.println("Veiculo não encontrado!");
        }
    }

    public double relatorioFaturamento(){
        System.out.println();
        return this.totalArrecadado;
    }

    public void relatorioVagas(){
        System.out.println();
        System.out.println("Total de vagas: " + vagas);
        System.out.println("Vagas disponiveis: " + (vagas - vagasOcupadas));
        System.out.println("Vagas ocupadas: " + vagasOcupadas);
    }

    public void relatorioVeiculos(){
        System.out.println();
        System.out.println("=====Veiculos no estacionamento=====");
        for (int i = 0; i < listaVeiculos.size(); i++){
            System.out.println((i + 1) + "° - " +"Placa: " + listaVeiculos.get(i).retornaPlaca());
        }
    }

}
