package br.com.jpcribeiro.service;

import br.com.jpcribeiro.model.Jogo;
import net.jqwik.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class JogoServiceTest {
    private JogoService service;

    @BeforeEach
    void setUp() {
        service = new JogoService();
    }

    @Test
    void testCriarELerJogo() {
        Jogo j = new Jogo(service.gerarId(), "The Legend of Zelda: Breath of the Wild", 299.90, "Aventura em mundo aberto para Nintendo Switch");
        service.adicionar(j);
        Jogo buscado = service.buscar(j.getId());
        assertNotNull(buscado);
        assertEquals("The Legend of Zelda: Breath of the Wild", buscado.getNome());
        assertEquals(299.90, buscado.getPreco());
    }

    @Test
    void testAtualizarJogo() {
        Jogo j = new Jogo(service.gerarId(), "FIFA 23", 249.90, "Simulador de futebol para PS4 e Xbox One e PC");
        service.adicionar(j);
        j.setNome("FIFA 25");
        j.setPreco(349.90);
        j.setDescricao("Simulador de futebol para PS5, Xbox Series S e PC");
        service.atualizar(j);
        Jogo atualizado = service.buscar(j.getId());
        assertEquals("FIFA 25", atualizado.getNome());
        assertEquals(349.90, atualizado.getPreco());
        assertEquals("Simulador de futebol para PS5, Xbox Series S e PC", atualizado.getDescricao());
    }

    @Test
    void testRemoverJogo() {
        Jogo j = new Jogo(service.gerarId(), "Minecraft", 99.90, "Jogo de construção e sobrevivência em mundo aberto");
        service.adicionar(j);
        service.remover(j.getId());
        Jogo removido =  service.buscar(j.getId());
        assertNull(removido);
    }

    @Test
    void testBuscarJogoInexistente() {
        Jogo j = service.buscar(999);
        assertNull(j);
    }

    @Test
    void testAtualizarJogoInexistente() {
        Jogo j = new Jogo(10, "God of War", 199.90, "Aventura épica de ação e mitologia nórdica");
        assertThrows(NoSuchElementException.class, () -> service.atualizar(j));
    }

    @Test
    void testRemoverJogoInexistente() {
        assertThrows(NoSuchElementException.class, () -> service.remover(10));
    }

    @Test
    void testCriarJogoComNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Jogo(5, "", 120.00, "Jogo sem nome"));
    }

    @Test
    void testCriarJogoComPrecoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Jogo(6, "Overwatch", -50.00, "Jogo de tiro em equipe"));
    }

    // Testes com Jqwik
    @Provide
    Arbitrary<String> nomesValidos() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(1)
                .ofMaxLength(100)
                .filter(s -> !s.isBlank());
    }

    @Provide
    Arbitrary<Double> precosValidos() {
        return Arbitraries.doubles()
                .between(0.01, 1000.00);
    }

    @Provide
    Arbitrary<String> descricoesValidas() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(1)
                .ofMaxLength(1000)
                .filter(s -> !s.isBlank());
    }

    @Property(tries=1000)
    void testeJqwikJogos (
            @ForAll Integer id,
            @ForAll("nomesValidos") String nome,
            @ForAll("precosValidos") Double preco,
            @ForAll("nomesValidos") String descricao
    ) {
        Jogo p = new Jogo(id, nome, preco, descricao);
        System.out.printf("Nome: %s | Preço: %.2f | Descrição: %s\n", nome, preco, descricao);
        assertEquals(id, p.getId());
        assertEquals(nome, p.getNome());
        assertEquals(preco, p.getPreco());
        assertEquals(descricao, p.getDescricao());
    }

}