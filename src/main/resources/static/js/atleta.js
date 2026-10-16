// Área do atleta: ver os planos de treino, o perfil e mudar a password

const sessao = exigirSessao(['ATLETA']);

const ORDEM_DIAS = ['Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado', 'Domingo'];

if (sessao) {
    document.getElementById('user-nome').textContent = sessao.sub;
    carregarPlanos();
    carregarPerfil();
}

// ---------- Navegação ----------

document.getElementById('nav').addEventListener('click', (e) => {
    const vista = e.target.dataset.vista;
    if (!vista) return;
    document.querySelectorAll('#nav button').forEach(b => b.classList.toggle('active', b.dataset.vista === vista));
    document.querySelectorAll('main > section').forEach(s => s.classList.toggle('hidden', s.id !== 'vista-' + vista));
});

// ---------- Treinos ----------

async function carregarPlanos() {
    const contentor = document.getElementById('planos');
    try {
        const planos = await api('/api/me/planos');
        desenharStats(planos);

        if (planos.length === 0) {
            contentor.innerHTML = '<div class="card empty">Ainda não tem planos de treino. O seu treinador vai criá-los em breve.</div>';
            return;
        }
        contentor.innerHTML = planos.map(desenharPlano).join('');
    } catch (err) {
        contentor.innerHTML = `<div class="card empty">${esc(err.message)}</div>`;
    }
}

function desenharStats(planos) {
    const hoje = new Date().toISOString().slice(0, 10);
    const activos = planos.filter(p => (!p.dataInicio || p.dataInicio <= hoje) && (!p.dataFim || p.dataFim >= hoje));
    const rotinas = planos.reduce((n, p) => n + p.rotinas.length, 0);
    const exercicios = planos.reduce((n, p) => n + p.rotinas.reduce((m, r) => m + r.itens.length, 0), 0);

    document.getElementById('stats').innerHTML = [
        ['Planos', planos.length],
        ['Planos activos', activos.length],
        ['Rotinas', rotinas],
        ['Exercícios', exercicios]
    ].map(([label, valor]) => `
        <div class="card stat"><div class="label">${label}</div><div class="value">${valor}</div></div>`).join('');
}

function desenharPlano(plano) {
    const rotinas = [...plano.rotinas].sort((a, b) => indiceDia(a.diaSemana) - indiceDia(b.diaSemana));
    const datas = [plano.dataInicio && 'De ' + formatarData(plano.dataInicio), plano.dataFim && 'até ' + formatarData(plano.dataFim)]
        .filter(Boolean).join(' ');

    return `
        <div class="card plano">
            <div class="plano-header">
                <h3>${esc(plano.nome)}</h3>
                <span class="datas">${esc(datas)}</span>
            </div>
            ${rotinas.length === 0
                ? '<div class="rotina empty">Este plano ainda não tem rotinas.</div>'
                : rotinas.map(desenharRotina).join('')}
        </div>`;
}

function desenharRotina(rotina) {
    const itens = rotina.itens.length === 0
        ? '<p class="hint" style="color: var(--muted); margin: 0">Sem exercícios.</p>'
        : `<div class="table-wrap"><table class="exercicios">
            <thead><tr><th>Exercício</th><th>Séries</th><th>Repetições</th><th>Carga</th><th>Descanso</th><th></th></tr></thead>
            <tbody>${rotina.itens.map(i => `
                <tr>
                    <td><strong>${esc(i.exercicio)}</strong>${i.grupoMuscular ? `<br><small style="color: var(--muted)">${esc(i.grupoMuscular)}</small>` : ''}</td>
                    <td>${esc(i.series)}</td>
                    <td>${esc(i.repeticoes)}</td>
                    <td>${esc(i.carga)}</td>
                    <td>${i.descanso != null ? esc(i.descanso) + ' s' : ''}</td>
                    <td>${i.urlVideoDemonstrativo ? `<a href="${esc(i.urlVideoDemonstrativo)}" target="_blank" rel="noopener">Ver vídeo</a>` : ''}</td>
                </tr>`).join('')}
            </tbody></table></div>`;

    return `
        <div class="rotina">
            <div class="rotina-title">
                ${rotina.diaSemana ? `<span class="badge">${esc(rotina.diaSemana)}</span>` : ''}
                ${esc(rotina.nome)}
            </div>
            ${itens}
        </div>`;
}

function indiceDia(dia) {
    const i = ORDEM_DIAS.indexOf(dia);
    return i === -1 ? 99 : i;
}

// ---------- Perfil ----------

async function carregarPerfil() {
    const contentor = document.getElementById('perfil');
    try {
        const a = await api('/api/me/atleta');
        document.getElementById('user-nome').textContent = a.nome || sessao.sub;

        const campos = [
            ['Nome', a.nome],
            ['Email', a.email],
            ['Contacto', a.contacto],
            ['Data de nascimento', formatarData(a.dataNascimento)],
            ['Género', a.genero === 'MASCULINO' ? 'Masculino' : a.genero === 'FEMENINO' ? 'Feminino' : ''],
            ['Cidade', a.cidade],
            ['País', a.pais],
            ['Altura', a.altura != null ? a.altura + ' m' : ''],
            ['Peso', a.peso != null ? a.peso + ' kg' : ''],
            ['Objectivo', a.objectivo]
        ];
        contentor.innerHTML = campos.map(([label, valor]) => `
            <div><div class="label">${label}</div><div class="value">${esc(valor) || '—'}</div></div>`).join('');
    } catch (err) {
        contentor.innerHTML = `<div class="empty">${esc(err.message)}</div>`;
    }
}

// ---------- Mudar password ----------

document.getElementById('form-password').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form = e.target;
    const msg = document.getElementById('pw-msg');
    const nova = form.novaPassword.value;

    const mostrar = (texto, ok) => {
        msg.textContent = texto;
        msg.className = 'alert ' + (ok ? 'alert-success' : 'alert-error');
    };

    if (nova !== form.confirmar.value) {
        mostrar('As novas passwords não coincidem.', false);
        return;
    }

    try {
        await api('/api/me/password', {
            method: 'PATCH',
            body: { passwordActual: form.passwordActual.value, novaPassword: nova }
        });
        form.reset();
        mostrar('Password alterada com sucesso.', true);
    } catch (err) {
        mostrar(err.message, false);
    }
});
