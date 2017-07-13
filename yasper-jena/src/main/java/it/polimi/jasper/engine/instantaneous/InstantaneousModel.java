package it.polimi.jasper.engine.instantaneous;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.*;
import org.apache.jena.shared.Command;
import org.apache.jena.shared.Lock;
import org.apache.jena.shared.PrefixMapping;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Log4j
@Data
@Getter
@Setter
public abstract class InstantaneousModel implements Model {

    protected InstantaneousGraph graph;
    protected Model model;

    public InstantaneousModel(InstantaneousGraph base) {
        graph = base;
    }

    public long getTimestamp() {
        return getGraph().getTimestamp();
    }


    @Override
    public long size() {
        return model.size();
    }

    @Override
    public boolean isEmpty() {
        return model.isEmpty();
    }

    @Override
    public ResIterator listSubjects() {
        return model.listSubjects();
    }

    @Override
    public NsIterator listNameSpaces() {
        return model.listNameSpaces();
    }

    @Override
    public Resource getResource(String uri) {
        return model.getResource(uri);
    }

    @Override
    public Property getProperty(String nameSpace, String localName) {
        return model.getProperty(nameSpace, localName);
    }

    @Override
    public Resource createResource() {
        return model.createResource();
    }

    @Override
    public Resource createResource(AnonId id) {
        return model.createResource(id);
    }

    @Override
    public Resource createResource(String uri) {
        return model.createResource(uri);
    }

    @Override
    public Property createProperty(String nameSpace, String localName) {
        return model.createProperty(nameSpace, localName);
    }

    @Override
    public Literal createLiteral(String v, String language) {
        return model.createLiteral(v, language);
    }

    @Override
    public Literal createLiteral(String v, boolean wellFormed) {
        return model.createLiteral(v, wellFormed);
    }

    @Override
    public Literal createTypedLiteral(String lex, RDFDatatype dtype) {
        return model.createTypedLiteral(lex, dtype);
    }

    @Override
    public Literal createTypedLiteral(Object value, RDFDatatype dtype) {
        return model.createTypedLiteral(value, dtype);
    }

    @Override
    public Resource getResource(String uri, ResourceF f) {
        return model.getResource(uri, f);
    }

    @Override
    public Property getProperty(String uri) {
        return model.getProperty(uri);
    }

    @Override
    public Bag getBag(String uri) {
        return model.getBag(uri);
    }

    @Override
    public Bag getBag(Resource r) {
        return model.getBag(r);
    }

    @Override
    public Alt getAlt(String uri) {
        return model.getAlt(uri);
    }

    @Override
    public Alt getAlt(Resource r) {
        return model.getAlt(r);
    }

    @Override
    public Seq getSeq(String uri) {
        return model.getSeq(uri);
    }

    @Override
    public Seq getSeq(Resource r) {
        return model.getSeq(r);
    }

    @Override
    public Resource createResource(Resource type) {
        return model.createResource(type);
    }

    @Override
    public RDFNode getRDFNode(Node n) {
        return model.getRDFNode(n);
    }

    @Override
    public Resource createResource(String uri, Resource type) {
        return model.createResource(uri, type);
    }

    @Override
    public Resource createResource(ResourceF f) {
        return model.createResource(f);
    }

    @Override
    public Resource createResource(String uri, ResourceF f) {
        return model.createResource(uri, f);
    }

    @Override
    public Property createProperty(String uri) {
        return model.createProperty(uri);
    }

