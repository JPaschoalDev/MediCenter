# Contrato da API — Medicenter

Base: `http://localhost:8080`

Todas as respostas são JSON.
- Datas no formato `AAAA-MM-DD` (exemplo: `1990-05-20`)
- CPF só com 11 números, sem pontos e traço

---

## Pacientes — `/api/pacientes`

| Método | URL | Corpo | Resposta |
|---|---|---|---|
| POST | /api/pacientes | PacienteRequest | 201 + paciente |
| GET | /api/pacientes | — | 200 + lista |
| GET | /api/pacientes/{id} | — | 200 + paciente / 404 |
| PUT | /api/pacientes/{id} | PacienteRequest | 200 + paciente |
| DELETE | /api/pacientes/{id} | — | 204 |

**PacienteRequest**

| Campo | Obrigatório | Observação |
|---|---|---|
| nome | sim | até 120 caracteres |
| cpf | sim | 11 números, único |
| dataNascimento | sim | deve ser no passado |
| telefone | não | até 15 caracteres |
| email | não | formato de e-mail válido |
| endereco | não | até 200 caracteres |

---

## Médicos — `/api/medicos`

| Método | URL | Corpo | Resposta |
|---|---|---|---|
| POST | /api/medicos | MedicoRequest | 201 + médico |
| GET | /api/medicos | — | 200 + lista |
| GET | /api/medicos/{id} | — | 200 + médico / 404 |
| PUT | /api/medicos/{id} | MedicoRequest | 200 + médico |
| DELETE | /api/medicos/{id} | — | 204 |

**MedicoRequest**

| Campo | Obrigatório | Observação |
|---|---|---|
| nome | sim | até 120 caracteres |
| cpf | sim | 11 números, único |
| crm | sim | até 20 caracteres, único |
| especialidade | sim | até 80 caracteres |
| telefone | não | até 15 caracteres |
| email | não | formato de e-mail válido |

---

## Funcionários — `/api/funcionarios`

| Método | URL | Corpo | Resposta |
|---|---|---|---|
| POST | /api/funcionarios | FuncionarioRequest | 201 + funcionário |
| GET | /api/funcionarios | — | 200 + lista |
| GET | /api/funcionarios/{id} | — | 200 + funcionário / 404 |
| PUT | /api/funcionarios/{id} | FuncionarioRequest | 200 + funcionário |
| DELETE | /api/funcionarios/{id} | — | 204 |

**FuncionarioRequest**

| Campo | Obrigatório | Observação |
|---|---|---|
| nome | sim | até 120 caracteres |
| cpf | sim | 11 números, único |
| cargo | sim | até 80 caracteres |
| dataAdmissao | sim | não pode ser futura |
| telefone | não | até 15 caracteres |
| email | não | formato de e-mail válido |

---

## Exemplo de resposta (médico)

```json
{
  "id": 1,
  "nome": "Dr. Carlos Almeida",
  "cpf": "98765432100",
  "crm": "CRM-SP 123456",
  "especialidade": "Cardiologia",
  "telefone": "11988887777",
  "email": "carlos@medicenter.com",
  "dataCadastro": "2026-10-06T18:53:43"
}
```

---

## Erros

| Código | Quando acontece | Corpo |
|---|---|---|
| 400 | Campo inválido ou faltando | `{ "status": 400, "mensagem": "Dados inválidos", "campos": { "cpf": "CPF é obrigatório" } }` |
| 404 | Registro não existe | `{ "status": 404, "mensagem": "Paciente não encontrado: 5", "campos": null }` |
| 409 | CPF ou CRM já cadastrado | `{ "status": 409, "mensagem": "Já existe um médico cadastrado com este CRM", "campos": null }` |

No erro 400, o objeto `campos` traz uma mensagem por campo. O frontend pode mostrar cada uma ao lado do campo certo.

---

## Autenticação — /api/auth

Login por sessão (cookie `JSESSIONID`). O navegador envia o cookie sozinho nas
chamadas `fetch` feitas a partir das páginas do próprio projeto.

| Método | URL | Acesso | Corpo | Resposta |
|---|---|---|---|---|
| POST | /api/auth/login | público | `{ "login", "senha" }` | 200 `{ "login", "perfil" }` / 401 |
| POST | /api/auth/logout | logado | — | 200 |
| GET | /api/auth/me | logado | — | 200 `{ "login", "perfil" }` / 401 |
| POST | /api/auth/cadastro | público | login, senha e `paciente` (veja abaixo) | 201 `{ "id", "login", "perfil", "ativo" }` |

Corpo do cadastro (paciente se cadastrando sozinho):

```json
{
  "login": "maria.silva",
  "senha": "senha123",
  "paciente": { "nome": "...", "cpf": "...", "dataNascimento": "1990-05-20",
                "telefone": "...", "email": "...", "endereco": "..." }
}
```

Regras: login de 4 a 60 caracteres (letras, números e `. _ @ -`), senha de 6 a 72.
Nos erros 400, os campos do paciente vêm com prefixo, por exemplo `paciente.cpf`.

## Usuários — /api/usuarios (somente FUNCIONARIO)

| Método | URL | Corpo | Resposta |
|---|---|---|---|
| POST | /api/usuarios | `{ "login", "senha", "perfil", "pessoaId" }` | 201 + usuário |
| GET | /api/usuarios | — | 200 + lista |

`perfil` aceita `MEDICO`, `FUNCIONARIO` ou `PACIENTE`. `pessoaId` é o id do médico,
funcionário ou paciente já cadastrado que vai receber o acesso.

## Quem acessa o quê

| Recurso | FUNCIONARIO | MEDICO | PACIENTE |
|---|---|---|---|
| /api/pacientes | tudo | só GET | — |
| /api/medicos | tudo | só GET | só GET |
| /api/funcionarios | tudo | — | — |
| /api/usuarios | tudo | — | — |

## Códigos de erro novos

- **401**: não está logado, ou login/senha inválidos
- **403**: está logado, mas o perfil não tem permissão

Usuário de teste: login `admin`, perfil FUNCIONARIO (a senha combinada na equipe).