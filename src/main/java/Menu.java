import java.util.Scanner;

public class Menu {
    //Atributos
    private final Estacionamento estacionamento = new Estacionamento();


    //Metodos da classe Menu
    public void mostraMenu(){
        Scanner sc = new Scanner(System.in);

        int escolha = 0;

        do{
            //Informa as opcoes para o usuario
            System.out.println();
            System.out.println("=====Entrada do Estacionamento=====");
            System.out.println("1 - Registrar entrada de veiculo");
            System.out.println("2 - Registrar saida de veiculo");
            System.out.println("3 - Relatorio de vagas");
            System.out.println("4 - Relatorio de veiculos");
            System.out.println("5 - Pesquisar veiculo");
            System.out.println("6 - Relatorio de faturamento");
            System.out.println("7 - Encerrar");
            System.out.print("Escolha a opção desejada: ");
            //Valida se o que foi digitado e do tipo int
            try{
                escolha = sc.nextInt();
            }catch (Exception e){
                System.out.println("Digite apenas o numero da opcao desejada");
                sc.nextLine(); //Limpar o utilmo  Scanner
            }

            //Menu de opcoes
            if(escolha == 1){
                estacionamento.registraEntrada();
            }else if(escolha == 2){
                estacionamento.registraSaida();
            }else if(escolha == 3){
                estacionamento.relatorioVagas();
            }else if(escolha == 4){
                estacionamento.relatorioVeiculos();
            }else if(escolha == 5){
                estacionamento.pesquisaVeiculo();
            }else if(escolha == 6){
                System.out.printf("Total arrecadado: R$%.2f%n", estacionamento.relatorioFaturamento());
            }else if(escolha == 7){
                System.out.println("Encerrando...");
            }else{
                System.out.println("Escolha invalida");
            }
        }while(escolha != 7);
        sc.close();
    }
}
