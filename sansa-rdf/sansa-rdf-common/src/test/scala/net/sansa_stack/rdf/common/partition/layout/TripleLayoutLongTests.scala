package net.sansa_stack.rdf.common.partition.layout

import net.sansa_stack.rdf.common.partition.layout.TripleLayoutLong._
import net.sansa_stack.rdf.common.partition.schema.SchemaStringLong
import org.apache.jena.datatypes.xsd.XSDDatatype
import org.apache.jena.graph.{ Node, NodeFactory, Triple }
import org.scalatest.FunSuite

/**
 * @author Gezim Sejdiu
 */
class TripleLayoutLongTests extends FunSuite {

  val triple = Triple.create(
    NodeFactory.createURI("http://dbpedia.org/resource/Germany"),
    NodeFactory.createURI("http://dbpedia.org/ontology/populationTotal"),
    NodeFactory.createLiteral("82175700", XSDDatatype.XSDlong))

  test("getting layout from triple should match") {
    val expectedLayout = new SchemaStringLong(triple.getSubject.getURI, triple.getObject.getLiteralValue.toString().toLong)
    assert(fromTriple(triple).equals(expectedLayout))
  }

}
