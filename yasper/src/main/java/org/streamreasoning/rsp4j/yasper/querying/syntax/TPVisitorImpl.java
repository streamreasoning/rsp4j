package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLBaseVisitor;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLParser;

import java.util.List;

public class TPVisitorImpl extends RSPQLBaseVisitor<CQ> {

  @Override
  public CQ visitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx) {
    RSPQLParser.TriplesSameSubjectContext t = ctx.triplesSameSubject();
    RSPQLParser.VarOrTermContext s = t.s;
    RSPQLParser.PropertyListNotEmptyContext po = t.ps;
    return super.visitTriplesTemplate(ctx);
  }

  @Override
  public CQ visitTriplesBlock(RSPQLParser.TriplesBlockContext ctx) {

    return super.visitTriplesBlock(ctx);
  }

  @Override
  public CQ visitTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx) {
    return super.visitTriplesSameSubject(ctx);
  }

  @Override
  public CQ visitPropertyListNotEmpty(RSPQLParser.PropertyListNotEmptyContext ctx) {
    return super.visitPropertyListNotEmpty(ctx);
  }

  @Override
  public CQ visitPropertyList(RSPQLParser.PropertyListContext ctx) {

    return super.visitPropertyList(ctx);
  }

  @Override
  public CQ visitObjectList(RSPQLParser.ObjectListContext ctx) {

    return super.visitObjectList(ctx);
  }

  @Override
  public CQ visitObject(RSPQLParser.ObjectContext ctx) {

    return super.visitObject(ctx);
  }

  @Override
  public CQ visitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx) {
    extractSubject(ctx.s);
    extractPropertyObject(ctx.ps);

    return super.visitTriplesSameSubjectPath(ctx);
  }

  private void extractSubject(RSPQLParser.VarOrTermContext varOrTerm) {
    RSPQLParser.VarContext var = varOrTerm.var();
    RSPQLParser.GraphTermContext term = varOrTerm.graphTerm();
    if (var != null) {
      String varText = var.getText();
      System.out.println("subject var: " + varText);
    } else {
      String termText = term.getText();
      System.out.println("subject term: " + termText);
    }
  }

  private void extractPropertyObject(RSPQLParser.PropertyListPathNotEmptyContext po) {
    for (RSPQLParser.PropertyListPathContext pCandidate : po.propertyListPath()) {
      extractProperty(pCandidate);
      for (RSPQLParser.ObjectPathContext object : pCandidate.objectListPath().objectPath()) {
        extractObject(object);
      }
    }
  }

  private void extractProperty(RSPQLParser.PropertyListPathContext propCandidate) {
    String pText = "";
    if (propCandidate.verbPath() != null) {
      pText = propCandidate.verbPath().getText();
      System.out.println("property term: " + pText);
    } else {
      pText = propCandidate.verbSimple().getText();
      System.out.println("property var: " + pText);
    }
  }

  private void extractObject(RSPQLParser.ObjectPathContext object) {
    if (object.graphNodePath().varOrTerm().var() != null) {
      System.out.println("object var " + object.graphNodePath().varOrTerm().var().getText());
    } else {
      System.out.println("object term " + object.graphNodePath().varOrTerm().graphTerm().getText());
    }
  }

  @Override
  public CQ visitPropertyListPathNotEmpty(RSPQLParser.PropertyListPathNotEmptyContext ctx) {
    return super.visitPropertyListPathNotEmpty(ctx);
  }

  @Override
  public CQ visitPropertyListPath(RSPQLParser.PropertyListPathContext ctx) {
    return super.visitPropertyListPath(ctx);
  }

  @Override
  public CQ visitObjectListPath(RSPQLParser.ObjectListPathContext ctx) {
    return super.visitObjectListPath(ctx);
  }

  @Override
  public CQ visitObjectPath(RSPQLParser.ObjectPathContext ctx) {
    return super.visitObjectPath(ctx);
  }
}
