package br.unicesumar.caronas.controller;

import br.unicesumar.caronas.dao.VeiculoDAO;
import br.unicesumar.caronas.model.Veiculo;
import java.util.List;

public class VeiculoController {

    private final VeiculoDAO veiculoDAO;

    public VeiculoController() {
        this.veiculoDAO = new VeiculoDAO();
    }

    /**
     * Salva ou atualiza um veículo.
     * Implementa a regra: se o ID for 0, salva; caso contrário, atualiza.
     */
    public void salvarOuAtualizar(Veiculo veiculo) {
        // Validação básica
        if (veiculo.getModelo() == null || veiculo.getModelo().trim().isEmpty() ||
                veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo e Placa são obrigatórios.");
        }

        // Regra de negócio simples
        if (veiculo.getId() == 0) {
            veiculoDAO.salvar(veiculo);
        } else {
            veiculoDAO.atualizar(veiculo);
        }
    }

    /**
     * Retorna a lista de todos os veículos cadastrados por um usuário.
     * @param idUsuario O ID do usuário logado.
     * @return Lista de veículos.
     */
    public List<Veiculo> listarVeiculosPorUsuario(int idUsuario) {
        return veiculoDAO.listarPorUsuario(idUsuario);
    }

    /**
     * Deleta um veículo pelo seu ID.
     * @param idVeiculo O ID do veículo a ser deletado.
     */
    public void deletarVeiculo(int idVeiculo) {
        veiculoDAO.deletar(idVeiculo);
    }

    // Método auxiliar para obter um veículo por ID, caso necessário para edição
    // Omitido por enquanto, mas pode ser adicionado no DAO e aqui se a View precisar.
}