    @Override
    public Literal createLiteral(String v) {
        return model.createLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(boolean v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(int v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(long v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(Calendar d) {
        return model.createTypedLiteral(d);
    }

    @Override
    public Literal createTypedLiteral(char v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(float v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(double v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(String v) {
        return model.createTypedLiteral(v);
    }

    @Override
    public Literal createTypedLiteral(Object value) {
        return model.createTypedLiteral(value);
    }

    @Override
    public Literal createTypedLiteral(String lex, String typeURI) {
        return model.createTypedLiteral(lex, typeURI);
    }

    @Override
    public Literal createTypedLiteral(Object value, String typeURI) {
        return model.createTypedLiteral(value, typeURI);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, boolean o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, float o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, double o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, long o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, int o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, char o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createLiteralStatement(Resource s, Property p, Object o) {
        return model.createLiteralStatement(s, p, o);
    }

    @Override
    public Statement createStatement(Resource s, Property p, String o) {
        return model.createStatement(s, p, o);
    }

    @Override
    public Statement createStatement(Resource s, Property p, String o, String l) {
        return model.createStatement(s, p, o, l);
    }

    @Override
    public Statement createStatement(Resource s, Property p, String o, boolean wellFormed) {
        return model.createStatement(s, p, o, wellFormed);
    }

    @Override
    public Statement createStatement(Resource s, Property p, String o, String l, boolean wellFormed) {
        return model.createStatement(s, p, o, l);
    }

    @Override
    public Bag createBag() {
        return model.createBag();
    }

    @Override
    public Bag createBag(String uri) {
        return model.createBag(uri);
    }

    @Override
    public Alt createAlt() {
        return model.createAlt();
    }

    @Override
    public Alt createAlt(String uri) {
        return model.createAlt(uri);
    }

    @Override
    public Seq createSeq() {
        return model.createSeq();
    }

    @Override
    public Seq createSeq(String uri) {
        return model.createSeq(uri);
    }

    @Override
    public Model add(Resource s, Property p, RDFNode o) {
        return model.add(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, boolean o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, long o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, int o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, char o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, float o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, double o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, Object o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model addLiteral(Resource s, Property p, Literal o) {
        return model.addLiteral(s, p, o);
    }

    @Override
    public Model add(Resource s, Property p, String o) {
        return model.add(s, p, o);
    }

    @Override
    public Model add(Resource s, Property p, String lex, RDFDatatype datatype) {
        return model.add(s, p, lex, datatype);
    }

    @Override
    public Model add(Resource s, Property p, String o, boolean wellFormed) {
        return model.add(s, p, o, wellFormed);
    }

    @Override
    public Model add(Resource s, Property p, String o, String l) {
        return model.add(s, p, o, l);
    }

    @Override
    public Model remove(Resource s, Property p, RDFNode o) {
        return model.remove(s, p, o);
    }

    @Override
    public Model remove(StmtIterator iter) {
        return model.remove(iter);
    }

    @Override
    public Model remove(Model m) {
        return model.remove(m);
    }

    @Override
    public StmtIterator listLiteralStatements(Resource subject, Property predicate, boolean object) {
        return model.listLiteralStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listLiteralStatements(Resource subject, Property predicate, char object) {
        return model.listLiteralStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listLiteralStatements(Resource subject, Property predicate, long object) {
        return model.listLiteralStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listLiteralStatements(Resource subject, Property predicate, int object) {
        return model.listLiteralStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listLiteralStatements(Resource subject, Property predicate, float object) {
        return model.listLiteralStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listLiteralStatements(Resource subject, Property predicate, double object) {
        return model.listLiteralStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listStatements(Resource subject, Property predicate, String object) {
        return model.listStatements(subject, predicate, object);
    }

    @Override
    public StmtIterator listStatements(Resource subject, Property predicate, String object, String lang) {
        return model.listStatements(subject, predicate, object, lang);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, boolean o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, long o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, char o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, float o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, double o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, Object o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listSubjectsWithProperty(Property p, String o) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public ResIterator listSubjectsWithProperty(Property p, String o, String l) {
        return model.listResourcesWithProperty(p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, boolean o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, long o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, int o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, char o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, float o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, double o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean containsLiteral(Resource s, Property p, Object o) {
        return model.containsLiteral(s, p, o);
    }

    @Override
    public boolean contains(Resource s, Property p, String o) {
        return model.contains(s, p, o);
    }

    @Override
    public boolean contains(Resource s, Property p, String o, String l) {
        return model.contains(s, p, o, l);
    }

    @Override
    public Statement createStatement(Resource s, Property p, RDFNode o) {
        return model.createStatement(s, p, o);
    }

    @Override
    public RDFList createList() {
        return model.createList();
    }

    @Override
    public RDFList createList(Iterator<? extends RDFNode> members) {
        return model.createList(members);
    }

    @Override
    public RDFList createList(RDFNode[] members) {
        return model.createList(members);
    }

    @Override
    public Model add(Statement s) {
        return model.add(s);
    }

    @Override
    public Model add(Statement[] statements) {
        return model.add(statements);
    }

    @Override
    public Model remove(Statement[] statements) {
        return model.remove(statements);
    }

    @Override
    public Model add(List<Statement> statements) {
        return model.add(statements);
    }

    @Override
    public Model remove(List<Statement> statements) {
        return model.remove(statements);
    }

    @Override
    public Model add(StmtIterator iter) {
        return model.add(iter);
    }

    @Override
    public Model add(Model m) {
        return model.add(m);
    }

    @Override
    public Model read(String url) {
        return model.read(url);
    }

    @Override
    public Model read(InputStream in, String base) {
        return read(in, base);
    }

    @Override
    public Model read(InputStream in, String base, String lang) {
        return model.read(in, base, lang);
    }

    @Override
    public Model read(Reader reader, String base) {
        return model.read(reader, base);
    }

    @Override
    public Model read(String url, String lang) {
        return model.read(url, lang);
    }

    @Override
    public Model read(Reader reader, String base, String lang) {
        return model.read(reader, base, lang);
    }

    @Override
    public Model read(String url, String base, String lang) {
        return model.read(url, base, lang);
    }

    @Override
    public Model write(Writer writer) {
        return model.write(writer);
    }

    @Override
    public Model write(Writer writer, String lang) {
        return model.write(writer, lang);
    }

    @Override
    public Model write(Writer writer, String lang, String base) {
        return model.write(writer, lang, base);
    }

    @Override
    public Model write(OutputStream out) {
        return model.write(out);
    }

    @Override
    public Model write(OutputStream out, String lang) {
        return model.write(out, lang);
    }

    @Override
    public Model write(OutputStream out, String lang, String base) {
        return model.write(out, lang, base);
    }

    @Override
    public Model remove(Statement s) {
        return model.remove(s);
    }

    @Override
    public Statement getRequiredProperty(Resource s, Property p) {
        return model.getRequiredProperty(s, p);
    }

    @Override
    public Statement getRequiredProperty(Resource s, Property p, String lang) {
        return model.getRequiredProperty(s, p, lang);
    }

    @Override
    public Statement getProperty(Resource s, Property p) {
        return model.getProperty(s, p);
    }

    @Override
    public Statement getProperty(Resource s, Property p, String lang) {
        return model.getProperty(s, p, lang);
    }

    @Override
    public ResIterator listSubjectsWithProperty(Property p) {
        return model.listSubjectsWithProperty(p);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p) {
        return model.listSubjectsWithProperty(p);
    }

    @Override
    public ResIterator listSubjectsWithProperty(Property p, RDFNode o) {
        return model.listSubjectsWithProperty(p, o);
    }

    @Override
    public ResIterator listResourcesWithProperty(Property p, RDFNode o) {
        return model.listSubjectsWithProperty(p, o);
    }

    @Override
    public NodeIterator listObjects() {
        return model.listObjects();
    }

    @Override
    public NodeIterator listObjectsOfProperty(Property p) {
        return model.listObjectsOfProperty(p);
    }

    @Override
    public NodeIterator listObjectsOfProperty(Resource s, Property p) {
        return model.listObjectsOfProperty(s, p);
    }

    @Override
    public boolean contains(Resource s, Property p) {
        return model.contains(s, p);
    }

    @Override
    public boolean containsResource(RDFNode r) {
        return model.containsResource(r);
    }

    @Override
    public boolean contains(Resource s, Property p, RDFNode o) {
        return model.contains(s, p, o);
    }

    @Override
    public boolean contains(Statement s) {
        return model.contains(s);
    }

    @Override
    public boolean containsAny(StmtIterator iter) {
        return model.containsAny(iter);
    }

    @Override
    public boolean containsAll(StmtIterator iter) {
        return model.containsAll(iter);
    }

    @Override
    public boolean containsAny(Model model) {
        return model.containsAny(model);
    }

    @Override
    public boolean containsAll(Model model) {
        return model.containsAll(model);
    }

    @Override
    public boolean isReified(Statement s) {
        return model.isReified(s);
    }

    @Override
    public Resource getAnyReifiedStatement(Statement s) {
        return model.getAnyReifiedStatement(s);
    }

    @Override
    public void removeAllReifications(Statement s) {
        model.removeAllReifications(s);
    }

    @Override
    public void removeReification(ReifiedStatement rs) {
        model.removeReification(rs);
    }

    @Override
    public StmtIterator listStatements() {
        return model.listStatements();
    }

    @Override
    public StmtIterator listStatements(Selector s) {
        return model.listStatements(s);
    }

    @Override
    public StmtIterator listStatements(Resource s, Property p, RDFNode o) {
        return model.listStatements(s, p, o);
    }

    @Override
    public ReifiedStatement createReifiedStatement(Statement s) {
        return model.createReifiedStatement(s);
    }

    @Override
    public ReifiedStatement createReifiedStatement(String uri, Statement s) {
        return model.createReifiedStatement(uri, s);
    }

    @Override
    public RSIterator listReifiedStatements() {
        return model.listReifiedStatements();
    }

    @Override
    public RSIterator listReifiedStatements(Statement st) {
        return model.listReifiedStatements(st);
    }

    @Override
    public Model query(Selector s) {
        return model.query(s);
    }

    @Override
    public Model union(Model model) {
        return model.union(model);
    }

    @Override
    public Model intersection(Model model) {
        return model.intersection(model);
    }

    @Override
    public Model difference(Model model) {
        return model.difference(model);
    }

    @Override
    public Model begin() {
        return model.begin();
    }

    @Override
    public Model abort() {
        return model.abort();
    }

    @Override
    public Model commit() {
        return model.commit();
    }

    @Override
    public Object executeInTransaction(Command cmd) {
        return model.executeInTransaction(cmd);
    }

    @Override
    public void executeInTxn(Runnable action) {
        model.executeInTxn(action);
    }

    @Override
    public boolean independent() {
        return model.independent();
    }

    @Override
    public boolean supportsTransactions() {
        return model.supportsTransactions();
    }

    @Override
    public boolean supportsSetOperations() {
        return model.supportsSetOperations();
    }

    @Override
    public boolean isIsomorphicWith(Model g) {
        return model.isIsomorphicWith(g);
    }

    @Override
    public void close() {
        model.close();
    }

    @Override
    public Lock getLock() {
        return model.getLock();
    }

    @Override
    public Model register(ModelChangedListener listener) {
        return model.register(listener);
    }

    @Override
    public Model unregister(ModelChangedListener listener) {
        return model.unregister(listener);
    }

    @Override
    public Model notifyEvent(Object e) {
        return model.notifyEvent(e);
    }

    @Override
    public Model removeAll() {
        return model.removeAll();
    }

    @Override
    public Model removeAll(Resource s, Property p, RDFNode r) {
        return model.removeAll(s, p, r);
    }

    @Override
    public boolean isClosed() {
        return model.isClosed();
    }

    @Override
    public Statement asStatement(Triple t) {
        return model.asStatement(t);
    }

    @Override
    public RDFNode asRDFNode(Node n) {
        return model.asRDFNode(n);
    }

    @Override
    public Resource wrapAsResource(Node n) {
        return model.wrapAsResource(n);
    }

    @Override
    public RDFReader getReader() {
        return model.getReader();
    }

    @Override
    public RDFReader getReader(String lang) {
        return model.getReader(lang);
    }

    @Override
    public String setReaderClassName(String lang, String className) {
        return model.setReaderClassName(lang, className);
    }

    @Override
    public void resetRDFReaderF() {
        model.resetRDFReaderF();
    }

    @Override
    public String removeReader(String lang) throws IllegalArgumentException {
        return model.removeReader(lang);
    }

    @Override
    public RDFWriter getWriter() {
        return model.getWriter();
    }

    @Override
    public RDFWriter getWriter(String lang) {
        return model.getWriter(lang);
    }

    @Override
    public String setWriterClassName(String lang, String className) {
        return model.setWriterClassName(lang, className);
    }

    @Override
    public void resetRDFWriterF() {
        model.resetRDFReaderF();
    }

    @Override
    public String removeWriter(String lang) throws IllegalArgumentException {
        return model.removeWriter(lang);
    }

    @Override
    public void enterCriticalSection(boolean readLockRequested) {
        model.enterCriticalSection(readLockRequested);
    }

    @Override
    public void leaveCriticalSection() {
        model.leaveCriticalSection();
    }

    @Override
    public PrefixMapping setNsPrefix(String prefix, String uri) {
        return model.setNsPrefix(prefix, uri);
    }

    @Override
    public PrefixMapping removeNsPrefix(String prefix) {
        return model.removeNsPrefix(prefix);
    }

    @Override
    public PrefixMapping clearNsPrefixMap() {
        return model.clearNsPrefixMap();
    }

    @Override
    public PrefixMapping setNsPrefixes(PrefixMapping other) {
        return model.setNsPrefixes(other);
    }

    @Override
    public PrefixMapping setNsPrefixes(Map<String, String> map) {
        return model.setNsPrefixes(map);
    }

    @Override
    public PrefixMapping withDefaultMappings(PrefixMapping map) {
        return model.withDefaultMappings(map);
    }

    @Override
    public String getNsPrefixURI(String prefix) {
        return model.getNsPrefixURI(prefix);
    }

    @Override
    public String getNsURIPrefix(String uri) {
        return model.getNsURIPrefix(uri);
    }

    @Override
    public Map<String, String> getNsPrefixMap() {
        return model.getNsPrefixMap();
    }

    @Override
    public String expandPrefix(String prefixed) {
        return model.expandPrefix(prefixed);
    }

    @Override
    public String shortForm(String uri) {
        return model.shortForm(uri);
    }

    @Override
    public String qnameFor(String uri) {
        return model.qnameFor(uri);
    }

    @Override
    public PrefixMapping lock() {
        return model.lock();
    }

    @Override
    public int numPrefixes() {
        return model.numPrefixes();
    }

    @Override
    public boolean samePrefixMappingAs(PrefixMapping other) {
        return model.samePrefixMappingAs(other);
    }

}