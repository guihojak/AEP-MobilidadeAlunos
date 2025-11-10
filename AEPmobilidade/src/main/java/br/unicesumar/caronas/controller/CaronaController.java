package br.unicesumar.caronas.controller;

import br.unicesumar.caronas.dao.CaronaDAO;
import br.unicesumar.caronas.model.Carona;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controla as operações de caronas e suas regras de negócio.
 */
public class CaronaController {

    private final CaronaDAO caronaDAO = new CaronaDAO();

    /**
     * Cria uma nova carona, validando data e vagas.
     */
    public boolean criarCarona(Carona carona) {
        if (carona.getDataHora().isBefore(LocalDateTime.now())) {
            System.out.println("Data/hora inválida (passada)!");
            return false;
        }
        if (carona.getVagas() <= 0) {
            System.out.println("Número de vagas inválido!");
            return false;
        }
        return caronaDAO.inserir(carona);
    }

    /**
     * Retorna todas as caronas futuras cadastradas.
     */
    public List<Carona> listarProximas() {
        return caronaDAO.listarProximas();
    }
}
