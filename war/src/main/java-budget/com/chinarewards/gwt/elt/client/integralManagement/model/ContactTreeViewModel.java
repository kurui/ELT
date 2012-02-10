package com.chinarewards.gwt.elt.client.integralManagement.model;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.chinarewards.gwt.elt.client.integralManagement.model.ContactDatabase.Category;
import com.chinarewards.gwt.elt.client.integralManagement.model.ContactDatabase.ContactInfo;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

/**
 * The {@link TreeViewModel} used to organize contacts into a hierarchy.
 */
public class ContactTreeViewModel implements TreeViewModel {

  /**
   * The images used for this example.
   */
  static interface Images extends ClientBundle {
    ImageResource contact();

    ImageResource contactsGroup();
  }

  /**
   * The cell used to render categories.
   */
  private static class CategoryCell extends AbstractCell<Category> {

    /**
     * The html of the image used for contacts.
     */
  //  private final String imageHtml;

    public CategoryCell() {
   //   this.imageHtml = AbstractImagePrototype.create(image).getHTML();
    }

    @Override
    public void render(Context context, Category value, SafeHtmlBuilder sb) {
      if (value != null) {
      //  sb.appendHtmlConstant(imageHtml).appendEscaped(" ");
//        sb.appendEscaped(value.getDisplayName());

    	  sb.appendHtmlConstant("<table style='width:100%'>");
    	  sb.appendHtmlConstant("<tr>");
    	  sb.appendHtmlConstant("<td >人事部</td>");
    	  sb.appendHtmlConstant("<td >5000</td>");
    	  sb.appendHtmlConstant("<td >8000</td>");
    	  sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
    	  sb.appendHtmlConstant("<tr>");
    	  sb.appendHtmlConstant("</tr>");
    	  sb.appendHtmlConstant("</table>");
      }
    }
  }

  /**
   * Tracks the number of contacts in a category that begin with the same
   * letter.
   */
  private static class LetterCount implements Comparable<LetterCount> {
    private final Category category;
    private final char firstLetter;
    private int count;

    /**
     * Construct a new {@link LetterCount} for one contact.
     * 
     * @param category the category
     * @param firstLetter the first letter of the contacts name
     */
    public LetterCount(Category category, char firstLetter) {
      this.category = category;
      this.firstLetter = firstLetter;
      this.count = 1;
    }

    public int compareTo(LetterCount o) {
      return (o == null) ? -1 : (firstLetter - o.firstLetter);
    }

    @Override
    public boolean equals(Object o) {
      return compareTo((LetterCount) o) == 0;
    }

    @Override
    public int hashCode() {
      return firstLetter;
    }

    /**
     * Increment the count.
     */
    public void increment() {
      count++;
    }
  }

  /**
   * The Cell used to render a {@link ContactInfo}.
   */

  static class ContactCell extends AbstractCell<ContactInfo> {

    /**
     * The html of the image used for contacts.
     */
  //  private final String imageHtml;

    public ContactCell() {
   //   this.imageHtml = AbstractImagePrototype.create(image).getHTML();
    }

    @Override
    public void render(Context context, ContactInfo value, SafeHtmlBuilder sb) {
      // Value can be null, so do a null check..
      if (value == null) {
        return;
      }
      sb.appendHtmlConstant("<table style='width:100%'>");

      // Add the contact image.
  //    sb.appendHtmlConstant("<tr><td rowspan='1'>");
  //    sb.appendHtmlConstant(imageHtml);
  //    sb.appendHtmlConstant("</td>");

      // Add the name and address.
      sb.appendHtmlConstant("<tr><td>");
      sb.appendEscaped("产品部");
      sb.appendHtmlConstant("</td>");
      sb.appendHtmlConstant("<td>1600</td>");
      sb.appendHtmlConstant("<td>5000</td>");
	  sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
      sb.appendHtmlConstant("</tr></table>");
    }
  }

  /**
   * A Cell used to render the LetterCount.
   */
  private static class LetterCountCell extends AbstractCell<LetterCount> {

