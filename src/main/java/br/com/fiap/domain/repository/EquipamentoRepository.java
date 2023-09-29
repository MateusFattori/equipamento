package br.com.fiap.domain.repository;

import br.com.fiap.domain.entity.Equipamento;
import br.com.fiap.infra.ConnectionFactory;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public class EquipamentoRepository implements Repository<Equipamento, Long> {


    private static final AtomicReference<EquipamentoRepository> instance = new AtomicReference();
        private EquipamentoRepository(){
        }


    public static EquipamentoRepository build() {
            EquipamentoRepository result = instance.get();
            if (Objects.isNull(result)){
                EquipamentoRepository repo = new EquipamentoRepository();
                result = repo;
            }else{
                result = instance.get();
            }
            return result;
    }
    @Override
    public Equipamento persist(Equipamento equipamento) {
        var sql = "BEGIN" +
                "INSERT INTO equipamento (NM_EQUIPAMENTO)" +
                "VALUES (?)" +
                "returning ID_EQUIPAMENTO into ?;" +
                "END;" +
                "";

            var factory = ConnectionFactory.build();
            Connection connection = factory.getConnection();

        CallableStatement cs = null;
        try{
            cs = connection.prepareCall( sql );
            cs.setString(1, equipamento.getNome());
            cs.registerOutParameter(2, Types.BIGINT);
            equipamento.setId(cs.getLong(2));
            cs.close();
            connection.close();
        }catch (SQLException e){
            System.err.println("Não foi possivel consultar os dados!\n" + e.getMessage() );
        }
        return equipamento;
    }

    @Override
    public List<Equipamento> findAll() {
        List<Equipamento> equipamentos = new ArrayList<>();

        try{

        var factory = ConnectionFactory.build();
        Connection connection = factory.getConnection();


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM equipamento");

        if (resultSet.isBeforeFirst()){
            while (resultSet.next()){
                Long id = resultSet.getLong("ID_EQUIPAMENTO");
                String nome = resultSet.getString("NM_EQUIPAMENTO");
                equipamentos.add(new Equipamento(id, nome,  ));
            }
        }

        resultSet.close();
        statement.close();
        connection.close();

        }catch (SQLException e){
            System.err.println( "Não foi possivel consultar os dados!\n" + e.getMessage());
        }
        return equipamentos;

    }

    @Override
    public Equipamento findById(Long aLong) {
        Equipamento equipamento = null;
        var sql = "SELECT * FROM equipamento where ID_EQUIPAMENTO=?";

        var factory = ConnectionFactory.build();
        Connection connection = factory.getConnection();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()){
                while (resultSet.isBeforeFirst()){
                    equipamento = new Equipamento(
                            resultSet.getLong("ID_EQUIPAMENTO"),
                            resultSet.getString("NM_EQUIPAMENTO"),
                            resultSet.
                    );
                }
            }else{
                System.err.println("Equipamentos não encontradado com o id = " + id);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            System.out.println( "Não foi possível executar a consulta: \n" + e.getMessage());
        }

        return equipamento;

    }

    @Override
    public Equipamento update(Equipamento equipamento) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
