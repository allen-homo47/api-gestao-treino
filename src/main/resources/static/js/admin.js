// Painel de gestão (administrador e treinador)

const sessao = exigirSessao(['ADMINISTRADOR', 'TREINADOR']);

const DIAS_SEMANA = ['Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'];
const TAMANHO_PAGINA = 10;

// Cada secção: endpoint, colunas da tabela e campos do formulário.
// Os nomes dos campos são os mesmos do DTO de pedido do backend.
const SECCOES = {
    atletas: {
        titulo: 'Atletas',
        descricao: 'Registo dos atletas do ginásio.',
        endpoint: '/api/atletas',
        colunas: [
            { campo: 'nome', titulo: 'Nome' },
            { campo: 'email', titulo: 'Email' },
            { campo: 'contacto', titulo: 'Contacto' },
            { campo: 'genero', titulo: 'Género' },
            { campo: 'objectivo', titulo: 'Objectivo' }
        ],
        campos: [
            { nome: 'nome', label: 'Nome', obrigatorio: true, full: true },
            { nome: 'email', label: 'Email', tipo: 'email', obrigatorio: true,
              dica: 'O email é o utilizador de login. A password inicial é 123456.' },
            { nome: 'contacto', label: 'Contacto' },
            { nome: 'pais', label: 'País' },
            { nome: 'cidade', label: 'Cidade' },
            { nome: 'dataNascimento', label: 'Data de nascimento', tipo: 'date' },
            { nome: 'genero', label: 'Género', tipo: 'select',
              opcoes: [{ valor: 'MASCULINO', texto: 'Masculino' }, { valor: 'FEMENINO', texto: 'Feminino' }] },
            { nome: 'altura', label: 'Altura (m)', tipo: 'number', passo: '0.01' },
            { nome: 'peso', label: 'Peso (kg)', tipo: 'number', passo: '0.1' },
            { nome: 'objectivo', label: 'Objectivo', full: true }
        ]
    },
    exercicios: {
        titulo: 'Exercícios',
        descricao: 'Catálogo de exercícios usados nos treinos.',
        endpoint: '/api/exercicios',
        colunas: [
            { campo: 'nomeExercicio', titulo: 'Exercício' },
            { campo: 'grupoMuscular', titulo: 'Grupo muscular' },
            { campo: 'urlVideoDemonstrativo', titulo: 'Vídeo', formato: v => v ? `<a href="${esc(v)}" target="_blank" rel="noopener">Ver vídeo</a>` : '' }
        ],
        campos: [
            { nome: 'nomeExercicio', label: 'Nome do exercício', obrigatorio: true, full: true },
            { nome: 'grupoMuscular', label: 'Grupo muscular', full: true },
            { nome: 'urlVideoDemonstrativo', label: 'URL do vídeo demonstrativo', tipo: 'url', full: true }
        ]
    },
    planos: {
        titulo: 'Planos de treino',
        descricao: 'Cada plano pertence a um atleta e tem várias rotinas.',
        endpoint: '/api/planos',
        colunas: [
            { campo: 'nome', titulo: 'Plano' },
            { campo: 'atletaNome', titulo: 'Atleta' },
            { campo: 'dataInicio', titulo: 'Início', formato: formatarData },
            { campo: 'dataFim', titulo: 'Fim', formato: formatarData }
        ],
        campos: [
            { nome: 'nome', label: 'Nome do plano', obrigatorio: true, full: true },
            { nome: 'atletaId', label: 'Atleta', tipo: 'select', obrigatorio: true, full: true,
              origem: { endpoint: '/api/atletas', texto: a => `${a.nome} (${a.email})` } },
            { nome: 'dataInicio', label: 'Data de início', tipo: 'date' },
            { nome: 'dataFim', label: 'Data de fim', tipo: 'date' }
        ]
    },
    rotinas: {
        titulo: 'Rotinas',
        descricao: 'Os dias de treino de cada plano.',
        endpoint: '/api/rotinas',
        colunas: [
            { campo: 'nome', titulo: 'Rotina' },
            { campo: 'diaSemana', titulo: 'Dia', formato: v => v ? `<span class="badge">${esc(v)}</span>` : '' },
            { campo: 'planoNome', titulo: 'Plano' }
        ],
        campos: [
            { nome: 'nome', label: 'Nome da rotina', obrigatorio: true, full: true, dica: 'Ex.: Treino A — Peito e tríceps' },
            { nome: 'diaSemana', label: 'Dia da semana', tipo: 'select',
              opcoes: DIAS_SEMANA.map(d => ({ valor: d, texto: d })) },
            { nome: 'planoId', label: 'Plano', tipo: 'select', obrigatorio: true,
              origem: { endpoint: '/api/planos', texto: p => p.atletaNome ? `${p.nome} — ${p.atletaNome}` : p.nome } }
        ]
    },
    itens: {
        titulo: 'Itens de treino',
        descricao: 'Os exercícios de cada rotina, com séries, repetições e carga.',
        endpoint: '/api/itens-treino',
        colunas: [
            { campo: 'exercicioNome', titulo: 'Exercício' },
            { campo: 'rotinaNome', titulo: 'Rotina' },
            { campo: 'series', titulo: 'Séries' },
            { campo: 'repeticoes', titulo: 'Repetições' },
            { campo: 'carga', titulo: 'Carga' },
            { campo: 'descanso', titulo: 'Descanso', formato: v => v != null ? `${esc(v)} s` : '' }
        ],
        campos: [
            { nome: 'rotinaId', label: 'Rotina', tipo: 'select', obrigatorio: true, full: true,
              origem: { endpoint: '/api/rotinas', texto: r => r.planoNome ? `${r.nome} (${r.planoNome})` : r.nome } },
            { nome: 'exercicioId', label: 'Exercício', tipo: 'select', obrigatorio: true, full: true,
              origem: { endpoint: '/api/exercicios', texto: e => e.nomeExercicio } },
            { nome: 'series', label: 'Séries', tipo: 'number', passo: '1' },
            { nome: 'repeticoes', label: 'Repetições', dica: 'Ex.: 12 ou 8-12' },
            { nome: 'carga', label: 'Carga', dica: 'Ex.: 20 kg' },
            { nome: 'descanso', label: 'Descanso (segundos)', tipo: 'number', passo: '1' }
        ]
    },
    utilizadores: {
        titulo: 'Utilizadores',
        descricao: 'Contas de acesso (administradores e treinadores). Os atletas são criados em "Atletas".',
        listar: '/usuarios/listaUsuarios',
        criar: '/usuarios/addUsuario',
        apagar: id => `/usuarios/apagar/${id}`,
        semEditar: true,
        apenasAdmin: true,
        colunas: [
            { campo: 'username', titulo: 'Utilizador' },
            { campo: 'tipoUser', titulo: 'Tipo', formato: v => v ? `<span class="badge">${esc(v)}</span>` : '' }
        ],
        campos: [
            { nome: 'nome', label: 'Nome', obrigatorio: true, full: true },
            { nome: 'username', label: 'Utilizador', obrigatorio: true },
            { nome: 'password', label: 'Password', tipo: 'password', obrigatorio: true },
            { nome: 'tipoUser', label: 'Tipo', tipo: 'select', obrigatorio: true, full: true,
              opcoes: [{ valor: 'TREINADOR', texto: 'Treinador' }, { valor: 'ADMINISTRADOR', texto: 'Administrador' }] }
        ]
    }
};

