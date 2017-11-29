package at.ac.tuwien.student.sese2017.xp.hotelmanagement.search.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.tartarus.snowball.ext.GermanStemmer;

/**
 * Analyzer for all full text search with german stemming.
 * @author johannes
 */
public class EntityAnalyzer extends Analyzer {
  @Override
  protected TokenStreamComponents createComponents(String arg0) {
    Tokenizer source = new WhitespaceTokenizer();
    TokenStream result = new LowerCaseFilter(source);
    result = new SnowballFilter(result, new GermanStemmer());
    return new TokenStreamComponents(source, result);
  }
}
