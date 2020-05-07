package it.polimi.sr.rsp.onsper.spe.operators.r2r;


import it.polimi.sr.rsp.onsper.spe.operators.r2s.responses.RelationalSolution;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class R2ROntop implements RelationToRelationOperator<RelationalSolution.Result> {

    private PreparedStatement statement;


    @Override
    public Stream<SolutionMapping<RelationalSolution.Result>> eval(long ts) {
        try {
            List<RelationalSolution> resultList = new ArrayList<>();
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                ResultSetMetaData metaData = res.getMetaData();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    Object object = res.getObject(columnLabel);
                    resultList.add(new RelationalSolution("res" + i, System.currentTimeMillis(), columnLabel, object.toString()));
                }
            }
            return resultList.stream().map(relationalSolution -> relationalSolution);
        } catch (SQLException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }


}
