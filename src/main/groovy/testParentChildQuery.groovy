import org.apache.log4j.Logger
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.client.solrj.response.QueryResponse

final Logger log = Logger.getLogger(this.class.name);
log.info "Starting ${this.class.name}..."

String host = 'localhost'
int port = 8983
String collection = 'anl'
String baseUrl = "http://$host:$port/solr/$collection"
boolean deleteAllDocs = true
int featureCount = 3

SolrClient solrClient = new HttpSolrClient.Builder(baseUrl).build();

SolrQuery sq = new SolrQuery('*:*')
QueryResponse rsp = solrClient.query(sq)

//SolrQuery oneParentAndKids = new SolrQuery("{!graph+from=")
