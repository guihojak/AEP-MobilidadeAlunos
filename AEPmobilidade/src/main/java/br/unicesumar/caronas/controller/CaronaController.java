package br.unicesumar.caronas.controller;

import br.unicesumar.caronas.dao.CaronaDAO;
import br.unicesumar.caronas.model.Carona;

import java.util.List;

public class CaronaController {

    private final CaronaDAO caronaDAO;

    public CaronaController() {
        this.caronaDAO = new CaronaDAO();
    }

    public boolean criarCarona(Carona carona) {
        return caronaDAO.inserir(carona);
    }

    public List<Carona> listarProximas() {
        return caronaDAO.listarProximas();
    }
}