let seccaoActual = 'atletas';
let paginaActual = 0;
let linhas = [];
let emEdicao = null; // registo a ser editado (null = novo)

// ---------- Início ----------

if (sessao) {
    document.getElementById('user-nome').textContent = sessao.sub;
    document.getElementById('user-tipo').textContent = sessao.tipo === 'ADMINISTRADOR' ? 'Administrador' : 'Treinador';
    montarMenu();
    const inicial = location.hash.slice(1);
    abrirSeccao(SECCOES[inicial] && podeVer(inicial) ? inicial : 'atletas');
}

function podeVer(chave) {
    return !SECCOES[chave].apenasAdmin || sessao.tipo === 'ADMINISTRADOR';
}

function montarMenu() {
    const nav = document.getElementById('nav');
    nav.innerHTML = Object.entries(SECCOES)
        .filter(([chave]) => podeVer(chave))
        .map(([chave, s]) => `<button data-seccao="${chave}">${esc(s.titulo)}</button>`)
        .join('');
    nav.addEventListener('click', (e) => {
        const chave = e.target.dataset.seccao;
        if (chave) abrirSeccao(chave);
    });
}

function abrirSeccao(chave) {
    seccaoActual = chave;
    paginaActual = 0;
    location.hash = chave;
    const s = SECCOES[chave];

    document.querySelectorAll('#nav button').forEach(b => b.classList.toggle('active', b.dataset.seccao === chave));
    document.getElementById('titulo').textContent = s.titulo;
    document.getElementById('descricao').textContent = s.descricao;
    document.getElementById('thead').innerHTML =
        '<tr>' + s.colunas.map(c => `<th>${esc(c.titulo)}</th>`).join('') + '<th></th></tr>';

    carregar();
}

