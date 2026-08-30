<div align="center">

# 🔎 Busca Padrão Web — Números de Cartas Pokémon

**Trabalho M1 · Linguagens Formais e Autômatos · UNIVALI**

Aplicação de **linguagens regulares** e **Autômatos Finitos Determinísticos (AFD)** na recuperação de informações na Web.

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![AFD](https://img.shields.io/badge/AFD-Determinístico-2563EB?style=for-the-badge)
![Regex](https://img.shields.io/badge/Regex-Linguagem%20Regular-16A34A?style=for-the-badge)
![License](https://img.shields.io/badge/Licença-Acadêmica-6B7280?style=for-the-badge)

---

### 🎯 O que o projeto faz?

O programa captura o **código-fonte HTML** de páginas Web sobre cartas Pokémon e, por meio de um **AFD**, identifica e exibe os números de carta no formato `número/total da coleção`.
```
Exemplo de saída:
  PAGINA ANALISADA: https://www.pkmncards.com/
   -> 025/165
   -> 151/165
   -> 001/198
  Total de itens encontrados: 3
```

</div>

---

## ✨ Funcionalidades

- 🌐 Captura o código-fonte de páginas Web com a classe `CapturaRecursosWeb` (API do professor)
- 🔍 Reconhece números de cartas Pokémon em **3 formatos válidos**
- ❌ Rejeita formatos inválidos automaticamente
- 🖥️ Exibe todos os itens encontrados no console, com página de origem e total

## 📐 Formatos reconhecidos

| Formato | Exemplo | Status |
| :---: | :---: | :---: |
| `dd/dd` | `12/34` | ✅ Aceito |
| `ddd/dd` | `025/16` | ✅ Aceito |
| `ddd/ddd` | `025/165` | ✅ Aceito |
| `dd/ddd` | `12/345` | ❌ Rejeitado |

---

## 🧠 Fundamentação Teórica

O padrão de numeração forma uma **linguagem regular**, representada de três formas equivalentes:

| Representação | Formalização |
| :--- | :--- |
| **Gramática Regular** | `G = (V, T, P, S)` com produções lineares à direita |
| **Expressão Regular** | `[0-9]{3}\/[0-9]{2,3} \| [0-9]{2}\/[0-9]{2}` |
| **AFD** | `M = (Q, Σ, δ, q0, F)` — 11 estados, 3 finais |

### 🤖 O Autômato Finito Determinístico

- **Estados:** `q0` a `q10`
- **Alfabeto:** `{0, 1, ..., 9, /}`
- **Estado inicial:** `q0`
- **Estados finais:**
  - `q5` → aceita `dd/dd`
  - `q9` → aceita `ddd/dd`
  - `q10` → aceita `ddd/ddd`

> 💡 O não-determinismo da gramática (`A → dB | dD`) é resolvido no estado `q2`, onde a leitura do próximo símbolo decide deterministicamente entre o ramo de 2 ou 3 dígitos.

---

## 📁 Estrutura do Projeto
```
projeto/
├── src/
│   ├── buscapadraoweb/
│   │   └── Main.java                # Lógica do AFD + busca nas páginas
│   └── buscaweb/
│       └── CapturaRecursosWeb.java  # API do professor (captura de páginas)
└── README.md
```

---

## 🚀 Como Executar

### Pré-requisitos

- ☕ JDK 17 ou superior
- 🖥️ VS Code com a extensão **"Extension Pack for Java"** (ou qualquer IDE Java)

### Passo a passo

1. Clone o repositório e abra a pasta no VS Code
2. Confirme que `CapturaRecursosWeb.java` está no pacote `buscaweb`
3. Abra `Main.java` e clique em **Run** (▶) ou pressione `Ctrl+F5`
4. Veja os resultados no terminal integrado

---

## 🌐 Páginas Analisadas

| # | Página | URL |
| :-: | :--- | :--- |
| 1 | **PkmnCards** | `https://www.pkmncards.com/` |
| 2 | **Pokémon Official Database** | `https://www.pokemon.com/us/pokemon-tcg/pokemon-cards` |
| 3 | **Pokémon TCG API** | `https://pokemontcg.io/` |

> ✏️ As URLs podem ser editadas diretamente no `Main.java`.

---

## 🛠️ Tecnologias

- ☕ **Java**
- 🤖 **Autômatos Finitos Determinísticos (AFD)**
- 🔤 **Linguagens Regulares**
- 🌐 **Web scraping** (captura de código-fonte HTML)

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