    @Override
    public void render(Context context, LetterCount value, SafeHtmlBuilder sb) {
      if (value != null) {
//        sb.appendEscaped(value.firstLetter + " (" + value.count + ")");

        
        sb.appendHtmlConstant("<table style='width:100%'>");
        sb.appendHtmlConstant("<tr><td>");
        sb.appendEscaped("IT部" + " (" + value.count + ")");
        sb.appendHtmlConstant("</td>");
        sb.appendHtmlConstant("<td>5060</td>");
        sb.appendHtmlConstant("<td>6000</td>");
  	  sb.appendHtmlConstant("<td ><a href='#'>追加</a> <a href='#'>颁奖历史</a></td>");
        sb.appendHtmlConstant("</tr></table>");
      }
    }
  }

  /**
   * The static images used in this model.
   */
  private static Images images;

  private final ListDataProvider<Category> categoryDataProvider;
  private final Cell<ContactInfo> contactCell;
  private final DefaultSelectionEventManager<ContactInfo> selectionManager =
      DefaultSelectionEventManager.createDefaultManager();
  private final SelectionModel<ContactInfo> selectionModel;

  public ContactTreeViewModel(final SelectionModel<ContactInfo> selectionModel) {
    this.selectionModel = selectionModel;
    if (images == null) {
      images = GWT.create(Images.class);
    }

    // Create a data provider that provides categories.
    categoryDataProvider = new ListDataProvider<Category>();
    List<Category> categoryList = categoryDataProvider.getList();
    for (Category category : ContactDatabase.get().queryCategories()) {
      categoryList.add(category);
    }

    // Construct a composite cell for contacts that includes a checkbox.
    List<HasCell<ContactInfo, ?>> hasCells = new ArrayList<HasCell<ContactInfo, ?>>();
    hasCells.add(new HasCell<ContactInfo, ContactInfo>() {

      private ContactCell cell = new ContactCell();

      public Cell<ContactInfo> getCell() {
        return cell;
      }

      public FieldUpdater<ContactInfo, ContactInfo> getFieldUpdater() {
        return null;
      }

      public ContactInfo getValue(ContactInfo object) {
        return object;
      }
    });

    contactCell = new CompositeCell<ContactInfo>(hasCells) {
      @Override
      public void render(Context context, ContactInfo value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<table style='width:100%'><tbody><tr>");
        super.render(context, value, sb);
        sb.appendHtmlConstant("</tr></tbody></table>");
        
      }

      @Override
      protected Element getContainerElement(Element parent) {
        // Return the first TR element in the table.
        return parent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
      }

      @Override
      protected <X> void render(Context context, ContactInfo value,
          SafeHtmlBuilder sb, HasCell<ContactInfo, X> hasCell) {
        Cell<X> cell = hasCell.getCell();
        sb.appendHtmlConstant("<td>");
        cell.render(context, hasCell.getValue(value), sb);
        sb.appendHtmlConstant("</td>");
        
      }
    };
  }

  public <T> NodeInfo<?> getNodeInfo(T value) {
    if (value == null) {
      // Return top level categories.
      return new DefaultNodeInfo<Category>(categoryDataProvider,
          new CategoryCell());
    } else if (value instanceof Category) {
      // Return the first letters of each first name.
      Category category = (Category) value;
      List<ContactInfo> contacts = ContactDatabase.get().queryContactsByCategory(
          category);
      Map<Character, LetterCount> counts = new TreeMap<Character, LetterCount>();
      for (ContactInfo contact : contacts) {
        Character first = contact.getFirstName().charAt(0);
        LetterCount count = counts.get(first);
        if (count == null) {
          count = new LetterCount(category, first);
          counts.put(first, count);
        } else {
          count.increment();
        }
      }
      List<LetterCount> orderedCounts = new ArrayList<LetterCount>(
          counts.values());
      return new DefaultNodeInfo<LetterCount>(
          new ListDataProvider<LetterCount>(orderedCounts),
          new LetterCountCell());
    } else if (value instanceof LetterCount) {
      // Return the contacts with the specified character and first name.
      LetterCount count = (LetterCount) value;
      List<ContactInfo> contacts = ContactDatabase.get().queryContactsByCategoryAndFirstName(
          count.category, count.firstLetter + "");
      ListDataProvider<ContactInfo> dataProvider = new ListDataProvider<ContactInfo>(
          contacts, ContactInfo.KEY_PROVIDER);
      return new DefaultNodeInfo<ContactInfo>(
          dataProvider, contactCell, selectionModel, selectionManager, null);
    }

    // Unhandled type.
    String type = value.getClass().getName();
    throw new IllegalArgumentException("Unsupported object type: " + type);
  }

  public boolean isLeaf(Object value) {
    return value instanceof ContactInfo;
  }
}