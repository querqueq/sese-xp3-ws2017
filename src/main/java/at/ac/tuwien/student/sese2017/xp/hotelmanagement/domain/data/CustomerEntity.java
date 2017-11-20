package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * This is a representation of the customer used by CustomerService and the CustomerController as
 * DTO.
 *
 * @author Michael
 * @author Johannes
 */
@AnalyzerDef(name = "customanalyzer",
    // How is a search value split and searched
    tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class),
    // Applied to the tokens for normalisation
    filters = {@TokenFilterDef(factory = LowerCaseFilterFactory.class),
        // Disassembles token into engrams.
        @TokenFilterDef(factory = EdgeNGramFilterFactory.class,
            params = {@Parameter(name = "maxGramSize", value = "15")})})
@AnalyzerDef(name = "customanalyzer_query",
    // How is a search value split and searched
    tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class),
    // Applied to the tokens for normalisation
    filters = {@TokenFilterDef(factory = LowerCaseFilterFactory.class),})
@Data
@Indexed
@Entity
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  /* configure analyzer. Store (cache the search fragments)
   * analyze (analyze the field with the given analyzers)
   */
  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  @NotNull
  private String name;

  @Column
  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate birthday;

  @Column
  @NotNull
  private Sex sex;

  @Column
  /* configure analyzer. Store (cache the search fragments)
   * analyze (analyze the field with the given analyzers)
   */
  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  @NotNull
  private String billingAddress;

  @Column
  private String companyName;

  @Column
  @Lob
  private String note;

  /**
   * Store user discount as double.
   *
   * <p>
   *   Percent value, Min value  is 0 max value is 100. Default is 0
   * </p>
   */
  @Column
  @NotNull
  @ColumnDefault("0")
  @Digits(fraction = 2, integer = 3)
  @Min(value = 0)
  @Max(value = 100)
  private BigDecimal discount;

  @Column
  @NotNull
  @Digits(fraction = 0, integer = 50)
  private String phoneNumber;

  @Column
  @Email
  @NotNull
  private String email;

  @Column
  private URL webAddress;

  @Column
  private String faxNumber;
}
