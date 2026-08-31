package buscapadraoweb;

import buscaweb.CapturaRecursosWeb;
import java.util.ArrayList;

/**
 * Universidade do Vale do Itajai - UNIVALI
 * Disciplina: Linguagens Formais e Automatos
 * Professor: Alex Luciano Roesler Rese, MSc.
 *
 * Trabalho M1: Busca Padrao Web com AFD
 * Tema: Numeros de Identificacao de Cartas Pokemon (dd/dd, ddd/dd, ddd/ddd)
 * Academicos: Gabriel Alexandre dos Santos e Mariah Theodora Gondim Bork
 */
public class Main {

    public static int get_char_ref(char[] vet, char ref) {
        for (int i = 0; i < vet.length; i++) {
            if (vet[i] == ref) {
                return i;
            }
        }
        return -1;
    }

    public static int get_string_ref(String[] vet, String ref) {
        for (int i = 0; i < vet.length; i++) {
            if (vet[i].equals(ref)) {
                return i;
            }
        }
        return -1;
    }

    public static int proximo_estado(char[] alfabeto, int[][] matriz, int estado_atual, char simbolo) {
        int simbol_indice = get_char_ref(alfabeto, simbolo);
        if (simbol_indice != -1 && estado_atual != -1) {
            return matriz[estado_atual][simbol_indice];
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        CapturaRecursosWeb crw = new CapturaRecursosWeb();
        crw.getListaRecursos().add("https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/151_(TCG)");
        crw.getListaRecursos().add("https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/Sword_%26_Shield_(TCG)");
        crw.getListaRecursos().add("https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/Base_Set_(TCG)");

        ArrayList<String> listaCodigos = crw.carregarRecursos();

        // Alfabeto: digitos 0 a 9 e a barra '/'
        char[] alfabeto = new char[11];
        alfabeto[0] = '0';
        alfabeto[1] = '1';
        alfabeto[2] = '2';
        alfabeto[3] = '3';
        alfabeto[4] = '4';
        alfabeto[5] = '5';
        alfabeto[6] = '6';
        alfabeto[7] = '7';
        alfabeto[8] = '8';
        alfabeto[9] = '9';
        alfabeto[10] = '/';

        // Vetor de estados do AFD
        String[] estados = new String[11];
        for (int e = 0; e <= 10; e++) {
            estados[e] = "q" + e;
        }
        String estado_inicial = "q0";

        // Estados de aceitacao final
        String[] estados_finais = new String[3];
        estados_finais[0] = "q5";
        estados_finais[1] = "q9";
        estados_finais[2] = "q10";

        // Matriz de transicoes [estados x simbolos] inicializada com -1
        int[][] matriz = new int[11][11];
        for (int r = 0; r < 11; r++) {
            for (int c = 0; c < 11; c++) {
                matriz[r][c] = -1;
            }
        }

        int barra_idx = get_char_ref(alfabeto, '/');

        // q0: qualquer digito -> q1
        for (int d = 0; d <= 9; d++) {
            int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
            matriz[get_string_ref(estados, "q0")][d_idx] = get_string_ref(estados, "q1");
        }

        // q1: qualquer digito -> q2
        for (int d = 0; d <= 9; d++) {
            int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
            matriz[get_string_ref(estados, "q1")][d_idx] = get_string_ref(estados, "q2");
        }

        // q2: digito -> q6 (ramo 3 digitos); barra -> q3 (ramo 2 digitos)
        for (int d = 0; d <= 9; d++) {
            int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
            matriz[get_string_ref(estados, "q2")][d_idx] = get_string_ref(estados, "q6");
        }
        matriz[get_string_ref(estados, "q2")][barra_idx] = get_string_ref(estados, "q3");

        // Ramo 2 digitos: q3 -> q4 -> q5
        for (int d = 0; d <= 9; d++) {
            int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
            matriz[get_string_ref(estados, "q3")][d_idx] = get_string_ref(estados, "q4");
            matriz[get_string_ref(estados, "q4")][d_idx] = get_string_ref(estados, "q5");
        }

        // Ramo 3 digitos: q6 -> q7 (barra), q7 -> q8 -> q9 -> q10
        matriz[get_string_ref(estados, "q6")][barra_idx] = get_string_ref(estados, "q7");
        for (int d = 0; d <= 9; d++) {
            int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
            matriz[get_string_ref(estados, "q7")][d_idx] = get_string_ref(estados, "q8");
            matriz[get_string_ref(estados, "q8")][d_idx] = get_string_ref(estados, "q9");
            matriz[get_string_ref(estados, "q9")][d_idx] = get_string_ref(estados, "q10");
        }

        // Varre o codigo-fonte de cada pagina Web
        for (int pIdx = 0; pIdx < listaCodigos.size(); pIdx++) {
            String codigoHTML = listaCodigos.get(pIdx);
            String urlOrigem = crw.getListaRecursos().get(pIdx);

            System.out.println("");
            System.out.println("PAGINA ANALISADA: " + urlOrigem);
            System.out.println("");

            int estado = get_string_ref(estados, estado_inicial);
            int estado_anterior = -1;
            ArrayList<String> palavras_reconhecidas = new ArrayList<String>();
            String palavra = "";

            for (int i = 0; i < codigoHTML.length(); i++) {
                estado_anterior = estado;
                estado = proximo_estado(alfabeto, matriz, estado, codigoHTML.charAt(i));

                if (estado == -1) {
                    estado = get_string_ref(estados, estado_inicial);
                    if (estado_anterior != -1 && get_string_ref(estados_finais, estados[estado_anterior]) != -1) {
                        if (!palavra.equals("")) {
                            palavras_reconhecidas.add(palavra);
                        }
                        i--;
                    }
                    palavra = "";
                } else {
                    palavra += codigoHTML.charAt(i);
                }
            }

            System.out.println("Total de itens encontrados: " + palavras_reconhecidas.size());
            for (String item : palavras_reconhecidas) {
                System.out.println(" -> " + item);
            }
            System.out.println();
        }
    }
}