// ---------- Listagem ----------

async function carregar() {
    const s = SECCOES[seccaoActual];
    const tbody = document.getElementById('tbody');
    const colspan = s.colunas.length + 1;
    tbody.innerHTML = `<tr><td colspan="${colspan}" class="empty">A carregar...</td></tr>`;

    try {
        let pagina;
        if (s.listar) {
            // Endpoint sem paginação (devolve uma lista)
            const lista = await api(s.listar);
            pagina = { content: lista, number: 0, totalPages: 1, totalElements: lista.length, first: true, last: true };
        } else {
            pagina = await api(`${s.endpoint}?page=${paginaActual}&size=${TAMANHO_PAGINA}&sort=id,desc`);
        }
        linhas = pagina.content;

        if (linhas.length === 0) {
            tbody.innerHTML = `<tr><td colspan="${colspan}" class="empty">Ainda não há registos. Clique em "+ Novo" para criar.</td></tr>`;
        } else {
            tbody.innerHTML = linhas.map((linha, i) => `
                <tr>
                    ${s.colunas.map(c => `<td>${c.formato ? c.formato(linha[c.campo]) : esc(linha[c.campo])}</td>`).join('')}
                    <td class="actions">
                        ${s.semEditar ? '' : `<button class="btn btn-secondary btn-sm" onclick="abrirModal(${i})">Editar</button>`}
                        <button class="btn btn-danger btn-sm" onclick="apagar(${i})">Apagar</button>
                    </td>
                </tr>`).join('');
        }
        desenharPaginacao(pagina);
    } catch (err) {
        tbody.innerHTML = `<tr><td colspan="${colspan}" class="empty">${esc(err.message)}</td></tr>`;
        document.getElementById('paginacao').innerHTML = '';
    }
}

function desenharPaginacao(p) {
    const el = document.getElementById('paginacao');
    if (p.totalPages <= 1) {
        el.innerHTML = `<span>${p.totalElements} registo(s)</span><span></span>`;
        return;
    }
    el.innerHTML = `
        <span>Página ${p.number + 1} de ${p.totalPages} · ${p.totalElements} registo(s)</span>
        <span>
            <button class="btn btn-secondary btn-sm" ${p.first ? 'disabled' : ''} onclick="mudarPagina(-1)">‹ Anterior</button>
            <button class="btn btn-secondary btn-sm" ${p.last ? 'disabled' : ''} onclick="mudarPagina(1)">Seguinte ›</button>
        </span>`;
}

function mudarPagina(delta) {
    paginaActual += delta;
    carregar();
}

async function apagar(indice) {
    const s = SECCOES[seccaoActual];
    const linha = linhas[indice];
    if (!confirm('Tem a certeza que quer apagar este registo?')) return;

    try {
        await api(s.apagar ? s.apagar(linha.id) : `${s.endpoint}/${linha.id}`, { method: 'DELETE' });
        toast('Registo apagado');
        // Se a página ficou vazia, volta uma para trás
        if (linhas.length === 1 && paginaActual > 0) paginaActual--;
        carregar();
    } catch (err) {
        toast(err.message, true);
    }
}

