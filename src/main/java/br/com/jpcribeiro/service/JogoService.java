package br.com.jpcribeiro.service;
import br.com.jpcribeiro.model.Jogo;
import java.util.HashMap;
import java.util.*;

public class JogoService {
    private final Map<Integer, Jogo> repositorio = new HashMap<>();
    private int ultimoId = 0;

    public int gerarId() {
        return ++ultimoId;
    }

    public void adicionar(Jogo jogo) {
        repositorio.put(jogo.getId(), jogo);
    }

    public Jogo buscar(int id) {
        return repositorio.get(id);
    }

    public void atualizar(Jogo jogo) {
        if (!repositorio.containsKey(jogo.getId())) {
            throw new NoSuchElementException("Jogo não encontrado");
        }

        repositorio.put(jogo.getId(), jogo);
    }

    public void remover(int id) {
        if (!repositorio.containsKey(id)) {
            throw new NoSuchElementException("Jogo não encontrado");
        }

        repositorio.remove(id);
    }

    public Collection<Jogo> listar() {
        return  repositorio.values();
    }
}
