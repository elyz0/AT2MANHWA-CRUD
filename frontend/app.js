const API = "http://localhost:8080/manhwa";

async function listar() {
  const res = await fetch(API);
  const manhwas = await res.json();
  const lista = document.getElementById("lista");
  lista.innerHTML = "";

  manhwas.forEach(m => {
    const card = document.createElement("div");
    card.className = "card";

    card.innerHTML = `
      <img src="${m.capa}" alt="${m.titulo}">
      <div class="card-content">
        <h3>${m.titulo}</h3>
        <p>${m.autor}</p>
        <p class="nota">Nota: ${m.nota}</p>
        <button onclick="deletar(${m.id})">Excluir</button>
        <button onclick="editar(${m.id})">Editar</button>
      </div>
    `;

    lista.appendChild(card);
  });
}

async function deletar(id) {
  await fetch(`${API}/${id}`, { method: "DELETE" });
  listar();
}

async function editar(id) {
  const res = await fetch(`${API}/${id}`);
  const m = await res.json();

  document.getElementById("titulo").value = m.titulo;
  document.getElementById("autor").value = m.autor;
  document.getElementById("genero").value = m.genero;
  document.getElementById("status").value = m.status;
  document.getElementById("descricao").value = m.descricao;
  document.getElementById("nota").value = m.nota;
  document.getElementById("capa").value = m.capa;

  // Salva o ID para saber que estamos editando
  document.getElementById("form").dataset.editId = m.id;
}

document.getElementById("form").onsubmit = async (e) => {
  e.preventDefault();

  const id = document.getElementById("form").dataset.editId;

  const manhwa = {
    titulo: document.getElementById("titulo").value,
    autor: document.getElementById("autor").value,
    genero: document.getElementById("genero").value,
    status: document.getElementById("status").value,
    descricao: document.getElementById("descricao").value,
    nota: parseFloat(document.getElementById("nota").value),
    capa: document.getElementById("capa").value
  };

  const method = id ? "PUT" : "POST";
  const url = id ? `${API}/${id}` : API;

  await fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(manhwa)
  });

  document.getElementById("form").reset();
  delete document.getElementById("form").dataset.editId;

  listar();
};

listar();
