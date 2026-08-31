<div align="center">

# 🔎 Busca Padrão Web — Números de Cartas Pokémon
**Trabalho M1 · Linguagens Formais e Autômatos · UNIVALI**
Aplicação de **linguagens regulares** e **Autômatos Finitos Determinísticos (AFD)** na recuperação de informações na Web.

![Java](https://img.shields.io/badge/Java-8+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![AFD](https://img.shields.io/badge/AFD-Determinístico-2563EB?style=for-the-badge)
![Regex](https://img.shields.io/badge/Regex-Linguagem%20Regular-16A34A?style=for-the-badge)
![License](https://img.shields.io/badge/Licença-Acadêmica-6B7280?style=for-the-badge)

---

### 🎯 O que o projeto faz?
O programa captura o **código-fonte HTML** de páginas Web sobre cartas Pokémon e, por meio de um **AFD**, identifica e exibe os números de carta no formato *número/total da coleção*.
```
Exemplo de saída:
  PAGINA ANALISADA: https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/151_(TCG)
   -> 001/165
   -> 025/165
   -> 151/165
  Total de itens encontrados: 165
```

</div>

---

## ✨ Funcionalidades

- 🌐 Captura o código-fonte de páginas Web com a classe `CapturaRecursosWeb`
- 🔍 Reconhece números de cartas Pokémon em **3 formatos válidos**
- ❌ Rejeita formatos inválidos automaticamente
- 🖥️ Exibe todos os itens encontrados no console, com página de origem e total

## 📐 Formatos reconhecidos

| Formato | Exemplo | Status |
| :---: | :---: | :---: |
| dd/dd | 12/34 | ✅ Aceito |
| ddd/dd | 025/16 | ✅ Aceito |
| ddd/ddd | 025/165 | ✅ Aceito |
| dd/ddd | 12/345 | ❌ Rejeitado |

---

## 🧠 Fundamentação Teórica

O padrão de numeração forma uma **linguagem regular**, representada de três formas equivalentes:

| Representação | Formalização |
| :--- | :--- |
| **Gramática Regular** | G = (V, T, P, S) com produções lineares à direita |
| **Expressão Regular** | `[0-9]{3}/[0-9]{2,3} \| [0-9]{2}/[0-9]{2}` |
| **AFD** | M = (Q, Σ, δ, q₀, F) — 11 estados, 3 finais |

### 🤖 O Autômato Finito Determinístico

- **Estados:** q₀ a q₁₀
- **Alfabeto:** {0, 1, ..., 9, /}
- **Estado inicial:** q₀
- **Estados finais:**
  - q₅ → aceita dd/dd
  - q₉ → aceita ddd/dd
  - q₁₀ → aceita ddd/ddd

> 💡 O não-determinismo da gramática (A → dB | dD) é resolvido no estado q₂, onde a leitura do próximo símbolo decide deterministicamente entre o ramo de 2 ou 3 dígitos.

---

## 📁 Estrutura do Projeto
```
projeto/
├── src/
│   ├── buscapadraoweb/
│   │   └── Main.java                # Lógica do AFD + varredura das páginas
│   └── buscaweb/
│       └── CapturaRecursosWeb.java  # Captura de código-fonte HTML (HTTP/HTTPS)
└── README.md
```

---

## 🚀 Como Executar

### Pré-requisitos
- ☕ JDK 8 ou superior
- 🖥️ VS Code com a extensão **"Extension Pack for Java"** (ou qualquer IDE Java)

### Passo a passo
1. Clone o repositório e abra a pasta no VS Code
2. Confirme que `CapturaRecursosWeb.java` está no pacote `buscaweb`
3. Abra `Main.java` e clique em **Run** (▶) ou pressione `Ctrl+F5`
4. Veja os resultados no terminal integrado

---

## 🌐 Páginas Analisadas

As páginas são obtidas via **Wayback Machine** (Internet Archive), que serve cópias estáticas do HTML da Bulbapedia — garantindo acesso sem bloqueios por Cloudflare ou JavaScript.

| # | Coleção | URL |
| :-: | :--- | :--- |
| 1 | **151 (TCG)** | `https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/151_(TCG)` |
| 2 | **Sword & Shield (TCG)** | `https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/Sword_%26_Shield_(TCG)` |
| 3 | **Base Set (TCG)** | `https://web.archive.org/web/2024/https://bulbapedia.bulbagarden.net/wiki/Base_Set_(TCG)` |

---

## 📊 Resultados da Execução

| Página | Padrões Identificados | Status |
| :--- | :--- | :---: |
| 151 (TCG) | 001/165, 025/165, 151/165, 207/165 | ✅ Conforme |
| Sword & Shield (TCG) | 001/202, 151/202, 165/202 | ✅ Conforme |
| Base Set (TCG) | 100/102, 101/102, 102/102 (rejeita 1/102 a 99/102) | ✅ Conforme |

> 📌 A página Base Set demonstra na prática a **rejeição do formato dd/ddd**: os números 1/102 a 9/102 (um dígito antes da barra) foram descartados, e apenas 100/102 a 102/102 foram reconhecidos no formato ddd/ddd.

---

## 🛠️ Tecnologias

- ☕ **Java** (JDK 8+)
- 🤖 **Autômatos Finitos Determinísticos (AFD)**
- 🔤 **Linguagens Regulares** (Gramática Regular + Expressão Regular)
- 🌐 **Captura de código-fonte HTML** (HTTP/HTTPS com `HttpURLConnection`)

---

## 👥 Autores

| | |
| :--- | :--- |
| **Gabriel Alexandre dos Santos** | 👨‍💻 |
| **Mariah Theodora Gondim Bork** | 👩‍💻 |

---

<div align="center">

### 🎓 Universidade do Vale do Itajaí (UNIVALI)
**Disciplina:** Linguagens Formais e Autômatos  
**Professor:** Alex Luciano Roesler Rese, MSc.

⭐ Se este projeto te ajudou, deixe uma estrelinha!

</div>
