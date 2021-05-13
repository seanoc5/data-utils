import org.apache.log4j.Logger
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.SolrDocumentList

final Logger log = Logger.getLogger(this.class.name);
log.info "Starting ${this.class.name}..."

String host = 'localhost'
int port = 8983
String collection = 'anl'
String baseUrl = "http://$host:$port/solr/$collection"
SolrClient solrClient = new HttpSolrClient.Builder(baseUrl).build();

int testPower = 4
for (genNumber in 1..<10) {

    int expectedCount = Math.pow(genNumber, testPower) + 1
    // expect all of the child features, plus 1 record for the parent genome

    log.info "Checking added test data for genome number ${genNumber} and test scaling power: $testPower which we expect result size of: $expectedCount..."
    SolrQuery graphQuery = new SolrQuery(" {!graph to=in_edge_i from=out_edge_i}id:g${genNumber}")
    graphQuery.setRows(expectedCount + 10)         // set to larger than expected number (10 more as a buffer)...
    long start = new Date().time

    QueryResponse rspGraphed = solrClient.query(graphQuery)
    SolrDocumentList graphDocs = rspGraphed.getResults()
    long end = new Date().time

    int gdSize = graphDocs.size()
    assert gdSize == expectedCount
    log.info "Got back $gdSize document -- $expectedCount were expected:: $genNumber ^ $testPower (${Math.pow(genNumber, testPower)}) feature docs plus 1 for parent genome)"
    log.info "Query took: ${end - start} milliseconds ($end - $start)"
    int gdFound = graphDocs.getNumFound()
    assert gdFound == expectedCount
    log.info "Found $gdFound documents in the collection for the query ($graphQuery) --  $expectedCount were expected"
}
//SolrQuery oneParentAndKids = new SolrQuery("{!graph+from=")
