package at.ac.tuwien.student.sese2017.xp.hotelmanagement.search.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.commongrams.CommonGramsFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.de.GermanNormalizationFilter;
import org.apache.lucene.analysis.de.GermanStemFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.ngram.Lucene43EdgeNGramTokenizer.Side;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.tartarus.snowball.ext.GermanStemmer;

public class EntityAnalyzer extends Analyzer {

  @Override
  protected TokenStreamComponents createComponents(String arg0) {
    Tokenizer source = new WhitespaceTokenizer();
    TokenStream result = new LowerCaseFilter(source);
//    result = new GermanNormalizationFilter(result);
//    result = new GermanStemFilter(result);
//    result = new EdgeNGramTokenFilter(result, 3, 15);
    result = new SnowballFilter(result, new GermanStemmer());
    return new TokenStreamComponents(source, result);
  }

}
