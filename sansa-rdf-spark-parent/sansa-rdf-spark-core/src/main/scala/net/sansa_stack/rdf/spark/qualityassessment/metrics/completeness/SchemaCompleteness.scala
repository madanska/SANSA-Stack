package net.sansa_stack.rdf.spark.qualityassessment.metrics.completeness

import org.apache.spark.rdd.RDD
import org.apache.jena.graph.{ Triple, Node }

object SchemaCompleteness {
  implicit class SchemaCompletenessFunctions(dataset: RDD[Triple]) extends Serializable {
    /**
     * This metric measures the ratio of the number of classes and relations
     * of the gold standard existing in g, and the number of classes and
     * relations in the gold standard.
     */
    def assessSchemaCompleteness() = {
      /*
     -->Rule->Filter-->
   		select (?p2, o) where ?s p=rdf:type isIRI(?o); ?p2 ?o2
			-->Action-->
			S+=?p2 && SC+=?o
			-->Post-processing-->
			|S| intersect |SC| / |SC|
   */

      val p2_o = dataset.filter(f =>
        f.getPredicate.getLocalName.equals("type")
          && f.getObject.isURI()).cache()

      val S = p2_o.map(_.getPredicate).distinct().cache()
      val SC = dataset.map(_.getObject).distinct().cache()

      val S_intersection_SC = S.intersection(SC).distinct

      val SC_count = SC.count()
      val S_intersection_SC_count = S_intersection_SC.count()

      if (SC_count > 0) S_intersection_SC_count.toDouble / SC_count.toDouble
      else 0.00
    }

  }
}