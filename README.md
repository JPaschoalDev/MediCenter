# MediCenter - Website Institucional para Clínica Médica

![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![Flexbox](https://img.shields.io/badge/Flexbox-38B2AC?style=for-the-badge&logo=css3&logoColor=white)
![Licença](https://img.shields.io/badge/Licença-MIT-blue?style=for-the-badge)
![SPRINGBOOT](https://img.shields.io/badge/springboot-E34F26?style=for-the-badge&logo=springboot&logoColor=white)

O **MediCenter** é uma landing page moderna, responsiva e otimizada, desenvolvida para clínicas médicas e centros de saúde. O projeto foca em usabilidade, semântica e acessibilidade, permitindo aos usuários consultar serviços, corpo médico, horários de funcionamento e notícias de forma rápida e intuitiva.

---

## Sumário

- [Visão Geral](#-visão-geral)
- [Funcionalidades e Seções](#-funcionalidades-e-seções)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Detalhamento do Código e Arquitetura](#-detalhamento-do-código-e-arquitetura)
- [Responsividade](#-responsividade)
- [Autor](#-autor)
- [Licença](#-licença)

---

## Visão Geral

O projeto foi desenvolvido para demonstrar a criação de uma interface web completa e profissional sem o uso de frameworks pesados. A estrutura é baseada em **HTML5 semântico** para melhor SEO e acessibilidade, enquanto a estilização utiliza **CSS3 puro** com **Flexbox** para alinhamentos flexíveis.

---

## Funcionalidades e Seções

- **Topo Informativo (Top Header)**: Exibição de contatos rápidos e números de emergência.
- **Navegação Principal (Navbar)**: Menu de links internos para fácil navegação entre seções.
- **Banner Principal (Hero Section)**: Destaque institucional com botão de ação (CTA) para agendamento.
- **Widgets de Acesso Rápido**:
  -  **Casos de Emergência**: Informações e contatos diretos de urgência.
  -  **Corpo Médico**: Apresentação da equipe e plantões.
  -  **Horário de Funcionamento**: Tabela explicativa de atendimento semanal.
- **Seção de Departamentos**: Apresentação visual das especialidades (Cardiologia, Pediatria, Odontologia, etc.).
- **Notícias e Artigos**: Feed de notícias de saúde com imagem, data, autor e prévia do artigo.
- **Rodapé (Footer)**: Links de navegação secundários, localização, formulário de newsletter e direitos autorais.

---

## Tecnologias Utilizadas

| Tecnologia | Descrição |
| :--- | :--- |
| **HTML5** | Estruturação semântica do documento (`<header>`, `<nav>`, `<section>`, `<article>`, `<footer>`). |
| **CSS3** | Estilização, padronização de cores, resets globais e tipografia. |
| **Flexbox** | Alinhamento e distribuição responsiva dos elementos da interface. |
| **Media Queries** | Ajuste do layout para diferentes resoluções e dispositivos. |

---

## Estrutura do Projeto

```text
MediCenter/
├── assets/
│   ├── css/
│   │   ├── style.css          # Estilos principais da aplicação
│   │   └── mediaqueries.css   # Regras de responsividade
│   └── images/                # Banners, ícones e imagens do site
├── index.html                 # Página principal
└── README.md                  # Documentação do projeto
```

---

## ⚙Como Executar o Projeto

### Pré-requisitos
Necessário apenas um navegador web moderno (Google Chrome, Mozilla Firefox, Microsoft Edge ou Safari).

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/JPaschoalDev/MediCenter.git](https://github.com/JPaschoalDev/MediCenter.git)
   ```

2. **Acesse a pasta do projeto:**
   ```bash
   cd MediCenter
   ```

3. **Abra o projeto:**
  - Dê um duplo clique no arquivo `index.html` para abrir direto no navegador.
  - Ou utilize a extensão **Live Server** no VS Code.

---

## Detalhamento do Código e Arquitetura

1. **HTML5 Semântico**: Tags escolhidas para estruturar logicamente o conteúdo, melhorando a acessibilidade e a indexação em motores de busca.
2. **CSS Modular e Flexbox**: Layout construído do zero utilizando contêineres flexíveis (`display: flex`, `flex-wrap`, `justify-content`), garantindo fluidez sem frameworks externos.
3. **Design System Simples**: Utilização de paleta de cores voltada à área de saúde (tons de azul, cinza e branco).

---

## Responsividade

O projeto se adapta automaticamente a diferentes tamanhos de tela:
- **Desktop (> 1024px)**: Layout completo em múltiplas colunas.
- **Tablets (768px - 1024px)**: Reorganização de widgets e grids para 2 colunas.
- **Smartphones (< 768px)**: Disposição em coluna única para navegação confortável em telas portáteis.

---

## Autor

Desenvolvido por **João Paschoal** e **Mateus Batista**

- GitHub: [@JPaschoalDev](https://github.com/JPaschoalDev) & [@mateusbsantos70]([https://github.com/JPaschoalDev](https://github.com/mateusbsantos70))
- Repositório: [MediCenter](https://github.com/JPaschoalDev/MediCenter)

---

## Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.