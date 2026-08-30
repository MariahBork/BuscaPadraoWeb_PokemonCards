 ArrayList&lt;String&gt; listaCodigos = crw.carregarRecursos();

 // Alfabeto composto por digitos decimais (0 a 9) e a barra &#39;/&#39;
 char[] alfabeto = new char[11];
 alfabeto[0] = &#39;0&#39;;
 alfabeto[1] = &#39;1&#39;;
 alfabeto[2] = &#39;2&#39;;
 alfabeto[3] = &#39;3&#39;;
 alfabeto[4] = &#39;4&#39;;
 alfabeto[5] = &#39;5&#39;;
 alfabeto[6] = &#39;6&#39;;
 alfabeto[7] = &#39;7&#39;;
 alfabeto[8] = &#39;8&#39;;
 alfabeto[9] = &#39;9&#39;;
 alfabeto[10] = &#39;/&#39;;

 // Vetor de estados do AFD
 String[] estados = new String[11];
 for (int e = 0; e &lt;= 10; e++) {
     estados[e] = &quot;q&quot; + e;
 }
 String estado_inicial = &quot;q0&quot;;

 // Estados de aceitacao final
 String[] estados_finais = new String[3];
 estados_finais[0] = &quot;q5&quot;;
 estados_finais[1] = &quot;q9&quot;;
 estados_finais[2] = &quot;q10&quot;;

 // Matriz de transicoes [estados x simbolos]
 int[][] matriz = new int[11][11];
 for (int r = 0; r &lt; 11; r++) {
     for (int c = 0; c &lt; 11; c++) {
         matriz[r][c] = -1;
     }
 }

 int barra_idx = get_char_ref(alfabeto, &#39;/&#39;);

 // Transicoes a partir de q0 (primeiro digito)
 for (int d = 0; d &lt;= 9; d++) {
     int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
     matriz[get_string_ref(estados, &quot;q0&quot;)][d_idx] = get_string_ref(estados, &quot;q1&quot;);
 }

 // Transicoes a partir de q1 (segundo digito)
 for (int d = 0; d &lt;= 9; d++) {
     int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
     matriz[get_string_ref(estados, &quot;q1&quot;)][d_idx] = get_string_ref(estados, &quot;q2&quot;);
 }

 // Transicoes a partir de q2: digito leva a q6 (ramo 3 digitos), barra leva a q3 (ramo 2 digitos)
 for (int d = 0; d &lt;= 9; d++) {
     int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
     matriz[get_string_ref(estados, &quot;q2&quot;)][d_idx] = get_string_ref(estados, &quot;q6&quot;);
 }
 matriz[get_string_ref(estados, &quot;q2&quot;)][barra_idx] = get_string_ref(estados, &quot;q3&quot;);

 // Transicoes do ramo de 2 digitos: q3 -&gt; q4 -&gt; q5
 for (int d = 0; d &lt;= 9; d++) {
     int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
     matriz[get_string_ref(estados, &quot;q3&quot;)][d_idx] = get_string_ref(estados, &quot;q4&quot;);
     matriz[get_string_ref(estados, &quot;q4&quot;)][d_idx] = get_string_ref(estados, &quot;q5&quot;);
 }

 // Transicoes do ramo de 3 digitos: q6 -&gt; q7 (barra), q7 -&gt; q8 -&gt; q9 -&gt; q10 (digitos)
 matriz[get_string_ref(estados, &quot;q6&quot;)][barra_idx] = get_string_ref(estados, &quot;q7&quot;);
 for (int d = 0; d &lt;= 9; d++) {
     int d_idx = get_char_ref(alfabeto, Character.forDigit(d, 10));
     matriz[get_string_ref(estados, &quot;q7&quot;)][d_idx] = get_string_ref(estados, &quot;q8&quot;);
     matriz[get_string_ref(estados, &quot;q8&quot;)][d_idx] = get_string_ref(estados, &quot;q9&quot;);
     matriz[get_string_ref(estados, &quot;q9&quot;)][d_idx] = get_string_ref(estados, &quot;q10&quot;);
 }

 // Processamento de cada pagina capturada
 for (int pIdx = 0; pIdx &lt; listaCodigos.size(); pIdx++) {
     String codigoHTML = listaCodigos.get(pIdx);
     String urlOrigem = crw.getListaRecursos().get(pIdx);

     System.out.println(&quot;&quot;);
     System.out.println(&quot;PAGINA ANALISADA: &quot; + urlOrigem);
     System.out.println(&quot;&quot;);

     int estado = get_string_ref(estados, estado_inicial);
     int estado_anterior = -1;
     ArrayList&lt;String&gt; palavras_reconhecidas = new ArrayList&lt;String&gt;();
     String palavra = &quot;&quot;;

     for (int i = 0; i &lt; codigoHTML.length(); i++) {
         estado_anterior = estado;
         estado = proximo_estado(alfabeto, matriz, estado, codigoHTML.charAt(i));

         if (estado == -1) {
             estado = get_string_ref(estados, estado_inicial);
             if (estado_anterior != -1 &amp;&amp; get_string_ref(estados_finais, estados[estado_anterior]) != -1) {
                 if (!palavra.equals(&quot;&quot;)) {
                     palavras_reconhecidas.add(palavra);
                 }
                 // Reavalia o caractere sob o estado inicial
                 i--;
             }
             palavra = &quot;&quot;;
         } else {
             palavra += codigoHTML.charAt(i);
         }
     }

     // Exibicao dos resultados obtidos
     System.out.println(&quot;Total de itens encontrados: &quot; + palavras_reconhecidas.size());
     for (String item : palavras_reconhecidas) {
         System.out.println(&quot; -&gt; &quot; + item);
     }
     System.out.println();
 }