// ---------- Formulário (modal) ----------

document.getElementById('btn-novo').addEventListener('click', () => abrirModal(null));

async function abrirModal(indice) {
    const s = SECCOES[seccaoActual];
    emEdicao = indice === null ? null : linhas[indice];

    document.getElementById('modal-titulo').textContent = (emEdicao ? 'Editar — ' : 'Novo — ') + s.titulo;
    document.getElementById('form-erro').classList.add('hidden');

    // Carrega as opções das listas ligadas a outras tabelas (atletas, planos, ...)
    const opcoesExternas = {};
    try {
        await Promise.all(s.campos.filter(c => c.origem).map(async c => {
            const pagina = await api(`${c.origem.endpoint}?size=1000&sort=id,desc`);
            opcoesExternas[c.nome] = pagina.content.map(item => ({ valor: item.id, texto: c.origem.texto(item) }));
        }));
    } catch (err) {
        toast(err.message, true);
        return;
    }

    document.getElementById('form-campos').innerHTML = s.campos.map(c => desenharCampo(c, opcoesExternas[c.nome])).join('');
    document.getElementById('modal').classList.remove('hidden');
    document.querySelector('#form-campos input, #form-campos select')?.focus();
}

function desenharCampo(c, opcoesExternas) {
    const valor = emEdicao ? emEdicao[c.nome] : '';
    const obrigatorio = c.obrigatorio ? 'required' : '';
    const classe = 'field' + (c.full ? ' full' : '');
    const label = `<label for="f-${c.nome}">${esc(c.label)}${c.obrigatorio ? ' *' : ''}</label>`;
    const dica = c.dica ? `<span class="hint">${esc(c.dica)}</span>` : '';

    if (c.tipo === 'select') {
        const opcoes = opcoesExternas || c.opcoes;
        const html = opcoes.map(o =>
            `<option value="${esc(o.valor)}" ${String(o.valor) === String(valor ?? '') ? 'selected' : ''}>${esc(o.texto)}</option>`
        ).join('');
        const vazio = opcoesExternas && opcoes.length === 0 ? 'Nenhum registo disponível' : '— Seleccione —';
        return `<div class="${classe}">${label}
            <select id="f-${c.nome}" name="${c.nome}" ${obrigatorio}><option value="">${vazio}</option>${html}</select>${dica}</div>`;
    }

    const tipo = c.tipo || 'text';
    const passo = c.passo ? `step="${c.passo}"` : '';
    return `<div class="${classe}">${label}
        <input id="f-${c.nome}" name="${c.nome}" type="${tipo}" ${passo} ${obrigatorio} value="${esc(valor)}">${dica}</div>`;
}

function fecharModal() {
    document.getElementById('modal').classList.add('hidden');
}

document.getElementById('modal').addEventListener('click', (e) => {
    if (e.target.id === 'modal') fecharModal();
});
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') fecharModal();
});

document.getElementById('form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const s = SECCOES[seccaoActual];
    const form = e.target;
    const botao = document.getElementById('btn-guardar');
    const erro = document.getElementById('form-erro');

    // Monta o corpo do pedido com os tipos certos (números, ids, null)
    const corpo = {};
    for (const c of s.campos) {
        const bruto = form.elements[c.nome].value.trim();
        if (bruto === '') {
            corpo[c.nome] = null;
        } else if (c.tipo === 'number' || c.origem) {
            corpo[c.nome] = Number(bruto);
        } else {
            corpo[c.nome] = bruto;
        }
    }

    botao.disabled = true;
    erro.classList.add('hidden');
    try {
        if (emEdicao) {
            await api(`${s.endpoint}/${emEdicao.id}`, { method: 'PUT', body: corpo });
            toast('Alterações guardadas');
        } else {
            await api(s.criar || s.endpoint, { method: 'POST', body: corpo });
            toast('Registo criado');
        }
        fecharModal();
        carregar();
    } catch (err) {
        erro.textContent = err.message;
        erro.classList.remove('hidden');
    } finally {
        botao.disabled = false;
    }
});
