// Endereço do back-end (mude aqui se a porta ou o servidor forem outros)
const API_URL = "http://localhost:8080";

/**
 * Liga um formulário + tabela a um recurso da API.
 * config = { recurso: "pacientes", nome: "Paciente", colunas: [{ chave, titulo, tipo? }] }
 */
function iniciarCrud(config) {
    const { recurso, nome, colunas } = config;
    const url = `${API_URL}/api/${recurso}`;

    const form = document.getElementById("form");
    const tbody = document.getElementById("lista");
    const aviso = document.getElementById("aviso");
    const btnSalvar = document.getElementById("btnSalvar");
    const btnCancelar = document.getElementById("btnCancelar");

    let registros = [];
    let editandoId = null;

    /* ---------- utilidades ---------- */

    function mostrarAviso(texto, tipo) {
        aviso.textContent = texto;
        aviso.className = "aviso " + tipo; // "ok" ou "erro"
        aviso.hidden = false;
    }

    function limparErros() {
        aviso.hidden = true;
        form.querySelectorAll(".erro-campo").forEach(function (s) { s.textContent = ""; });
        form.querySelectorAll(".invalido").forEach(function (i) { i.classList.remove("invalido"); });
    }

    function mostrarErrosDosCampos(campos) {
        Object.keys(campos).forEach(function (campo) {
            const span = form.querySelector('[data-erro="' + campo + '"]');
            const input = form.elements[campo];
            if (span) span.textContent = campos[campo];
            if (input) input.classList.add("invalido");
        });
    }

    function formatar(valor, tipo) {
        if (valor === null || valor === undefined || valor === "") return "—";
        if (tipo === "data" && /^\d{4}-\d{2}-\d{2}/.test(valor)) {
            const p = valor.substring(0, 10).split("-");
            return p[2] + "/" + p[1] + "/" + p[0];
        }
        return valor;
    }

    // Faz a chamada e devolve o JSON; em caso de erro, lança o corpo do erro da API
    async function requisitar(endereco, opcoes) {
        let resposta;
        try {
            resposta = await fetch(endereco, opcoes);
        } catch (e) {
            throw { mensagem: "Não foi possível conectar ao servidor. Verifique se o back-end está rodando em " + API_URL };
        }
        if (resposta.status === 204) return null;

        let corpo = null;
        try { corpo = await resposta.json(); } catch (e) { /* sem corpo */ }

        if (!resposta.ok) throw corpo || { mensagem: "Erro " + resposta.status };
        return corpo;
    }

    /* ---------- ler o formulário e montar o JSON ---------- */

    function coletarDados() {
        const dados = {};
        new FormData(form).forEach(function (valor, chave) {
            valor = valor.trim();
            // CPF e telefone: só números, como o contrato da API pede
            if (chave === "cpf" || chave === "telefone") valor = valor.replace(/\D/g, "");
            // Campos opcionais vazios não são enviados
            if (valor !== "") dados[chave] = valor;
        });
        return dados;
    }

    /* ---------- tabela ---------- */

    function desenharTabela() {
        tbody.innerHTML = "";

        if (registros.length === 0) {
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = colunas.length + 1;
            td.className = "vazio";
            td.textContent = "Nenhum registro cadastrado ainda.";
            tr.appendChild(td);
            tbody.appendChild(tr);
            return;
        }

        registros.forEach(function (item) {
            const tr = document.createElement("tr");

            colunas.forEach(function (col) {
                const td = document.createElement("td");
                td.textContent = formatar(item[col.chave], col.tipo);
                tr.appendChild(td);
            });

            const tdAcoes = document.createElement("td");
            tdAcoes.className = "acoes";

            const btnEditar = document.createElement("button");
            btnEditar.type = "button";
            btnEditar.className = "btn-tabela editar";
            btnEditar.textContent = "Editar";
            btnEditar.addEventListener("click", function () { editar(item); });

            const btnExcluir = document.createElement("button");
            btnExcluir.type = "button";
            btnExcluir.className = "btn-tabela excluir";
            btnExcluir.textContent = "Excluir";
            btnExcluir.addEventListener("click", function () { excluir(item); });

            tdAcoes.appendChild(btnEditar);
            tdAcoes.appendChild(btnExcluir);
            tr.appendChild(tdAcoes);
            tbody.appendChild(tr);
        });
    }

    /* ---------- ações com a API ---------- */

    async function listar() {
        try {
            registros = await requisitar(url);
            desenharTabela();
        } catch (erro) {
            mostrarAviso(erro.mensagem, "erro");
        }
    }

    async function salvar(evento) {
        evento.preventDefault();
        limparErros();

        const dados = coletarDados();
        const editando = editandoId !== null;

        try {
            await requisitar(editando ? url + "/" + editandoId : url, {
                method: editando ? "PUT" : "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(dados)
            });

            mostrarAviso(nome + (editando ? " atualizado" : " cadastrado") + " com sucesso!", "ok");
            sairDoModoEdicao();
            listar();
        } catch (erro) {
            mostrarAviso(erro.mensagem || "Erro ao salvar", "erro");
            if (erro.campos) mostrarErrosDosCampos(erro.campos);
        }
    }

    async function excluir(item) {
        if (!confirm("Excluir " + item.nome + "?")) return;
        limparErros();

        try {
            await requisitar(url + "/" + item.id, { method: "DELETE" });
            mostrarAviso(nome + " excluído.", "ok");
            if (editandoId === item.id) sairDoModoEdicao();
            listar();
        } catch (erro) {
            mostrarAviso(erro.mensagem || "Erro ao excluir", "erro");
        }
    }

    /* ---------- edição ---------- */

    function editar(item) {
        limparErros();
        editandoId = item.id;

        Array.prototype.forEach.call(form.elements, function (campo) {
            if (campo.name && item[campo.name] !== undefined && item[campo.name] !== null) {
                campo.value = item[campo.name];
            }
        });

        btnSalvar.textContent = "Salvar alterações";
        btnCancelar.hidden = false;
        window.scrollTo({ top: 0, behavior: "smooth" });
    }

    function sairDoModoEdicao() {
        editandoId = null;
        form.reset();
        btnSalvar.textContent = "Cadastrar";
        btnCancelar.hidden = true;
    }

    btnCancelar.addEventListener("click", function () {
        limparErros();
        sairDoModoEdicao();
    });

    form.addEventListener("submit", salvar);
    listar();
}