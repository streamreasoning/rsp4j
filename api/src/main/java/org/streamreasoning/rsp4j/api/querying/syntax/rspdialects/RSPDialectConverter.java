package org.streamreasoning.rsp4j.api.querying.syntax.rspdialects;

public interface RSPDialectConverter {

    public String convertToDialectFromRSPQLSyntax(String rspqlQuery, RSPDialect dialectRSPDialect);

}
