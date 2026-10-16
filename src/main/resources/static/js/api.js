// Funções comuns: token JWT, chamadas à API e pequenas ajudas de interface

const TOKEN_KEY = 'treinos_token';

function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

function logout() {
    localStorage.removeItem(TOKEN_KEY);
    window.location.href = 'index.html';
}

// Lê os dados guardados dentro do token (sub, id, tipo, exp)
function lerToken(token = getToken()) {
    if (!token) return null;
    try {
        const payload = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
        const json = decodeURIComponent(atob(payload).split('')
            .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)).join(''));
        const dados = JSON.parse(json);
        if (dados.exp && dados.exp * 1000 < Date.now()) return null; // expirado
        return dados;
    } catch (e) {
        return null;
    }
}

// Garante que há sessão e que o tipo de utilizador pode ver a página
function exigirSessao(tiposPermitidos) {
    const dados = lerToken();
    if (!dados) {
        logout();
        return null;
    }
    if (tiposPermitidos && !tiposPermitidos.includes(dados.tipo)) {
        window.location.href = paginaInicial(dados.tipo);
        return null;
    }
    return dados;
}

function paginaInicial(tipo) {
    return tipo === 'ATLETA' ? 'atleta.html' : 'admin.html';
}

// Chamada à API com o token. Lança Error com a mensagem do backend se falhar.
async function api(caminho, { method = 'GET', body } = {}) {
    const headers = { 'Accept': 'application/json' };
    const token = getToken();
    if (token) headers['Authorization'] = 'Bearer ' + token;
    if (body !== undefined) headers['Content-Type'] = 'application/json';

    const resposta = await fetch(caminho, {
        method,
        headers,
        body: body !== undefined ? JSON.stringify(body) : undefined
    });

    // Sessão expirada (excepto no próprio login, onde 401 é "credenciais erradas")
    if (resposta.status === 401 && !caminho.startsWith('/api/auth')) {
        logout();
        throw new Error('Sessão expirada');
    }

    if (resposta.status === 204) return null;

    const texto = await resposta.text();
    const dados = texto ? JSON.parse(texto) : null;

    if (!resposta.ok) {
        if (resposta.status === 403) throw new Error('Não tem permissão para esta operação');
        throw new Error((dados && dados.mensagem) || 'Erro ' + resposta.status);
    }
    return dados;
}

// Escapa texto antes de o pôr em HTML
function esc(valor) {
    if (valor === null || valor === undefined) return '';
    return String(valor)
        .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;').replace(/'/g, '&#39;');
}

function formatarData(iso) {
    if (!iso) return '';
    const [a, m, d] = String(iso).split('-');
    return d && m && a ? `${d}/${m}/${a}` : iso;
}

let toastTimer;
function toast(mensagem, erro = false) {
    let el = document.getElementById('toast');
    if (!el) {
        el = document.createElement('div');
        el.id = 'toast';
        document.body.appendChild(el);
    }
    el.className = 'toast' + (erro ? ' error' : '');
    el.textContent = mensagem;
    el.style.opacity = '1';
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => { el.style.opacity = '0'; }, 3000);
}
