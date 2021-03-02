package br.com.letscode.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Aplicacao {

    private FileSystemExplorer fileSystemExplorer;

    public static void main(String[] args) {
        Aplicacao aplicacao = new Aplicacao();
        aplicacao.init();
    }

    private void init(){
        this.imprimirCabecalho();
        final var path = this.lerCaminhoParaExplorer();
        this.fileSystemExplorer = new FileSystemExplorer(path);
        //this.apresentarResumo();
        this.buscarArquivosPorExtensao();
    }

    private void imprimirCabecalho(){
        System.out.println("##########################################");
        System.out.println("#### Navegação em sistemas de arquivos ###");
    }

    private Path lerCaminhoParaExplorer(){
        System.out.println("Qual caminho deseja explorar?");
        Scanner scanner = new Scanner(System.in);
        final var caminho = scanner.nextLine();
        return Paths.get(caminho);
    }

    private void apresentarResumo() {
        System.out.println("Possui a seguinte estrutura...");
        fileSystemExplorer.printDirectoryContent();
    }

    private void buscarArquivosPorExtensao() {
        System.out.println("Informe uma extensão para filtrar o conteúdo desse diretório. ");
        Scanner scanner = new Scanner(System.in);
        final var extension = scanner.nextLine();
        fileSystemExplorer.filterFilesWithExtension(extension);
    }

}
