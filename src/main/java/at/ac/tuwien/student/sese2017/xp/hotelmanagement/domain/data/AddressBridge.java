package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

public class AddressBridge implements FieldBridge {

  @Override
  public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
    AddressEntity address = (AddressEntity) value;

    StringBuilder addressBuilder = new StringBuilder();
    addressBuilder.append(address.getCity())
    .append(" ")
    .append(address.getName())
    .append(" ")
    .append(address.getState())
    .append(" ")
    .append(address.getStreetAddress1())
    .append(" ")
    .append(address.getStreetAddress2())
    .append(" ")
    .append(address.getZipCode())
    ;
   
    IndexableField allFields = new TextField("address", addressBuilder.toString(), Store.NO);
    
    document.add(allFields);
  }

}
